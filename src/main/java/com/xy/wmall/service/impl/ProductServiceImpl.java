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
public class ProductServiceImpl extends BaseServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
	private ProductMapper productMapper;
	
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
    		productMapper.delete(product);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_DELETE_ERROR, "【" + product.toString() + "】删除失败", e);
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
    		return productMapper.listProduct();
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "查询产品列表失败", e);
		}
    }
    
}
