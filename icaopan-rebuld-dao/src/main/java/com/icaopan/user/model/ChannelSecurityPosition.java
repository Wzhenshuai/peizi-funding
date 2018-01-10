package com.icaopan.user.model;

import java.math.BigDecimal;
import java.util.Date;

public class ChannelSecurityPosition {

    private Integer id;

    private Integer customerId;

    private String internalSecurityId;

    private String stockName;
    
    private BigDecimal available;

    private BigDecimal amount;

    private Integer channelId;

    private Date updateTime;

    private BigDecimal costPrice;

    private BigDecimal totalCost;

    //持仓现价
    private BigDecimal price;

    private Integer userId;

    private String channelName;

    private String userName;

    private String customerName;

    private String customerNotes;

    private String     channelCode;
    //通道委托要买的数量
    private BigDecimal handleQuantity;

    private String createTime;

    public ChannelSecurityPosition() {
    }

    public ChannelSecurityPosition(Integer customerId, String internalSecurityId, Integer channelId, Integer userId) {
        this.customerId = customerId;
        this.internalSecurityId = internalSecurityId;
        this.channelId = channelId;
        this.userId = userId;
        this.totalCost = BigDecimal.ZERO;
    }

    public ChannelSecurityPosition(BigDecimal available, Integer channelId, String channelCode) {
        this.available = available;
        this.channelId = channelId;
        this.channelCode = channelCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public BigDecimal getHandleQuantity() {
        return handleQuantity;
    }

    public void setHandleQuantity(BigDecimal handleQuantity) {
        this.handleQuantity = handleQuantity;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
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

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
    
    
    
}