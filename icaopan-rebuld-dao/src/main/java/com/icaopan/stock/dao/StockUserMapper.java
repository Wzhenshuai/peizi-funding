package com.icaopan.stock.dao;

import com.icaopan.stock.model.StockUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2017/8/30.
 */
public interface StockUserMapper {

    int queryStockCntByUserIdAndCode(@Param("userId") Integer userId, @Param("stockCode") String stockCode);

    int insert(@Param("stockUserList") List<StockUser> stockUsers);

    int update(@Param("stockUserList") List<StockUser> stockUsers);

    List<StockUser> queryByUserId(@Param("userId") Integer userId);

    int deleteByUserId(@Param("userId") Integer userId);

}
