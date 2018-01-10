package com.icaopan.marketdata.security;

import com.icaopan.marketdata.constant.ExchangeConsts;
import com.icaopan.marketdata.market.DailyLimit;
import com.icaopan.marketdata.market.DailyLimitPriceService;
import com.icaopan.marketdata.provider.tonglian.HttpUtils;
import com.icaopan.marketdata.provider.tonglian.Ticker;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("securityDataService")
public class SecurityDataService {

	private static final Logger logger = LoggerFactory
			.getLogger(SecurityDataService.class);
	@Autowired
	private SecurityDataBuilder securityDataBuilder;
	@Autowired
	private DailyLimitPriceService dailyLimitPriceService;

	private List<SecurityData> szResults = new ArrayList<SecurityData>();
	private List<SecurityData> shResults = new ArrayList<SecurityData>();

	@PostConstruct
	public void readShSecurityData() {
		shResults.clear();
		try {
			List<Ticker> tickerList = HttpUtils
					.getTickRTSnapshot(ExchangeConsts.SHANG_JIAO_SUO);
			for (Ticker ticker : tickerList) {
				SecurityData securityData = securityDataBuilder.convert(ticker);
				DailyLimit limit = dailyLimitPriceService
						.getDailyLimitPrice(ticker);
				if (limit != null) {
					securityData.setLimitDown(limit.getLimitDown());
					securityData.setLimitUp(limit.getLimitUp());
				}
				shResults.add(securityData);
			}
		} catch (IOException e) {
			logger.error("readSecurityData error", e);
		} catch (EncoderException e) {
			logger.error("readSecurityData error", e);
		}
	}

	@PostConstruct
	public void readSzSecurityData() {
		szResults.clear();
		try {
			List<Ticker> tickerList = HttpUtils
					.getTickRTSnapshot(ExchangeConsts.SHEN_JIAO_SUO);
			for (Ticker ticker : tickerList) {
				SecurityData securityData = securityDataBuilder.convert(ticker);
				DailyLimit limit = dailyLimitPriceService
						.getDailyLimitPrice(ticker);
				if (limit != null) {
					securityData.setLimitDown(limit.getLimitDown());
					securityData.setLimitUp(limit.getLimitUp());
				}
				szResults.add(securityData);
			}
		} catch (IOException e) {
			logger.error("readSecurityData error", e);
		} catch (EncoderException e) {
			logger.error("readSecurityData error", e);
		}
	}

	/**
	 * 根据交易所名称查询股票列表
	 *
	 * @param exchangeCode
	 * @return
	 */
	public List<SecurityData> getSecurityDatas(String exchangeCode) {
		if (StringUtils.equals(exchangeCode, ExchangeConsts.SHEN_JIAO_SUO)) {
//			if (szResults.isEmpty()) {
				readSzSecurityData();
//			}
			return szResults;
		}
		if (StringUtils.equals(exchangeCode, ExchangeConsts.SHANG_JIAO_SUO)) {
//			if (shResults.isEmpty()) {
				readShSecurityData();
//			}
			return shResults;
		}
		return new ArrayList<SecurityData>();
	}
}
