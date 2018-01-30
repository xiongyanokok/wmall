package com.xy.wmall.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * model 实体类
 * 
 * @author admin
 * @date 2018年01月26日 上午10:41:12
 */
@Data
public class Logistics implements Serializable {

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
     * 物流公司ID
     */
	private Integer companyId;
	
	/**
     * 物流单号
     */
	private String number;
	
	/**
     * 物流价格
     */
	private Integer price;
	
	/**
     * 成本价格
     */
	private Integer cost;
	
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
