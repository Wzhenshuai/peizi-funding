package com.icaopan.trade.bean;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/7/17 0017.
 */
public class CheckPositionResult {

    private Integer userId;

    private String userName;

    private String localCode;

    private BigDecimal channelPositionSum;

    private BigDecimal userPositionSum;

    private BigDecimal difference;

    private BigDecimal flowAmount;

    private BigDecimal userAmount;

    public BigDecimal getUserAmount() {
        return userAmount;
    }

    public void setUserAmount(BigDecimal userAmount) {
        this.userAmount = userAmount;
    }

    public BigDecimal getFlowAmount() {
        return flowAmount;
    }

    public void setFlowAmount(BigDecimal flowAmount) {
        this.flowAmount = flowAmount;
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

    public String getLocalCode() {
        return localCode;
    }

    public void setLocalCode(String localCode) {
        this.localCode = localCode;
    }

    public BigDecimal getChannelPositionSum() {
        return channelPositionSum;
    }

    public void setChannelPositionSum(BigDecimal channelPositionSum) {
        this.channelPositionSum = channelPositionSum;
    }

    public BigDecimal getUserPositionSum() {
        return userPositionSum;
    }

    public void setUserPositionSum(BigDecimal userPositionSum) {
        this.userPositionSum = userPositionSum;
    }

    public BigDecimal getDifference() {
        return difference;
    }

    public void setDifference(BigDecimal difference) {
        this.difference = difference;
    }

    @Override
    public String toString() {
        return "{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", localCode='" + localCode + '\'' +
                ", channelPositionSum=" + channelPositionSum +
                ", userPositionSum=" + userPositionSum +
                ", difference=" + difference +
                '}' + "\n";
    }

    public String amountToString() {
        return "{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", flowAmount=" + flowAmount +
                ", userAmount=" + userAmount +
                ", difference=" + difference +
                '}' + "\n";
    }
}
