package com.xy.wmall.enums;

/**
 * 业务级错误码
 * 
 * @author xiongyan
 * @date 2017年6月6日 下午4:19:08
 */
public enum ErrorCodeEnum {

	/**
	 * 参数检查失败
	 */
	PARAM_CHECK_ERROR(20001),
	
	/**
	 * HTTP接口调用失败
	 */
	HTTP_EXECUTE_ERROR(20002),
	
	
	/**
	 * 数据库插入失败
	 */
	DB_INSERT_ERROR(30001),
	
	/**
	 * 数据库更新失败
	 */
	DB_UPDATE_ERROR(30002),
	
	/**
	 * 数据库删除失败
	 */
	DB_DELETE_ERROR(30003),
	
	/**
	 * 数据库查询失败
	 */
	DB_SELECT_ERROR(30004),
	
	/**
	 * 数据库批量操作失败
	 */
	DB_BATCH_ERROR(30005),
	
	
	/**
	 * redis查询失败
	 */
	REDIS_GET_ERROR(40001),
	
	/**
	 * redis添加失败
	 */
	REDIS_ADD_ERROR(40002),
	
	/**
	 * redis删除失败
	 */
	REDIS_REMOVE_ERROR(40003);
	
	/**
	 * 错误码
	 */
	private Integer code;
	
	private ErrorCodeEnum(Integer code){
		this.code= code;
	}

	public Integer getCode() {
		return code;
	}
}
