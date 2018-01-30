package com.xy.wmall.enums;

/**
 * 订单类型
 * 
 * @author xiongyan
 * @date 2016年11月21日 上午11:39:33
 */
public enum OrderTypeEnum {

	/**
	 * 代理订单
	 */
	PROXY_ORDER(1),
	
	/**
	 * 零售订单
	 */
	RETAIL_ORDER(2);
	
	/**
	 * 类型
	 */
	private Integer value;
	
	private OrderTypeEnum(Integer value){
		this.value= value;
	}

	public Integer getValue() {
		return value;
	}
}
