package com.xy.wmall.service;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.Deliver;
import com.xy.wmall.pojo.Statistics;

/**
 * Service 接口
 * 
 * @author admin
 * @date 2017年10月28日 上午08:53:59
 */
public interface DeliverService extends BaseService<Deliver> {
	
	/**
	 * 查询待发货单
	 * 
	 * @param map
	 * @return
	 */
	List<Deliver> listWaitDeliver(Map<String, Object> map);
	
	/**
     * 修改发货状态
     * 
     * @param deliver
     */
    void updateDeliverStatus(Deliver deliver);

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
