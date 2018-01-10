package com.icaopan.user.bean;

/**
 * Created by RoyLeong @royleo.xyz on 2016/12/19.
 */
public class PositionParams {

    private Integer userId;

    private Integer customerId;

    private Integer channelId;

    private String internalSecurityId;

    private String userName;

    public PositionParams() {
    }

    public PositionParams(Integer customerId, String internalSecurityId) {
        this.customerId = customerId;
        this.internalSecurityId = internalSecurityId;
    }

    public PositionParams(String internalSecurityId, Integer channelId, Integer customerId) {
        this.internalSecurityId = internalSecurityId;
        this.customerId = customerId;
        this.channelId = channelId;
    }

    public PositionParams(Integer userId, Integer customerId, Integer channelId, String internalSecurityId) {
        this.userId = userId;
        this.customerId = customerId;
        this.channelId = channelId;
        this.internalSecurityId = internalSecurityId;
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

    public String getInternalSecurityId() {
        return internalSecurityId;
    }

    public void setInternalSecurityId(String internalSecurityId) {
        this.internalSecurityId = internalSecurityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
