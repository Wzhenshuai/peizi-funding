package com.icaopan.trade.model;

import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.enums.enumBean.TradeStatus;
import com.icaopan.util.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

public class PlacementHistory {
    private Integer id;

    private String securityCode;
    
    private String securityName;

    private TradeSide side;

    private String userName;

    private BigDecimal quantity;

    private BigDecimal price;

    private BigDecimal amount;

    private TradeStatus status;

    private BigDecimal commissionFee;

    private BigDecimal stampDutyFee;

    private BigDecimal transferFee;

    private BigDecimal fillQuantity;

    private BigDecimal fillAmount;

    private BigDecimal fillPrice;

    private Integer userId;

    private Date dateTime;

    private Integer placementId;

    private Integer customerId;


    public String getSideStr() {
        return side.getDisplay();
    }

    public String getStatusStr() {
        return status.getDisplay();
    }

    public String getDateTimeStr() {
        return DateUtils.formatDateTime(dateTime);
    }

    public Date getDateTime() {
        return dateTime;
    }


    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSecurityCode() {
        return securityCode;
    }


    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public BigDecimal getCommissionFee() {
        return commissionFee;
    }

    public void setCommissionFee(BigDecimal commissionFee) {
        this.commissionFee = commissionFee;
    }

    public BigDecimal getStampDutyFee() {
        return stampDutyFee;
    }

    public void setStampDutyFee(BigDecimal stampDutyFee) {
        this.stampDutyFee = stampDutyFee;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    public BigDecimal getFillQuantity() {
        return fillQuantity;
    }

    public void setFillQuantity(BigDecimal fillQuantity) {
        this.fillQuantity = fillQuantity;
    }

    public BigDecimal getFillAmount() {
        return fillAmount;
    }

    public void setFillAmount(BigDecimal fillAmount) {
        this.fillAmount = fillAmount;
    }

    public BigDecimal getFillPrice() {
        return fillPrice;
    }

    public void setFillPrice(BigDecimal fillPrice) {
        this.fillPrice = fillPrice;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public TradeSide getSide() {
        return side;
    }


    public void setSide(TradeSide side) {
        this.side = side;
    }


    public TradeStatus getStatus() {
        return status;
    }


    public void setStatus(TradeStatus status) {
        this.status = status;
    }

    public Integer getPlacementId() {
        return placementId;
    }


    public void setPlacementId(Integer placementId) {
        this.placementId = placementId;
    }


    public Integer getCustomerId() {
        return customerId;
    }


    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

	public String getSecurityName() {
		return securityName;
	}

	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}
    
    


}