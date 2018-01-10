package com.icaopan.risk.service.impl;

import com.icaopan.common.util.SecurityUtil;
import com.icaopan.enums.enumBean.RiskResult;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.risk.service.RiskFilterChain;
import com.icaopan.risk.service.RiskService;
import com.icaopan.stock.service.PoolService;
import com.icaopan.trade.model.ComplianceResult;
import com.icaopan.trade.model.Placement;
import com.icaopan.trade.service.ComplianceService;
import com.icaopan.user.model.User;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * desc 禁买股风控管理
 *
 * Created by kanglj on 17/3/7.
 */
@Service("bannedStockRiskService")
public class BannedStockRiskServiceImpl implements RiskService {

    @Autowired
    private PoolService poolService;
    @Autowired
    private ComplianceService complianceService;
    private static Logger log = com.icaopan.log.LogUtil.getTradeLogger();

    @Override
    public RiskResult doRiskFilter(User user, Placement placement, RiskFilterChain chain) {
        if(StringUtils.equals(placement.getSide().getCode(), TradeSide.SELL.getCode())){
            // 继续执行过滤链的下一节点
            return chain.doRiskFilter(user, placement, chain);
        }
        // 查询股票是否是禁买股
        if(poolService.isBannedStock(SecurityUtil.getInternalSecurityIdBySecurityCode(placement.getSecurityCode()),user.getCustomerId())){
            log.info((new StringBuilder("用户ID: ")).append(user.getId())
                    .append(", 姓名: ").append(user.getRealName()).append(", 风控返回结果:")
                    .append(RiskResult.BannedStockRiskFail.getDisplay()).toString());
            complianceService.saveLog(user, placement, "股票买入", RiskResult.BannedStockRiskFail.getDisplay());
            return RiskResult.BannedStockRiskFail;
        }
        // 继续执行过滤链的下一节点
        return chain.doRiskFilter(user, placement, chain);
    }

}
