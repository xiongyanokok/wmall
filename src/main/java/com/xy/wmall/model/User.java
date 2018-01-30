package com.xy.wmall.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * model 实体类
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:27
 */
@Data
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 主键
     */
	private Integer id;
	
	/**
     * 代理人ID
     */
	private Integer proxyId;
	
	/**
     * 用户名
     */
	private String username;
	
	/**
     * 密码
     */
	private String password;
	
	/**
     * 用户类型：1管理员，2代理人员
     */
	private Integer type;
	
	/**
     * 创建时间
     */
	private Date createTime;
	
	/**
     * 修改时间
     */
	private Date updateTime;
	
	/**
     * 删除：1删除，0未删除
     */
	private Boolean isDelete;
	
}
