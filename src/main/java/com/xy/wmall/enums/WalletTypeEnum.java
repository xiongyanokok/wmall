package com.xy.wmall.enums;

/**
 * 资金类型
 * 
 * @author xiongyan
 * @date 2016年11月21日 上午11:39:33
 */
public enum WalletTypeEnum {

	/**
	 * 存入
	 */
	DEPOSIT(1),
	
	/**
	 * 支出
	 */
	EXPENDITURE(2);
	
	/**
	 * 类型
	 */
	private Integer value;
	
	private WalletTypeEnum(Integer value){
		this.value= value;
	}

	public Integer getValue() {
		return value;
	}
}
