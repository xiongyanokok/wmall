package com.xy.wmall.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.wmall.common.Assert;
import com.xy.wmall.common.WmallCache;
import com.xy.wmall.common.utils.DateUtils;
import com.xy.wmall.enums.OrderStatusEnum;
import com.xy.wmall.enums.OrderTypeEnum;
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.model.Order;
import com.xy.wmall.model.OrderDetail;
import com.xy.wmall.model.Product;
import com.xy.wmall.model.Proxy;
import com.xy.wmall.pojo.Statistics;
import com.xy.wmall.service.OrderDetailService;
import com.xy.wmall.service.OrderService;
import com.xy.wmall.service.ProductService;
import com.xy.wmall.service.ProxyService;
import com.xy.wmall.service.WalletService;

/**
 * Controller
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:11
 */
@Controller
@RequestMapping(value = "/admin/order", produces = { "application/json; charset=UTF-8" })
public class OrderController extends BaseController {

	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
	private OrderService orderService;
    
    @Autowired
    private ProxyService proxyService;
    
    @Autowired
    private OrderDetailService orderDetailService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private WalletService walletService;
    
	/**
	 * 进入列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model) {
		List<Product> products = productService.listProduct();
		model.addAttribute("products", products);
		model.addAttribute("years", DateUtils.listYear());
		model.addAttribute("months", DateUtils.listMonth());
		return "order/list";
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
			if (StringUtils.isNotEmpty(proxyId)) {
				// 代理ID
				map.put("proxyId", proxyId); 
			} else {
				// 上级代理ID
				map.put("parentProxyId", getProxyId()); 
			}
			// 产品id
			map.put("productId", request.getParameter("productId")); 
			// 微信昵称
			map.put("wechatName", request.getParameter("wechatName"));
			// 订单类型
			map.put("orderType", request.getParameter("orderType")); 
			// 是否累计
			map.put("isAccumulate", request.getParameter("isAccumulate"));
			// 自然月
			map.put("natureMonth", request.getParameter("natureMonth"));
			// 订单id
			map.put("groupBy", "id"); 
			// 查询订单
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
			Map<String, Object> detailMap = new HashMap<>();
			detailMap.put("orderIds", orderIds);
			List<OrderDetail> orderDetails = orderDetailService.listOrderDetail(detailMap);
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
	 * 计算价格
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/computePrice", method = {RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> computePrice(Order order) {
			Assert.notNull(order, "计算价格数据为空");
			
			// 产品id
			Integer productId = order.getProductId()[0];
			// 数量
			Integer amount = order.getAmount()[0];
			// 产品价格
			Map<String, Object> productPriceMap = new HashMap<>();
			if (null != order.getOrderType() && OrderTypeEnum.RETAIL_ORDER.getValue().equals(order.getOrderType())) {
				// 零售订单
				BigDecimal unitPrice = WmallCache.getRetailPrice(productId);
				Assert.notNull(unitPrice, "无产品价格");
				productPriceMap.put("unitPrice", unitPrice);
				productPriceMap.put("totalPrice", unitPrice.multiply(new BigDecimal(amount)).intValue());
			} else {
				// 代理订单
				Integer orderPrice = 0;
				if (null != order.getIsAccumulate() && order.getIsAccumulate()) {
					// 累计（查询自然月的产品总额）
					Map<String, Object> map = new HashMap<>();
					map.put("productId", productId);
					map.put("proxyId", order.getProxyId());
					map.put("orderType", OrderTypeEnum.PROXY_ORDER.getValue());
					map.put("isAccumulate", order.getIsAccumulate());
					map.put("natureMonth", order.getNatureMonth());
					map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
					List<Statistics> list = orderService.orderStatistics(map);
					if (CollectionUtils.isNotEmpty(list)) {
						Statistics statistics = list.get(0);
						amount += statistics.getOrderNumber();
						orderPrice = statistics.getOrderPrice();
					}
				}
				BigDecimal unitPrice = WmallCache.getProxyPrice(productId, amount);
				Assert.notNull(unitPrice, "无产品价格");
				productPriceMap.put("unitPrice", unitPrice);
				productPriceMap.put("totalPrice", unitPrice.multiply(new BigDecimal(amount)).intValue() - orderPrice);
			}
			Assert.notEmpty(productPriceMap, "无产品价格");
			return buildSuccess(productPriceMap);
	}
	
	/**
	 * 进入零售订单页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/retail_add", method = { RequestMethod.GET })
	public String retailAdd(Model model) {
		List<Product> products = productService.listProduct();
		model.addAttribute("products", products);
		return "order/retail_add";
	}
	
	/**
	 * 进入新增页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET })
	public String add(Model model, Integer proxyId) {
		Assert.notNull(proxyId, "proxyId为空");
		Proxy proxy = proxyService.getProxyById(proxyId);
		Assert.notNull(proxy, "代理不存在");
		model.addAttribute("proxyId", proxyId);
		List<Product> products = productService.listProduct();
		model.addAttribute("products", products);
		model.addAttribute("years", DateUtils.listYear());
		model.addAttribute("months", DateUtils.listMonth());
		model.addAttribute("currentYear", DateUtils.currentYear());
		model.addAttribute("currentMonth", DateUtils.currentMonth());
		// 查询代理钱包余额
		model.addAttribute("balance", walletService.getWalletBalance(proxyId));
		return "order/add";
	}
	
	/**
	 * 保存数据
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> save(Order order) {
		Assert.notNull(order, "保存数据为空");
		// 上级代理id
		order.setParentProxyId(getProxyId());
		if (null != order.getProxyId()) {
			// 代理订单
			order.setOrderType(OrderTypeEnum.PROXY_ORDER.getValue()); 
		} else {
			// 零售订单
			order.setProxyId(getProxyId());
			order.setIsAccumulate(TrueFalseStatusEnum.FALSE.getValue());
			order.setOrderType(OrderTypeEnum.RETAIL_ORDER.getValue()); 
		}
		// 自然月
		if (null != order.getIsAccumulate() && order.getIsAccumulate()) {
			order.setNatureMonth(order.getYear() + "-" + order.getMonth());
		} else {
			order.setNatureMonth(DateUtils.natureMonth());
		}
		// 默认已完成
		order.setOrderStatus(OrderStatusEnum.ORDER_SUCCESS.getValue());
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
	 * 进入修改页面
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
		if (OrderTypeEnum.PROXY_ORDER.getValue().equals(order.getOrderType())) {
			// 查询代理钱包余额
			model.addAttribute("balance", walletService.getWalletBalance(order.getProxyId()));
		}
		return "order/edit";
	}
	
	/**
	 * 修改数据
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
		Order order = orderService.getOrderById(id);
		Assert.notNull(order, "数据不存在");
		orderService.remove(order);
		logger.info("【{}】删除成功", order);
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
		model.addAttribute("currentYear", order.getNatureMonth().split("-")[0]);
		model.addAttribute("currentMonth", order.getNatureMonth().split("-")[1]);
		return "order/detail";
	}
	
	/**
	 * 进入更正订单
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/correct", method = { RequestMethod.GET })
	public String correct(Model model, Integer proxyId) {
		List<Product> products = productService.listProduct();
		model.addAttribute("products", products);
		model.addAttribute("proxyId", proxyId);
		return "order/correct";
	}
	
	/**
	 * 更正订单数据
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/correct_save", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> correctSave(Order order) {
		Assert.notNull(order, "保存数据为空");
		order.setParentProxyId(getProxyId());
		if (order.getProxyId().equals(getProxyId())) {
			order.setParentProxyId(getParentProxyId());
		}
		order.setOrderType(OrderTypeEnum.PROXY_ORDER.getValue()); 
		order.setOrderPrice(0);
		order.setPreferentialPrice(0);
		order.setIsAccumulate(TrueFalseStatusEnum.FALSE.getValue());
		order.setNatureMonth(DateUtils.natureMonth());
		order.setOrderStatus(OrderStatusEnum.ORDER_SUCCESS.getValue());
		order.setCreateUserId(getUserId());
		order.setCreateTime(new Date());
		order.setUpdateUserId(getUserId());
		order.setUpdateTime(new Date());
		order.setIsDelete(TrueFalseStatusEnum.FALSE.getValue());
		orderService.saveCorrect(order);
		logger.info("【{}】更正订单成功", order);
		return buildSuccess("保存成功");
	}
	
}
