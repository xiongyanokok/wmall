package com.xy.wmall.mapper;

import java.util.List;
import java.util.Map;

/**
 * BaseMapper
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:08
 */
public interface BaseMapper<T> {

	/**
	 * 新增数据库记录
	 *
	 * @param t
	 */
	void insert(T t);

	/**
	 * 更新数据库记录
	 *
	 * @param t
	 */
	void update(T t);
	
	/**
	 * 删除数据库记录
	 * 
	 * @param id
	 */
	void delete(T t);

	/**
	 * 根据map查询
	 * 
	 * @param map
	 * @return
	 */
	T getByMap(Map<String, Object> map);

	/**
	 * 根据map查询
	 * 
	 * @param map
	 * @return
	 */
	List<T> listByMap(Map<String, Object> map);

	/**
	 * 批量保存
	 * 
	 * @param list
	 */
	void batchInsert(List<T> list);

	/**
	 * 批量更新
	 * 
	 * @param list
	 */
	void batchUpdate(List<T> list);

}
