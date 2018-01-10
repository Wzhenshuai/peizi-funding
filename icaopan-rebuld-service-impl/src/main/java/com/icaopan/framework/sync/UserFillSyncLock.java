package com.icaopan.framework.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <P></P>
 * User: <a href="mailto:xuhm@gudaiquan.com">许昊旻</a>
 * Date: 2017/6/16
 * Time: 上午10:45
 */
@Service
public class UserFillSyncLock extends SyncLock {
    private final Logger logger = LoggerFactory.getLogger(UserFillSyncLock.class);

    @Override
    public String getPrefix() {
        return "userFill";
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

}