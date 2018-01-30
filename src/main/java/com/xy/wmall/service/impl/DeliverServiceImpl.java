package com.xy.wmall.service.impl;

import java.util.ArrayList;
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
import com.xy.wmall.mapper.DeliverMapper;
import com.xy.wmall.model.Deliver;
import com.xy.wmall.model.DeliverDetail;
import com.xy.wmall.pojo.Statistics;
import com.xy.wmall.service.DeliverService;

/**
 * Service 实现
 * 
 * @author admin
 * @date 2017年10月28日 上午08:53:59
 */
@Service
public class DeliverServiceImpl implements DeliverService {

    @Autowired
	private DeliverMapper deliverMapper;
    
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
    public Deliver selectByPrimaryKey(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
	    	return deliverMapper.selectByPrimaryKey(id);
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
    public Deliver getDeliverById(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
    		Map<String, Object> map = new HashMap<>();
    		map.put("id", id);
    		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
	    	return deliverMapper.getDeliver(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + id + "】查询失败", e);
		}
    }
    
	/**
     * 保存数据
     *
     * @param deliver
     * @throws WmallException
     */
    @Override
    public void save(Deliver deliver) {
    	Assert.notNull(deliver, "保存数据为空");
    	try {
    		// 保存发货信息
			deliverMapper.insert(deliver);
			
			// 产品id
			Integer[] productId = deliver.getProductId();
			// 数量
			Integer[] amount = deliver.getAmount();
			// 保存发货详情信息
	    	List<DeliverDetail> deliverDetails = new ArrayList<>(productId.length);
			for (int i=0; i<productId.length; i++) {
				DeliverDetail deliverDetail = new DeliverDetail();
				deliverDetail.setDeliverId(deliver.getId());
				deliverDetail.setProductId(productId[i]);
				deliverDetail.setAmount(amount[i]);
				deliverDetails.add(deliverDetail);
			}
			deliverDetailMapper.batchInsert(deliverDetails);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_INSERT_ERROR, "【" + deliver.toString() + "】保存失败", e);
		}
    }

    /**
     * 修改数据
     *
     * @param deliver
     * @throws WmallException
     */
    @Override
    public void update(Deliver deliver) {
    	Assert.notNull(deliver, "修改数据为空");
    	try {
    		// 修改发货单
    		deliverMapper.update(deliver);
    		
    		// 删除发货单详情
    		deliverDetailMapper.delete(deliver.getId());
    		
    		// 产品id
			Integer[] productId = deliver.getProductId();
			// 数量
			Integer[] amount = deliver.getAmount();
			// 保存发货详情信息
	    	List<DeliverDetail> deliverDetails = new ArrayList<>(productId.length);
			for (int i=0; i<productId.length; i++) {
				DeliverDetail deliverDetail = new DeliverDetail();
				deliverDetail.setDeliverId(deliver.getId());
				deliverDetail.setProductId(productId[i]);
				deliverDetail.setAmount(amount[i]);
				deliverDetails.add(deliverDetail);
			}
			deliverDetailMapper.batchInsert(deliverDetails);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_UPDATE_ERROR, "【" + deliver.toString() + "】修改失败", e);
		}
    }
    
    /**
     * 发货
     * 
     * @param deliver
     */
    @Override
    public void status(Deliver deliver) {
    	Assert.notNull(deliver, "修改数据为空");
    	try {
    		deliverMapper.update(deliver);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_UPDATE_ERROR, "【" + deliver.toString() + "】发货失败", e);
		}
    }
    
    /**
     * 删除数据
     * 
     * @param deliver
     * @throws WmallException
     */
    @Override
    public void remove(Deliver deliver) {
    	Assert.notNull(deliver, "删除数据为空");
		try {
    		Deliver deleteDeliver = new Deliver();
    		deleteDeliver.setId(deliver.getId());
    		deleteDeliver.setIsDelete(TrueFalseStatusEnum.TRUE.getValue());
    		deliverMapper.update(deleteDeliver);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_DELETE_ERROR, "【" + deliver.toString() + "】删除失败", e);
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
    public Deliver getDeliver(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return deliverMapper.getDeliver(map);
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
    public List<Deliver> listDeliver(Map<String, Object> map) {
   	 	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return deliverMapper.listDeliver(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + map + "】查询列表失败", e);
		}
    }
    
    /**
     * 查询发货单
     * 
     * @param map
     * @return
     */
    @Override
    public List<Deliver> queryDeliver(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return deliverMapper.queryDeliver(map);
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
    public void batchSave(List<Deliver> list) {
    	Assert.notEmpty(list, "批量保存数据为空");
    	try {
			List<List<Deliver>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<Deliver> page : pageList) {
				deliverMapper.batchInsert(page);
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
    public void batchUpdate(List<Deliver> list) {
    	Assert.notEmpty(list, "批量修改数据为空");
    	try {
			List<List<Deliver>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<Deliver> page : pageList) {
				deliverMapper.batchUpdate(page);
			}
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "批量修改失败", e);
		}
    }
    
    /**
     * 发货统计
     * 
     * @param map
     * @return
     */
    @Override
    public List<Statistics> deliverStatistics(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return deliverMapper.deliverStatistics(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + map + "】查询统计失败", e);
		}
    }
    
    /**
     * 待发货数量
     * 
     * @param map
     * @return
     */
    @Override
    public Integer countWaitDeliver(Map<String, Object> map) {
    	try {
	    	return deliverMapper.countWaitDeliver(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + map + "】查询待发货失败", e);
		}
    }
    
    /**
     * 批量对货
     * 
     * @param map
     */
    @Override
    public void batchInventory(Map<String, Object> map) {
    	Assert.notEmpty(map, "批量对货数据为空");
    	try {
    		deliverMapper.batchInventory(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "【" + map + "】批量对货失败", e);
		}
    }
    
}
