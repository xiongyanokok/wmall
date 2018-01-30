package com.xy.wmall.enums;

/**
 * 是否状态
 * 
 * @author xiongyan
 * @date 2016年11月21日 上午11:39:33
 */
public enum TrueFalseStatusEnum {

	/**
	 * 是
	 */
	TRUE(true),
	
	/**
	 * 否
	 */
	FALSE(false);
	
    private Boolean value;
	
    private TrueFalseStatusEnum(Boolean value) {
		this.value = value;
	}
    
	public Boolean getValue() {
		return this.value;
	}
}
