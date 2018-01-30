package com.xy.wmall.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * model 实体类
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:24
 */
@Data
public class ProxyLevel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 主键
     */
	private Integer id;
	
	/**
     * 代理ID
     */
	private Integer proxyId;
	
	/**
     * 货款
     */
	private Integer price;
	
	/**
     * 级别：1小咖，2中咖，3大咖，4顶级大咖，5带砖大咖
     */
	private Integer level;
	
	/**
     * 月份
     */
	private String month;
	
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
