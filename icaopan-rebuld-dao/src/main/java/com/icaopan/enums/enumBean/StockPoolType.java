package com.icaopan.enums.enumBean;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by RoyLeong @royleo.xyz on 2016/11/28.
 */
public enum StockPoolType {

    BannedShares(0, "0", "BannedShares", "禁买股"),
    SmallPlates(1, "1", "SmallPlates", "中小板"),
    Gem(2, "2", "Gem", "创业版");


    private int num;

    private String code;

    private String name;

    private String display;

    StockPoolType(int num, String code, String name, String display) {
        this.num = num;
        this.code = code;
        this.name = name;
        this.display = display;
    }

    public static StockPoolType getByName(String name) {
        for (StockPoolType stockPoolType : StockPoolType.values()) {
            if (StringUtils.equals(stockPoolType.getName(), name)) {
                return stockPoolType;
            }
        }
        return null;
    }

    public static StockPoolType getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (StockPoolType type : StockPoolType.values()) {
            if (StringUtils.equals(type.getCode(), code)) {
                return type;
            }
        }
        return null;
    }

    public static StockPoolType getByDisplay(String display) {
        if (StringUtils.isBlank(display)) {
            return null;
        }
        for (StockPoolType stockPoolType : StockPoolType.values()) {
            if (StringUtils.equals(stockPoolType.getDisplay(), display)) {
                return stockPoolType;
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
