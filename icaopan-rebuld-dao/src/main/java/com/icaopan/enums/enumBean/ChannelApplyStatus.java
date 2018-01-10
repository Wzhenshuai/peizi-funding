package com.icaopan.enums.enumBean;

import org.apache.commons.lang.StringUtils;

/**
 * Created by Administrator on 2017/6/30 0030.
 */
public enum ChannelApplyStatus {

    HANDLEING(0,"0","HANDLEING","处理中"),

    HANDLE_SUCCEED(1,"1","HANDLE_SUCCEED","处理完成"),

    HANDLE_FALSE(2,"2","HANDLE_FALSE","处理失败");

    private int    num;
    private String code;
    private String name;
    private String display;

    private ChannelApplyStatus(int num,String code,String name,String display){
        this.num = num;
        this.code = code;
        this.name = name;
        this.display = display;
    }

    public static ChannelApplyStatus getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (ChannelApplyStatus channelApplyStatus : ChannelApplyStatus.values()) {
            if (StringUtils.equals(channelApplyStatus.getCode(), code)) {
                return channelApplyStatus;
            }
        }
        return null;
    }

    public static ChannelApplyStatus getByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (ChannelApplyStatus channelApplyStatus : ChannelApplyStatus.values()) {
            if (StringUtils.equals(channelApplyStatus.getName(), name)) {
                return channelApplyStatus;
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
