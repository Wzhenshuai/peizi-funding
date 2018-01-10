package com.icaopan.clearing.service.impl;

import com.icaopan.clearing.service.CheckLogService;
import com.icaopan.trade.bean.CheckPositionResult;
import com.icaopan.trade.dao.CheckLogMapper;
import com.icaopan.trade.model.CheckLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/17 0017.
 */
@Service("checkLogService")
public class CheckLogServiceImpl implements CheckLogService {

    @Autowired
    private CheckLogMapper checkLogMapper;

    @Override
    public void saveCheckLog(CheckLog checkLog) {
        checkLogMapper.insert(checkLog);
    }

    @Override
    public void saveCheckLogList(List<CheckLog> checkLogs) {

        checkLogMapper.insertList(checkLogs);
    }

    @Override
    public List<CheckLog> queryCheckLog() {
        List<CheckLog> checkLogs = checkLogMapper.selectCheckLog();
        if (checkLogs.size() > 0) {
            for (CheckLog checklog : checkLogs) {
                String[] results = checklog.getResult().split("\n");
                List<String> resultList = Arrays.asList(results);
                checklog.setResults(resultList);
            }
        }
        return checkLogs;
    }

    @Override
    public void checkPosition() {
        List<CheckPositionResult> checkList = checkLogMapper.checkPosition();
        CheckLog checkLog = null;

        if (checkList.size() == 0) {
            checkLog = new CheckLog(new Date(), "用户和通道证券对账", "SUCCESS");

        } else {
            StringBuffer resultBuffer = new StringBuffer();
            for (CheckPositionResult checkPositionResult : checkList) {
                resultBuffer.append(checkPositionResult.toString());
            }
            checkLog = new CheckLog(new Date(), "用户和通道证券对账", resultBuffer.toString());
        }
        saveCheckLog(checkLog);
    }

    @Override
    public void checkCashAmount() {
        List<CheckPositionResult> checkList = checkLogMapper.checkCashAmount();
        CheckLog checkLog = null;

        if (checkList.size() == 0) {
            checkLog = new CheckLog(new Date(), "用户和流水资金对账", "SUCCESS");

        } else {
            StringBuffer resultBuffer = new StringBuffer();
            for (CheckPositionResult checkPositionResult : checkList) {
                resultBuffer.append(checkPositionResult.amountToString());
            }
            checkLog = new CheckLog(new Date(), "用户和流水资金对账", resultBuffer.toString());
        }
        saveCheckLog(checkLog);
    }

}
