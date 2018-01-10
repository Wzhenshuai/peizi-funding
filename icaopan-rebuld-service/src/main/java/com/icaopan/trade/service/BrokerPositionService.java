package com.icaopan.trade.service;

import java.util.List;

import com.icaopan.trade.bean.BrokerPositionQueryParams;
import com.icaopan.trade.bean.BrokerPositionResult;
import com.icaopan.trade.model.BrokerPosition;

public interface BrokerPositionService {

	
	List<BrokerPositionResult> selectByCondition(BrokerPositionQueryParams params);
	
	BrokerPosition selectById(Integer id);

	BrokerPositionResult queryDetailByBrokerPositionId(Integer id);

	void adjustChannelPositionByBrokerPosition(BrokerPositionResult result,
			List<String> ids, List<String> sides, List<String> amounts,
			List<String> costPrices);

	void saveBrokerPosition(String account, String stockCode, double amount,
			double costPrice);
}
