package com.xy.wmall.service;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.OrderDetail;

/**
 * Service 接口
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:14
 */
public interface OrderDetailService {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    OrderDetail selectByPrimaryKey(Integer id);
    
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    OrderDetail getOrderDetailById(Integer id);
    
    /**
     * 保存数据
     *
     * @param orderDetail
     */
    void save(OrderDetail orderDetail);

    /**
     * 修改数据
     *
     * @param orderDetail
     */
    void update(OrderDetail orderDetail);
    
    /**
     * 删除数据
     * 
     * @param orderDetail
     */
    void remove(OrderDetail orderDetail);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    OrderDetail getOrderDetail(Map<String, Object> map);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    List<OrderDetail> listOrderDetail(Map<String, Object> map);
    
    /**
     * 批量保存
     * 
     * @param list
     */
    void batchSave(List<OrderDetail> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<OrderDetail> list);
    
}
