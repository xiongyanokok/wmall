package com.xy.wmall.controller;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.wmall.common.Assert;
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.model.Product;
import com.xy.wmall.service.ProductService;

/**
 * Controller
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:19
 */
@Controller
@RequestMapping(value = "/admin/product", produces = { "application/json; charset=UTF-8" })
public class ProductController extends BaseController {

	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
	private ProductService productService;
	
	/**
	 * 进入列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model) {
		return "product/list";
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
			// 产品名称
			map.put("productName", request.getParameter("productName")); 
			return productService.listProduct(map);
		});
	}
	
	/**
	 * 进入新增页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET })
	public String add(Model model) {
		return "product/add";
	}
	
	/**
	 * 保存数据
	 * 
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> save(Product product) {
		Assert.notNull(product, "保存数据为空");
		product.setCreateUserId(getUserId());
		product.setCreateTime(new Date());
		product.setUpdateUserId(getUserId());
		product.setUpdateTime(new Date());
		product.setIsDelete(TrueFalseStatusEnum.FALSE.getValue());
		productService.save(product);
		logger.info("【{}】保存成功", product);
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
		Product product = productService.getProductById(id);
		Assert.notNull(product, "数据不存在");
		model.addAttribute("product", product);
		return "product/edit";
	}
	
	/**
	 * 修改数据
	 * 
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> update(Product product) {
		Assert.notNull(product, "修改数据为空");
		Product productInfo = productService.getProductById(product.getId());
		Assert.notNull(productInfo, "数据不存在");
		product.setUpdateUserId(getUserId());
		product.setUpdateTime(new Date());
		productService.update(product);
		logger.info("【{}】修改成功", product);
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
		Product product = productService.getProductById(id);
		Assert.notNull(product, "数据不存在");
		productService.remove(product);
		logger.info("【{}】删除成功", product);
		return buildSuccess("删除成功");
	}
	
}
