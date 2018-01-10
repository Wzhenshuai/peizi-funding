package com.icaopan.trade.service.dto;

import com.icaopan.enums.enumBean.TradeSide;

import java.math.BigDecimal;

/**
 * Created by Wangzs on 2017-03-08.
 */
public class TradeFeeParam {

    private String securityCode;

    private TradeSide side;

    private BigDecimal price;

    private BigDecimal quantity;

    private Boolean isSzTransferFee;

    /**佣金费率**/
    private BigDecimal ratioFee;
    /**佣金低消**/
    private BigDecimal minCost;

    public TradeFeeParam(){}

    public TradeFeeParam(String securityCode, TradeSide side, BigDecimal price, Boolean isSzTransferFee, BigDecimal quantity,BigDecimal ratioFee,BigDecimal minCost) {
        this.securityCode = securityCode;
        this.side = side;
        this.price = price;
        this.isSzTransferFee = isSzTransferFee;
        this.quantity = quantity;
        this.ratioFee = ratioFee;
        this.minCost = minCost;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public TradeSide getSide() {
        return side;
    }

    public void setSide(TradeSide side) {
        this.side = side;
    }

    public BigDecimal getPrice() {
        if (price == null){
            price = new BigDecimal(0);
        }
        return price;

    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getIsSzTransferFee() {
        return isSzTransferFee;
    }

    public void setIsSzTransferFee(Boolean isSzTransferFee) {
        this.isSzTransferFee = isSzTransferFee;
    }

    public BigDecimal getQuantity() {
        if (quantity == null){
            quantity = new BigDecimal(0);
        }
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRatioFee() {
        return ratioFee;
    }

    public void setRatioFee(BigDecimal ratioFee) {
        this.ratioFee = ratioFee;
    }

    public BigDecimal getMinCost() {
        return minCost;
    }

    public void setMinCost(BigDecimal minCost) {
        this.minCost = minCost;
    }
}
