package com.xy.wmall.enums;

/**
 * 算术类型
 * 
 * @author xiongyan
 * @date 2016年11月21日 上午11:39:33
 */
public enum ArithmeticTypeEnum {

	/**
	 * 添加
	 */
	ADD(1),
	
	/**
	 * 减少
	 */
	SUB(2);
	
	/**
	 * 类型
	 */
	private Integer value;
	
	private ArithmeticTypeEnum(Integer value){
		this.value= value;
	}

	public Integer getValue() {
		return value;
	}
}
