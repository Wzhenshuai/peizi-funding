package com.icaopan.enums.enumBean;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by RoyLeong @royleo.xyz on 2016/11/28.
 */
public enum StockBonusStatus {

    Wait(1, "1", "Wait", "待处理"),
    Finish(2, "2", "Finish", "完成"),
    Invalid(3, "3", "Invalid", "不处理");

    private int num;

    private String code;

    private String name;

    private String display;

    StockBonusStatus(int num, String code, String name, String display) {
        this.num = num;
        this.code = code;
        this.name = name;
        this.display = display;
    }

    public static StockBonusStatus getByName(String name) {
        for (StockBonusStatus status : StockBonusStatus.values()) {
            if (StringUtils.equals(status.getName(), name)) {
                return status;
            }
        }
        return null;
    }

    public static StockBonusStatus getByCode(String code) {
        if (org.apache.commons.lang.StringUtils.isBlank(code)) {
            return null;
        }
        for (StockBonusStatus status : StockBonusStatus.values()) {
            if (StringUtils.equals(status.getCode(), code)) {
                return status;
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
