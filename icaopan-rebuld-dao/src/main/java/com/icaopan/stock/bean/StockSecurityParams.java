package com.icaopan.stock.bean;

/**
 * Created by RoyLeong @royleo.xyz on 2016/11/30.
 */
public class StockSecurityParams {

    private String exchangeCode;

    private String code;

    private String name;

    private Boolean suspensionFlag;

    public StockSecurityParams() {
    }

    public StockSecurityParams(String exchangeCode, String code, String name, Boolean suspensionFlag) {
        this.exchangeCode = exchangeCode;
        this.code = code;
        this.name = name;
        this.suspensionFlag = suspensionFlag;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSuspensionFlag() {
        return suspensionFlag;
    }

    public void setSuspensionFlag(Boolean suspensionFlag) {
        this.suspensionFlag = suspensionFlag;
    }
}
