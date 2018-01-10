package com.icaopan.risk.service;

import com.icaopan.enums.enumBean.RiskResult;
import com.icaopan.trade.model.Placement;
import com.icaopan.user.model.User;

/**
 * desc 风控管理接口
 * <p>
 * Created by kanglj on 17/3/7.
 */
public interface RiskService {
    // 风控过滤
    RiskResult doRiskFilter(User user, Placement placement, RiskFilterChain chain);
}
