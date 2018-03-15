package com.xy.wmall.mapper;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.Order;
import com.xy.wmall.pojo.Statistics;

/**
 * Mapper
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:11
 */
public interface OrderMapper extends BaseMapper<Order> {

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
    
    /**
     * 产品进货价格
     * 
     * @param map
     * @return
     */
    List<Statistics> purchasePrice(Map<String, Object> map);
    
}
