package com.xy.wmall.mapper;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.Memo;

/**
 * Mapper
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:08
 */
public interface MemoMapper {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    Memo selectByPrimaryKey(Integer id);

    /**
     * 新增数据库记录
     *
     * @param memo
     */
    void insert(Memo memo);

    /**
     * 更新数据库记录
     *
     * @param memo
     */
    void update(Memo memo);

    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    Memo getMemo(Map<String, Object> map);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    List<Memo> listMemo(Map<String, Object> map);
    
    /**
     * 批量保存
     * 
     * @param list
     */
    void batchInsert(List<Memo> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<Memo> list);
    
}
