package com.xy.wmall.service;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.Wallet;

/**
 * Service 接口
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:30
 */
public interface WalletService {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    Wallet selectByPrimaryKey(Integer id);
    
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Wallet getWalletById(Integer id);
    
    /**
     * 保存数据
     *
     * @param wallet
     */
    void save(Wallet wallet);

    /**
     * 修改数据
     *
     * @param wallet
     */
    void update(Wallet wallet);
    
    /**
     * 删除数据
     * 
     * @param wallet
     */
    void remove(Wallet wallet);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    Wallet getWallet(Map<String, Object> map);
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     */
    List<Wallet> listWallet(Map<String, Object> map);
    
    /**
     * 批量保存
     * 
     * @param list
     */
    void batchSave(List<Wallet> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<Wallet> list);
    
    /**
     * 查询代理钱包余额
     * 
     * @param proxyIds
     * @return
     */
    Map<Integer, Integer> listWalletBalance(List<Integer> proxyIds);
    
    /**
     * 查询代理钱包余额
     * 
     * @param proxyId
     * @return
     */
    Integer getWalletBalance(Integer proxyId);
 
    /**
     * 统计钱包余额
     * 
     * @param map
     * @return
     */
    Integer getStatisticsWallet(Map<String, Object> map);
    
}
