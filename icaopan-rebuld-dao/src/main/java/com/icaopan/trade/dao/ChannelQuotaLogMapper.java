package com.icaopan.trade.dao;


import com.icaopan.trade.bean.ChannelQuotaLog;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChannelQuotaLogMapper {

    int insert(ChannelQuotaLog record);

    int insertSelective(ChannelQuotaLog record);

    ChannelQuotaLog selectByPrimaryKey(Integer id);

    List<ChannelQuotaLog> selectByPage(@Param("params") ChannelQuotaLog channelQuotaLog, @Param("page") Page<ChannelQuotaLog> page);

}