package com.icaopan.stock.service;

import com.icaopan.stock.bean.StockPoolParams;
import com.icaopan.stock.bean.StockSecurityParams;
import com.icaopan.stock.model.StockPool;
import com.icaopan.stock.model.StockSecurity;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.StockDetailVO;

import java.util.List;

/**
 * Created by RoyLeong@royleo.xyz on 2016/11/15.
 */
public interface SecurityService {

    public int saveStockPool(StockPool stockPool);              //保存股票池信息

    public int saveBatchStockPool(List<StockPool> listStockPool);   //批量保存股票池信息(批量导入)

    public Page findStockPoolByPage(Page page, StockPoolParams params); // 查询所有股票信息，并作分页处理

    public int deleteStockPool(StockPool stockPool);        //根据资金方ID和类型删除股票池

    public List<StockPool> findStockPoolByCustomerId(Integer customerId);   ///根据资金方ID，查找股票池

    public boolean saveStockSecurity(StockSecurity stockSecurity);      //保存证券信息

    public boolean saveBatchStockSecurity(List<StockSecurity> stockSecurity); //批量保存证券信息

    StockSecurity findStockSecurityById(String internalSecurityId);    //根据证券编号查找证券信息

    public StockSecurity findByNameAndCode(String name, String Code);

    Page findStockSecurityByPage(Page page, StockSecurityParams params);     //分页查找所有证券信息

    List<StockSecurity> findByCodeStartIndex(String codeStartIndex);

    public List<StockSecurity> findAllStockSecurity();

    public boolean update(String id, String name, String firstLetter, Boolean suspensionFlag);     //更新证券名称拼音首字母缩写

    public int updateFlag(String internalSecurityId);           //更新证券是否停牌信息

    public boolean containName(String name);

    public boolean containCode(String code);

    public StockDetailVO queryStockDetail(String securityCode);

    public String queryNameByCode(String securityCode);

    /**
     * 更新股票信息
     */
    public String updateAll();

	List<StockSecurity> findAllStockSecurityByExchange(String exchange);

	List<String> findAllStockCode();
}
