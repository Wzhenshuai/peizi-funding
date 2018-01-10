package com.icaopan.customer.model;

import com.icaopan.util.DateUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ChannelLimit implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String securityCode;

    private String securityName;

    private Integer channelId;

    private BigDecimal amount;

    private BigDecimal positionSummaryAmount;   //持仓总市值

    private BigDecimal notTradedAmount;     //未成交总金额

    private double disLimit;                //距限额差额

    private BigDecimal limitRatio;

    private Date updateTime;

    private Date createTime;

    private String channelName;

    private String updateTimeStr;

    private String createTimeStr;

    public  ChannelLimit(){};

    public ChannelLimit(Integer id,BigDecimal amount){
        this.id = id;
        this.amount = amount;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUpdateTimeStr(){
        return DateUtils.formatDateTime(updateTime);
    }

    public String getCreateTimeStr(){
        return DateUtils.formatDateTime(createTime);
    }

    public ChannelLimit(Integer channelId, BigDecimal amount, String securityCode){
        this.channelId =  channelId;
        this.amount = amount;
        this.securityCode =  securityCode;
    }

    public BigDecimal getPositionSummaryAmount() {
        return positionSummaryAmount==null?positionSummaryAmount:positionSummaryAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setPositionSummaryAmount(BigDecimal positionSummaryAmount) {
        this.positionSummaryAmount = positionSummaryAmount;
    }

    public BigDecimal getNotTradedAmount() {
        return notTradedAmount==null?notTradedAmount:notTradedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setNotTradedAmount(BigDecimal notTradedAmount) {
        this.notTradedAmount = notTradedAmount;
    }

    public BigDecimal getLimitRatio() {
        return limitRatio.setScale(2,BigDecimal.ROUND_HALF_UP);
    }

    public void setLimitRatio(BigDecimal limitRatio) {
        this.limitRatio = limitRatio;
    }

    public double getDisLimit() {
        return amount.subtract(positionSummaryAmount.add(notTradedAmount)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}