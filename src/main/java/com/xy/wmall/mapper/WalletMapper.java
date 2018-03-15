package com.xy.wmall.mapper;

import java.util.List;
import java.util.Map;

import com.xy.wmall.model.Wallet;

/**
 * Mapper
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:30
 */
public interface WalletMapper extends BaseMapper<Wallet> {

    /**
     * 查询余额
     * 
     * @param map
     * @return
     */
    List<Wallet> listWalletBalance(Map<String, Object> map);
    
    /**
     * 统计钱包余额
     * 
     * @param map
     * @return
     */
    Integer getStatisticsWallet(Map<String, Object> map);
    
}
