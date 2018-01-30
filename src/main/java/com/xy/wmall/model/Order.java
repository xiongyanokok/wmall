package com.xy.wmall.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * model 实体类
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:11
 */
@Data
public class Order implements Serializable {

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
     * 上级代理ID
     */
	private Integer parentProxyId;
	
	/**
     * 订单类型：1代理订单，2零售订单
     */
	private Integer orderType;
	
	/**
     * 订单价格
     */
	private Integer orderPrice;
	
	/**
     * 优惠价格
     */
	private Integer preferentialPrice;
	
	/**
     * 是否累积：1是，0否
     */
	private Boolean isAccumulate;
	
	/**
     * 自然月（yyyy-mm）
     */
	private String natureMonth;
	
	/**
     * 订单状态：1未支付，2已支付，3已取消，4已完成
     */
	private Integer orderStatus;
	
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
     * 微信昵称
     */
    private String wechatName;
    
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
     * 赠送
     */
    private Integer[] give;
    
    /**
     * 年
     */
    private String year;
    
    /**
     * 月
     */
    private String month;
    
    /**
     * 收件人姓名
     */
	private String receiveName;
	
	/**
     * 收件人电话
     */
	private String receivePhone;
	
	/**
     * 收件人地址
     */
	private String receiveAddress;
	
	/**
     * 发货类型
     */
	private Integer deliverType;
	
	/**
     * 快递费
     */
    private Integer courierPrice;
    
    /**
     * 订单详情
     */
    private List<OrderDetail> orderDetails;
    
    /**
     * 更正类型
     */
    private Integer correctType;
	
}
