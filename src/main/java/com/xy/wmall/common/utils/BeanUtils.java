package com.xy.wmall.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cglib.beans.BeanMap;

/**
 * bean 工具类
 * 
 * @author xiongyan
 * @date 2017年11月8日 上午10:51:06
 */
public class BeanUtils {

	private BeanUtils() {

	}

	/**
	 * 根据id合并
	 * 
	 * @param name
	 * @param t1
	 * @param t2
	 */
	public static <T> List<T> merge(String id, Collection<T> t1, Collection<T> t2) {
		Map<Object, T> listMap = new HashMap<>();
		Map<Object, BeanMap> map = new HashMap<>();
		if (CollectionUtils.isNotEmpty(t1)) {
			for (T t : t1) {
				BeanMap beanMap = BeanMap.create(t);
				map.put(beanMap.get(id), beanMap);
				listMap.put(beanMap.get(id), t);
			}
		}

		if (CollectionUtils.isNotEmpty(t2)) {
			for (T t : t2) {
				BeanMap beanMap = BeanMap.create(t);
				BeanMap bm = map.get(beanMap.get(id));
				if (null != bm) {
					for (Object key : bm.keySet()) {
						if (null != bm.get(key)) {
							beanMap.put(t, key, bm.get(key));
						}
					}
				}
				listMap.put(beanMap.get(id), t);
			}
		}
		
		return new ArrayList<>(listMap.values());
	}

}
