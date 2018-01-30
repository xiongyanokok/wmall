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
import com.xy.wmall.mapper.DeliverDetailMapper;
import com.xy.wmall.model.DeliverDetail;
import com.xy.wmall.service.DeliverDetailService;

/**
 * Service 实现
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:03
 */
@Service
public class DeliverDetailServiceImpl implements DeliverDetailService {

    @Autowired
	private DeliverDetailMapper deliverDetailMapper;
	
	/**
     * 根据主键查询
     *
     * @param id
     * @return
     * @throws WmallException
     */
    @Override
    public DeliverDetail selectByPrimaryKey(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
	    	return deliverDetailMapper.selectByPrimaryKey(id);
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
    public DeliverDetail getDeliverDetailById(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
    		Map<String, Object> map = new HashMap<>();
    		map.put("id", id);
    		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
	    	return deliverDetailMapper.getDeliverDetail(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + id + "】查询失败", e);
		}
    }
    
	/**
     * 保存数据
     *
     * @param deliverDetail
     * @throws WmallException
     */
    @Override
    public void save(DeliverDetail deliverDetail) {
    	Assert.notNull(deliverDetail, "保存数据为空");
    	try {
			deliverDetailMapper.insert(deliverDetail);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_INSERT_ERROR, "【" + deliverDetail.toString() + "】保存失败", e);
		}
    }

    /**
     * 修改数据
     *
     * @param deliverDetail
     * @throws WmallException
     */
    @Override
    public void update(DeliverDetail deliverDetail) {
    	Assert.notNull(deliverDetail, "修改数据为空");
    	try {
    		deliverDetailMapper.update(deliverDetail);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_UPDATE_ERROR, "【" + deliverDetail.toString() + "】修改失败", e);
		}
    }
    
    /**
     * 删除数据
     * 
     * @param deliverDetail
     * @throws WmallException
     */
    @Override
    public void remove(DeliverDetail deliverDetail) {
    	Assert.notNull(deliverDetail, "删除数据为空");
		try {
    		DeliverDetail deleteDeliverDetail = new DeliverDetail();
    		deleteDeliverDetail.setId(deliverDetail.getId());
    		deliverDetailMapper.update(deleteDeliverDetail);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_DELETE_ERROR, "【" + deliverDetail.toString() + "】删除失败", e);
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
    public DeliverDetail getDeliverDetail(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return deliverDetailMapper.getDeliverDetail(map);
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
    public List<DeliverDetail> listDeliverDetail(Map<String, Object> map) {
   	 	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return deliverDetailMapper.listDeliverDetail(map);
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
    public void batchSave(List<DeliverDetail> list) {
    	Assert.notEmpty(list, "批量保存数据为空");
    	try {
			List<List<DeliverDetail>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<DeliverDetail> page : pageList) {
				deliverDetailMapper.batchInsert(page);
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
    public void batchUpdate(List<DeliverDetail> list) {
    	Assert.notEmpty(list, "批量修改数据为空");
    	try {
			List<List<DeliverDetail>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<DeliverDetail> page : pageList) {
				deliverDetailMapper.batchUpdate(page);
			}
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "批量修改失败", e);
		}
    }
    
}
