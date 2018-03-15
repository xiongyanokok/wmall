package com.xy.wmall.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * BaseService 接口
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:08
 */
public interface BaseService<T> {

    /**
     * 保存数据
     *
     * @param t
     */
    void save(T t);

    /**
     * 修改数据
     *
     * @param t
     */
    void update(T t);
    
    /**
	 * 删除数据
	 * 
	 * @param t
	 */
	void remove(T t);
	
	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    T getById(Serializable id);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    T getByMap(Map<String, Object> map);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    List<T> listByMap(Map<String, Object> map);
    
    /**
     * 批量保存
     * 
     * @param list
     */
    void batchSave(List<T> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<T> list);
    
}
