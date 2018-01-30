package com.xy.wmall.service;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.LogisticsCompany;

/**
 * Service 接口
 * 
 * @author admin
 * @date 2018年01月18日 下午09:30:03
 */
public interface LogisticsCompanyService {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    LogisticsCompany selectByPrimaryKey(Integer id);
    
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    LogisticsCompany getLogisticsCompanyById(Integer id);
    
    /**
     * 保存数据
     *
     * @param logisticsCompany
     */
    void save(LogisticsCompany logisticsCompany);

    /**
     * 修改数据
     *
     * @param logisticsCompany
     */
    void update(LogisticsCompany logisticsCompany);
    
    /**
     * 删除数据
     * 
     * @param logisticsCompany
     */
    void remove(LogisticsCompany logisticsCompany);
    
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
    void batchSave(List<LogisticsCompany> list);
    
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
    List<LogisticsCompany> listLogisticsCompany();
    
}
