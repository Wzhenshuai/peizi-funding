package com.icaopan.stock.model;

import java.util.Date;

/**
 * 股票白名单以及股票黑名单的通用实体类
 */
public class StockUser {
    private Integer id;

    private String stockCode;

    private String stockName;

    private Integer userId;

    private Integer customerId;

    private Date updateTime;

    public StockUser() {
    }

    public StockUser(String stockCode, String stockName, Integer userId, Integer customerId) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.userId = userId;
        this.customerId = customerId;
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
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
