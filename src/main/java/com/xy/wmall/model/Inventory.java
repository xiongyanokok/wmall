package com.xy.wmall.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * model 实体类
 * 
 * @author admin
 * @date 2017年11月28日 上午09:32:14
 */
@Data
public class Inventory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 主键
     */
	private Integer id;
	
	/**
     * 代理id
     */
	private Integer proxyId;
	
	/**
     * 对货详情
     */
	private String details;
	
	/**
     * 代理余额
     */
	private Integer balance;
	
	/**
     * 备注
     */
	private String remark;
	
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
