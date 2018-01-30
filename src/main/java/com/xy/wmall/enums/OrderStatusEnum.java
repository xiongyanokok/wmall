package com.xy.wmall.enums;

/**
 * 订单状态
 * 
 * @author xiongyan
 * @date 2016年11月21日 上午11:39:33
 */
public enum OrderStatusEnum {

	/**
	 * 未支付
	 */
	WAIT_PAYMENT(1),
	
	/**
	 * 已支付
	 */
	PAYMENT_SUCCESS(2),
	
	/**
	 * 已取消
	 */
	ORDER_CANCEL(3),
	
	/**
	 * 已完成
	 */
	ORDER_SUCCESS(4);
	
	/**
	 * 状态
	 */
	private Integer value;
	
	private OrderStatusEnum(Integer value){
		this.value= value;
	}

	public Integer getValue() {
		return value;
	}
}
