package com.icaopan.trade.bean;


public class FillHistoryParams {

    private String securityCode;

    private Integer userId;

    private Integer channelId;

    private Integer customerId;

    private String startTime;

    private String endTime;

    private String side;

    private String userName;

    public String getSecurityCode() {
        return securityCode;
    }


    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }


    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Integer getCustomerId() {
        return customerId;
    }


    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }


    public Integer getChannelId() {
        return channelId;
    }


    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }


    public String getStartTime() {
        return startTime;
    }


    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    public String getEndTime() {
        return endTime;
    }


    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public String getSide() {
        return side;
    }


    public void setSide(String side) {
        this.side = side;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


}
