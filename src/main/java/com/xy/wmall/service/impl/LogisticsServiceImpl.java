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
import com.xy.wmall.mapper.LogisticsMapper;
import com.xy.wmall.model.Logistics;
import com.xy.wmall.service.LogisticsService;

/**
 * Service 实现
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:06
 */
@Service
public class LogisticsServiceImpl implements LogisticsService {

    @Autowired
	private LogisticsMapper logisticsMapper;
	
	/**
     * 根据主键查询
     *
     * @param id
     * @return
     * @throws WmallException
     */
    @Override
    public Logistics selectByPrimaryKey(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
	    	return logisticsMapper.selectByPrimaryKey(id);
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
    public Logistics getLogisticsById(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
    		Map<String, Object> map = new HashMap<>();
    		map.put("id", id);
    		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
	    	return logisticsMapper.getLogistics(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + id + "】查询失败", e);
		}
    }
    
	/**
     * 保存数据
     *
     * @param logistics
     * @throws WmallException
     */
    @Override
    public void save(Logistics logistics) {
    	Assert.notNull(logistics, "保存数据为空");
    	try {
			logisticsMapper.insert(logistics);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_INSERT_ERROR, "【" + logistics.toString() + "】保存失败", e);
		}
    }

    /**
     * 修改数据
     *
     * @param logistics
     * @throws WmallException
     */
    @Override
    public void update(Logistics logistics) {
    	Assert.notNull(logistics, "修改数据为空");
    	try {
    		logisticsMapper.update(logistics);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_UPDATE_ERROR, "【" + logistics.toString() + "】修改失败", e);
		}
    }
    
    /**
     * 删除数据
     * 
     * @param logistics
     * @throws WmallException
     */
    @Override
    public void remove(Logistics logistics) {
    	Assert.notNull(logistics, "删除数据为空");
		try {
    		Logistics deleteLogistics = new Logistics();
    		deleteLogistics.setId(logistics.getId());
    		deleteLogistics.setIsDelete(TrueFalseStatusEnum.TRUE.getValue());
    		logisticsMapper.update(deleteLogistics);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_DELETE_ERROR, "【" + logistics.toString() + "】删除失败", e);
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
    public Logistics getLogistics(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return logisticsMapper.getLogistics(map);
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
    public List<Logistics> listLogistics(Map<String, Object> map) {
   	 	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return logisticsMapper.listLogistics(map);
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
    public void batchSave(List<Logistics> list) {
    	Assert.notEmpty(list, "批量保存数据为空");
    	try {
			List<List<Logistics>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<Logistics> page : pageList) {
				logisticsMapper.batchInsert(page);
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
    public void batchUpdate(List<Logistics> list) {
    	Assert.notEmpty(list, "批量修改数据为空");
    	try {
			List<List<Logistics>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<Logistics> page : pageList) {
				logisticsMapper.batchUpdate(page);
			}
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "批量修改失败", e);
		}
    }
    
}
