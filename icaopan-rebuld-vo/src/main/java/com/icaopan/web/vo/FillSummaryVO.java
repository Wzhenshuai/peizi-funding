package com.icaopan.web.vo;

import java.math.BigDecimal;

public class FillSummaryVO {

    //股票名称
    private String     securityName;
    //股票代码
    private String     securityCode;
    //成交次数
    private Integer    filledTimes;
    //成交价格
    private BigDecimal filledPrice;
    //成交数量
    private BigDecimal filledQty;
    //成交金额
    private BigDecimal filledAmount;
    //交易类型
    private String     tradeTypeDisplay;

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public Integer getFilledTimes() {
        return filledTimes;
    }

    public void setFilledTimes(Integer filledTimes) {
        this.filledTimes = filledTimes;
    }

    public BigDecimal getFilledPrice() {
        return filledPrice;
    }

    public void setFilledPrice(BigDecimal filledPrice) {
        this.filledPrice = filledPrice;
    }

    public BigDecimal getFilledQty() {
        return filledQty;
    }

    public void setFilledQty(BigDecimal filledQty) {
        this.filledQty = filledQty;
    }

    public String getTradeTypeDisplay() {
        return tradeTypeDisplay;
    }

    public void setTradeTypeDisplay(String tradeTypeDisplay) {
        this.tradeTypeDisplay = tradeTypeDisplay;
    }

    public BigDecimal getFilledAmount() {
        return filledAmount;
    }

    public void setFilledAmount(BigDecimal filledAmount) {
        this.filledAmount = filledAmount;
    }

}
