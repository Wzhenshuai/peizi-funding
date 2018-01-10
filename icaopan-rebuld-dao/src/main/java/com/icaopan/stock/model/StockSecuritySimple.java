package com.icaopan.stock.model;


import java.util.Date;

public class StockSecuritySimple {

    private String code;

    private String name;

    private Boolean suspensionFlag;


    public StockSecuritySimple() {
    }

    public StockSecuritySimple(String internalSecurityId, String exchangeCode, String code, String name, Date issueDate, Boolean suspensionFlag, String firstLetter) {
        this.code = code;
        this.name = name;
        this.suspensionFlag = suspensionFlag;
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

    public Boolean getSuspensionFlag() {
        return suspensionFlag;
    }

    public void setSuspensionFlag(Boolean suspensionFlag) {
        this.suspensionFlag = suspensionFlag;
    }

    public boolean isSuspension() {
        return suspensionFlag.booleanValue();
    }

}