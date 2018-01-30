package com.xy.wmall.service;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.Price;

/**
 * Service 接口
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:16
 */
public interface PriceService {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    Price selectByPrimaryKey(Integer id);
    
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Price getPriceById(Integer id);
    
    /**
     * 保存数据
     *
     * @param price
     */
    void save(Price price);

    /**
     * 修改数据
     *
     * @param price
     */
    void update(Price price);
    
    /**
     * 删除数据
     * 
     * @param price
     */
    void remove(Price price);
    
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
    void batchSave(List<Price> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<Price> list);
    
}
