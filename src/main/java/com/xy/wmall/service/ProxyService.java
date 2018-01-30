package com.xy.wmall.service;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.Proxy;

/**
 * Service 接口
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:21
 */
public interface ProxyService {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    Proxy selectByPrimaryKey(Integer id);
    
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Proxy getProxyById(Integer id);
    
    /**
     * 保存数据
     *
     * @param proxy
     */
    void save(Proxy proxy);

    /**
     * 修改数据
     *
     * @param proxy
     */
    void update(Proxy proxy);
    
    /**
     * 删除数据
     * 
     * @param proxy
     */
    void remove(Proxy proxy);
    
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
    void batchSave(List<Proxy> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<Proxy> list);
    
}
