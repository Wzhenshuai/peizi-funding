package com.icaopan.enums.enumBean;

import org.apache.commons.lang.StringUtils;

/**
 * <P>用户通道类型</P>
 * User: <a href="mailto:xuhm@gudaiquan.com">许昊旻</a>
 * Date: 2017/6/9
 * Time: 上午12:11
 */
public enum UserChannelType {
    UNLIMITED(0, "0", "UNLIMITED", "不限金额"),
    LIMITED(1, "1", "LIMITED", "限制金额");
    private int    num;
    private String code;
    private String name;
    private String display;

    private UserChannelType(int num, String code, String name, String display) {
        this.num = num;
        this.code = code;
        this.name = name;
        this.display = display;
    }

    public static UserChannelType getByName(String name) {
        for (UserChannelType type : UserChannelType.values()) {
            if (StringUtils.equals(type.getName(), name)) {
                return type;
            }
        }
        return null;
    }

    public static UserChannelType getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (UserChannelType type : UserChannelType.values()) {
            if (StringUtils.equals(type.getCode(), code)) {
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

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}