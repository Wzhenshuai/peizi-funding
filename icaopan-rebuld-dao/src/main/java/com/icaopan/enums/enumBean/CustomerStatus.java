package com.icaopan.enums.enumBean;

import org.apache.commons.lang.StringUtils;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName CustomerStatus
 * @Description (客户的 状态)
 * @Date 2016年12月1日 下午2:16:48
 */
public enum CustomerStatus {

    NORMAL(0, "0", "NORMAL", "正常"),
    LOCKED(1, "1", "LOCKED", "锁定"),
    LOGOUT(2, "2", "LOGOUT", "停用");

    private int    num;
    private String code;
    private String name;
    private String display;

    private CustomerStatus(int num, String code, String name, String display) {
        this.num = num;
        this.code = code;
        this.name = name;
        this.display = display;

    }

    public static CustomerStatus getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (CustomerStatus customerStatus : CustomerStatus.values()) {
            if (StringUtils.equals(customerStatus.getCode(), code)) {
                return customerStatus;
            }
        }
        return null;
    }

    public static CustomerStatus getByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (CustomerStatus customerStatus : CustomerStatus.values()) {
            if (StringUtils.equals(customerStatus.getName(), name)) {
                return customerStatus;
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
