package com.icaopan.common.util;

/**
 * Created by ffff on 2017-04-11.
 */
public class ExceptionConstants {
    public static final String PLACEMENT_QUANTITY_LEFT = "零股必须全部卖出";
    public static final String SELL_QUANTITY_NOT_ENOUGH = "可卖数量不足";
    public static final String CASH_NOT_ENOUGH = "可用资金不足";
    public static final String QUOTA_NOT_ENOUGH = "限额不足";
    public static final String SECURITY_AVAILABLE_NOT_ENOUGH = "证券可用数量不足";
    public static final String PLACEMENT_PRICE_ERROR = "委托价格输入不正确";
    public static final String PLACEMENT_QUANTITY_ERROR = "委托数量输入不正确";
    public static final String SECURITY_NOT_EXIST = "证券代码不存在";
    public static final String CHANNEL_NOT_EXIST = "交易通道未设置";
    public static final String CANCEL_FAIL = "撤单失败";
    public static final String USER_BUY_SELL_LOCKED = "用户状态为锁买卖，禁止买入卖出操作";
    public static final String USER_BUY_LOCKED = "用户状态为锁买，禁止买入操作";
    public static final String USER_LOGOUT = "用户状态为停用状态，禁止操作";
}
