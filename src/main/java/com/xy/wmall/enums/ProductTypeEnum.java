package com.xy.wmall.enums;

/**
 * 产品类型
 * 
 * @author xiongyan
 * @date 2016年11月21日 上午11:39:33
 */
public enum ProductTypeEnum {

	/**
	 * 代理产品
	 */
	PROXY_PRODUCT(1),
	
	/**
	 * 赠品
	 */
	GIFT_PRODUCT(2);
	
	/**
	 * 类型
	 */
	private Integer value;
	
	private ProductTypeEnum(Integer value){
		this.value= value;
	}

	public Integer getValue() {
		return value;
	}
}
