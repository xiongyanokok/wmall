package com.xy.wmall.mapper;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.Proxy;

/**
 * Mapper
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:21
 */
public interface ProxyMapper {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    Proxy selectByPrimaryKey(Integer id);

    /**
     * 新增数据库记录
     *
     * @param proxy
     */
    void insert(Proxy proxy);

    /**
     * 更新数据库记录
     *
     * @param proxy
     */
    void update(Proxy proxy);

    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    Proxy getProxy(Map<String, Object> map);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    List<Proxy> listProxy(Map<String, Object> map);
    
    /**
     * 批量保存
     * 
     * @param list
     */
    void batchInsert(List<Proxy> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<Proxy> list);
    
}
