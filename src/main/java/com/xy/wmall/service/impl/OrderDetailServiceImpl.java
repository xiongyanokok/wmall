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
import com.xy.wmall.mapper.OrderDetailMapper;
import com.xy.wmall.model.OrderDetail;
import com.xy.wmall.service.OrderDetailService;

/**
 * Service 实现
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:14
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
	private OrderDetailMapper orderDetailMapper;
	
	/**
     * 根据主键查询
     *
     * @param id
     * @return
     * @throws WmallException
     */
    @Override
    public OrderDetail selectByPrimaryKey(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
	    	return orderDetailMapper.selectByPrimaryKey(id);
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
    public OrderDetail getOrderDetailById(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
    		Map<String, Object> map = new HashMap<>();
    		map.put("id", id);
    		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
	    	return orderDetailMapper.getOrderDetail(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + id + "】查询失败", e);
		}
    }
    
	/**
     * 保存数据
     *
     * @param orderDetail
     * @throws WmallException
     */
    @Override
    public void save(OrderDetail orderDetail) {
    	Assert.notNull(orderDetail, "保存数据为空");
    	try {
			orderDetailMapper.insert(orderDetail);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_INSERT_ERROR, "【" + orderDetail.toString() + "】保存失败", e);
		}
    }

    /**
     * 修改数据
     *
     * @param orderDetail
     * @throws WmallException
     */
    @Override
    public void update(OrderDetail orderDetail) {
    	Assert.notNull(orderDetail, "修改数据为空");
    	try {
    		orderDetailMapper.update(orderDetail);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_UPDATE_ERROR, "【" + orderDetail.toString() + "】修改失败", e);
		}
    }
    
    /**
     * 删除数据
     * 
     * @param orderDetail
     * @throws WmallException
     */
    @Override
    public void remove(OrderDetail orderDetail) {
    	Assert.notNull(orderDetail, "删除数据为空");
		try {
    		OrderDetail deleteOrderDetail = new OrderDetail();
    		deleteOrderDetail.setId(orderDetail.getId());
    		orderDetailMapper.update(deleteOrderDetail);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_DELETE_ERROR, "【" + orderDetail.toString() + "】删除失败", e);
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
    public OrderDetail getOrderDetail(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return orderDetailMapper.getOrderDetail(map);
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
    public List<OrderDetail> listOrderDetail(Map<String, Object> map) {
   	 	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return orderDetailMapper.listOrderDetail(map);
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
    public void batchSave(List<OrderDetail> list) {
    	Assert.notEmpty(list, "批量保存数据为空");
    	try {
			List<List<OrderDetail>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<OrderDetail> page : pageList) {
				orderDetailMapper.batchInsert(page);
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
    public void batchUpdate(List<OrderDetail> list) {
    	Assert.notEmpty(list, "批量修改数据为空");
    	try {
			List<List<OrderDetail>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<OrderDetail> page : pageList) {
				orderDetailMapper.batchUpdate(page);
			}
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "批量修改失败", e);
		}
    }
    
}
