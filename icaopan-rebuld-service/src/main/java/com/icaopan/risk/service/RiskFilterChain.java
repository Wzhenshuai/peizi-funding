package com.icaopan.risk.service;

import com.icaopan.enums.enumBean.RiskResult;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.trade.model.Placement;
import com.icaopan.user.model.User;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * desc 风控过滤链
 * <p>
 * Created by kanglj on 17/3/7.
 */
public class RiskFilterChain implements RiskService {

    private List<RiskService> filterList = new ArrayList<>();
    private int               index      = 0;

    public RiskFilterChain addFilter(RiskService filter) {
        this.filterList.add(filter);
        return this;
    }

    @Override
    public RiskResult doRiskFilter(User user, Placement placement, RiskFilterChain chain) {
        if (StringUtils.equals(placement.getSide().getName(), TradeSide.SELL.getName())) {
            return RiskResult.Success;
        }
        // 如果过滤完成,则退出
        if (index == filterList.size())
            return RiskResult.Success;
        // 过滤链条的下一个节点
        return filterList.get(index++).doRiskFilter(user, placement, chain);
    }
}
