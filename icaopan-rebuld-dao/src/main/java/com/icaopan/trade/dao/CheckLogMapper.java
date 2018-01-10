package com.icaopan.trade.dao;


import com.icaopan.trade.bean.CheckPositionResult;
import com.icaopan.trade.model.CheckLog;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CheckLogMapper {

    int insert(CheckLog checkLog);

    int insertList(List<CheckLog> checkLogs);

    List<CheckLog> selectCheckLog();

    List<CheckPositionResult> checkPosition();

    List<CheckPositionResult> checkCashAmount();
}
