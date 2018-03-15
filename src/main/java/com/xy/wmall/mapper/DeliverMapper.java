package com.xy.wmall.mapper;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.Deliver;
import com.xy.wmall.pojo.Statistics;

/**
 * Mapper
 * 
 * @author admin
 * @date 2017年10月28日 上午08:53:59
 */
public interface DeliverMapper extends BaseMapper<Deliver> {

    /**
     * 发货统计
     * 
     * @param map
     * @return
     */
    List<Statistics> deliverStatistics(Map<String, Object> map);
 
    /**
     * 待发货数量
     * 
     * @param map
     * @return
     */
    Integer countWaitDeliver(Map<String, Object> map);
    
    /**
     * 批量对货
     * 
     * @param map
     */
    void batchInventory(Map<String, Object> map);
    
}
