package com.xy.wmall.mapper;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.LogisticsCompany;

/**
 * Mapper
 * 
 * @author admin
 * @date 2018年01月18日 下午09:30:03
 */
public interface LogisticsCompanyMapper {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    LogisticsCompany selectByPrimaryKey(Integer id);

    /**
     * 新增数据库记录
     *
     * @param logisticsCompany
     */
    void insert(LogisticsCompany logisticsCompany);

    /**
     * 更新数据库记录
     *
     * @param logisticsCompany
     */
    void update(LogisticsCompany logisticsCompany);

    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    LogisticsCompany getLogisticsCompany(Map<String, Object> map);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    List<LogisticsCompany> listLogisticsCompany(Map<String, Object> map);
    
    /**
     * 批量保存
     * 
     * @param list
     */
    void batchInsert(List<LogisticsCompany> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<LogisticsCompany> list);
    
    /**
     * 查询物流公司列表
     * 
     * @return
     */
    List<LogisticsCompany> selectLogisticsCompany();
}
