package com.icaopan.trade.bean;

import java.math.BigDecimal;
import java.util.Date;

public class FillDock {

    //委托编号
    private String     executionSno;
    //成交编号
    private String     reportSno;
    //资金账号
    private String     account;
    //成交价格
    private BigDecimal price;
    //成交数量
    private BigDecimal quantity;
    //成交日期
    private Date       createDate;
    //方向
    private String     side;

    public String getExecutionSno() {
        return executionSno;
    }

    public void setExecutionSno(String executionSno) {
        this.executionSno = executionSno;
    }

    public String getReportSno() {
        return reportSno;
    }

    public void setReportSno(String reportSno) {
        this.reportSno = reportSno;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

}
