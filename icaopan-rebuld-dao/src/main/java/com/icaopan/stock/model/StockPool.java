package com.icaopan.stock.model;

import com.icaopan.enums.enumBean.StockPoolType;

import java.util.Date;

public class StockPool {
    private Integer id;

    private String stockCode;

    private String stockName;

    private Date modifyTime;

    private String type;

    private String typeStr;

    private Integer customerId;

    private String customerName;

    private String placeHolder;

    public StockPool() {
    }

    public StockPool(String stockCode, String stockName, String type) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode == null ? null : stockCode.trim();
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName == null ? null : stockName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeStr() {
        return (type == null) ? null : StockPoolType.getByCode(type).getDisplay();
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }
}