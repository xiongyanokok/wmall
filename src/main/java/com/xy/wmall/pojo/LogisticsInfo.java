package com.xy.wmall.pojo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 物流信息
 * 
 * @author xiongyan
 * @date 2018年1月18日 下午5:00:16
 */
@Getter
@Setter
public class LogisticsInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 时间
	 */
	private String time;
	
	/**
	 * 地点和跟踪进度
	 */
	private String context;

}
