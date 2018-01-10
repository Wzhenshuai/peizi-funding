package com.icaopan.enums.enumBean;

import org.apache.commons.lang.StringUtils;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName TradeFowType
 * @Description (交易流水的类型，包括 证券 和资金)
 * @Date 2016年12月1日 下午3:16:18
 */
public enum TradeFowType {

    SECURITY_BUY(0, "0", "证券买入", "SECURITY_BUY"),
    SECURITY_SELL(1, "1", "证券卖出", "SECURITY_SELL"),
    CASH_ADD(2, "2", "资金增加", "CASH_ADD"),
    CASH_REDUCE(3, "3", "资金减少", "CASH_REDUCE"),
    STOCK_ADD(4, "4", "证券增加", "STOCK_ADD"),
    STOCK_REDUCE(5, "5", "证券减少", "STOCK_REDUCE"),
    COST_PRICE_ADJUST(6, "6", "成本价调整", "COST_PRICE_ADJUST");


    private int    num;
    private String code;
    private String display;
    private String name;

    private TradeFowType(int num, String code, String display, String name) {
        this.num = num;
        this.code = code;
        this.display = display;
        this.name = name;
    }

    public static TradeFowType getByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (TradeFowType tradeFowType : TradeFowType.values()) {
            if (StringUtils.equals(tradeFowType.getName(), name)) {
                return tradeFowType;
            }
        }
        return null;
    }

    public static TradeFowType getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (TradeFowType tradeFowType : TradeFowType.values()) {
            if (StringUtils.equals(tradeFowType.getCode(), code)) {
                return tradeFowType;
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

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
