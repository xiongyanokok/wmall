package com.xy.wmall.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.wmall.common.Assert;
import com.xy.wmall.common.utils.ListPageUtils;
import com.xy.wmall.enums.ErrorCodeEnum;
import com.xy.wmall.enums.TrueFalseStatusEnum;
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
public class WalletServiceImpl implements WalletService {

    @Autowired
	private WalletMapper walletMapper;
	
	/**
     * 根据主键查询
     *
     * @param id
     * @return
     * @throws WmallException
     */
    @Override
    public Wallet selectByPrimaryKey(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
	    	return walletMapper.selectByPrimaryKey(id);
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
    public Wallet getWalletById(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
    		Map<String, Object> map = new HashMap<>();
    		map.put("id", id);
    		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
	    	return walletMapper.getWallet(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + id + "】查询失败", e);
		}
    }
    
	/**
     * 保存数据
     *
     * @param wallet
     * @throws WmallException
     */
    @Override
    public void save(Wallet wallet) {
    	Assert.notNull(wallet, "保存数据为空");
    	try {
			walletMapper.insert(wallet);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_INSERT_ERROR, "【" + wallet.toString() + "】保存失败", e);
		}
    }

    /**
     * 修改数据
     *
     * @param wallet
     * @throws WmallException
     */
    @Override
    public void update(Wallet wallet) {
    	Assert.notNull(wallet, "修改数据为空");
    	try {
    		walletMapper.update(wallet);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_UPDATE_ERROR, "【" + wallet.toString() + "】修改失败", e);
		}
    }
    
    /**
     * 删除数据
     * 
     * @param wallet
     * @throws WmallException
     */
    @Override
    public void remove(Wallet wallet) {
    	Assert.notNull(wallet, "删除数据为空");
		try {
    		Wallet deleteWallet = new Wallet();
    		deleteWallet.setId(wallet.getId());
    		deleteWallet.setIsDelete(TrueFalseStatusEnum.TRUE.getValue());
    		walletMapper.update(deleteWallet);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_DELETE_ERROR, "【" + wallet.toString() + "】删除失败", e);
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
    public Wallet getWallet(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return walletMapper.getWallet(map);
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
    public List<Wallet> listWallet(Map<String, Object> map) {
   	 	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return walletMapper.listWallet(map);
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
    public void batchSave(List<Wallet> list) {
    	Assert.notEmpty(list, "批量保存数据为空");
    	try {
			List<List<Wallet>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<Wallet> page : pageList) {
				walletMapper.batchInsert(page);
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
    public void batchUpdate(List<Wallet> list) {
    	Assert.notEmpty(list, "批量修改数据为空");
    	try {
			List<List<Wallet>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<Wallet> page : pageList) {
				walletMapper.batchUpdate(page);
			}
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "批量修改失败", e);
		}
    }
    
    /**
     * 批量查询代理钱包余额
     * 
     * @param proxyIds
     * @return
     */
    @Override
    public Map<Integer, Integer> listWalletBalance(List<Integer> proxyIds) {
    	Assert.notEmpty(proxyIds, "查询数据为空");
    	try {
    		Map<String, Object> map = new HashMap<>();
    		map.put("proxyIds", proxyIds);
    		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
    		map.put("groupBy", "proxy_id");
    		List<Wallet> wallets = walletMapper.listWalletBalance(map);
    		if (CollectionUtils.isEmpty(wallets) || null == wallets.get(0)) {
    			return Collections.emptyMap();
    		}
    		Map<Integer, Integer> balanceMap = new HashMap<>();
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
    	Assert.notNull(proxyId, "查询数据为空");
    	try {
    		Map<String, Object> map = new HashMap<>();
    		map.put("proxyId", proxyId);
    		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
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
