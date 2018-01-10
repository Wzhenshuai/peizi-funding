package com.icaopan.clearing.service;

import com.icaopan.trade.bean.CheckPositionResult;
import com.icaopan.trade.model.CheckLog;

import java.util.List;

/**
 * Created by Administrator on 2017/7/17 0017.
 */
public interface CheckLogService {

    public void saveCheckLog(CheckLog checkLog);

    public void saveCheckLogList(List<CheckLog> checkLogs);

    public List<CheckLog> queryCheckLog();

    // 执行对账 统计用户持仓和通道持仓
    public void checkPosition();

    public void checkCashAmount();
}
