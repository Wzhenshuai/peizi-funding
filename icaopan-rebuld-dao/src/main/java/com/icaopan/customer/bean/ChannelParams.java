package com.icaopan.customer.bean;


public class ChannelParams {

    private String channelType;

    private String name;

    private Boolean isAvailable;

    private Integer customerId;

    private String code;

    private Integer channelId;

    private Boolean autoFill;


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Boolean getIsAvailable() {
        return isAvailable;
    }


    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }


    public Integer getCustomerId() {
        return customerId;
    }


    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }


    public String getChannelType() {
        return channelType;
    }


    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Boolean getAutoFill() {
        return autoFill;
    }

    public void setAutoFill(Boolean autoFill) {
        this.autoFill = autoFill;
    }
}
