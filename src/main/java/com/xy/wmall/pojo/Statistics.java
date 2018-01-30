package com.xy.wmall.pojo;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 统计汇总
 * 
 * @author xiongyan
 * @date 2017年11月3日 上午10:32:45
 */
@Getter
@Setter
@EqualsAndHashCode
public class Statistics implements Serializable, Comparable<Statistics> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 产品id
	 */
	private Integer productId;
	
	/**
	 * 订单数量
	 */
	private Integer orderNumber;
	
	/**
	 * 订单价格
	 */
	private Integer orderPrice;
	
	/**
	 * 进货数量
	 */
	private Integer purchaseNumber;
	
	/**
	 * 进货价格
	 */
	private Integer purchasePrice;
	
	/**
	 * 老大发货数量
	 */
	private Integer deliverNumber;
	
	/**
	 * 剩余数量
	 */
	private Integer surplusNumber;
	
	/**
	 * 自己发货数量
	 */
	private Integer myDeliverNumber;
	
	/**
	 * 发货到家数量
	 */
	private Integer deliverHomeNumber;
	
	@Override
	public int compareTo(Statistics statistics) {
		return statistics.getProductId().compareTo(this.getProductId());
	}

}
