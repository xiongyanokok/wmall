package com.xy.wmall.service;

import java.util.List;

import com.xy.wmall.model.Product;

/**
 * Service 接口
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:19
 */
public interface ProductService extends BaseService<Product> {

    /**
     * 查询产品列表
     * 
     * @return
     */
    List<Product> listProduct();
    
}
