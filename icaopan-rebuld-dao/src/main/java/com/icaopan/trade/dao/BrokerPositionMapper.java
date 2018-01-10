package com.icaopan.trade.dao;

import java.util.List;

import com.icaopan.trade.bean.BrokerPositionQueryParams;
import com.icaopan.trade.model.BrokerPosition;

public interface BrokerPositionMapper {

	int insert(BrokerPosition record);

    BrokerPosition selectByPrimaryKey(Integer id);
    
    List<BrokerPosition> selectByCondition(BrokerPositionQueryParams params);
}