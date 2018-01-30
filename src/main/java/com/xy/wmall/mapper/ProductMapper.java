package com.xy.wmall.mapper;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.Product;

/**
 * Mapper
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:19
 */
public interface ProductMapper {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    Product selectByPrimaryKey(Integer id);

    /**
     * 新增数据库记录
     *
     * @param product
     */
    void insert(Product product);

    /**
     * 更新数据库记录
     *
     * @param product
     */
    void update(Product product);

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
    void batchInsert(List<Product> list);
    
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
    List<Product> selectProduct();
    
}
