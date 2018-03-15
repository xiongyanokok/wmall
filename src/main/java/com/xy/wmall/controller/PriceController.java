package com.xy.wmall.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.wmall.common.Assert;
import com.xy.wmall.common.WmallCache;
import com.xy.wmall.common.utils.JacksonUtils;
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.model.Price;
import com.xy.wmall.model.Product;
import com.xy.wmall.service.PriceService;
import com.xy.wmall.service.ProductService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:16
 */
@Controller
@RequestMapping(value = "/admin/price", produces = { "application/json; charset=UTF-8" })
@Slf4j
public class PriceController extends BaseController {

    @Autowired
	private PriceService priceService;
    
    @Autowired
    private ProductService productService;
	
	/**
	 * 进入列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model, Integer productId) {
		Assert.notNull(productId, "productId为空");
		model.addAttribute("productId", productId);
		List<Product> products = productService.listProduct();
		model.addAttribute("products", products);
		model.addAttribute("productsJson", JacksonUtils.serialize(products));
		return "price/list";
	}
	
	/**
	 * 列表分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/query", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> query() {
		return pageInfoResult(map -> {
			// 查询条件
			// 产品id
			map.put("productId", request.getParameter("productId")); 
			return priceService.listByMap(map);
		});
	}
	
	/**
	 * 进入新增页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET })
	public String add(Model model, Integer productId) {
		Assert.notNull(productId, "productId为空");
		Product product = productService.getById(productId);
		Assert.notNull(product, "产品不存在");
		model.addAttribute("product", product);
		return "price/add";
	}
	
	/**
	 * 保存数据
	 * 
	 * @param price
	 * @return
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> save(Price price) {
		Assert.notNull(price, "保存数据为空");
		price.setCreateUserId(getUserId());
		price.setCreateTime(new Date());
		price.setUpdateUserId(getUserId());
		price.setUpdateTime(new Date());
		price.setIsDelete(TrueFalseStatusEnum.FALSE.getValue());
		priceService.save(price);
		log.info("【{}】保存成功", price);
		// 更新价格缓存
		WmallCache.putPrice(price);
		return buildSuccess("保存成功");
	}
	
	/**
	 * 进入修改页面
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.GET })
	public String edit(Model model, Integer id) {
		Assert.notNull(id, "id为空");
		Price price = priceService.getById(id);
		Assert.notNull(price, "数据不存在");
		Product product = productService.getById(price.getProductId());
		Assert.notNull(product, "产品不存在");
		model.addAttribute("product", product);
		model.addAttribute("price", price);
		return "price/edit";
	}
	
	/**
	 * 修改数据
	 * 
	 * @param price
	 * @return
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> update(Price price) {
		Assert.notNull(price, "修改数据为空");
		Price priceInfo = priceService.getById(price.getId());
		Assert.notNull(priceInfo, "数据不存在");
		price.setUpdateUserId(getUserId());
		price.setUpdateTime(new Date());
		priceService.update(price);
		log.info("【{}】修改成功", price);
		// 更新价格缓存
		if (!price.getAmount().equals(priceInfo.getAmount())) {
			WmallCache.removePrice(priceInfo);
		}
		WmallCache.putPrice(price);
		return buildSuccess("修改成功");
	}
	
	/**
	 * 删除数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> delete(Integer id) {
		Assert.notNull(id, "id为空");
		Price price = priceService.getById(id);
		Assert.notNull(price, "数据不存在");
		priceService.remove(price);
		log.info("【{}】删除成功", price);
		// 删除价格缓存
		WmallCache.removePrice(price);
		return buildSuccess("删除成功");
	}
	
}
