package com.icaopan.user.model;

import com.icaopan.util.BigDecimalUtil;

import java.math.BigDecimal;
import java.util.Date;

public class UserSecurityPosition {

    private Integer id;

    private Integer customerId;

    private String internalSecurityId;

    private BigDecimal available;

    private BigDecimal amount;

    private Date updateTime;

    private BigDecimal costPrice;

    private Integer userId;

    private String userName;

    private String customerName;

    private BigDecimal totalCost;


    public UserSecurityPosition() {
    }

    public UserSecurityPosition(Integer customerId, String internalSecurityId, Integer userId, BigDecimal available, BigDecimal amount, BigDecimal costPrice) {
        this.customerId = customerId;
        this.internalSecurityId = internalSecurityId;
        this.userId = userId;
        this.available = available;
        this.amount = amount;
        this.costPrice = costPrice;
        if(this.costPrice!=null&&this.amount!=null) {
            this.totalCost = BigDecimalUtil.multiply(costPrice,amount);
        }else{
            this.totalCost = BigDecimalUtil.ZERO;
        }
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

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}