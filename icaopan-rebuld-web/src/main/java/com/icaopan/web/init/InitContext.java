package com.icaopan.web.init;


import com.icaopan.marketdata.market.MarketdataService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.icaopan.marketdata.market.MarketDataManager;

public class InitContext implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//初始化行情信息
		MarketDataManager marketDataManager = (MarketDataManager) event.getApplicationContext()
				.getBean("marketDataManager");
		try {
			marketDataManager.readMarketData();
		} catch (Exception e) {
			e.printStackTrace();
		}

		MarketdataService marketdataService = (MarketdataService) event.getApplicationContext()
				.getBean("marketdataService");
		try {
			marketdataService.handleAilyChangePercent(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
