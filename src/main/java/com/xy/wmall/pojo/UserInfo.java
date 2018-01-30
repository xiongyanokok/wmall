package com.xy.wmall.pojo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户信息
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:21
 */
@Getter
@Setter
public class UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private Integer userId;
	
	/**
     * 代理ID
     */
	private Integer proxyId;
	
	/**
     * 上级代理ID
     */
	private Integer parentProxyId;
	
	/**
     * 微信号
     */
	private String wechatNumber;
	
	/**
     * 微信昵称
     */
	private String wechatName;
	
	/**
     * 姓名
     */
	private String name;
	
	/**
     * 性别：1男，0女
     */
	private Boolean sex;
	
	/**
     * 手机号
     */
	private String phone;
	
	/**
     * 地址
     */
	private String address;
	
	/**
     * 代理价
     */
	private Integer proxyPrice;
	
	/**
     * 身份证号
     */
	private String idNumber;
	
	/**
     * 授权码
     */
	private String authorizationCode;
	
	/**
     * 级别：1小咖，2中咖，3大咖，4顶级大咖，5带砖大咖
     */
	private Integer level;

}
