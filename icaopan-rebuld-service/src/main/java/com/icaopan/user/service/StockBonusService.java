package com.icaopan.user.service;

import java.util.List;

import com.icaopan.user.bean.StockBonusParams;
import com.icaopan.user.bean.StockBonusScheme;
import com.icaopan.user.model.UserStockBonus;
import com.icaopan.util.page.Page;

public interface StockBonusService {

    public void makeBonus(StockBonusScheme scheme);

    public Page<UserStockBonus> findStockBonusByPage(Page<UserStockBonus> page, StockBonusParams params);
    
    public void updateUserStockBonusToInvalid();

    void doBonusAdjust(Integer id);

	void makeBonusBatch(List<StockBonusScheme> list);

	List<StockBonusScheme> getDistributeFromMarketData(String tradeDate);
}
