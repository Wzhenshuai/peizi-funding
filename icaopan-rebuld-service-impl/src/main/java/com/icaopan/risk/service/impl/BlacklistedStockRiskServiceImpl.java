package com.icaopan.risk.service.impl;

import com.icaopan.enums.enumBean.RiskResult;
import com.icaopan.enums.enumBean.SeniorRiskType;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.risk.service.RiskFilterChain;
import com.icaopan.risk.service.RiskService;
import com.icaopan.stock.service.StockUserBlacklistService;
import com.icaopan.trade.model.ComplianceResult;
import com.icaopan.trade.model.Placement;
import com.icaopan.trade.service.ComplianceService;
import com.icaopan.user.model.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * desc 用户的股票黑名单风控管理
 *
 * Created by kanglj on 17/3/7.
 */
@Service("blacklistedStockRiskService")
public class BlacklistedStockRiskServiceImpl implements RiskService {

    @Autowired
    private StockUserBlacklistService stockUserBlacklistService;
    @Autowired
    private ComplianceService complianceService;
    private static Logger log = com.icaopan.log.LogUtil.getTradeLogger();

    @Override
    public RiskResult doRiskFilter(User user, Placement placement, RiskFilterChain chain) {
        if(StringUtils.equals(placement.getSide().getCode(), TradeSide.SELL.getCode())){
            // 继续执行过滤链的下一节点
            return chain.doRiskFilter(user, placement, chain);
        }
        // 如果用户开启股票黑名单风控标志
        if((user.getRiskFlag() & SeniorRiskType.Blacklist.getNum()) == SeniorRiskType.Blacklist.getNum()){
            int count = stockUserBlacklistService.queryStockCntByUserIdAndCode(user.getId(), placement.getSecurityCode());
            // 如果该股票在用户的股票黑名单中
            if(count > 0){
                log.info((new StringBuilder("用户ID: ")).append(user.getId())
                        .append(", 姓名: ").append(user.getRealName()).append(", 风控返回结果:")
                        .append(RiskResult.StockBlacklistRiskFail.getDisplay()).toString());
                complianceService.saveLog(user, placement, "股票买入", RiskResult.StockBlacklistRiskFail.getDisplay());
                return RiskResult.StockBlacklistRiskFail;
            }
        }
        // 继续执行过滤链的下一节点
        return chain.doRiskFilter(user, placement, chain);
    }

}
