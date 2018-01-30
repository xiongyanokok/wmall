package com.xy.wmall.common;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.xy.wmall.enums.ErrorCodeEnum;
import com.xy.wmall.exception.WmallException;

/**
 * 断言
 * 
 * @author xiongyan
 * @date 2017年6月6日 下午3:41:39
 */
public final class Assert {
	
	private Assert() {
		
	}

	/**
	 * assert expression is true
	 * 
	 * @param expression
	 * @param message
	 * @throws WmallException if {@code expression} is {@code false}
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new WmallException(ErrorCodeEnum.PARAM_CHECK_ERROR, message);
		}
	}
	
	/**
	 * assert text is not empty
	 * 
	 * @param text
	 * @param message
	 * @throws WmallException if the text is empty
	 */
	public static void hasLength(String text, String message) {
		if (StringUtils.isEmpty(text)) {
			throw new WmallException(ErrorCodeEnum.PARAM_CHECK_ERROR, message);
		}
	}
	
	/**
	 * assert object is not null 
	 * 
	 * @param object
	 * @param message
	 * @throws WmallException if the object is not {@code null}
	 */
	public static void isNull(Object object, String message) {
		if (null != object) {
			throw new WmallException(ErrorCodeEnum.PARAM_CHECK_ERROR, message);
		}
	}
	
	/**
	 * assert object is null
	 * 
	 * @param object
	 * @param message
	 * @throws WmallException if the object is {@code null}
	 */
	public static void notNull(Object object, String message) {
		if (null == object) {
			throw new WmallException(ErrorCodeEnum.PARAM_CHECK_ERROR, message);
		}
	}
	
	/**
	 * assert collection is not empty
	 * 
	 * @param collection
	 * @param message
	 * @throws WmallException if the collection is {@code null} or collection no elements
	 */
	public static void notEmpty(Collection<?> collection, String message) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new WmallException(ErrorCodeEnum.PARAM_CHECK_ERROR, message);
		}
	}
	
	/**
	 * assert map is not empty
	 * 
	 * @param map
	 * @param message
	 * @throws WmallException if the map is {@code null} or map no elements
	 */
	public static void notEmpty(Map<?, ?> map, String message) {
		if (MapUtils.isEmpty(map)) {
			throw new WmallException(ErrorCodeEnum.PARAM_CHECK_ERROR, message);
		}
	}
	
}
