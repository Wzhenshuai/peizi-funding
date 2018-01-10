package com.icaopan.stock.service.impl;

import com.icaopan.stock.dao.StockUserBlacklistMapper;
import com.icaopan.stock.model.StockUser;
import com.icaopan.stock.service.StockUserBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2016/12/19.
 */
@Service("stockUserBlacklistService")
public class StockUserBlacklistServiceImpl implements StockUserBlacklistService {

    @Autowired
    StockUserBlacklistMapper stockUserBlacklistMapper;

    @Override
    public int queryStockCntByUserIdAndCode(Integer userId, String stockCode) {
        return stockUserBlacklistMapper.queryStockCntByUserIdAndCode(userId, stockCode);
    }

    @Override
    public List<StockUser> queryByUserId(Integer userId) {
        return stockUserBlacklistMapper.queryByUserId(userId);
    }

    @Override
    public int insertBatch(List<StockUser> stockUsers) {
        return stockUserBlacklistMapper.insert(stockUsers);
    }

    @Override
    public int updateBatch(List<StockUser> stockUsers) {
        deleteByUserId(stockUsers.get(0).getUserId());
        int insert =  insertBatch(stockUsers);
        return insert;
    }

    @Override
    public int deleteByUserId(Integer userId) {
        return stockUserBlacklistMapper.deleteByUserId(userId);
    }
}
