package com.icaopan.trade.bean;


public class ChannelPlacemenHistoryParams {

    private String securityCode;

    private String side;

    private String status;
    /**
     * 券商资金账号
     **/
    private String account;

    private Integer channelId;

    private Integer customerId;

    private String startTime;

    private String endTime;

    private Integer userId;

    private String userName;

    private Integer placementId;


    public String getSecurityCode() {
        return securityCode;
    }


    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }


    public String getSide() {
        return side;
    }


    public void setSide(String side) {
        this.side = side;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getAccount() {
        return account;
    }


    public void setAccount(String account) {
        this.account = account;
    }


    public Integer getChannelId() {
        return channelId;
    }


    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }


    public Integer getCustomerId() {
        return customerId;
    }


    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }


    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
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


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getPlacementId() {
        return placementId;
    }

    public void setPlacementId(Integer placementId) {
        this.placementId = placementId;
    }
}
