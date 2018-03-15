package com.xy.wmall.mapper;

import java.util.List;

import com.xy.wmall.model.LogisticsCompany;

/**
 * Mapper
 * 
 * @author admin
 * @date 2018年01月18日 下午09:30:03
 */
public interface LogisticsCompanyMapper extends BaseMapper<LogisticsCompany> {

    /**
     * 查询物流公司列表
     * 
     * @return
     */
    List<LogisticsCompany> listLogisticsCompany();
    
}
