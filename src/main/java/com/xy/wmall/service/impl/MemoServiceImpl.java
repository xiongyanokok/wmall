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
import com.xy.wmall.mapper.MemoMapper;
import com.xy.wmall.model.Memo;
import com.xy.wmall.service.MemoService;

/**
 * Service 实现
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:08
 */
@Service
public class MemoServiceImpl implements MemoService {

    @Autowired
	private MemoMapper memoMapper;
	
	/**
     * 根据主键查询
     *
     * @param id
     * @return
     * @throws WmallException
     */
    @Override
    public Memo selectByPrimaryKey(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
	    	return memoMapper.selectByPrimaryKey(id);
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
    public Memo getMemoById(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
    		Map<String, Object> map = new HashMap<>();
    		map.put("id", id);
    		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
	    	return memoMapper.getMemo(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + id + "】查询失败", e);
		}
    }
    
	/**
     * 保存数据
     *
     * @param memo
     * @throws WmallException
     */
    @Override
    public void save(Memo memo) {
    	Assert.notNull(memo, "保存数据为空");
    	try {
			memoMapper.insert(memo);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_INSERT_ERROR, "【" + memo.toString() + "】保存失败", e);
		}
    }

    /**
     * 修改数据
     *
     * @param memo
     * @throws WmallException
     */
    @Override
    public void update(Memo memo) {
    	Assert.notNull(memo, "修改数据为空");
    	try {
    		memoMapper.update(memo);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_UPDATE_ERROR, "【" + memo.toString() + "】修改失败", e);
		}
    }
    
    /**
     * 删除数据
     * 
     * @param memo
     * @throws WmallException
     */
    @Override
    public void remove(Memo memo) {
    	Assert.notNull(memo, "删除数据为空");
		try {
    		Memo deleteMemo = new Memo();
    		deleteMemo.setId(memo.getId());
    		deleteMemo.setIsDelete(TrueFalseStatusEnum.TRUE.getValue());
    		memoMapper.update(deleteMemo);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_DELETE_ERROR, "【" + memo.toString() + "】删除失败", e);
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
    public Memo getMemo(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return memoMapper.getMemo(map);
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
    public List<Memo> listMemo(Map<String, Object> map) {
   	 	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return memoMapper.listMemo(map);
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
    public void batchSave(List<Memo> list) {
    	Assert.notEmpty(list, "批量保存数据为空");
    	try {
			List<List<Memo>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<Memo> page : pageList) {
				memoMapper.batchInsert(page);
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
    public void batchUpdate(List<Memo> list) {
    	Assert.notEmpty(list, "批量修改数据为空");
    	try {
			List<List<Memo>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<Memo> page : pageList) {
				memoMapper.batchUpdate(page);
			}
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "批量修改失败", e);
		}
    }
    
}
