package com.xy.wmall.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.wmall.common.Assert;
import com.xy.wmall.common.utils.DateUtils;
import com.xy.wmall.common.utils.JacksonUtils;
import com.xy.wmall.enums.OrderTypeEnum;
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.model.Order;
import com.xy.wmall.model.OrderDetail;
import com.xy.wmall.model.Product;
import com.xy.wmall.service.OrderDetailService;
import com.xy.wmall.service.OrderService;
import com.xy.wmall.service.ProductService;
import com.xy.wmall.service.WalletService;

/**
 * Controller
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:11
 */
@Controller
@RequestMapping(value = "/admin/purchase", produces = { "application/json; charset=UTF-8" })
public class PurchaseController extends BaseController {

	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);

    @Autowired
	private OrderService orderService;
    
    @Autowired
    private OrderDetailService orderDetailService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private WalletService walletService;
	
	/**
	 * 进入进货列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model) {
		List<Product> products = productService.listProduct();
		model.addAttribute("products", products);
		model.addAttribute("productsJson", JacksonUtils.serialize(products));
		model.addAttribute("years", DateUtils.listYear());
		model.addAttribute("months", DateUtils.listMonth());
		return "purchase/list";
	}
	
	/**
	 * 进货列表分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/query", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> query() {
		return pageInfoResult(map -> {
			// 查询条件
			// 上级代理ID
			map.put("parentProxyId", getParentProxyId()); 
			// 产品id
			map.put("productId", request.getParameter("productId")); 
			// 自然月
			map.put("natureMonth", request.getParameter("natureMonth"));
			// 订单id
			map.put("groupBy", "id");
			// 查询进货单
			List<Order> orders = orderService.listOrder(map);
			if (CollectionUtils.isEmpty(orders)) {
				return Collections.emptyList();
			}
			
			// 获取订单id
			List<Integer> orderIds = new ArrayList<>(orders.size());
			for (Order order : orders) {
				orderIds.add(order.getId());
			}
			// 查询订单详情
			Map<String, Object> dMap = new HashMap<>();
			dMap.put("orderIds", orderIds);
			List<OrderDetail> orderDetails = orderDetailService.listOrderDetail(dMap);
			for (Order order : orders) {
				List<OrderDetail> details = new ArrayList<>();
				for (OrderDetail orderDetail : orderDetails) {
					if (order.getId().equals(orderDetail.getOrderId())) {
						details.add(orderDetail);
					}
				}
				order.setOrderDetails(details);
			}
			return orders;
		});
	}
	
	/**
	 * 进入自己进货页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET })
	public String add(Model model) {
		model.addAttribute("proxyId", getProxyId());
		List<Product> products = productService.listProduct();
		model.addAttribute("products", products);
		model.addAttribute("years", DateUtils.listYear());
		model.addAttribute("months", DateUtils.listMonth());
		model.addAttribute("currentYear", DateUtils.currentYear());
		model.addAttribute("currentMonth", DateUtils.currentMonth());
		// 查询代理钱包余额
		model.addAttribute("balance", walletService.getWalletBalance(getProxyId()));
		return "purchase/add";
	}
	
	/**
	 * 保存自己进货数据
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> save(Order order) {
		Assert.notNull(order, "保存数据为空");
		order.setParentProxyId(getParentProxyId());
		order.setOrderType(OrderTypeEnum.PROXY_ORDER.getValue());
		if (null != order.getIsAccumulate() && order.getIsAccumulate()) {
			order.setNatureMonth(order.getYear() + "-" + order.getMonth());
		} else {
			order.setNatureMonth(DateUtils.natureMonth());
		}
		order.setCreateUserId(getUserId());
		order.setCreateTime(new Date());
		order.setUpdateUserId(getUserId());
		order.setUpdateTime(new Date());
		order.setIsDelete(TrueFalseStatusEnum.FALSE.getValue());
		orderService.save(order);
		logger.info("【{}】保存成功", order);
		return buildSuccess("保存成功");
	}
	
	/**
	 * 进入自己进货修改页面
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.GET })
	public String edit(Model model, Integer id) {
		Assert.notNull(id, "id为空");
		Order order = orderService.getOrderById(id);
		Assert.notNull(order, "数据不存在");
		model.addAttribute("order", order);
		// 查询订单详情
		Map<String, Object> map = new HashMap<>();
		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
		map.put("orderId", id);
		List<OrderDetail> orderDetails = orderDetailService.listOrderDetail(map);
		model.addAttribute("orderDetails", orderDetails);
		List<Product> products = productService.listProduct();
		model.addAttribute("products", products);
		model.addAttribute("years", DateUtils.listYear());
		model.addAttribute("months", DateUtils.listMonth());
		model.addAttribute("currentYear", order.getNatureMonth().split("-")[0]);
		model.addAttribute("currentMonth", order.getNatureMonth().split("-")[1]);
		// 查询代理钱包余额
		model.addAttribute("balance", walletService.getWalletBalance(order.getProxyId()));
		return "purchase/edit";
	}
	
	/**
	 * 修改自己进货数据
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> update(Order order) {
		Assert.notNull(order, "修改数据为空");
		Order orderInfo = orderService.getOrderById(order.getId());
		Assert.notNull(orderInfo, "数据不存在");
		order.setProxyId(orderInfo.getProxyId());
		order.setUpdateUserId(getUserId());
		order.setUpdateTime(new Date());
		orderService.update(order);
		logger.info("【{}】修改成功", order);
		return buildSuccess("修改成功");
	}
	
}
