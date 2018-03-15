package com.xy.wmall.service;

import java.util.List;

import com.xy.wmall.model.LogisticsCompany;

/**
 * Service 接口
 * 
 * @author admin
 * @date 2018年01月18日 下午09:30:03
 */
public interface LogisticsCompanyService extends BaseService<LogisticsCompany> {

    /**
     * 查询物流公司列表
     * 
     * @return
     */
    List<LogisticsCompany> listLogisticsCompany();
    
}
