package com.xy.wmall.service;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.Inventory;

/**
 * Service 接口
 * 
 * @author admin
 * @date 2017年11月20日 下午10:31:44
 */
public interface InventoryService {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    Inventory selectByPrimaryKey(Integer id);
    
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Inventory getInventoryById(Integer id);
    
    /**
     * 保存数据
     *
     * @param inventory
     */
    void save(Inventory inventory);

    /**
     * 修改数据
     *
     * @param inventory
     */
    void update(Inventory inventory);
    
    /**
     * 删除数据
     * 
     * @param inventory
     */
    void remove(Inventory inventory);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    Inventory getInventory(Map<String, Object> map);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    List<Inventory> listInventory(Map<String, Object> map);
    
    /**
     * 批量保存
     * 
     * @param list
     */
    void batchSave(List<Inventory> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<Inventory> list);
    
}
