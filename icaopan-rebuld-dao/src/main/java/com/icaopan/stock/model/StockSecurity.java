package com.icaopan.stock.model;

import com.icaopan.util.DateUtils;

import java.util.Date;

public class StockSecurity {
    private String internalSecurityId;

    private String exchangeCode;

    private String code;

    private String name;

    private Date modifyTime;

    private Date issueDate;

    private Boolean suspensionFlag;

    private String flag;

    private String firstLetter;

    private String modifyTimeStr;

    private String issueDateStr;

    public StockSecurity() {
    }

    public StockSecurity(String internalSecurityId, String exchangeCode, String code, String name, Date issueDate, Boolean suspensionFlag, String firstLetter) {
        this.internalSecurityId = internalSecurityId;
        this.exchangeCode = exchangeCode;
        this.code = code;
        this.name = name;
        this.issueDate = issueDate;
        this.suspensionFlag = suspensionFlag;
        this.firstLetter = firstLetter;
    }

    public String getInternalSecurityId() {
        return internalSecurityId;
    }

    public void setInternalSecurityId(String internalSecurityId) {
        this.internalSecurityId = internalSecurityId == null ? null : internalSecurityId.trim();
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode == null ? null : exchangeCode.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Boolean getSuspensionFlag() {
        return suspensionFlag;
    }

    public void setSuspensionFlag(Boolean suspensionFlag) {
        this.suspensionFlag = suspensionFlag;
    }

    public boolean isSuspension() {
        return suspensionFlag.booleanValue();
    }

    public String getFlag() {

        if (!suspensionFlag) {
            return "正常";
        }
        return "停牌";
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter == null ? null : firstLetter.trim();
    }

    public String getIssueDateStr() {
        return (issueDate == null) ? null : DateUtils.formatDate(issueDate, "yyyy-MM-dd");
    }

    public String getModifyTimeStr() {
        return DateUtils.formatDateTime(modifyTime);
    }


}