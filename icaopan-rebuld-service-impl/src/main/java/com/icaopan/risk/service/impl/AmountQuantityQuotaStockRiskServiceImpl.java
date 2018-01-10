package com.icaopan.risk.service.impl;

import com.icaopan.common.util.SecurityUtil;
import com.icaopan.enums.enumBean.RiskResult;
import com.icaopan.enums.enumBean.SeniorRiskType;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.risk.service.RiskFilterChain;
import com.icaopan.risk.service.RiskService;
import com.icaopan.stock.service.PoolService;
import com.icaopan.stock.service.StockUserBlacklistService;
import com.icaopan.trade.model.ComplianceResult;
import com.icaopan.trade.model.Placement;
import com.icaopan.trade.service.ComplianceService;
import com.icaopan.user.model.User;
import com.icaopan.util.BigDecimalUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * desc 用户单笔金额、数量上限风控
 *
 * Created by kanglj on 17/3/7.
 */
@Service("amountQuantityQuotaStockRiskService")
public class AmountQuantityQuotaStockRiskServiceImpl implements RiskService {

    @Autowired
    private ComplianceService complianceService;
    private static Logger log = com.icaopan.log.LogUtil.getTradeLogger();

    @Override
    public RiskResult doRiskFilter(User user, Placement placement, RiskFilterChain chain) {
        if(StringUtils.equals(placement.getSide().getCode(), TradeSide.SELL.getCode())){
            // 继续执行过滤链的下一节点
            return chain.doRiskFilter(user, placement, chain);
        }
        // 如果开启用户单笔金额、数量上限风控
        if((user.getRiskFlag() & SeniorRiskType.Quota.getNum()) == SeniorRiskType.Quota.getNum()){
            BigDecimal placementAmount = BigDecimalUtil.multiply(placement.getPrice(), placement.getQuantity());
            if(BigDecimal.ZERO.compareTo(user.getRiskAmountQuota()) != 0 && placementAmount.compareTo(user.getRiskAmountQuota()) >= 0){
                log.info((new StringBuilder("用户ID: ")).append(user.getId())
                        .append(", 姓名: ").append(user.getRealName()).append(", 风控返回结果:")
                        .append(RiskResult.AmountQuotaRiskFail.getDisplay()).toString());
                complianceService.saveLog(user, placement, "股票买入", RiskResult.AmountQuotaRiskFail.getDisplay());
                return RiskResult.AmountQuotaRiskFail;
            }
            if(BigDecimal.ZERO.compareTo(user.getRiskQuantityQuota()) != 0 && placement.getQuantity().compareTo(user.getRiskQuantityQuota()) >= 0){
                log.info((new StringBuilder("用户ID: ")).append(user.getId())
                        .append(", 姓名: ").append(user.getRealName()).append(", 风控返回结果:")
                        .append(RiskResult.QuantityQuotaRiskFail.getDisplay()).toString());
                complianceService.saveLog(user, placement, "股票买入", RiskResult.QuantityQuotaRiskFail.getDisplay());
                return RiskResult.QuantityQuotaRiskFail;
            }
        }
        // 继续执行过滤链的下一节点
        return chain.doRiskFilter(user, placement, chain);
    }

}
