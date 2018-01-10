package com.icaopan.enums.enumBean;

/**
 * Created by kanglj on 17/3/28.
 */
public enum SeniorRiskType {

    Quota(1, "1", "Quota", "金额上限及下单数量上限"),
    Amplitude(2, "2", "Amplitude", "涨跌停振幅比例控制"),
    Blacklist(4, "4", "Blacklist", "股票黑名单"),
    Whitelist(8, "8", "Whitelist", "股票白名单");

    private int num;

    private String code;

    private String name;

    private String display;

    SeniorRiskType(int num, String code, String name, String display) {
        this.num = num;
        this.code = code;
        this.name = name;
        this.display = display;
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
