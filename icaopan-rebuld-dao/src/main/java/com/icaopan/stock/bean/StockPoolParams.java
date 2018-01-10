package com.icaopan.stock.bean;

/**
 * Created by RoyLeong @royleo.xyz on 2016/11/30.
 */
public class StockPoolParams {

    private String stockName;

    private String stockCode;

    private String type;

    private Integer customerId;

    public StockPoolParams() {
    }

    public StockPoolParams(String stockName, String type, Integer customerId) {
        this.stockName = stockName;
        this.type = type;
        this.customerId = customerId;
    }

    public StockPoolParams(String stockName, String stockCode, String type, Integer customerId) {
        this.stockName = stockName;
        this.stockCode = stockCode;
        this.type = type;
        this.customerId = customerId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
