package com.icaopan.risk.bean;

import java.math.BigDecimal;

/**
 * Created by dongyuan on 17/3/31.
 */
public class RiskMarketVO {
    private Integer    id;
    private String     securityId;
    private BigDecimal positionAvailable;
    private BigDecimal positionAmount;
    private Integer    customerId;
    private Integer    channelId;
    private BigDecimal costPrice;
    private String     userName;
    private Integer    userId;
    private String     realName;
    private BigDecimal warnLine;
    private BigDecimal openLine;
    private String     status;
    private BigDecimal singleStockScale;
    private BigDecimal smallStockScale;
    private BigDecimal createStockScale;
    private BigDecimal smallSingleStockScale;
    private BigDecimal createSingleStockScale;
    private BigDecimal amount;
    private BigDecimal available;
    private BigDecimal cashAmount;
    private BigDecimal financeAmount;
    private BigDecimal frozenAmount;
    private String     securityCode;
    private String     securityName;
    private BigDecimal marketValue;
    private BigDecimal latestPrice;
    private BigDecimal marketProfit;
    private BigDecimal marketProfitPercent;
    private String     channelName;
    private String     customerName;
    private boolean    suspensionFlag;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public BigDecimal getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(BigDecimal latestPrice) {
        this.latestPrice = latestPrice;
    }

    public BigDecimal getMarketValue() {
        if (latestPrice == null || positionAmount == null) {
            return BigDecimal.ZERO;
        }
        marketValue = latestPrice.multiply(positionAmount);
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    public BigDecimal getMarketProfit() {
        if (latestPrice == null || costPrice == null) {
            return BigDecimal.ZERO;
        }
        marketProfit = latestPrice.subtract(costPrice);
        return marketProfit;
    }

    public void setMarketProfit(BigDecimal marketProfit) {
        this.marketProfit = marketProfit;
    }

    public BigDecimal getMarketProfitPercent() {
        if (marketProfit == null || costPrice == null || costPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        marketProfitPercent = marketProfit.divide(costPrice, 2, BigDecimal.ROUND_HALF_UP);
        return marketProfitPercent;
    }

    public void setMarketProfitPercent(BigDecimal marketProfitPercent) {
        this.marketProfitPercent = marketProfitPercent;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public BigDecimal getPositionAvailable() {
        return positionAvailable;
    }

    public void setPositionAvailable(BigDecimal positionAvailable) {
        this.positionAvailable = positionAvailable;
    }

    public BigDecimal getPositionAmount() {
        return positionAmount;
    }

    public void setPositionAmount(BigDecimal positionAmount) {
        this.positionAmount = positionAmount;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public BigDecimal getWarnLine() {
        return warnLine;
    }

    public void setWarnLine(BigDecimal warnLine) {
        this.warnLine = warnLine;
    }

    public BigDecimal getOpenLine() {
        return openLine;
    }

    public void setOpenLine(BigDecimal openLine) {
        this.openLine = openLine;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getSingleStockScale() {
        return singleStockScale;
    }

    public void setSingleStockScale(BigDecimal singleStockScale) {
        this.singleStockScale = singleStockScale;
    }

    public BigDecimal getSmallStockScale() {
        return smallStockScale;
    }

    public void setSmallStockScale(BigDecimal smallStockScale) {
        this.smallStockScale = smallStockScale;
    }

    public BigDecimal getCreateStockScale() {
        return createStockScale;
    }

    public void setCreateStockScale(BigDecimal createStockScale) {
        this.createStockScale = createStockScale;
    }

    public BigDecimal getSmallSingleStockScale() {
        return smallSingleStockScale;
    }

    public void setSmallSingleStockScale(BigDecimal smallSingleStockScale) {
        this.smallSingleStockScale = smallSingleStockScale;
    }

    public BigDecimal getCreateSingleStockScale() {
        return createSingleStockScale;
    }

    public void setCreateSingleStockScale(BigDecimal createSingleStockScale) {
        this.createSingleStockScale = createSingleStockScale;
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

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getFinanceAmount() {
        return financeAmount;
    }

    public void setFinanceAmount(BigDecimal financeAmount) {
        this.financeAmount = financeAmount;
    }

    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean isSuspensionFlag() {
        return suspensionFlag;
    }

    public void setSuspensionFlag(boolean suspensionFlag) {
        this.suspensionFlag = suspensionFlag;
    }
}
