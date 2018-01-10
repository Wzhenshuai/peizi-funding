package com.icaopan.trade.service.impl;

import com.icaopan.trade.bean.ChannelQuotaLog;
import com.icaopan.trade.dao.ChannelQuotaLogMapper;
import com.icaopan.trade.service.ChannelQuotaLogService;
import com.icaopan.util.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2017/10/9.
 */
@Service("channelQuotaLogService")
public class ChannelQuotaLogServiceImpl implements ChannelQuotaLogService{

    @Autowired
    ChannelQuotaLogMapper quotaLogMapper;

    @Override
    public boolean saveLog(ChannelQuotaLog log) {
        int i = quotaLogMapper.insert(log);
        return i>0;
    }

    @Override
    public ChannelQuotaLog findById(Integer id) {
        return quotaLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public Page<ChannelQuotaLog> selectQuotaLogByPage(Page<ChannelQuotaLog> page, ChannelQuotaLog param) {
        List<ChannelQuotaLog> logList = new ArrayList<ChannelQuotaLog>();
        logList = quotaLogMapper.selectByPage(param,page);
        page.setAaData(logList);
        return page;
    }
}
