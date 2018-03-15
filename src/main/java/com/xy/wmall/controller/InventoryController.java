package com.xy.wmall.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.wmall.common.Assert;
import com.xy.wmall.common.utils.BeanUtils;
import com.xy.wmall.common.utils.CommonUtils;
import com.xy.wmall.common.utils.JacksonUtils;
import com.xy.wmall.enums.DeliverTypeEnum;
import com.xy.wmall.enums.ProductTypeEnum;
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.model.Inventory;
import com.xy.wmall.model.Product;
import com.xy.wmall.model.Proxy;
import com.xy.wmall.pojo.Statistics;
import com.xy.wmall.service.DeliverService;
import com.xy.wmall.service.InventoryService;
import com.xy.wmall.service.OrderService;
import com.xy.wmall.service.ProductService;
import com.xy.wmall.service.ProxyService;
import com.xy.wmall.service.WalletService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller
 * 
 * @author admin
 * @date 2017年11月20日 下午10:31:44
 */
@Controller
@RequestMapping(value = "/admin/inventory", produces = { "application/json; charset=UTF-8" })
@Slf4j
public class InventoryController extends BaseController {

    @Autowired
	private InventoryService inventoryService;
    
    @Autowired
	private ProxyService proxyService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
	private OrderService orderService;
    
    @Autowired
	private DeliverService deliverService;
    
    @Autowired
    private WalletService walletService;
	
	/**
	 * 进入列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model, Integer proxyId) {
		if (null == proxyId) {
			proxyId = getProxyId();
		} else {
			model.addAttribute("proxyId", proxyId);
		}
		Assert.notNull(proxyId, "proxyId为空");
		Proxy proxy = proxyService.getById(proxyId);
		Assert.notNull(proxy, "代理不存在");
		model.addAttribute("proxy", proxy);
		List<Product> products = productService.listProduct();
		model.addAttribute("productsJson", JacksonUtils.serialize(products));
		return "inventory/list";
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
			String proxyId = request.getParameter("proxyId");
			Assert.notNull(proxyId, "proxyId为空");
			// 代理ID
			map.put("proxyId", proxyId); 
			// 对货开始时间
			map.put("startDate", request.getParameter("startDate"));
			// 对货结束时间
			map.put("endDate", request.getParameter("endDate")); 
			return inventoryService.listByMap(map);
		});
	}
	
	/**
	 * 保存数据
	 * 
	 * @param inventory
	 * @return
	 */
	@RequestMapping(value = "/proxy_save", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> proxySave(Integer proxyId) {
		Assert.notNull(proxyId, "proxyId为空");
		Map<String, Object> map = CommonUtils.defaultQueryMap();
		map.put("proxyId", proxyId);
		// 订单统计
		List<Statistics> orderStatistics = orderService.orderStatistics(map);
		// 发货统计
		map.put("deliverStatus", TrueFalseStatusEnum.TRUE.getValue());
		List<Statistics> deliverStatistics = deliverService.deliverStatistics(map);
		// 合并
		List<Statistics> list = BeanUtils.merge("productId", orderStatistics, deliverStatistics);
		if (CollectionUtils.isEmpty(list)) {
			return buildFail("无交易记录");
		}
		
		Collections.sort(list);
		
		Map<Integer, Integer> productMap = new HashMap<>();
		List<Product> products = productService.listProduct();
		for (Product product : products) {
			productMap.put(product.getId(), product.getProductType());
		}
		StringBuilder details = new StringBuilder();
		for (Statistics statistics : list) {
			if (ProductTypeEnum.PROXY_PRODUCT.getValue().equals(productMap.get(statistics.getProductId()))) {
				if (details.length() > 0) {
					details.append("#");
				}
				int orderNumber = null != statistics.getOrderNumber() ? statistics.getOrderNumber() : 0;
				int deliverNumber = null != statistics.getDeliverNumber() ? statistics.getDeliverNumber() : 0;
				details.append(statistics.getProductId());
				details.append("_");
				details.append(orderNumber);
				details.append("_");
				details.append(deliverNumber);
				details.append("_");
				details.append(orderNumber - deliverNumber);
			}
		}
		if (details.length() == 0) {
			return buildFail("无代理产品交易记录");
		}
		Inventory inventory = new Inventory();
		inventory.setProxyId(proxyId);
		inventory.setDetails(details.toString());
		inventory.setBalance(walletService.getWalletBalance(proxyId));
		inventory.setCreateUserId(getUserId());
		inventory.setCreateTime(new Date());
		inventory.setUpdateUserId(getUserId());
		inventory.setUpdateTime(new Date());
		inventory.setIsDelete(TrueFalseStatusEnum.FALSE.getValue());
		inventoryService.save(inventory);
		log.info("【{}】代理对货成功", inventory);
		return buildSuccess("保存成功");
	}
	
	/**
	 * 保存数据
	 * 
	 * @param inventory
	 * @return
	 */
	@RequestMapping(value = "/super_save", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> superSave() {
		Map<String, Object> map = CommonUtils.defaultQueryMap();
		// 进货统计
		map.put("parentProxyId", getParentProxyId());
		List<Statistics> purchaseStatistics = orderService.purchaseStatistics(map);
		// 老大发货统计
		map.remove("parentProxyId");
		map.put("deliverTypes", Arrays.asList(DeliverTypeEnum.SUPER_DELIVER.getValue(), DeliverTypeEnum.FACTORY_DELIVER.getValue()));
		map.put("deliverStatus", TrueFalseStatusEnum.TRUE.getValue());
		List<Statistics> deliverStatistics = deliverService.deliverStatistics(map);
		// 合并
		List<Statistics> list = BeanUtils.merge("productId", purchaseStatistics, deliverStatistics);
		if (CollectionUtils.isEmpty(list)) {
			return buildFail("无交易记录");
		}
		
		Collections.sort(list);
		
		Map<Integer, Integer> productMap = new HashMap<>();
		List<Product> products = productService.listProduct();
		for (Product product : products) {
			productMap.put(product.getId(), product.getProductType());
		}
		StringBuilder details = new StringBuilder();
		for (Statistics statistics : list) {
			if (ProductTypeEnum.PROXY_PRODUCT.getValue().equals(productMap.get(statistics.getProductId()))) {
				if (details.length() > 0) {
					details.append("#");
				}
				int purchaseNumber = null != statistics.getPurchaseNumber() ? statistics.getPurchaseNumber() : 0;
				int deliverNumber = null != statistics.getDeliverNumber() ? statistics.getDeliverNumber() : 0;
				details.append(statistics.getProductId());
				details.append("_");
				details.append(purchaseNumber);
				details.append("_");
				details.append(deliverNumber);
				details.append("_");
				details.append(purchaseNumber - deliverNumber);
			}
		}
		if (details.length() == 0) {
			return buildFail("无代理产品交易记录");
		}
		Inventory inventory = new Inventory();
		inventory.setProxyId(getProxyId());
		inventory.setDetails(details.toString());
		inventory.setBalance(walletService.getWalletBalance(getProxyId()));
		inventory.setCreateUserId(getUserId());
		inventory.setCreateTime(new Date());
		inventory.setUpdateUserId(getUserId());
		inventory.setUpdateTime(new Date());
		inventory.setIsDelete(TrueFalseStatusEnum.FALSE.getValue());
		inventoryService.save(inventory);
		log.info("【{}】老大对货成功", inventory);
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
		Inventory inventory = inventoryService.getById(id);
		Assert.notNull(inventory, "数据不存在");
		model.addAttribute("inventory", inventory);
		return "inventory/edit";
	}
	
	/**
	 * 修改数据
	 * 
	 * @param inventory
	 * @return
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> update(Inventory inventory) {
		Assert.notNull(inventory, "修改数据为空");
		Inventory inventoryInfo = inventoryService.getById(inventory.getId());
		Assert.notNull(inventoryInfo, "数据不存在");
		inventory.setUpdateUserId(getUserId());
		inventory.setUpdateTime(new Date());
		inventoryService.update(inventory);
		log.info("【{}】修改成功", inventory);
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
		Inventory inventory = inventoryService.getById(id);
		Assert.notNull(inventory, "数据不存在");
		inventoryService.remove(inventory);
		log.info("【{}】删除成功", inventory);
		return buildSuccess("删除成功");
	}
	
	/**
	 * 进入详情页面
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/detail", method = { RequestMethod.GET })
	public String detail(Model model, Integer id) {
		Assert.notNull(id, "id为空");
		Inventory inventory = inventoryService.getById(id);
		Assert.notNull(inventory, "数据不存在");
		model.addAttribute("remark", inventory.getRemark());
		List<Statistics> statisticsList = new ArrayList<>();
		String details = inventory.getDetails();
		String[] ps = details.split("#");
		for (int i=0; i<ps.length; i++) {
			String[] pis = ps[i].split("_");
			Statistics statistics = new Statistics();
			statistics.setProductId(Integer.valueOf(pis[0]));
			statistics.setOrderNumber(Integer.valueOf(pis[1]));
			statistics.setDeliverNumber(Integer.valueOf(pis[2]));
			statistics.setSurplusNumber(Integer.valueOf(pis[3]));
			statisticsList.add(statistics);
		}
		model.addAttribute("statisticsList", statisticsList);
		List<Product> products = productService.listProduct();
		model.addAttribute("products", products);
		return "inventory/detail";
	}
	
	/**
	 * 对货
	 * 
	 * @param model
	 * @param proxyId
	 * @return
	 */
	@RequestMapping(value = "/bill", method = {RequestMethod.GET})
	public String bill(Model model, Integer proxyId) {
		List<Product> products = productService.listProduct();
		model.addAttribute("productsJson", JacksonUtils.serialize(products));
		if (null != proxyId) {
			model.addAttribute("proxyId", proxyId);
			return "inventory/proxy_bill";
		} else {
			return "inventory/super_bill";
		}
	}
	
	/**
	 * 代理对货
	 * 
	 * @return
	 */
	@RequestMapping(value = "/proxy_bill", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> proxyBill(Integer proxyId) {
		Assert.notNull(proxyId, "proxyId为空");
		Map<String, Object> map = CommonUtils.defaultQueryMap();
		map.put("proxyId", proxyId);
		// 订单统计
		List<Statistics> orderStatistics = orderService.orderStatistics(map);
		// 发货统计
		map.put("deliverStatus", TrueFalseStatusEnum.TRUE.getValue());
		List<Statistics> deliverStatistics = deliverService.deliverStatistics(map);
		// 合并
		List<Statistics> list = BeanUtils.merge("productId", orderStatistics, deliverStatistics);
		
		if (CollectionUtils.isNotEmpty(list)) {
			String productType = request.getParameter("productType");
			if (StringUtils.isNotEmpty(productType)) {
				Map<Integer, Integer> productMap = new HashMap<>();
				List<Product> products = productService.listProduct();
				for (Product product : products) {
					productMap.put(product.getId(), product.getProductType());
				}
				for (Iterator<Statistics> iter = list.iterator(); iter.hasNext();) {
					Statistics statistics = iter.next();
					if (!Integer.valueOf(productType).equals(productMap.get(statistics.getProductId()))) {
						iter.remove();
					}
				}
			}
			Collections.sort(list);
		}
		return buildData(list);
	}
	
	/**
	 * 老大对货
	 * 
	 * @return
	 */
	@RequestMapping(value = "/super_bill", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> superBill() {
		Map<String, Object> map = CommonUtils.defaultQueryMap();
		// 进货统计
		map.put("parentProxyId", getParentProxyId());
		List<Statistics> purchaseStatistics = orderService.purchaseStatistics(map);
		// 老大发货统计
		map.remove("parentProxyId");
		map.put("deliverTypes", Arrays.asList(DeliverTypeEnum.SUPER_DELIVER.getValue(), DeliverTypeEnum.FACTORY_DELIVER.getValue()));
		map.put("deliverStatus", TrueFalseStatusEnum.TRUE.getValue());
		List<Statistics> deliverStatistics = deliverService.deliverStatistics(map);
		// 合并
		List<Statistics> list = BeanUtils.merge("productId", purchaseStatistics, deliverStatistics);
		
		if (CollectionUtils.isNotEmpty(list)) {
			String productType = request.getParameter("productType");
			if (StringUtils.isNotEmpty(productType)) {
				Map<Integer, Integer> productMap = new HashMap<>();
				List<Product> products = productService.listProduct();
				for (Product product : products) {
					productMap.put(product.getId(), product.getProductType());
				}
				for (Iterator<Statistics> iter = list.iterator(); iter.hasNext();) {
					Statistics statistics = iter.next();
					if (!Integer.valueOf(productType).equals(productMap.get(statistics.getProductId()))) {
						iter.remove();
					}
				}
			}
			Collections.sort(list);
		}
		return buildData(list);
	}
	
}
