package com.icaopan.risk.bean;

import java.math.BigDecimal;

/**
 * Created by RoyLeong @royleo.xyz on 2017/3/13.
 * 封装传输参数类
 */
public class Content {

    private String code;

    private BigDecimal availableToSell;

    private BigDecimal price;

    public Content() {
    }

    public Content(String code, BigDecimal availableToSell, BigDecimal price) {
        this.code = code;
        this.availableToSell = availableToSell;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getAvailableToSell() {
        return availableToSell;
    }

    public void setAvailableToSell(BigDecimal availableToSell) {
        this.availableToSell = availableToSell;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}