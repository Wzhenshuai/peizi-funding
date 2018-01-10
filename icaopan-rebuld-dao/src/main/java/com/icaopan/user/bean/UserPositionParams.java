package com.icaopan.user.bean;

import java.math.BigDecimal;

/**
 * Created by wangzs  on 2017/3/14.
 */
public class UserPositionParams {

    private Integer userId;

    private Integer customerId;

    private String internalSecurityId;

    private BigDecimal amount;

    private BigDecimal available;

    private BigDecimal costPrice;

    private String userName;


    public UserPositionParams() {
    }

    public UserPositionParams(Integer userId, String internalSecurityId, BigDecimal amount, BigDecimal available, BigDecimal costPrice) {
        this.userId = userId;
        this.internalSecurityId = internalSecurityId;
        this.amount = amount;
        this.available = available;
        this.costPrice = costPrice;
    }

    public UserPositionParams(Integer userId, String internalSecurityId, BigDecimal available) {
        this.userId = userId;
        this.internalSecurityId = internalSecurityId;
        this.available = available;
    }

    public UserPositionParams(Integer userId, Integer customerId, String internalSecurityId) {
        this.userId = userId;
        this.customerId = customerId;
        this.internalSecurityId = internalSecurityId;
    }

    public UserPositionParams(Integer userId, String internalSecurityId, BigDecimal amount, BigDecimal costPrice) {
        this.userId = userId;
        this.internalSecurityId = internalSecurityId;
        this.amount = amount;
        this.costPrice = costPrice;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
