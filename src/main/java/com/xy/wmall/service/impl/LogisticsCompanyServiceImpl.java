package com.xy.wmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.xy.wmall.common.Assert;
import com.xy.wmall.common.Constant;
import com.xy.wmall.enums.ErrorCodeEnum;
import com.xy.wmall.exception.WmallException;
import com.xy.wmall.mapper.LogisticsCompanyMapper;
import com.xy.wmall.model.LogisticsCompany;
import com.xy.wmall.service.LogisticsCompanyService;

/**
 * Service 实现
 * 
 * @author admin
 * @date 2018年01月18日 下午09:30:03
 */
@Service
public class LogisticsCompanyServiceImpl extends BaseServiceImpl<LogisticsCompanyMapper, LogisticsCompany> implements LogisticsCompanyService {

    @Autowired
	private LogisticsCompanyMapper logisticsCompanyMapper;
	
    /**
     * 保存数据
     *
     * @param logisticsCompany
     * @throws WmallException
     */
    @CacheEvict(value = Constant.LOGISTICS_CACHE, allEntries = true)
    @Override
    public void save(LogisticsCompany logisticsCompany) {
    	Assert.notNull(logisticsCompany, "保存数据为空");
    	try {
			logisticsCompanyMapper.insert(logisticsCompany);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_INSERT_ERROR, "【" + logisticsCompany.toString() + "】保存失败", e);
		}
    }

    /**
     * 修改数据
     *
     * @param logisticsCompany
     * @throws WmallException
     */
    @CacheEvict(value = Constant.LOGISTICS_CACHE, allEntries = true)
    @Override
    public void update(LogisticsCompany logisticsCompany) {
    	Assert.notNull(logisticsCompany, "修改数据为空");
    	try {
    		logisticsCompanyMapper.update(logisticsCompany);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_UPDATE_ERROR, "【" + logisticsCompany.toString() + "】修改失败", e);
		}
    }
    
    /**
     * 删除数据
     * 
     * @param logisticsCompany
     * @throws WmallException
     */
    @CacheEvict(value = Constant.LOGISTICS_CACHE, allEntries = true)
    @Override
    public void remove(LogisticsCompany logisticsCompany) {
    	Assert.notNull(logisticsCompany, "删除数据为空");
		try {
    		logisticsCompanyMapper.delete(logisticsCompany);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_DELETE_ERROR, "【" + logisticsCompany.toString() + "】删除失败", e);
    	}
    }
    
    /**
     * 查询物流公司列表
     * 
     * @return
     */
    @Cacheable(value = Constant.LOGISTICS_CACHE)
    @Override
    public List<LogisticsCompany> listLogisticsCompany() {
    	try {
	    	return logisticsCompanyMapper.listLogisticsCompany();
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "查询物流公司列表失败", e);
		}
    }
    
}
