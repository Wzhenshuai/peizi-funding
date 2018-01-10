package com.icaopan.stock.service.impl;

import com.icaopan.stock.dao.StockUserWhitelistMapper;
import com.icaopan.stock.model.StockUser;
import com.icaopan.stock.service.StockUserBlacklistService;
import com.icaopan.stock.service.StockUserWhitelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kanglj on 2017/8/28.
 */
@Service("stockUserWhitelistService")
public class StockUserWhitelistServiceImpl implements StockUserWhitelistService {

    @Autowired
    StockUserWhitelistMapper stockUserWhitelistMapper;

    @Override
    public int queryStockCntByUserIdAndCode(Integer userId, String stockCode) {
        return stockUserWhitelistMapper.queryStockCntByUserIdAndCode(userId, stockCode);
    }

    @Override
    public List<StockUser> queryByUserId(Integer userId) {
        return stockUserWhitelistMapper.queryByUserId(userId);
    }

    @Override
    public int insertBatch(List<StockUser> stockUsers) {
        return stockUserWhitelistMapper.insert(stockUsers);
    }

    @Override
    public int updateBatch(List<StockUser> stockUsers) {
        deleteByUserId(stockUsers.get(0).getUserId());
        int insert =  insertBatch(stockUsers);
        return insert;
    }

    @Override
    public int deleteByUserId(Integer userId) {
        return stockUserWhitelistMapper.deleteByUserId(userId);
    }
}
