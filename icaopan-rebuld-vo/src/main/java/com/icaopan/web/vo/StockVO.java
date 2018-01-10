package com.icaopan.web.vo;


public class StockVO {

    private String value;
    private String data;
    private String name;
    private String shortNameCN;
    private String exChangeCode;
    private String stockStatus;//0 - 正常   1- 停牌  2- 退市 

    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getExChangeCode() {
        return exChangeCode;
    }

    public void setExChangeCode(String exChangeCode) {
        this.exChangeCode = exChangeCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortNameCN() {
        return shortNameCN;
    }

    public void setShortNameCN(String shortNameCN) {
        this.shortNameCN = shortNameCN;
    }

    @Override
    public String toString() {
        return "StockBean [value=" + value + ", data=" + data + ", name="
               + name + ", shortNameCN=" + shortNameCN + ", stockStatus="
               + stockStatus + ", getStockStatus()=" + getStockStatus()
               + ", getValue()=" + getValue() + ", getData()=" + getData()
               + ", getName()=" + getName() + ", getShortNameCN()="
               + getShortNameCN() + ", getClass()=" + getClass()
               + ", hashCode()=" + hashCode() + ", toString()="
               + super.toString() + "]";
    }
}
