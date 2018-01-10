package com.icaopan.customer.model;

import com.icaopan.enums.enumBean.CustomerCostPattern;
import com.icaopan.enums.enumBean.CustomerStatus;
import com.icaopan.util.BigDecimalUtil;
import com.icaopan.util.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Customer implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String notes;

    /**
     * 正常,停用,锁定
     **/
    private String status;

    /**
     * 帐户余额
     **/
    private BigDecimal balance;

    /**
     * 低消模式：0通道，1公司
     **/
    private String costPattern;

    private Date createTime;

    /**
     * 最低消费
     **/
    private BigDecimal minCost;

    private Date modifyTime;

    /**
     * 用户默认费率
     **/
    private BigDecimal defaultTatio;

    private String defaultRatioStr;

    /**
     * 用户默认低消
     **/
    private BigDecimal defaultMinCost;

    private String createTimeStr;

    private String modifyTimeStr;

    private String costPatternStr;

    private String  statusStr;
    /**
     * 是否收取深市过户费
     **/
    private Boolean isSzTransferFee;

    public Customer() {
    }

    public Customer(Integer id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    public String getCostPattern() {
        return costPattern;
    }

    public void setCostPattern(String costPattern) {
        this.costPattern = costPattern;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getMinCost() {
        return minCost;
    }

    public void setMinCost(BigDecimal minCost) {
        this.minCost = minCost;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public BigDecimal getDefaultTatio() {
        return defaultTatio;
    }

    public void setDefaultTatio(BigDecimal defaultTatio) {
        this.defaultTatio = defaultTatio;
    }

    public BigDecimal getDefaultMinCost() {
        return defaultMinCost;
    }

    public void setDefaultMinCost(BigDecimal defaultMinCost) {
        this.defaultMinCost = defaultMinCost;
    }

    public String getCreateTimeStr() {
        return com.icaopan.util.DateUtils.formatDateTime(createTime);
    }


    public String getModifyTimeStr() {
        return DateUtils.formatDateTime(modifyTime);
    }

    public String getCostPatternStr() {
        return (costPattern == null) ? "" : CustomerCostPattern.getByCode(costPattern).getDisplay();
    }

    public String getStatusStr() {
        return (StringUtils.isBlank(status)) ? "" : CustomerStatus.getByCode(status).getDisplay();
    }


    public Boolean getIsSzTransferFee() {
        return isSzTransferFee;
    }

    public void setIsSzTransferFee(Boolean isSzTransferFee) {
        this.isSzTransferFee = isSzTransferFee;
    }

    public String getDefaultRatioStr() {
        if (defaultTatio == null) {
            return "";
        } else {
            return BigDecimalUtil.subZeroAndDot(defaultTatio).toString();
        }
    }
}