package com.xy.wmall.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * model 实体类
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:16
 */
@Data
public class Price implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 主键
     */
	private Integer id;
	
	/**
     * 产品ID
     */
	private Integer productId;
	
	/**
     * 价格类型：1代理价，2零售价
     */
	private Integer priceType;
	
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
     * 创建人ID
     */
	private Integer createUserId;
	
	/**
     * 创建时间
     */
	private Date createTime;
	
	/**
     * 修改人ID
     */
	private Integer updateUserId;
	
	/**
     * 修改时间
     */
	private Date updateTime;
	
	/**
     * 删除：1删除，0未删除
     */
	private Boolean isDelete;
	
}
