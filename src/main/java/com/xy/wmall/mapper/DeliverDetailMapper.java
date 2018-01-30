package com.xy.wmall.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xy.wmall.model.DeliverDetail;

/**
 * Mapper
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:03
 */
public interface DeliverDetailMapper {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    DeliverDetail selectByPrimaryKey(Integer id);

    /**
     * 新增数据库记录
     *
     * @param deliverDetail
     */
    void insert(DeliverDetail deliverDetail);

    /**
     * 更新数据库记录
     *
     * @param deliverDetail
     */
    void update(DeliverDetail deliverDetail);
    
    /**
     * 删除数据库记录
     *
     * @param deliverId
     */
    void delete(@Param("deliverId") Integer deliverId);

    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    DeliverDetail getDeliverDetail(Map<String, Object> map);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    List<DeliverDetail> listDeliverDetail(Map<String, Object> map);
    
    /**
     * 批量保存
     * 
     * @param list
     */
    void batchInsert(List<DeliverDetail> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<DeliverDetail> list);
    
}
