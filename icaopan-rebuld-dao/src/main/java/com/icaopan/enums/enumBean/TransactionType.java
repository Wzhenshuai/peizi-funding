package com.icaopan.enums.enumBean;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by RoyLeong @royleo.xyz on 2017/2/7.
 */
public enum TransactionType {

    /**
     * 类型：普通(NORMAL)成交  撤单(CANCEL)成交  废单(INVALID)成交
     */
    NORMAL(0, "0", "NORMAL", "正常"),
    CANCELLED(1, "1", "CANCELED", "撤单"),
    INVALID(2, "2", "INVALID", "废单");

    private int    num;
    private String code;
    private String name;
    private String display;

    private TransactionType(int num, String code, String name, String display) {
        this.num = num;
        this.code = code;
        this.name = name;
        this.display = display;
    }

    public static TransactionType getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (TransactionType type : TransactionType.values()) {
            if (StringUtils.equals(type.getCode(), code)) {
                return type;
            }
        }
        return null;
    }

    public static TransactionType getByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (TransactionType type : TransactionType.values()) {
            if (StringUtils.equals(type.getName(), name)) {
                return type;
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
