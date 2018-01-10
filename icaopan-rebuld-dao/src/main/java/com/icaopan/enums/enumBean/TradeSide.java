package com.icaopan.enums.enumBean;

import org.apache.commons.lang.StringUtils;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName TradeType
 * @Description (交易类型, 买入、卖出)
 * @Date 2016年12月1日 下午2:16:48
 */
public enum TradeSide {
    BUY(0, "0", "BUY", "买入"),
    SELL(1, "1", "SELL", "卖出");

    private int    num;
    private String code;
    private String name;
    private String display;

    private TradeSide(int num, String code, String name, String display) {
        this.num = num;
        this.code = code;
        this.name = name;
        this.display = display;
    }

    public static TradeSide getByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (TradeSide tradeType : TradeSide.values()) {
            if (StringUtils.equals(tradeType.getName(), name)) {
                return tradeType;
            }
        }
        return null;
    }

    public static TradeSide getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (TradeSide tradeType : TradeSide.values()) {
            if (StringUtils.equals(tradeType.getCode(), code)) {
                return tradeType;
            }
        }
        return null;
    }

    public static TradeSide getByNum(int num) {
        for (TradeSide tradeType : TradeSide.values()) {
            if (tradeType.getNum() == num) {
                return tradeType;
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
