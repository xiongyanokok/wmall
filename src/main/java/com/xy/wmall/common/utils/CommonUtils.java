package com.xy.wmall.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.xy.wmall.exception.WmallException;

/**
 * 工具类
 * 
 * @author xiongyan
 * @date 2017年10月30日 上午10:25:13
 */
public class CommonUtils {

	private CommonUtils() {
		
	}
	
	/**
	 * 对象深拷贝
	 * 
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T clone(T t) {
		T clonedObj = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(t);
			oos.close();
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			clonedObj = (T) ois.readObject();
			ois.close();
		} catch (Exception e) {
			// error
		}
		return clonedObj;
	}
	
	/**
	 * 字段map
	 */
	private static final Map<String, String> FIELDMAP = new HashMap<>();
	
	/**
	 * 正则表达式
	 */
	private static final Pattern LINEPATTERN = Pattern.compile("_(\\w)");
	
	/** 
	 * 下划线转驼峰
	 * 
	 * @param lineName
	 * @return 
	 */
	public static String lineToHump(String lineName) {
		if (StringUtils.isEmpty(lineName)) {
			throw new WmallException("lineName is not null");
		}
		
		String humpName = FIELDMAP.get(lineName);
		if (StringUtils.isNotEmpty(humpName)) {
			return humpName;
		}
		
		Matcher matcher = LINEPATTERN.matcher(lineName);
		StringBuffer newStr = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(newStr, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(newStr);
		humpName = newStr.toString();
		FIELDMAP.put(lineName, humpName);
		return humpName;
	}
	
	/**
	 * 驼峰转下划线
	 * 
	 * @param humpName
	 * @return
	 */
	public static String humpToLine(String humpName) {
		if (StringUtils.isEmpty(humpName)) {
			throw new WmallException("humpName is not null");
		}
		
		String lineName = FIELDMAP.get(humpName);
		if (StringUtils.isNotEmpty(lineName)) {
			return lineName;
		}
		
		// 驼峰转下划线
		StringBuilder newStr = new StringBuilder();
		for (int i = 0; i < humpName.length(); i++) {
			char c = humpName.charAt(i);
			if (Character.isUpperCase(c)) {
				newStr.append("_");
				newStr.append(Character.toLowerCase(c));
			} else {
				newStr.append(c);
			}
		}
		lineName = newStr.toString();
		FIELDMAP.put(humpName, lineName);
		return lineName;
	}
	
	/**
	 * 去掉html标签
	 * 
	 * @param str
	 * @return
	 */
	public static String removeHtml(String str) {
		if (StringUtils.isEmpty(str)) {
			return StringUtils.EMPTY;
		}
		return str.replaceAll("<[^>]*>| |　|&nbsp;", StringUtils.EMPTY);
	}
	
	/**
	 * 是否ajax请求
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request) {
		String header = request.getHeader("x-requested-with"); 
		return null != header && "XMLHttpRequest".equalsIgnoreCase(header);
	}
	
}
