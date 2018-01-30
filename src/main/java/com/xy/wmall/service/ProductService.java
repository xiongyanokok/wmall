package com.xy.wmall.service;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.Product;

/**
 * Service 接口
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:19
 */
public interface ProductService {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    Product selectByPrimaryKey(Integer id);
    
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Product getProductById(Integer id);
    
    /**
     * 保存数据
     *
     * @param product
     */
    void save(Product product);

    /**
     * 修改数据
     *
     * @param product
     */
    void update(Product product);
    
    /**
     * 删除数据
     * 
     * @param product
     */
    void remove(Product product);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    Product getProduct(Map<String, Object> map);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    List<Product> listProduct(Map<String, Object> map);
    
    /**
     * 批量保存
     * 
     * @param list
     */
    void batchSave(List<Product> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<Product> list);
    
    /**
     * 查询产品列表
     * 
     * @return
     */
    List<Product> listProduct();
    
}
