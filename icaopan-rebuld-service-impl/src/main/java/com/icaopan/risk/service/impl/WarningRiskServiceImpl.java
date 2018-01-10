package com.icaopan.risk.service.impl;

import com.icaopan.enums.enumBean.RiskResult;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.risk.service.RiskFilterChain;
import com.icaopan.risk.service.RiskService;
import com.icaopan.trade.model.ComplianceResult;
import com.icaopan.trade.model.Placement;
import com.icaopan.trade.service.ComplianceService;
import com.icaopan.user.model.User;
import com.icaopan.user.service.UserService;
import com.icaopan.util.LogUtil;
import com.icaopan.web.vo.AccountVO;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * desc 警戒线风控管理
 *
 * Created by kanglj on 17/3/7.
 */
@Service("warningRiskService")
public class WarningRiskServiceImpl implements RiskService {

    @Autowired
    private UserService userService;

    @Autowired
    private ComplianceService complianceService;
    
    private static Logger log = com.icaopan.log.LogUtil.getTradeLogger();
    @Override
    public RiskResult doRiskFilter(User user, Placement placement, RiskFilterChain chain) {
        if(StringUtils.equals(placement.getSide().getCode(), TradeSide.SELL.getCode())){
            // 继续执行过滤链的下一节点
            return chain.doRiskFilter(user, placement, chain);
        }
        // 警戒线风控逻辑
        AccountVO vo = userService.queryUserAccount(user);
        // 若股票市值+现金余额小于等于警戒线
        if(vo.getTotalAmount().compareTo(vo.getWarnLine()) <= 0) {
            log.info((new StringBuilder("用户ID: ")).append(user.getId())
                    .append(", 姓名: ").append(user.getRealName()).append(", 风控返回结果:")
                    .append(RiskResult.WarningRiskFail).toString());
            String reason="{总金额：%s,警告线：%s}";
            reason = String.format(reason, vo.getTotalAmount(),vo.getWarnLine());
            reason = RiskResult.WarningRiskFail.getDisplay()+":"+reason;
            complianceService.saveLog(user, placement, "股票买入", reason);
            return RiskResult.WarningRiskFail;
        }
        // 继续执行过滤链的下一节点
        return chain.doRiskFilter(user, placement, chain);
    }

}
