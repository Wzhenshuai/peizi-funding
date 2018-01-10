package com.icaopan.risk.service;

/**
 * desc 定时刷新任务
 * <p>
 * Created by kanglj on 17/3/7.
 */
public interface RiskTaskService {
    // 将用户
    void scheduledFlush();

    void positionComplementaryFlush();

    // 单个用户信息刷入内存
    void singleUserFlush(Integer userId);

    void delUserFromContainer(Integer userId);
}
