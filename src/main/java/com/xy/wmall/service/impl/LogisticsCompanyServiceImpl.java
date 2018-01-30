package com.xy.wmall.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.wmall.common.Assert;
import com.xy.wmall.common.utils.ListPageUtils;
import com.xy.wmall.enums.ErrorCodeEnum;
import com.xy.wmall.enums.TrueFalseStatusEnum;
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
public class LogisticsCompanyServiceImpl implements LogisticsCompanyService {

    @Autowired
	private LogisticsCompanyMapper logisticsCompanyMapper;
	
	/**
     * 根据主键查询
     *
     * @param id
     * @return
     * @throws WmallException
     */
    @Override
    public LogisticsCompany selectByPrimaryKey(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
	    	return logisticsCompanyMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + id + "】查询失败", e);
		}
    }
    
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     * @throws WmallException
     */
    @Override
    public LogisticsCompany getLogisticsCompanyById(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
    		Map<String, Object> map = new HashMap<>();
    		map.put("id", id);
    		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
	    	return logisticsCompanyMapper.getLogisticsCompany(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + id + "】查询失败", e);
		}
    }
    
	/**
     * 保存数据
     *
     * @param logisticsCompany
     * @throws WmallException
     */
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
    @Override
    public void remove(LogisticsCompany logisticsCompany) {
    	Assert.notNull(logisticsCompany, "删除数据为空");
		try {
    		LogisticsCompany deleteLogisticsCompany = new LogisticsCompany();
    		deleteLogisticsCompany.setId(logisticsCompany.getId());
    		deleteLogisticsCompany.setIsDelete(TrueFalseStatusEnum.TRUE.getValue());
    		logisticsCompanyMapper.update(deleteLogisticsCompany);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_DELETE_ERROR, "【" + logisticsCompany.toString() + "】删除失败", e);
    	}
    }
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     * @throws WmallException
     */
    @Override
    public LogisticsCompany getLogisticsCompany(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return logisticsCompanyMapper.getLogisticsCompany(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + map + "】查询对象失败", e);
		}
    }
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     * @throws WmallException
     */
    @Override
    public List<LogisticsCompany> listLogisticsCompany(Map<String, Object> map) {
   	 	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return logisticsCompanyMapper.listLogisticsCompany(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + map + "】查询列表失败", e);
		}
    }
    
    /**
     * 批量保存
     * 
     * @param list
     * @throws WmallException
     */
    @Override
    public void batchSave(List<LogisticsCompany> list) {
    	Assert.notEmpty(list, "批量保存数据为空");
    	try {
			List<List<LogisticsCompany>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<LogisticsCompany> page : pageList) {
				logisticsCompanyMapper.batchInsert(page);
			}
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "批量保存失败", e);
		}
    }
    
    /**
     * 批量更新
     * 
     * @param list
     * @throws WmallException
     */
    @Override
    public void batchUpdate(List<LogisticsCompany> list) {
    	Assert.notEmpty(list, "批量修改数据为空");
    	try {
			List<List<LogisticsCompany>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<LogisticsCompany> page : pageList) {
				logisticsCompanyMapper.batchUpdate(page);
			}
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "批量修改失败", e);
		}
    }
    
    /**
     * 查询物流公司列表
     * 
     * @return
     */
    @Override
    public List<LogisticsCompany> listLogisticsCompany() {
    	try {
	    	return logisticsCompanyMapper.selectLogisticsCompany();
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "查询物流公司列表失败", e);
		}
    }
    
}
