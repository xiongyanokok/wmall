package com.xy.wmall.mapper;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.ProxyLevel;

/**
 * Mapper
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:24
 */
public interface ProxyLevelMapper {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    ProxyLevel selectByPrimaryKey(Integer id);

    /**
     * 新增数据库记录
     *
     * @param proxyLevel
     */
    void insert(ProxyLevel proxyLevel);

    /**
     * 更新数据库记录
     *
     * @param proxyLevel
     */
    void update(ProxyLevel proxyLevel);

    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    ProxyLevel getProxyLevel(Map<String, Object> map);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    List<ProxyLevel> listProxyLevel(Map<String, Object> map);
    
    /**
     * 批量保存
     * 
     * @param list
     */
    void batchInsert(List<ProxyLevel> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<ProxyLevel> list);
    
}
