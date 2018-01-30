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
public interface OrderMapper {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    Order selectByPrimaryKey(Integer id);

    /**
     * 新增数据库记录
     *
     * @param order
     */
    void insert(Order order);

    /**
     * 更新数据库记录
     *
     * @param order
     */
    void update(Order order);

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
    void batchInsert(List<Order> list);
    
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
    
    /**
     * 产品进货价格
     * 
     * @param map
     * @return
     */
    List<Statistics> purchasePrice(Map<String, Object> map);
    
}
