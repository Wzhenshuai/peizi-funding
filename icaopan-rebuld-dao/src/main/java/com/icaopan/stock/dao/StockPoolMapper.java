package com.icaopan.stock.dao;

import com.icaopan.stock.bean.StockPoolParams;
import com.icaopan.stock.model.StockPool;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockPoolMapper {

    /**
     * 添加股票池信息
     *
     * @param stockPool
     * @return boolean
     */
    boolean insert(StockPool stockPool);

    /**
     * 批量保存股票信息
     *
     * @param record
     * @return boolean
     */
    boolean inertBatch(List<StockPool> record);

    /**
     * 批量删除
     */
    boolean deleteBatch(List<Integer> ids);

    /**
     * 查询所有股票信息
     *
     * @param page
     * @return List<StockPool>
     */
    List<StockPool> findByPage(@Param("page") Page page, @Param("params") StockPoolParams params);

    /**
     * 根棍股票池信息
     *
     * @param id
     * @param code
     * @param name
     * @param type
     * @return boolean
     */
    boolean update(@Param("id") Integer id, @Param("name") String name, @Param("code") String code, @Param("customerId") Integer customerId, @Param("type") String type);

    /**
     * 根据主键查找股票池信息
     *
     * @param id
     * @return StockPool
     */
    StockPool findByPrimaryId(Integer id);


    /**
     * 根据股票编号，股票名 查找股票池信息
     *
     * @param stockCode
     * @param stockName
     * @return stockPool
     */
    List<StockPool> findByNameAndCode(@Param("stockName") String stockName, @Param("stockCode") String stockCode);

    /**
     * 根据顾客ID，股票类型删除股票信息
     *
     * @param customerId,stockPoolType
     * @return boolean
     */
    boolean deleteByIdAndStockType(@Param("customerId") Integer customerId,
                                   @Param("stockPoolType") String stockPoolType);

    /**
     * 根据顾客ID 股票类型查询股票池信息
     *
     * @param customerId,stockPoolType
     * @return List<StockPool>
     */
    List<StockPool> findByIdAndStockType(@Param("customerId") Integer customerId,
                                         @Param("stockPoolType") String stockPoolType);

    int findBannedStockCnt(@Param("stockCode") String stockCode, @Param("customerId") Integer customerId);

    int selectCntByCodeAndType(@Param("stockCode") String stockCode, @Param("stockType") String stockType);
}