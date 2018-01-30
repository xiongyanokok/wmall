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
public interface WalletMapper {

	/**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    Wallet selectByPrimaryKey(Integer id);

    /**
     * 新增数据库记录
     *
     * @param wallet
     */
    void insert(Wallet wallet);

    /**
     * 更新数据库记录
     *
     * @param wallet
     */
    void update(Wallet wallet);

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
    void batchInsert(List<Wallet> list);
    
    /**
     * 批量更新
     * 
     * @param list
     */
    void batchUpdate(List<Wallet> list);
    
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
