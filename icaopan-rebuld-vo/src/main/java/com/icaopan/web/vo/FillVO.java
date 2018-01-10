package com.icaopan.web.vo;

import java.math.BigDecimal;
import java.util.Date;

public class FillVO {

    //股票名称
    private String     securityName;
    //股票代码
    private String     securityCode;
    //成交时间
    private Date       filledTime;
    //成交价格
    private BigDecimal filledPrice;
    //成交数量
    private BigDecimal filledQty;
    //成交金额
    private BigDecimal filledAmount;
    //交易类型-中文
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

    public Date getFilledTime() {
        return filledTime;
    }

    public void setFilledTime(Date filledTime) {
        this.filledTime = filledTime;
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
