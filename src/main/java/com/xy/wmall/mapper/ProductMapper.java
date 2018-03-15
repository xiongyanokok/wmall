package com.xy.wmall.mapper;

import java.util.List;

import com.xy.wmall.model.Product;

/**
 * Mapper
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:19
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 查询产品列表
     * 
     * @return
     */
    List<Product> listProduct();
    
}
