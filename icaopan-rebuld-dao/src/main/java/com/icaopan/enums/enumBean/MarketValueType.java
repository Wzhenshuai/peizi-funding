package com.icaopan.enums.enumBean;

/**
 * Created by dongyuan on 17/3/28.
 */
public enum MarketValueType {

    None(0, "0", "None", "市值大于0等于0都不选择"),
    MoreThanZero(1, "1", "MoreThanZero", "市值大于0"),
    EqualsZero(2, "2", "EqualsZero", "市值等于0"),
    ALL(3, "3", "ALL", "市值大于等于0");

    private int num;

    private String code;

    private String name;

    private String display;

    MarketValueType(int num, String code, String name, String display) {
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
