package com.icaopan.web.vo;

import java.math.BigDecimal;

public class StockDetailVO {

    private String     shortNameCn;
    private BigDecimal lastPrice;
    private String     Date;

    public String getShortNameCn() {
        return shortNameCn;
    }

    public void setShortNameCn(String shortNameCn) {
        this.shortNameCn = shortNameCn;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }


}
