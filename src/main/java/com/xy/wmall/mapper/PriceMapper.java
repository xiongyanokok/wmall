package com.xy.wmall.mapper;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.Price;

/**
 * Mapper
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:16
 */
public interface PriceMapper {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    Price selectByPrimaryKey(Integer id);

    /**
     * 新增数据库记录
     *
     * @param price
     */
    void insert(Price price);

    /**
     * 更新数据库记录
     *
     * @param price
     */
    void update(Price price);

    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    Price getPrice(Map<String, Object> map);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    List<Price> listPrice(Map<String, Object> map);
    
    /**
     * 批量保存
     * 
     * @param list
     */
    void batchInsert(List<Price> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<Price> list);
    
}
