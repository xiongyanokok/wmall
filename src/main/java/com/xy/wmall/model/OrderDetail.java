package com.xy.wmall.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * model 实体类
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:14
 */
@Data
public class OrderDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 主键
     */
	private Integer id;
	
	/**
     * 订单ID
     */
	private Integer orderId;
	
	/**
     * 产品ID
     */
	private Integer productId;
	
	/**
     * 数量
     */
	private Integer amount;
	
	/**
     * 单价
     */
	private BigDecimal unitPrice;
	
	/**
     * 总价
     */
	private Integer totalPrice;
	
	/**
     * 赠送
     */
	private Integer give;
	
}
