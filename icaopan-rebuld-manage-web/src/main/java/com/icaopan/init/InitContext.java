package com.icaopan.init;


import com.icaopan.marketdata.market.MarketDataManager;
import com.icaopan.marketdata.market.MarketdataService;
import com.icaopan.risk.service.RiskTaskService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

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
		// 风控大盘数据初始化
		RiskTaskService riskTaskService = (RiskTaskService) event.getApplicationContext()
				.getBean("riskTaskService");
		try {
			riskTaskService.scheduledFlush();
			riskTaskService.positionComplementaryFlush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
