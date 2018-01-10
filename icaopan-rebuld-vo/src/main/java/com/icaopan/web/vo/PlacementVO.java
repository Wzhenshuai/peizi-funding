package com.icaopan.web.vo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlacementVO {

    //委托id
    private Integer placementId;
    //证券代码
    private String  securityCode;
    //证券名称
    private String  securityName;
    //委托时间
    private Date    createTime;

    private String     tradeType;
    //交易类型
    private String     tradeTypeDisplay;
    //委托价格
    private BigDecimal placementPrice;
    //委托数量
    private BigDecimal placementQty;
    //委托金额
    private BigDecimal placementAmount;
    //成交价格
    private BigDecimal filledPrice;
    //成交数量
    private BigDecimal filledQty;
    //成交金额
    private BigDecimal filledAmount;
    //成交比例
    private BigDecimal filledPercentage;
    //委托状态
    private String     placementStatusDisplay;
    //委托状态
    private String     placementStatus;
    //委托时间展示
    private String     createTimeStr;

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getPlacementId() {
        return placementId;
    }

    public void setPlacementId(Integer placementId) {
        this.placementId = placementId;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTradeTypeDisplay() {
        return tradeTypeDisplay;
    }

    public void setTradeTypeDisplay(String tradeTypeDisplay) {
        this.tradeTypeDisplay = tradeTypeDisplay;
    }

    public BigDecimal getPlacementPrice() {
        return placementPrice;
    }

    public void setPlacementPrice(BigDecimal placementPrice) {
        this.placementPrice = placementPrice;
    }

    public BigDecimal getPlacementQty() {
        return placementQty;
    }

    public void setPlacementQty(BigDecimal placementQty) {
        this.placementQty = placementQty;
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

    public BigDecimal getFilledAmount() {
        return filledAmount;
    }

    public void setFilledAmount(BigDecimal filledAmount) {
        this.filledAmount = filledAmount;
    }

    public String getPlacementStatusDisplay() {
        return placementStatusDisplay;
    }

    public void setPlacementStatusDisplay(String placementStatusDisplay) {
        this.placementStatusDisplay = placementStatusDisplay;
    }

    public BigDecimal getPlacementAmount() {
        return placementPrice.multiply(placementQty);
    }

    public void setPlacementAmount(BigDecimal placementAmount) {
        this.placementAmount = placementAmount;
    }

    public BigDecimal getFilledPercentage() {
        if (filledQty == null) {
            return BigDecimal.ZERO;
        }
        filledPercentage = filledQty.divide(placementQty, 2, BigDecimal.ROUND_HALF_UP);
        return filledPercentage;
    }

    public void setFilledPercentage(BigDecimal filledPercentage) {
        this.filledPercentage = filledPercentage;
    }


    public String getPlacementStatus() {
        return placementStatus;
    }

    public void setPlacementStatus(String placementStatus) {
        this.placementStatus = placementStatus;
    }

    public String getCreateTimeStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        return format.format(createTime);
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}
