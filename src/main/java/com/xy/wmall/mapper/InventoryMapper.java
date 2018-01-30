package com.xy.wmall.mapper;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.Inventory;

/**
 * Mapper
 * 
 * @author admin
 * @date 2017年11月20日 下午10:31:44
 */
public interface InventoryMapper {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    Inventory selectByPrimaryKey(Integer id);

    /**
     * 新增数据库记录
     *
     * @param inventory
     */
    void insert(Inventory inventory);

    /**
     * 更新数据库记录
     *
     * @param inventory
     */
    void update(Inventory inventory);

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
    void batchInsert(List<Inventory> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<Inventory> list);
    
}
