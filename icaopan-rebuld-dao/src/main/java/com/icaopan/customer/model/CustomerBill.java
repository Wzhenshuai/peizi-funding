package com.icaopan.customer.model;

import com.icaopan.util.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerBill {
    private Integer id;

    private Integer channelId;

    private String operationType;

    private BigDecimal balance;

    private BigDecimal fillAmount;

    private BigDecimal fee;

    private String operationUser;

    private Date operationTime;

    private Integer customerId;

    private String channelName;
    private String customerName;
    private String operationTimeStr;

    public CustomerBill() {
    }

    public CustomerBill(String operationType, BigDecimal balance, String operationUser, Date operationTime, Integer customerId) {
        this.operationType = operationType;
        this.balance = balance;
        this.operationUser = operationUser;
        this.operationTime = operationTime;
        this.customerId = customerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getFillAmount() {
        return fillAmount;
    }

    public void setFillAmount(BigDecimal fillAmount) {
        this.fillAmount = fillAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getOperationUser() {
        return operationUser;
    }

    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser == null ? null : operationUser.trim();
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    ;

    public String getOperationTimeStr() {
        if (operationTime == null) {
            return "";
        }
        return DateUtils.formatDateTime(operationTime);
    }
}