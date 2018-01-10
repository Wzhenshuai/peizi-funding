package com.icaopan.enums.enumBean;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by RoyLeong @royleo.xyz on 2017/7/25.
 * 用户登记类型：
 * 1：name:普通用户 value: 0 (default)
 * 2: name:高级用户 value: 1
 */
public enum  UserClassType {

    NormalUser(0,"0","Normal","普通用户"),

    AdvancedUser(1,"1","Adcanced","高级用户");

    private int num;

    private String code;

    private String name;

    private String display;

    UserClassType(int num, String code, String name, String display) {
        this.num = num;
        this.code = code;
        this.name = name;
        this.display = display;
    }

    public static UserClassType getByCode(String code){
        if (StringUtils.isBlank(code)) return null;
        for (UserClassType type : UserClassType.values()){
            if (StringUtils.equals(type.getCode(),code)){
                return type;
            }
        }
        return null;
    }

    public static UserClassType getByName(String name){
        if (StringUtils.isBlank(name)) return null;
        for (UserClassType type : UserClassType.values()){
            if (StringUtils.equals(type.getName(),name)) return type;
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
