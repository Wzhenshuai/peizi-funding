package com.icaopan.user.model;

import java.math.BigDecimal;
import java.util.Date;

public class UserChannelPosition {

    private String securityCode;

    private Integer channelId;

    private String channelName;

    private BigDecimal amount;

    private String userName;

    private String internalSecurityId;

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInternalSecurityId() {
        return internalSecurityId;
    }

    public void setInternalSecurityId(String internalSecurityId) {
        this.internalSecurityId = internalSecurityId;
    }

}