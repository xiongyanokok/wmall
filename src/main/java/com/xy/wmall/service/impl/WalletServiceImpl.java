package com.xy.wmall.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.wmall.common.Assert;
import com.xy.wmall.common.utils.CommonUtils;
import com.xy.wmall.enums.ErrorCodeEnum;
import com.xy.wmall.exception.WmallException;
import com.xy.wmall.mapper.WalletMapper;
import com.xy.wmall.model.Wallet;
import com.xy.wmall.service.WalletService;

/**
 * Service 实现
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:30
 */
@Service
public class WalletServiceImpl extends BaseServiceImpl<WalletMapper, Wallet> implements WalletService {

    @Autowired
	private WalletMapper walletMapper;
	
    /**
     * 批量查询代理钱包余额
     * 
     * @param proxyIds
     * @return
     */
    @Override
    public Map<Integer, Integer> listWalletBalance(List<Integer> proxyIds) {
    	Assert.notEmpty(proxyIds, "proxyIds为空");
    	try {
    		Map<String, Object> map = CommonUtils.defaultQueryMap();
    		map.put("proxyIds", proxyIds);
    		map.put("groupBy", "proxy_id");
    		List<Wallet> wallets = walletMapper.listWalletBalance(map);
    		if (CollectionUtils.isEmpty(wallets)) {
    			return Collections.emptyMap();
    		}
    		Map<Integer, Integer> balanceMap = new HashMap<>(wallets.size());
    		for (Wallet wallet : wallets) {
    			balanceMap.put(wallet.getProxyId(), wallet.getPrice());
    		}
    		return balanceMap;
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "【" + proxyIds + "】查询余额失败", e);
		}
    }
    
    /**
     * 查询代理钱包余额
     * 
     * @param proxyId
     * @return
     */
    @Override
    public Integer getWalletBalance(Integer proxyId) {
    	Assert.notNull(proxyId, "proxyId为空");
    	try {
    		Map<String, Object> map = CommonUtils.defaultQueryMap();
    		map.put("proxyId", proxyId);
    		List<Wallet> wallets = walletMapper.listWalletBalance(map);
    		if (CollectionUtils.isEmpty(wallets) || null == wallets.get(0)) {
    			return 0;
    		}
    		return wallets.get(0).getPrice();
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "【" + proxyId + "】查询余额失败", e);
		}
    }
   
    /**
     * 统计钱包余额
     * 
     * @param map
     * @return
     */
    @Override
    public Integer getStatisticsWallet(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
    		return walletMapper.getStatisticsWallet(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "【" + map + "】统计钱包余额失败", e);
		}
    }
    
}
