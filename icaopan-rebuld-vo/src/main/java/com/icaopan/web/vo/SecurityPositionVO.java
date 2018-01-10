package com.icaopan.web.vo;

import java.math.BigDecimal;

public class SecurityPositionVO {

    private Integer    id;
    //证券名称
    private String     securityName;
    //证券代码
    private String     securityCode;
    //股票市值
    private BigDecimal marketValue;
    //持仓盈亏
    private BigDecimal marketProfit;
    //持仓盈亏比例
    private BigDecimal marketProfitPercent;
    //持仓数量
    private BigDecimal amount;
    //持仓可用数量
    private BigDecimal availableAmount;
    //现价
    private BigDecimal latestPrice;
    //成本价
    private BigDecimal costPrice;
    private String costPriceStr;
    //持仓总成本
    private BigDecimal totalCost;

    private String channelName;

    private Integer channelId;

    private String userName;

    private String customerName;

    private String customerNotes;

    private Integer customerId;
    // 是否是停牌股
    private boolean suspensionFlag;
    private String  suspensionDisplay;
    
    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public BigDecimal getMarketValue() {
        if (latestPrice == null || amount == null) {
            return BigDecimal.ZERO;
        }
        marketValue = latestPrice.multiply(amount);
        return marketValue;
    }

    public BigDecimal getMarketProfit() {
        if (latestPrice == null || totalCost == null) {
            return BigDecimal.ZERO;
        }
        if (amount == null) {
            return totalCost.negate();
        }
        return latestPrice.multiply(amount).subtract(totalCost);
    }

    public BigDecimal getMarketProfitPercent() {
        if (latestPrice == null || costPrice == null || amount==null || amount.intValue() == 0 || costPrice.intValue()==0) {
            return BigDecimal.ZERO;
        }
        marketProfit = latestPrice.subtract(costPrice);
        marketProfitPercent = marketProfit.divide(costPrice, 4, BigDecimal.ROUND_HALF_UP);
        return marketProfitPercent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public BigDecimal getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(BigDecimal latestPrice) {
        this.latestPrice = latestPrice;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public boolean isSuspensionFlag() {
        return suspensionFlag;
    }

    public void setSuspensionFlag(boolean suspensionFlag) {
        this.suspensionFlag = suspensionFlag;
    }

    public String getSuspensionDisplay() {
        return this.suspensionFlag ? "停牌" : "正常";
    }

    public void setSuspensionDisplay(String suspensionDisplay) {
        this.suspensionDisplay = suspensionDisplay;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getCustomerNotes() {
        return customerNotes;
    }

    public void setCustomerNotes(String customerNotes) {
        this.customerNotes = customerNotes;
    }

    public String getCostPriceStr() {
        if (costPrice==null){
            return null;
        }
        return costPrice.setScale(2,BigDecimal.ROUND_HALF_UP).toString();
    }
}
