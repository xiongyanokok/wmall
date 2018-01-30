package com.xy.wmall.enums;

/**
 * 发货类型
 * 
 * @author xiongyan
 * @date 2016年11月21日 上午11:39:33
 */
public enum DeliverTypeEnum {
	
	/**
	 * 不发货
	 */
	NOT_DELIVER(0),

	/**
	 * 自己发货
	 */
	SELF_DELIVER(1),
	
	/**
	 * 老大发货
	 */
	SUPER_DELIVER(2),
	
	/**
	 * 工厂发货
	 */
	FACTORY_DELIVER(3);
	
	/**
	 * 状态
	 */
	private Integer value;
	
	private DeliverTypeEnum(Integer value){
		this.value= value;
	}

	public Integer getValue() {
		return value;
	}
}
