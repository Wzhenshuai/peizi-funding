package com.icaopan.enums.enumBean;

import org.apache.commons.lang.StringUtils;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName CustomerCostPattern
 * @Description (客户的抵消模式)
 * @Date 2016年12月1日 下午3:22:35
 */
public enum CustomerCostPattern {

    CHANNL(0, "0", "CHANNL", "通道"), //低消模式
    FINANCING_FIRM(1, "1", "FINANCING_FIRM", "公司");


    private int    num;
    private String code;
    private String name;
    private String display;

    private CustomerCostPattern(int num, String code, String name, String display) {
        this.num = num;
        this.code = code;
        this.name = name;
        this.display = display;
    }

    public static CustomerCostPattern getByCode(String code) {
        for (CustomerCostPattern customerCostPattern : CustomerCostPattern.values()) {
            if (StringUtils.equals(customerCostPattern.getCode(), code)) {
                return customerCostPattern;
            }
        }
        return null;
    }

    public static CustomerCostPattern getByName(String name) {
        for (CustomerCostPattern customerCostPattern : CustomerCostPattern.values()) {
            if (StringUtils.equals(customerCostPattern.getName(), name)) {
                return customerCostPattern;
            }
        }
        return null;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

}
