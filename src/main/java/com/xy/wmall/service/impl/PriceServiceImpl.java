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
import com.xy.wmall.mapper.PriceMapper;
import com.xy.wmall.model.Price;
import com.xy.wmall.service.PriceService;

/**
 * Service 实现
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:16
 */
@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
	private PriceMapper priceMapper;
	
	/**
     * 根据主键查询
     *
     * @param id
     * @return
     * @throws WmallException
     */
    @Override
    public Price selectByPrimaryKey(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
	    	return priceMapper.selectByPrimaryKey(id);
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
    public Price getPriceById(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
    		Map<String, Object> map = new HashMap<>();
    		map.put("id", id);
    		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
	    	return priceMapper.getPrice(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + id + "】查询失败", e);
		}
    }
    
	/**
     * 保存数据
     *
     * @param price
     * @throws WmallException
     */
    @Override
    public void save(Price price) {
    	Assert.notNull(price, "保存数据为空");
    	try {
			priceMapper.insert(price);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_INSERT_ERROR, "【" + price.toString() + "】保存失败", e);
		}
    }

    /**
     * 修改数据
     *
     * @param price
     * @throws WmallException
     */
    @Override
    public void update(Price price) {
    	Assert.notNull(price, "修改数据为空");
    	try {
    		priceMapper.update(price);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_UPDATE_ERROR, "【" + price.toString() + "】修改失败", e);
		}
    }
    
    /**
     * 删除数据
     * 
     * @param price
     * @throws WmallException
     */
    @Override
    public void remove(Price price) {
    	Assert.notNull(price, "删除数据为空");
		try {
    		Price deletePrice = new Price();
    		deletePrice.setId(price.getId());
    		deletePrice.setIsDelete(TrueFalseStatusEnum.TRUE.getValue());
    		priceMapper.update(deletePrice);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_DELETE_ERROR, "【" + price.toString() + "】删除失败", e);
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
    public Price getPrice(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return priceMapper.getPrice(map);
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
    public List<Price> listPrice(Map<String, Object> map) {
   	 	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return priceMapper.listPrice(map);
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
    public void batchSave(List<Price> list) {
    	Assert.notEmpty(list, "批量保存数据为空");
    	try {
			List<List<Price>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<Price> page : pageList) {
				priceMapper.batchInsert(page);
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
    public void batchUpdate(List<Price> list) {
    	Assert.notEmpty(list, "批量修改数据为空");
    	try {
			List<List<Price>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<Price> page : pageList) {
				priceMapper.batchUpdate(page);
			}
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "批量修改失败", e);
		}
    }
    
}
