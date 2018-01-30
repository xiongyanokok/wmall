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
import com.xy.wmall.mapper.ProxyLevelMapper;
import com.xy.wmall.model.ProxyLevel;
import com.xy.wmall.service.ProxyLevelService;

/**
 * Service 实现
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:24
 */
@Service
public class ProxyLevelServiceImpl implements ProxyLevelService {

    @Autowired
	private ProxyLevelMapper proxyLevelMapper;
	
	/**
     * 根据主键查询
     *
     * @param id
     * @return
     * @throws WmallException
     */
    @Override
    public ProxyLevel selectByPrimaryKey(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
	    	return proxyLevelMapper.selectByPrimaryKey(id);
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
    public ProxyLevel getProxyLevelById(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
    		Map<String, Object> map = new HashMap<>();
    		map.put("id", id);
    		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
	    	return proxyLevelMapper.getProxyLevel(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + id + "】查询失败", e);
		}
    }
    
	/**
     * 保存数据
     *
     * @param proxyLevel
     * @throws WmallException
     */
    @Override
    public void save(ProxyLevel proxyLevel) {
    	Assert.notNull(proxyLevel, "保存数据为空");
    	try {
			proxyLevelMapper.insert(proxyLevel);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_INSERT_ERROR, "【" + proxyLevel.toString() + "】保存失败", e);
		}
    }

    /**
     * 修改数据
     *
     * @param proxyLevel
     * @throws WmallException
     */
    @Override
    public void update(ProxyLevel proxyLevel) {
    	Assert.notNull(proxyLevel, "修改数据为空");
    	try {
    		proxyLevelMapper.update(proxyLevel);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_UPDATE_ERROR, "【" + proxyLevel.toString() + "】修改失败", e);
		}
    }
    
    /**
     * 删除数据
     * 
     * @param proxyLevel
     * @throws WmallException
     */
    @Override
    public void remove(ProxyLevel proxyLevel) {
    	Assert.notNull(proxyLevel, "删除数据为空");
		try {
    		ProxyLevel deleteProxyLevel = new ProxyLevel();
    		deleteProxyLevel.setId(proxyLevel.getId());
    		deleteProxyLevel.setIsDelete(TrueFalseStatusEnum.TRUE.getValue());
    		proxyLevelMapper.update(deleteProxyLevel);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_DELETE_ERROR, "【" + proxyLevel.toString() + "】删除失败", e);
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
    public ProxyLevel getProxyLevel(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return proxyLevelMapper.getProxyLevel(map);
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
    public List<ProxyLevel> listProxyLevel(Map<String, Object> map) {
   	 	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return proxyLevelMapper.listProxyLevel(map);
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
    public void batchSave(List<ProxyLevel> list) {
    	Assert.notEmpty(list, "批量保存数据为空");
    	try {
			List<List<ProxyLevel>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<ProxyLevel> page : pageList) {
				proxyLevelMapper.batchInsert(page);
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
    public void batchUpdate(List<ProxyLevel> list) {
    	Assert.notEmpty(list, "批量修改数据为空");
    	try {
			List<List<ProxyLevel>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<ProxyLevel> page : pageList) {
				proxyLevelMapper.batchUpdate(page);
			}
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "批量修改失败", e);
		}
    }
    
}
