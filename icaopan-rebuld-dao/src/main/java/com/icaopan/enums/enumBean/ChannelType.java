package com.icaopan.enums.enumBean;

import org.apache.commons.lang.StringUtils;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName ChannelType
 * @Description (通道类型)
 * @Date 2016年12月1日 下午2:16:48
 */
public enum ChannelType {

    PERSONAL(0, "0", "PERSONAL", "个人"),
    PB(1, "1", "PB", "恒生pb");

    private int    num;
    private String code;
    private String name;
    private String display;

    private ChannelType(int num, String code, String name, String display) {
        this.num = num;
        this.code = code;
        this.name = name;
        this.display = display;
    }

    public static ChannelType getByName(String name) {
        for (ChannelType channelType : ChannelType.values()) {
            if (StringUtils.equals(channelType.getName(), name)) {
                return channelType;
            }
        }
        return null;
    }

    public static ChannelType getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (ChannelType channelType : ChannelType.values()) {
            if (StringUtils.equals(channelType.getCode(), code)) {
                return channelType;
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
