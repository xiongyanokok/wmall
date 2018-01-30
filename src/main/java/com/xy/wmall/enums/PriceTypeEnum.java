package com.xy.wmall.enums;

/**
 * 价格类型
 * 
 * @author xiongyan
 * @date 2016年11月21日 上午11:39:33
 */
public enum PriceTypeEnum {

	/**
	 * 代理价格
	 */
	PROXY_PRICE(1),
	
	/**
	 * 零售价格
	 */
	RETAIL_PRICE(2);
	
	/**
	 * 类型
	 */
	private Integer value;
	
	private PriceTypeEnum(Integer value){
		this.value= value;
	}

	public Integer getValue() {
		return value;
	}
}
