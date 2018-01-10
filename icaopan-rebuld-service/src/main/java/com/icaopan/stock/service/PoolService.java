package com.icaopan.stock.service;

import com.icaopan.enums.enumBean.StockPoolType;
import com.icaopan.stock.bean.StockPoolParams;
import com.icaopan.stock.model.StockPool;
import com.icaopan.util.page.Page;

import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2016/12/19.
 */
public interface PoolService {

    boolean save(StockPool stockPool);

    boolean saveBatch(List<StockPool> stockPool);

    boolean update(Integer id, String name, String code, Integer customerId, String type);

    public boolean containName(String name);

    public boolean containCode(String code);

    boolean isBannedStock(String stockCode, Integer customerId);

    boolean isSmallPlatesStock(String stockCode);

    boolean isGemStock(String stockCode);

    public StockPool findByPrimaryId(Integer id);

    public List<StockPool> findByCode(String code);

    Page findByPage(Page page, StockPoolParams params);

    boolean delete(Integer customerId, StockPoolType type);

    boolean deleteBatch(Integer[] ids);

    List<StockPool> findByIdAndType(Integer customerId, StockPoolType types);

    String updateStockSecurity();

}
