package com.xy.wmall.service;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.Order;
import com.xy.wmall.pojo.Statistics;

/**
 * Service 接口
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:11
 */
public interface OrderService extends BaseService<Order> {
	
	/**
     * 保存更正
     * 
     * @param order
     */
    void saveCorrect(Order order);

    /**
     * 订单统计
     * 
     * @param map
     * @return
     */
    List<Statistics> orderStatistics(Map<String, Object> map);
    
    /**
     * 进货统计
     * 
     * @param map
     * @return
     */
    List<Statistics> purchaseStatistics(Map<String, Object> map);
    
}
