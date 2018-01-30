package com.xy.wmall.model;

import java.io.Serializable;

import lombok.Data;

/**
 * model 实体类
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:03
 */
@Data
public class DeliverDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 主键
     */
	private Integer id;
	
	/**
     * 发货单ID
     */
	private Integer deliverId;
	
	/**
     * 产品ID
     */
	private Integer productId;
	
	/**
     * 数量
     */
	private Integer amount;
	
}
