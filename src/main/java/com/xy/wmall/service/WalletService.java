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
public interface WalletService extends BaseService<Wallet> {

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
