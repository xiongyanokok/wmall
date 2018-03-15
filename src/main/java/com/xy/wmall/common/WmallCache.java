package com.xy.wmall.common;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.collections4.MapUtils;

import com.xy.wmall.enums.PriceTypeEnum;
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.model.LogisticsCompany;
import com.xy.wmall.model.Price;

/**
 * 缓存
 * 
 * @author xiongyan
 * @date 2017年11月4日 下午2:35:58
 */
public final class WmallCache {

	private WmallCache() {
	}
	
	/**
	 * 零售价
	 */
	private static Map<Integer, BigDecimal> retailPriceMap = new HashMap<>();
	
	/**
	 * 代理价格
	 */
	private static Map<Integer, TreeMap<Integer, BigDecimal>> proxyPriceMap = new HashMap<>();
	
	/**
	 * 物流公司
	 */
	private static Map<Integer, LogisticsCompany> logisticsCompanyMap = new HashMap<>();
	
	
	/**
	 * 添加产品价
	 * 
	 * @param price
	 */
	public static void putPrice(Price price) {
		if (PriceTypeEnum.PROXY_PRICE.getValue().equals(price.getPriceType())) {
			TreeMap<Integer, BigDecimal> treeMap = proxyPriceMap.get(price.getProductId());
			if (null == treeMap) {
				treeMap = new TreeMap<>();
				proxyPriceMap.put(price.getProductId(), treeMap);
			}
			treeMap.put(price.getAmount(), price.getUnitPrice());
		} else {
			retailPriceMap.put(price.getProductId(), price.getUnitPrice());
		}
	}
	
	/**
	 * 删除产品价
	 * 
	 * @param price
	 */
	public static void removePrice(Price price) {
		if (PriceTypeEnum.PROXY_PRICE.getValue().equals(price.getPriceType())) {
			TreeMap<Integer, BigDecimal> treeMap = proxyPriceMap.get(price.getProductId());
			if (null != treeMap) {
				treeMap.remove(price.getAmount());
			}
		} else {
			retailPriceMap.remove(price.getProductId());
		}
	}
	
	/**
	 * 获取产品零售价
	 * 
	 * @param productId
	 * @return
	 */
	public static BigDecimal getRetailPrice(Integer productId) {
		return retailPriceMap.get(productId);
	}
	
	/**
	 * 获取产品代理价
	 * 
	 * @param productId
	 * @param amount
	 * @return
	 */
	public static BigDecimal getProxyPrice(Integer productId, Integer amount) {
		TreeMap<Integer, BigDecimal> priceMap = proxyPriceMap.get(productId);
		if (MapUtils.isEmpty(priceMap)) {
			return null;
		}
		if (priceMap.lastKey() < amount) {
			return null;
		}
		SortedMap<Integer, BigDecimal> subMap = priceMap.headMap(amount, TrueFalseStatusEnum.TRUE.getValue());
		Integer key = null;
		if (MapUtils.isEmpty(subMap)) {
			subMap = priceMap.tailMap(amount, TrueFalseStatusEnum.TRUE.getValue());
			key = subMap.firstKey();
		} else {
			key = subMap.lastKey();
		}
		return priceMap.get(key);
	}
	
	/**
	 * 添加物流公司缓存
	 * 
	 * @param logisticsCompany
	 */
	public static void putLogisticsCompany(LogisticsCompany logisticsCompany) {
		LogisticsCompany company = new LogisticsCompany();
		company.setName(logisticsCompany.getName());
		company.setPinyin(logisticsCompany.getPinyin());
		logisticsCompanyMap.put(logisticsCompany.getId(), company);
	}
	
	/**
	 * 删除物流公司缓存
	 * 
	 * @param id
	 */
	public static void removeLogisticsCompany(Integer id) {
		logisticsCompanyMap.remove(id);
	}
	
	/**
	 * 获取物流公司名称
	 * 
	 * @param id
	 * @return
	 */
	public static String getLogisticsCompanyName(Integer id) {
		LogisticsCompany logisticsCompany = logisticsCompanyMap.get(id);
		return null != logisticsCompany ? logisticsCompany.getName() : null;
	}
	
	/**
	 * 获取物流公司拼音
	 * 
	 * @param id
	 * @return
	 */
	public static String getLogisticsCompanyPinyin(Integer id) {
		LogisticsCompany logisticsCompany = logisticsCompanyMap.get(id);
		return null != logisticsCompany ? logisticsCompany.getPinyin() : null;
	}
}
