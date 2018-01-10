package com.icaopan.trade.service;

import com.icaopan.trade.bean.ChannelQuotaLog;
import com.icaopan.util.page.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2017/10/9.
 */
public interface ChannelQuotaLogService {

    boolean saveLog(ChannelQuotaLog log);

    ChannelQuotaLog findById(Integer id);

    Page<ChannelQuotaLog> selectQuotaLogByPage(Page<ChannelQuotaLog> page,ChannelQuotaLog param);
}
