package com.xy.wmall.service;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.DeliverDetail;

/**
 * Service 接口
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:03
 */
public interface DeliverDetailService {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    DeliverDetail selectByPrimaryKey(Integer id);
    
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    DeliverDetail getDeliverDetailById(Integer id);
    
    /**
     * 保存数据
     *
     * @param deliverDetail
     */
    void save(DeliverDetail deliverDetail);

    /**
     * 修改数据
     *
     * @param deliverDetail
     */
    void update(DeliverDetail deliverDetail);
    
    /**
     * 删除数据
     * 
     * @param deliverDetail
     */
    void remove(DeliverDetail deliverDetail);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    DeliverDetail getDeliverDetail(Map<String, Object> map);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    List<DeliverDetail> listDeliverDetail(Map<String, Object> map);
    
    /**
     * 批量保存
     * 
     * @param list
     */
    void batchSave(List<DeliverDetail> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<DeliverDetail> list);
    
}
