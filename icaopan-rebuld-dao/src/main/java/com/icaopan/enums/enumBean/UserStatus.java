package com.icaopan.enums.enumBean;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by RoyLeong @royleo.xyz on 2016/11/28.
 */
public enum UserStatus {

    Normal(0, "0", "Normal", "正常"),
    Logout(2, "2", "Logout", "停用"),
    BuySellLocked(1,"1","BuySellLocked","锁买卖"),
    BuyLocked(3,"3","BuyLocked","锁买");

    private int num;

    private String code;

    private String name;

    private String display;

    UserStatus(int num, String code, String name, String display) {
        this.num = num;
        this.code = code;
        this.name = name;
        this.display = display;
    }

    public static UserStatus getByName(String name) {
        for (UserStatus status : UserStatus.values()) {
            if (StringUtils.equals(status.getName(), name)) {
                return status;
            }
        }
        return null;
    }

    public static UserStatus getByCode(String code) {
        if (org.apache.commons.lang.StringUtils.isBlank(code)) {
            return null;
        }
        for (UserStatus status : UserStatus.values()) {
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
