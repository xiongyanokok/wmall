package com.xy.wmall.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.xy.wmall.common.Assert;
import com.xy.wmall.common.Constant;
import com.xy.wmall.common.utils.ListPageUtils;
import com.xy.wmall.enums.ErrorCodeEnum;
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.exception.WmallException;
import com.xy.wmall.mapper.ProductMapper;
import com.xy.wmall.model.Product;
import com.xy.wmall.service.ProductService;

/**
 * Service 实现
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:19
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
	private ProductMapper productMapper;
	
	/**
     * 根据主键查询
     *
     * @param id
     * @return
     * @throws WmallException
     */
    @Override
    public Product selectByPrimaryKey(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
	    	return productMapper.selectByPrimaryKey(id);
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
    public Product getProductById(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
    		Map<String, Object> map = new HashMap<>();
    		map.put("id", id);
    		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
	    	return productMapper.getProduct(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + id + "】查询失败", e);
		}
    }
    
	/**
     * 保存数据
     *
     * @param product
     * @throws WmallException
     */
    @CacheEvict(value = Constant.PRODUCT_CACHE, allEntries = true)
    @Override
    public void save(Product product) {
    	Assert.notNull(product, "保存数据为空");
    	try {
			productMapper.insert(product);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_INSERT_ERROR, "【" + product.toString() + "】保存失败", e);
		}
    }

    /**
     * 修改数据
     *
     * @param product
     * @throws WmallException
     */
    @CacheEvict(value = Constant.PRODUCT_CACHE, allEntries = true)
    @Override
    public void update(Product product) {
    	Assert.notNull(product, "修改数据为空");
    	try {
    		productMapper.update(product);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_UPDATE_ERROR, "【" + product.toString() + "】修改失败", e);
		}
    }
    
    /**
     * 删除数据
     * 
     * @param product
     * @throws WmallException
     */
	@CacheEvict(value = Constant.PRODUCT_CACHE, allEntries = true)
    @Override
    public void remove(Product product) {
    	Assert.notNull(product, "删除数据为空");
		try {
    		Product deleteProduct = new Product();
    		deleteProduct.setId(product.getId());
    		deleteProduct.setIsDelete(TrueFalseStatusEnum.TRUE.getValue());
    		productMapper.update(deleteProduct);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_DELETE_ERROR, "【" + product.toString() + "】删除失败", e);
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
    public Product getProduct(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return productMapper.getProduct(map);
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
    public List<Product> listProduct(Map<String, Object> map) {
   	 	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return productMapper.listProduct(map);
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
    public void batchSave(List<Product> list) {
    	Assert.notEmpty(list, "批量保存数据为空");
    	try {
			List<List<Product>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<Product> page : pageList) {
				productMapper.batchInsert(page);
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
    public void batchUpdate(List<Product> list) {
    	Assert.notEmpty(list, "批量修改数据为空");
    	try {
			List<List<Product>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<Product> page : pageList) {
				productMapper.batchUpdate(page);
			}
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "批量修改失败", e);
		}
    }
    
    /**
     * 查询产品列表
     * 
     * @return
     */
    @Cacheable(value = Constant.PRODUCT_CACHE)
    @Override
    public List<Product> listProduct() {
    	try {
    		return productMapper.selectProduct();
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "查询产品列表失败", e);
		}
    }
    
}
