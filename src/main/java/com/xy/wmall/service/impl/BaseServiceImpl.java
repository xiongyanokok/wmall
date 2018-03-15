package com.xy.wmall.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.xy.wmall.common.Assert;
import com.xy.wmall.common.utils.CommonUtils;
import com.xy.wmall.common.utils.ListPageUtils;
import com.xy.wmall.enums.ErrorCodeEnum;
import com.xy.wmall.exception.WmallException;
import com.xy.wmall.mapper.BaseMapper;
import com.xy.wmall.service.BaseService;

/**
 * BaseServiceImpl 实现
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:08
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> implements BaseService<T> {

	@Autowired
    protected M baseMapper;
	
	/**
     * 保存数据
     *
     * @param t
     * @throws WmallException
     */
    @Override
    public void save(T t) {
    	Assert.notNull(t, "保存数据为空");
    	try {
			baseMapper.insert(t);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_INSERT_ERROR, "【" + t.toString() + "】保存失败", e);
		}
    }

    /**
     * 修改数据
     *
     * @param t
     * @throws WmallException
     */
    @Override
    public void update(T t) {
    	Assert.notNull(t, "修改数据为空");
    	try {
    		baseMapper.update(t);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_UPDATE_ERROR, "【" + t.toString() + "】修改失败", e);
		}
    }
    
    /**
     * 删除数据
     * 
     * @param t
     * @throws WmallException
     */
    @Override
    public void remove(T t) {
    	Assert.notNull(t, "删除数据为空");
		try {
    		baseMapper.delete(t);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_DELETE_ERROR, "【" + t.toString() + "】删除失败", e);
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
    public T getById(Serializable id) {
    	Assert.notNull(id, "id为空");
    	try {
    		Map<String, Object> map = CommonUtils.defaultQueryMap();
    		map.put("id", id);
	    	return baseMapper.getByMap(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + id + "】查询对象失败", e);
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
    public T getByMap(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return baseMapper.getByMap(map);
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
    public List<T> listByMap(Map<String, Object> map) {
   	 	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return baseMapper.listByMap(map);
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
    public void batchSave(List<T> list) {
    	Assert.notEmpty(list, "批量保存数据为空");
    	try {
			List<List<T>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<T> page : pageList) {
				baseMapper.batchInsert(page);
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
    public void batchUpdate(List<T> list) {
    	Assert.notEmpty(list, "批量修改数据为空");
    	try {
			List<List<T>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<T> page : pageList) {
				baseMapper.batchUpdate(page);
			}
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "批量修改失败", e);
		}
    }
    
}
