package com.icaopan.enums.enumBean;

import org.apache.commons.lang.StringUtils;

/**
 * Created by RoyLeong @royleo.xyz on 2017/10/9.
 */
public enum UserChannelOperateType {

    CREATE(0, "0", "CREATE", "创建"),
    UPDATE(1, "1", "UPDATE", "编辑");

    private int    num;
    private String code;
    private String name;
    private String display;

    private UserChannelOperateType(int num, String code, String name, String display) {
        this.num = num;
        this.code = code;
        this.name = name;
        this.display = display;
    }

    public static UserChannelOperateType getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (UserChannelOperateType type : UserChannelOperateType.values()) {
            if (StringUtils.equals(type.getCode(), code)) {
                return type;
            }
        }
        return null;
    }

    public static UserChannelOperateType getByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (UserChannelOperateType type : UserChannelOperateType.values()) {
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
