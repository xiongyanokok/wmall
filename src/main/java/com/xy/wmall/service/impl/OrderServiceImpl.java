package com.xy.wmall.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.wmall.common.Assert;
import com.xy.wmall.common.utils.ListPageUtils;
import com.xy.wmall.enums.ArithmeticTypeEnum;
import com.xy.wmall.enums.ErrorCodeEnum;
import com.xy.wmall.enums.OrderTypeEnum;
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.enums.WalletTypeEnum;
import com.xy.wmall.exception.WmallException;
import com.xy.wmall.mapper.DeliverDetailMapper;
import com.xy.wmall.mapper.DeliverMapper;
import com.xy.wmall.mapper.OrderDetailMapper;
import com.xy.wmall.mapper.OrderMapper;
import com.xy.wmall.model.Deliver;
import com.xy.wmall.model.DeliverDetail;
import com.xy.wmall.model.Order;
import com.xy.wmall.model.OrderDetail;
import com.xy.wmall.model.Wallet;
import com.xy.wmall.pojo.Statistics;
import com.xy.wmall.service.OrderService;
import com.xy.wmall.service.WalletService;

/**
 * Service 实现
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:11
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
	private OrderMapper orderMapper;
    
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    
    @Autowired
    private DeliverMapper deliverMapper;
    
    @Autowired
    private DeliverDetailMapper deliverDetailMapper;
    
    @Autowired
    private WalletService walletService;
	
	/**
     * 根据主键查询
     *
     * @param id
     * @return
     * @throws WmallException
     */
    @Override
    public Order selectByPrimaryKey(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
	    	return orderMapper.selectByPrimaryKey(id);
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
    public Order getOrderById(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
    		Map<String, Object> map = new HashMap<>();
    		map.put("id", id);
    		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
	    	return orderMapper.getOrder(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + id + "】查询失败", e);
		}
    }
    
	/**
     * 保存数据
     *
     * @param order
     * @throws WmallException
     */
    @Override
    public void save(Order order) {
    	Assert.notNull(order, "保存数据为空");
    	try {
    		// 保存订单信息
			orderMapper.insert(order);
			
			// 产品id
			Integer[] productId = order.getProductId();
			// 数量
			Integer[] amount = order.getAmount();
			// 单价
		    BigDecimal[] unitPrice = order.getUnitPrice();
		    // 总价
		    Integer[] totalPrice = order.getTotalPrice();
		    // 赠送
		    Integer[] give = order.getGive();
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
				if (ArrayUtils.isNotEmpty(give) && null != give[i]) {
					orderDetail.setGive(give[i]);
				}
				orderDetails.add(orderDetail);
			}
			orderDetailMapper.batchInsert(orderDetails);
			
			// 钱包余额
			Integer balance = walletService.getWalletBalance(order.getProxyId());
			if (null != balance && balance > 0) {
				Wallet wallet = new Wallet();
				wallet.setProxyId(order.getProxyId());
				wallet.setOrderId(order.getId());
				wallet.setPrice(order.getOrderPrice() >= balance ? balance : order.getOrderPrice());
				wallet.setType(WalletTypeEnum.EXPENDITURE.getValue());
				wallet.setRemark("补货支出");
				wallet.setCreateUserId(order.getCreateUserId());
				wallet.setCreateTime(new Date());
				wallet.setUpdateUserId(order.getCreateUserId());
				wallet.setUpdateTime(new Date());
				wallet.setIsDelete(TrueFalseStatusEnum.FALSE.getValue());
				walletService.save(wallet);
			}
			
			// 代理订单，不发货
			if (OrderTypeEnum.PROXY_ORDER.getValue().equals(order.getOrderType())) {
				return;
			}
			
			// 保存发货单信息
			Deliver deliver = new Deliver();
			deliver.setProxyId(order.getProxyId());
			deliver.setParentProxyId(order.getParentProxyId());
			deliver.setReceiveName(order.getReceiveName());
			deliver.setReceivePhone(order.getReceivePhone());
			deliver.setReceiveAddress(order.getReceiveAddress());
			deliver.setCourierPrice(order.getCourierPrice());
			deliver.setDeliverType(order.getDeliverType());
			deliver.setDeliverStatus(TrueFalseStatusEnum.FALSE.getValue());
			deliver.setInventoryStatus(TrueFalseStatusEnum.FALSE.getValue());
			deliver.setCreateUserId(order.getCreateUserId());
			deliver.setCreateTime(new Date());
			deliver.setUpdateUserId(order.getCreateUserId());
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
			throw new WmallException(ErrorCodeEnum.DB_INSERT_ERROR, "【" + order.toString() + "】保存失败", e);
		}
    }
    
    /**
     * 保存更正
     * 
     * @param order
     */
    @Override
    public void saveCorrect(Order order) {
    	Assert.notNull(order, "保存数据为空");
    	try {
    		// 保存订单信息
			orderMapper.insert(order);
			
			// 保存订单详情
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderId(order.getId());
			orderDetail.setProductId(order.getProductId()[0]);
			Integer amount = order.getAmount()[0];
			if (ArithmeticTypeEnum.SUB.getValue().equals(order.getCorrectType())) {
				amount = -amount;
			}
			orderDetail.setAmount(amount);
			orderDetail.setUnitPrice(new BigDecimal(0));
			orderDetail.setTotalPrice(0);
			orderDetail.setGive(0);
			orderDetailMapper.insert(orderDetail);
    	} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_INSERT_ERROR, "【" + order.toString() + "】保存失败", e);
		}
    }
    
    /**
     * 修改数据
     *
     * @param order
     * @throws WmallException
     */
    @Override
    public void update(Order order) {
    	Assert.notNull(order, "修改数据为空");
    	try {
    		// 修改订单
    		orderMapper.update(order);
    		
    		// 删除订单详情
    		orderDetailMapper.delete(order.getId());
    		
    		// 产品id
			Integer[] productId = order.getProductId();
			// 数量
			Integer[] amount = order.getAmount();
			// 单价
		    BigDecimal[] unitPrice = order.getUnitPrice();
		    // 总价
		    Integer[] totalPrice = order.getTotalPrice();
		    // 赠送
		    Integer[] give = order.getGive();
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
				if (ArrayUtils.isNotEmpty(give) && null != give[i]) {
					orderDetail.setGive(give[i]);
				}
				orderDetails.add(orderDetail);
			}
			orderDetailMapper.batchInsert(orderDetails);
			
			// 根据订单id查询钱包支出
			Map<String, Object> map = new HashMap<>();
			map.put("proxyId", order.getProxyId());
			map.put("orderId", order.getId());
			map.put("type", WalletTypeEnum.EXPENDITURE.getValue());
			map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
			Wallet wallet = walletService.getWallet(map);
			if (null != wallet) {
				Integer price = order.getOrderPrice() - wallet.getPrice();
				if (price > 0) {
					// 钱包余额
					Integer balance = walletService.getWalletBalance(order.getProxyId());
					if (null != balance && balance > 0) {
						wallet.setPrice(wallet.getPrice() + (price >= balance ? balance : price));
						walletService.update(wallet);
					}
				} else {
					wallet.setPrice(order.getOrderPrice());
					walletService.update(wallet);
				}
			}
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_UPDATE_ERROR, "【" + order.toString() + "】修改失败", e);
		}
    }
    
    /**
     * 删除数据
     * 
     * @param order
     * @throws WmallException
     */
    @Override
    public void remove(Order order) {
    	Assert.notNull(order, "删除数据为空");
		try {
    		Order deleteOrder = new Order();
    		deleteOrder.setId(order.getId());
    		deleteOrder.setIsDelete(TrueFalseStatusEnum.TRUE.getValue());
    		orderMapper.update(deleteOrder);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_DELETE_ERROR, "【" + order.toString() + "】删除失败", e);
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
    public Order getOrder(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return orderMapper.getOrder(map);
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
    public List<Order> listOrder(Map<String, Object> map) {
   	 	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return orderMapper.listOrder(map);
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
    public void batchSave(List<Order> list) {
    	Assert.notEmpty(list, "批量保存数据为空");
    	try {
			List<List<Order>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<Order> page : pageList) {
				orderMapper.batchInsert(page);
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
    public void batchUpdate(List<Order> list) {
    	Assert.notEmpty(list, "批量修改数据为空");
    	try {
			List<List<Order>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<Order> page : pageList) {
				orderMapper.batchUpdate(page);
			}
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "批量修改失败", e);
		}
    }
    
    /**
     * 订单统计
     * 
     * @param map
     * @return
     */
    @Override
    public List<Statistics> orderStatistics(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return orderMapper.orderStatistics(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + map + "】查询统计失败", e);
		}
    }
    
    /**
     * 进货统计
     * 
     * @param map
     * @return
     */
    @Override
    public List<Statistics> purchaseStatistics(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return orderMapper.purchaseStatistics(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + map + "】查询统计失败", e);
		}
    }
    
}
