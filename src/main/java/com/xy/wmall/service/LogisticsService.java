package com.xy.wmall.service;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.Logistics;

/**
 * Service 接口
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:06
 */
public interface LogisticsService {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    Logistics selectByPrimaryKey(Integer id);
    
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Logistics getLogisticsById(Integer id);
    
    /**
     * 保存数据
     *
     * @param logistics
     */
    void save(Logistics logistics);

    /**
     * 修改数据
     *
     * @param logistics
     */
    void update(Logistics logistics);
    
    /**
     * 删除数据
     * 
     * @param logistics
     */
    void remove(Logistics logistics);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    Logistics getLogistics(Map<String, Object> map);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    List<Logistics> listLogistics(Map<String, Object> map);
    
    /**
     * 批量保存
     * 
     * @param list
     */
    void batchSave(List<Logistics> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<Logistics> list);
    
}
