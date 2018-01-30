package com.xy.wmall.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.wmall.common.Assert;
import com.xy.wmall.common.utils.ListPageUtils;
import com.xy.wmall.enums.ErrorCodeEnum;
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.exception.WmallException;
import com.xy.wmall.mapper.InventoryMapper;
import com.xy.wmall.model.Inventory;
import com.xy.wmall.service.InventoryService;

/**
 * Service 实现
 * 
 * @author admin
 * @date 2017年11月20日 下午10:31:44
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
	private InventoryMapper inventoryMapper;
	
	/**
     * 根据主键查询
     *
     * @param id
     * @return
     * @throws WmallException
     */
    @Override
    public Inventory selectByPrimaryKey(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
	    	return inventoryMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + id + "】查询失败", e);
		}
    }
    
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     * @throws WmallException
     */
    @Override
    public Inventory getInventoryById(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
    		Map<String, Object> map = new HashMap<>();
    		map.put("id", id);
    		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
	    	return inventoryMapper.getInventory(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + id + "】查询失败", e);
		}
    }
    
	/**
     * 保存数据
     *
     * @param inventory
     * @throws WmallException
     */
    @Override
    public void save(Inventory inventory) {
    	Assert.notNull(inventory, "保存数据为空");
    	try {
			inventoryMapper.insert(inventory);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_INSERT_ERROR, "【" + inventory.toString() + "】保存失败", e);
		}
    }

    /**
     * 修改数据
     *
     * @param inventory
     * @throws WmallException
     */
    @Override
    public void update(Inventory inventory) {
    	Assert.notNull(inventory, "修改数据为空");
    	try {
    		inventoryMapper.update(inventory);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_UPDATE_ERROR, "【" + inventory.toString() + "】修改失败", e);
		}
    }
    
    /**
     * 删除数据
     * 
     * @param inventory
     * @throws WmallException
     */
    @Override
    public void remove(Inventory inventory) {
    	Assert.notNull(inventory, "删除数据为空");
		try {
    		Inventory deleteInventory = new Inventory();
    		deleteInventory.setId(inventory.getId());
    		deleteInventory.setIsDelete(TrueFalseStatusEnum.TRUE.getValue());
    		inventoryMapper.update(deleteInventory);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_DELETE_ERROR, "【" + inventory.toString() + "】删除失败", e);
    	}
    }
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     * @throws WmallException
     */
    @Override
    public Inventory getInventory(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return inventoryMapper.getInventory(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + map + "】查询对象失败", e);
		}
    }
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     * @throws WmallException
     */
    @Override
    public List<Inventory> listInventory(Map<String, Object> map) {
   	 	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return inventoryMapper.listInventory(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + map + "】查询列表失败", e);
		}
    }
    
    /**
     * 批量保存
     * 
     * @param list
     * @throws WmallException
     */
    @Override
    public void batchSave(List<Inventory> list) {
    	Assert.notEmpty(list, "批量保存数据为空");
    	try {
			List<List<Inventory>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<Inventory> page : pageList) {
				inventoryMapper.batchInsert(page);
			}
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "批量保存失败", e);
		}
    }
    
    /**
     * 批量更新
     * 
     * @param list
     * @throws WmallException
     */
    @Override
    public void batchUpdate(List<Inventory> list) {
    	Assert.notEmpty(list, "批量修改数据为空");
    	try {
			List<List<Inventory>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<Inventory> page : pageList) {
				inventoryMapper.batchUpdate(page);
			}
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "批量修改失败", e);
		}
    }
    
}
