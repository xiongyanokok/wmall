package com.xy.wmall.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.wmall.common.Assert;
import com.xy.wmall.common.utils.DateUtils;
import com.xy.wmall.common.utils.ListPageUtils;
import com.xy.wmall.enums.DeliverTypeEnum;
import com.xy.wmall.enums.ErrorCodeEnum;
import com.xy.wmall.enums.OrderStatusEnum;
import com.xy.wmall.enums.OrderTypeEnum;
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.exception.WmallException;
import com.xy.wmall.mapper.DeliverDetailMapper;
import com.xy.wmall.mapper.DeliverMapper;
import com.xy.wmall.mapper.OrderDetailMapper;
import com.xy.wmall.mapper.OrderMapper;
import com.xy.wmall.mapper.ProxyMapper;
import com.xy.wmall.model.Deliver;
import com.xy.wmall.model.DeliverDetail;
import com.xy.wmall.model.Order;
import com.xy.wmall.model.OrderDetail;
import com.xy.wmall.model.Proxy;
import com.xy.wmall.service.ProxyService;

/**
 * Service 实现
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:21
 */
@Service
public class ProxyServiceImpl implements ProxyService {

    @Autowired
	private ProxyMapper proxyMapper;
    
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    
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
    public Proxy selectByPrimaryKey(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
	    	return proxyMapper.selectByPrimaryKey(id);
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
    public Proxy getProxyById(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
    		Map<String, Object> map = new HashMap<>();
    		map.put("id", id);
    		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
	    	return proxyMapper.getProxy(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + id + "】查询失败", e);
		}
    }
    
	/**
     * 保存数据
     *
     * @param proxy
     * @throws WmallException
     */
    @Override
    public void save(Proxy proxy) {
    	Assert.notNull(proxy, "保存数据为空");
    	try {
    		// 保存代理基本信息
			proxyMapper.insert(proxy);

			// 老代理，不下单，不发货
			if (proxy.getIsOldProxy()) {
				return;
			}
		    
			// 保存订单信息
	    	Order order = new Order();
			order.setProxyId(proxy.getId());
			order.setParentProxyId(proxy.getParentId());
			order.setOrderType(OrderTypeEnum.PROXY_ORDER.getValue());
			order.setOrderPrice(proxy.getProxyPrice());
			order.setPreferentialPrice(0);
			order.setIsAccumulate(TrueFalseStatusEnum.FALSE.getValue());
			order.setNatureMonth(DateUtils.natureMonth());
			order.setOrderStatus(OrderStatusEnum.ORDER_SUCCESS.getValue());
			order.setCreateUserId(proxy.getCreateUserId());
			order.setCreateTime(new Date());
			order.setUpdateUserId(proxy.getCreateUserId());
			order.setUpdateTime(new Date());
			order.setIsDelete(TrueFalseStatusEnum.FALSE.getValue());
			orderMapper.insert(order);
			
			// 产品id
			Integer[] productId = proxy.getProductId();
			// 数量
			Integer[] amount = proxy.getAmount();
			// 单价
		    BigDecimal[] unitPrice = proxy.getUnitPrice();
		    // 总价
		    Integer[] totalPrice = proxy.getTotalPrice();
			// 保存订单详情信息
	    	List<OrderDetail> orderDetails = new ArrayList<>(productId.length);
			for (int i=0; i<productId.length; i++) {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrderId(order.getId());
				orderDetail.setProductId(productId[i]);
				orderDetail.setAmount(amount[i]);
				orderDetail.setUnitPrice(unitPrice[i]);
				orderDetail.setTotalPrice(totalPrice[i]);
				orderDetail.setGive(0);
				orderDetails.add(orderDetail);
			}
			orderDetailMapper.batchInsert(orderDetails);
			
			// 发货类型
			if (DeliverTypeEnum.NOT_DELIVER.getValue().equals(proxy.getDeliverType())) {
				return;
			}
			
			// 保存发货单信息
			Deliver deliver = new Deliver();
			deliver.setProxyId(proxy.getId());
			deliver.setParentProxyId(proxy.getParentId());
			deliver.setReceiveName(proxy.getName());
			deliver.setReceivePhone(proxy.getPhone());
			deliver.setReceiveAddress(proxy.getAddress());
			deliver.setCourierPrice(proxy.getCourierPrice());
			deliver.setDeliverType(proxy.getDeliverType());
			deliver.setDeliverStatus(TrueFalseStatusEnum.FALSE.getValue());
			deliver.setInventoryStatus(TrueFalseStatusEnum.FALSE.getValue());
			deliver.setCreateUserId(proxy.getCreateUserId());
			deliver.setCreateTime(new Date());
			deliver.setUpdateUserId(proxy.getCreateUserId());
			deliver.setUpdateTime(new Date());
			deliver.setIsDelete(TrueFalseStatusEnum.FALSE.getValue());
			deliverMapper.insert(deliver);
			
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
			throw new WmallException(ErrorCodeEnum.DB_INSERT_ERROR, "【" + proxy.toString() + "】保存失败", e);
		}
    }

    /**
     * 修改数据
     *
     * @param proxy
     * @throws WmallException
     */
    @Override
    public void update(Proxy proxy) {
    	Assert.notNull(proxy, "修改数据为空");
    	try {
    		proxyMapper.update(proxy);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_UPDATE_ERROR, "【" + proxy.toString() + "】修改失败", e);
		}
    }
    
    /**
     * 删除数据
     * 
     * @param proxy
     * @throws WmallException
     */
    @Override
    public void remove(Proxy proxy) {
    	Assert.notNull(proxy, "删除数据为空");
		try {
    		Proxy deleteProxy = new Proxy();
    		deleteProxy.setId(proxy.getId());
    		deleteProxy.setIsDelete(TrueFalseStatusEnum.TRUE.getValue());
    		proxyMapper.update(deleteProxy);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_DELETE_ERROR, "【" + proxy.toString() + "】删除失败", e);
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
    public Proxy getProxy(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return proxyMapper.getProxy(map);
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
    public List<Proxy> listProxy(Map<String, Object> map) {
   	 	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return proxyMapper.listProxy(map);
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
    public void batchSave(List<Proxy> list) {
    	Assert.notEmpty(list, "批量保存数据为空");
    	try {
			List<List<Proxy>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<Proxy> page : pageList) {
				proxyMapper.batchInsert(page);
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
    public void batchUpdate(List<Proxy> list) {
    	Assert.notEmpty(list, "批量修改数据为空");
    	try {
			List<List<Proxy>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<Proxy> page : pageList) {
				proxyMapper.batchUpdate(page);
			}
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "批量修改失败", e);
		}
    }
    
}
