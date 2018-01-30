package com.xy.wmall.common.utils;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * jackson 序列化工具类
 * 
 * @author xiongyan
 * @date 2017年10月27日 上午10:14:24
 */
public class JacksonUtils {
	
	private JacksonUtils() {
		
	}

	/**
	 * ObjectMapper
	 */
	private static ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(Include.NON_EMPTY).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	

	/**
	 * 序列化
	 * 
	 * @param value
	 * @return
	 */
	public static String serialize(Object value) {
        if (null == value) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(value);
        } catch (IOException e) {
            return null;
        }
    }
	
	/**
	 * 反序列化
	 * 
	 * @param content
	 * @param valueType
	 * @return
	 */
	public <T> T deserialize(String content, Class<T> valueType) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        try {
            return objectMapper.readValue(content, valueType);
        } catch (IOException e) {
            return null;
        }
    }
	
	/**
	 * 反序列化
	 * 
	 * @param content
	 * @param valueTypeRef
	 * @return
	 */
	public static <T> T deserialize(String content, TypeReference<T> valueTypeRef) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        try {
            return objectMapper.readValue(content, valueTypeRef);
        } catch (IOException e) {
            return null;
        }
    }
}
