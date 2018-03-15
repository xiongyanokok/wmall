package com.xy.wmall.mapper;

import com.xy.wmall.model.OrderDetail;

/**
 * Mapper
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:14
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

	/**
     * 删除数据库记录
     *
     * @param orderId
     */
    void deleteByOrderId(Integer orderId);
    
}
