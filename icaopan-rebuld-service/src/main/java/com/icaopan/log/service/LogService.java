package com.icaopan.log.service;

import com.icaopan.sys.model.Log;
import com.icaopan.sys.model.LogParams;
import com.icaopan.util.page.Page;

/**
 * Created by wangzs on 2017/12/21 0021.
 */
public interface LogService {

    public Page<Log> findByCustomer(Page<Log> page, Integer customerId);

    public Page<Log> findByUser(Page<Log> page, Integer userId);

    public Page<Log> findByLogParams(Page<Log> page, LogParams logParams);

    public int insert(Log log);
}
