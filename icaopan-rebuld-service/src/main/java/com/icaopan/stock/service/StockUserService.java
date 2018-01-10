package com.icaopan.stock.service;

import com.icaopan.stock.model.StockUser;

import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2017/8/30.
 */
public interface StockUserService {

    int queryStockCntByUserIdAndCode(Integer userId, String stockCode);  // 按用户ID和股票代码查询股票是否在名单中

    List<StockUser> queryByUserId(Integer userId);  //根据用户ID查询在名单中的股票数据

    int insertBatch(List<StockUser> stockUsers);      //批量插入数据

    int updateBatch(List<StockUser> stockUsers);        //批量更新数据

    int deleteByUserId(Integer userId);                 //根据用户ID删除数据
}
