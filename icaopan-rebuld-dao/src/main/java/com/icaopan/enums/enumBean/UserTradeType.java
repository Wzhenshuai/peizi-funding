package com.icaopan.enums.enumBean;

import org.apache.commons.lang.StringUtils;

/**
 * <P>用户交易类型：通道优先下单、通道平分下单</P>
 * User: <a href="mailto:xuhm@gudaiquan.com">许昊旻</a>
 * Date: 2017/6/9
 * Time: 上午12:23
 */
public enum UserTradeType {
    PRIOR(0, "0", "PRIOR", "优先下单"),
    DIVIDE(1, "1", "DIVIDE", "平分下单"),
    PROPORTION(2, "2", "PROPORTION", "比例下单");
    private int    num;
    private String code;
    private String name;
    private String display;

    private UserTradeType(int num, String code, String name, String display) {
        this.num = num;
        this.code = code;
        this.name = name;
        this.display = display;
    }

    public static UserTradeType getByName(String name) {
        for (UserTradeType type : UserTradeType.values()) {
            if (StringUtils.equals(type.getName(), name)) {
                return type;
            }
        }
        return null;
    }

    public static UserTradeType getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (UserTradeType type : UserTradeType.values()) {
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
