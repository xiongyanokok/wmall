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
public interface OrderService {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    Order selectByPrimaryKey(Integer id);
    
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Order getOrderById(Integer id);
    
    /**
     * 保存数据
     *
     * @param order
     */
    void save(Order order);
    
    /**
     * 保存更正
     * 
     * @param order
     */
    void saveCorrect(Order order);

    /**
     * 修改数据
     *
     * @param order
     */
    void update(Order order);
    
    /**
     * 删除数据
     * 
     * @param order
     */
    void remove(Order order);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    Order getOrder(Map<String, Object> map);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    List<Order> listOrder(Map<String, Object> map);
    
    /**
     * 批量保存
     * 
     * @param list
     */
    void batchSave(List<Order> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<Order> list);
    
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
