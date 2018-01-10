package com.icaopan.sys.service.impl;

import com.icaopan.log.service.LogService;
import com.icaopan.sys.dao.LogMapper;
import com.icaopan.sys.model.Log;
import com.icaopan.sys.model.LogParams;
import com.icaopan.util.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangzs on 2017/12/21 0021.
 */
@Service("logService")
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public Page<Log> findByCustomer(Page<Log> page, Integer customerId) {
        page.setAaData(logMapper.findByCustomer(customerId));
        return page;
    }
    @Override
    public Page<Log> findByUser(Page<Log> page, Integer userId) {
        page.setAaData(logMapper.findByUser(userId));
        return page;
    }

    @Override
    public Page<Log> findByLogParams(Page<Log> page, LogParams logParams) {
        page.setAaData(logMapper.findByLogParams(page, logParams));
        return page;
    }

    @Override
    public int insert(Log log) {
        return logMapper.insert(log);
    }
}
