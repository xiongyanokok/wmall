package com.xy.wmall.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.wmall.common.Assert;
import com.xy.wmall.enums.ErrorCodeEnum;
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
public class DeliverServiceImpl extends BaseServiceImpl<DeliverMapper, Deliver> implements DeliverService {

    @Autowired
	private DeliverMapper deliverMapper;
    
    @Autowired
    private DeliverDetailMapper deliverDetailMapper;
	
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
    		deliverDetailMapper.deleteByDeliverId(deliver.getId());
    		
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
	 * 查询待发货单
	 * 
	 * @param map
	 * @return
	 */
    @Override
	public List<Deliver> listWaitDeliver(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return deliverMapper.listWaitDeliver(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + map + "】查询待发货失败", e);
		}
	}
	
    /**
     * 修改发货状态
     * 
     * @param deliver
     */
    @Override
    public void updateDeliverStatus(Deliver deliver) {
    	Assert.notNull(deliver, "修改数据为空");
    	try {
    		deliverMapper.update(deliver);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_UPDATE_ERROR, "【" + deliver.toString() + "】发货失败", e);
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
