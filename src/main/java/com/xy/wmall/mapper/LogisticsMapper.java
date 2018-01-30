package com.xy.wmall.mapper;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.Logistics;

/**
 * Mapper
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:06
 */
public interface LogisticsMapper {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    Logistics selectByPrimaryKey(Integer id);

    /**
     * 新增数据库记录
     *
     * @param logistics
     */
    void insert(Logistics logistics);

    /**
     * 更新数据库记录
     *
     * @param logistics
     */
    void update(Logistics logistics);

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
    void batchInsert(List<Logistics> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<Logistics> list);
    
}
