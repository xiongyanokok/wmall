package com.xy.wmall.mapper;

import com.xy.wmall.model.DeliverDetail;

/**
 * Mapper
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:03
 */
public interface DeliverDetailMapper extends BaseMapper<DeliverDetail> {

	/**
     * 删除数据库记录
     *
     * @param deliverId
     */
    void deleteByDeliverId(Integer deliverId);
    
}
