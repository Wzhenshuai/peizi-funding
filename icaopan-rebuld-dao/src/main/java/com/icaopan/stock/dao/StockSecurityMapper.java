package com.icaopan.stock.dao;


import com.icaopan.stock.bean.StockSecurityParams;
import com.icaopan.stock.model.StockSecurity;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockSecurityMapper {

    /**
     * 保存证券信息
     *
     * @param record
     * @return boolean
     */
    boolean insert(StockSecurity record);

    /**
     * 批量保存证券信息
     *
     * @param stockSecurity
     * @return boolean
     */
    boolean insertBatch(List<StockSecurity> stockSecurity);

    /**
     * 根据证券编号查询证券信息
     *
     * @param internalSecurityId
     * @return StockSecurity
     */
    StockSecurity selectByPrimaryKey(String internalSecurityId);

    /**
     * 查询所有证券信息,分页处理
     *
     * @param page
     * @return List<StockSecurity>
     */
    List<StockSecurity> findAllByPage(@Param("page") Page page, @Param("params") StockSecurityParams params);

    /**
     * 根据股票编码和股票信息查询股票信息
     * 用户验证股票名和股票编号是否入库
     *
     * @param name
     * @param code
     * @return StockSecurity
     */
    StockSecurity findByNameAndCode(@Param("name") String name, @Param("code") String code);

    /**
     * 更新证券拼音缩写,同时，证券名称也会发生改变
     *
     * @param internalSecurityId,name,firstLetter
     * @return boolean
     */
    boolean update(@Param("internalSecurityId") String internalSecurityId,
                   @Param("name") String name,
                   @Param("firstLetter") String firstLetter,
                   @Param("suspensionFlag") Boolean suspensionFlag);

    /**
     * 更新股票状态
     *
     * @param internalSecurityId,suspensionFlag
     * @return boolean
     */
    boolean updateFlag(@Param("internalSecurityId") String internalSecurityId, @Param("suspensionFlag") boolean suspensionFlag);

    /**
     * 根据股票类型查询股票信息
     * 中小板以0开头
     * 创业板以3开头
     * @param codeStartIndex
     * @return
     * */
    List<StockSecurity> findByCodeStartIndex(@Param("codeStartIndex")String codeStartIndex);

    public List<StockSecurity> findAllStockSecurity();
    
    public List<StockSecurity> findAllStockSecurityByExchange(@Param("exchange")String exchange);
    
    public List<String> findAllStockCode();
}