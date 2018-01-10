package com.icaopan.risk.service.impl;

import com.icaopan.enums.enumBean.RiskResult;
import com.icaopan.enums.enumBean.SeniorRiskType;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.marketdata.market.MarketdataService;
import com.icaopan.risk.service.RiskFilterChain;
import com.icaopan.risk.service.RiskService;
import com.icaopan.stock.service.StockUserBlacklistService;
import com.icaopan.trade.model.Placement;
import com.icaopan.trade.service.ComplianceService;
import com.icaopan.user.model.User;

import elf.api.marketdata.marketdata.MarketDataSnapshot;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * desc 用户昨收涨跌停股票振幅风控
 *
 * Created by kanglj on 17/3/7.
 */
@Service("amplitudeRiskService")
public class AmplitudeRiskServiceImpl implements RiskService {

    @Autowired
    private StockUserBlacklistService stockUserBlacklistService;
    @Autowired
    private ComplianceService complianceService;
    @Autowired
    private MarketdataService marketdataService;
    private static Logger log = com.icaopan.log.LogUtil.getTradeLogger();

    @Override
    public RiskResult doRiskFilter(User user, Placement placement, RiskFilterChain chain) {
        if(StringUtils.equals(placement.getSide().getCode(), TradeSide.SELL.getCode())){
            // 继续执行过滤链的下一节点
            return chain.doRiskFilter(user, placement, chain);
        }
        // 如果用户开启昨收涨跌停股票振幅风控
        if((user.getRiskFlag() & SeniorRiskType.Amplitude.getNum()) == SeniorRiskType.Amplitude.getNum()){
            // 获取昨收涨停股票列表
            Map<String, MarketDataSnapshot> upChangePercentMap = marketdataService.getUpChangePercentMap();
            MarketDataSnapshot upSnapshot = upChangePercentMap.get(placement.getSecurityCode());
            // 如果该股票在涨停股票列表中存在,并且振幅大于等于涨停振幅限制
//            if(upSnapshot != null && new BigDecimal(upSnapshot.getAmplitude()).compareTo(user.getRiskUpAmplitude()) >= 0){
//                log.info((new StringBuilder("用户ID: ")).append(user.getId())
//                        .append(", 姓名: ").append(user.getRealName()).append(", 风控返回结果:")
//                        .append(RiskResult.UpAmplitudeRiskFail.getDisplay()).toString());
//                complianceService.saveLog(user, placement, "股票买入", RiskResult.UpAmplitudeRiskFail.getDisplay());
//                return RiskResult.UpAmplitudeRiskFail;
//            }
            // 获取昨收跌停股票列表
            Map<String, MarketDataSnapshot> downChangePercentMap = marketdataService.getDownChangePercentMap();
            MarketDataSnapshot downSnapshot = downChangePercentMap.get(placement.getSecurityCode());
            //  如果该股票在跌停股票列表中存在,并且振幅大于等于跌停振幅限制
//            if(downSnapshot != null && new BigDecimal(downSnapshot.getAmplitude()).compareTo(user.getRiskDownAmplitude()) >= 0){
//                log.info((new StringBuilder("用户ID: ")).append(user.getId())
//                        .append(", 姓名: ").append(user.getRealName()).append(", 风控返回结果:")
//                        .append(RiskResult.DownAmplitudeRiskFail.getDisplay()).toString());
//                complianceService.saveLog(user, placement, "股票买入", RiskResult.DownAmplitudeRiskFail.getDisplay());
//                return RiskResult.DownAmplitudeRiskFail;
//            }
        }
        // 继续执行过滤链的下一节点
        return chain.doRiskFilter(user, placement, chain);
    }
}
