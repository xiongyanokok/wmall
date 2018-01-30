package com.xy.wmall.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * model 实体类
 * 
 * @author admin
 * @date 2017年11月06日 下午04:04:25
 */
@Data
public class Proxy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 主键
     */
	private Integer id;
	
	/**
     * 父ID
     */
	private Integer parentId;
	
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
	
	
    /**
     * 快递费
     */
    private Integer courierPrice;
    
    /**
     * 是否老代理
     */
    private Boolean isOldProxy;
        
    /**
     * 发货类型
     */
	private Integer deliverType;
    
    /**
     * 产品id
     */
    private Integer[] productId;
    
    /**
     * 数量
     */
    private Integer[] amount;
    
    /**
     * 单价
     */
    private BigDecimal[] unitPrice;
    
    /**
     * 总价
     */
    private Integer[] totalPrice;
    
    /**
     * 余额
     */
    private Integer balance;

}
