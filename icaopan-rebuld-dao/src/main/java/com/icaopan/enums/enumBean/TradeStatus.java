package com.icaopan.enums.enumBean;

import org.apache.commons.lang.StringUtils;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName TradeStatus
 * @Description (交易状态)
 * @Date 2016年12月1日 下午2:16:48
 */
public enum TradeStatus {

    SENDING(0, "0", "SENDING", "正报"),
    SENDACK(1, "1", "SENDACK", "已报"),
    PARTIALLYFILLED(2, "2", "PARTIALLYFILLED", "部成"),
    FILLED(3, "3", "FILLED", "已成"),
    CANCELLING(4, "4", "CANCELLING", "正撤"),
    CANCELLED(5, "5", "CANCELLED", "已撤"),
    INVALID(6, "6", "INVALID", "废单");

    private int    num;
    private String code;
    private String name;
    private String display;

    private TradeStatus(int num, String code, String name, String display) {
        this.num = num;
        this.code = code;
        this.name = name;
        this.display = display;

    }

    public static TradeStatus getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (TradeStatus tradeStatus : TradeStatus.values()) {
            if (StringUtils.equals(tradeStatus.getCode(), code)) {
                return tradeStatus;
            }
        }
        return null;
    }

    public static TradeStatus getByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (TradeStatus tradeStatus : TradeStatus.values()) {
            if (StringUtils.equals(tradeStatus.getName(), name)) {
                return tradeStatus;
            }
        }
        return null;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
