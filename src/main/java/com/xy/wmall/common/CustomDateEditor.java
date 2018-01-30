package com.xy.wmall.common;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringUtils;

import com.xy.wmall.common.utils.DateUtils;

/**
 * 自定义日期编辑器
 * 
 * @author xiongyan
 * @date 2016年12月15日 上午11:01:17
 */
public class CustomDateEditor extends PropertyEditorSupport {

	/**
	 * 字符串转日期类型
	 */
	@Override
	public void setAsText(String text) {
		if (StringUtils.isNotEmpty(text)) {
			try {
				setValue(DateUtils.parse(text));
			} catch (Exception ex) {
				throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
			}
		}
	}

}
