package com.xy.wmall.controller;

import java.util.Arrays;
import java.util.Collections;
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

import com.xy.wmall.common.utils.BeanUtils;
import com.xy.wmall.common.utils.DateUtils;
import com.xy.wmall.common.utils.JacksonUtils;
import com.xy.wmall.enums.DeliverTypeEnum;
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.model.Product;
import com.xy.wmall.pojo.Statistics;
import com.xy.wmall.service.DeliverService;
import com.xy.wmall.service.OrderService;
import com.xy.wmall.service.ProductService;

/**
 * Controller
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:21
 */
@Controller
@RequestMapping(value = "/admin/statistics", produces = { "application/json; charset=UTF-8" })
public class StatisticsController extends BaseController {

    @Autowired
    private ProductService productService;
    
    @Autowired
	private OrderService orderService;
    
    @Autowired
	private DeliverService deliverService;
    

	/**
	 * 进入产品统计页面
	 * 
	 * @param model
	 * @param proxyId
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET})
	public String list(Model model) {
		List<Product> products = productService.listProduct();
		model.addAttribute("productsJson", JacksonUtils.serialize(products));
		model.addAttribute("years", DateUtils.listYear());
		model.addAttribute("months", DateUtils.listMonth());
		return "statistics/list";
	}
	
	/**
	 * 查询产品统计
	 * 
	 * @return
	 */
	@RequestMapping(value = "/query", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> query() {
		Map<String, Object> map = new HashMap<>();
		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
		map.put("natureMonth", request.getParameter("natureMonth"));
		map.put("parentProxyId", getProxyId());
		// 订单统计
		List<Statistics> orderStatistics = orderService.orderStatistics(map);
		// 进货统计
		map.put("parentProxyId", getParentProxyId());
		List<Statistics> purchaseStatistics = orderService.purchaseStatistics(map);
		// 合并
		List<Statistics> list = BeanUtils.merge("productId", orderStatistics, purchaseStatistics);
		// 发货到家统计
		Map<Integer, Integer> deliverHomeStatisticsMap = new HashMap<>();
		map.put("deliverStatus", TrueFalseStatusEnum.TRUE.getValue());
		List<Statistics> deliverHomeStatistics = deliverService.deliverStatistics(map);
		if (CollectionUtils.isNotEmpty(deliverHomeStatistics)) {
			for (Statistics statistics : deliverHomeStatistics) {
				deliverHomeStatisticsMap.put(statistics.getProductId(), statistics.getDeliverNumber());
			}
		}
		// 老大发货统计
		Map<Integer, Integer> deliverStatisticsMap = new HashMap<>();
		map.remove("parentProxyId");
		map.put("deliverTypes", Arrays.asList(DeliverTypeEnum.SUPER_DELIVER.getValue(), DeliverTypeEnum.FACTORY_DELIVER.getValue()));
		List<Statistics> deliverStatistics = deliverService.deliverStatistics(map);
		if (CollectionUtils.isNotEmpty(deliverStatistics)) {
			for (Statistics statistics : deliverStatistics) {
				deliverStatisticsMap.put(statistics.getProductId(), statistics.getDeliverNumber());
			}
		}
		// 我的发货统计
		Map<Integer, Integer> myDeliverStatisticsMap = new HashMap<>();
		map.remove("deliverTypes");
		map.put("deliverType", DeliverTypeEnum.SELF_DELIVER.getValue());
		List<Statistics> myDeliverStatistics = deliverService.deliverStatistics(map);
		if (CollectionUtils.isNotEmpty(myDeliverStatistics)) {
			for (Statistics statistics : myDeliverStatistics) {
				myDeliverStatisticsMap.put(statistics.getProductId(), statistics.getDeliverNumber());
			}
		}
		
		for (Statistics statistics : list) {
			statistics.setDeliverNumber(deliverStatisticsMap.get(statistics.getProductId()));
			statistics.setMyDeliverNumber(myDeliverStatisticsMap.get(statistics.getProductId()));
			statistics.setDeliverHomeNumber(deliverHomeStatisticsMap.get(statistics.getProductId()));
		}
		
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
