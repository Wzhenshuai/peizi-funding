package com.icaopan.trade.bean;

import java.math.BigDecimal;

public class PlacementDock {

    //资金账号
    private String     account;
    //股票代码
    private String     symbol;
    //委托价格
    private BigDecimal price;
    //委托数量z
    private BigDecimal quantity;
    //成交数量
    private BigDecimal fillQuantity;
    //委托编号
    private String     executionSno;
    //委托方向
    private String     side;
    //委托时间
    private String     time;
    //委托日期
    private String     createDate;
    //错误信息
    private String     errorMessage;
    //委托状态
    private String     status;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getFillQuantity() {
        return fillQuantity;
    }

    public void setFillQuantity(BigDecimal fillQuantity) {
        this.fillQuantity = fillQuantity;
    }

    public String getExecutionSno() {
        return executionSno;
    }

    public void setExecutionSno(String executionSno) {
        this.executionSno = executionSno;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
