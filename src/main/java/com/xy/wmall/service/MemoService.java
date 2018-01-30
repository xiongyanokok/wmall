package com.xy.wmall.service;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.Memo;

/**
 * Service 接口
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:08
 */
public interface MemoService {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    Memo selectByPrimaryKey(Integer id);
    
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Memo getMemoById(Integer id);
    
    /**
     * 保存数据
     *
     * @param memo
     */
    void save(Memo memo);

    /**
     * 修改数据
     *
     * @param memo
     */
    void update(Memo memo);
    
    /**
     * 删除数据
     * 
     * @param memo
     */
    void remove(Memo memo);
    
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
    void batchSave(List<Memo> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<Memo> list);
    
}
