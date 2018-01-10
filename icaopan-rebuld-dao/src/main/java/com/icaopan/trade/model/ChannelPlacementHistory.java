package com.icaopan.trade.model;

import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.enums.enumBean.TradeStatus;
import com.icaopan.util.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

public class ChannelPlacementHistory {
    private Integer id;

    private String securityCode;

    private String securityName;
    
    private TradeSide side;

    private String userName;

    private String channelName;

    private String customerName;

    private BigDecimal quantity;

    private BigDecimal price;

    private BigDecimal amount;

    private BigDecimal fillQuantity;

    private BigDecimal fillAmount;

    private BigDecimal fillPrice;

    private TradeStatus status;

    private String account;
    private String placementCode;

    private String rejectMessage;

    private Date    dateTime;
    /**
     * 通道ID
     **/
    private Integer channelId;
    /**
     * 通道委托ID
     **/
    private Integer channelPlacementId;
    /**
     * 客户ID
     **/
    private Integer customerId;
    /**
     * 用户委托ID
     **/
    private Integer placementId;

    private Integer userId;

    private BigDecimal sysCommissionFee;

    private BigDecimal tradeCommissionFee;

    public BigDecimal getSysCommissionFee() {
        return sysCommissionFee;
    }

    public void setSysCommissionFee(BigDecimal sysCommissionFee) {
        this.sysCommissionFee = sysCommissionFee;
    }

    public BigDecimal getTradeCommissionFee() {
        return tradeCommissionFee;
    }

    public void setTradeCommissionFee(BigDecimal tradeCommissionFee) {
        this.tradeCommissionFee = tradeCommissionFee;
    }

    public String getSideStr() {
        return side.getDisplay();
    }

    public String getStatusStr() {
        return status.getDisplay();
    }

    public String getDateTimeStr() {
        return DateUtils.formatDateTime(dateTime);
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


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPlacementCode() {
        return placementCode;
    }

    public void setPlacementCode(String placementCode) {
        this.placementCode = placementCode;
    }

    public String getRejectMessage() {
        return rejectMessage;
    }

    public void setRejectMessage(String rejectMessage) {
        this.rejectMessage = rejectMessage == null ? null : rejectMessage.trim();
    }


    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getChannelId() {
        return channelId;
    }


    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }


    public Integer getChannelPlacementId() {
        return channelPlacementId;
    }


    public void setChannelPlacementId(Integer channelPlacementId) {
        this.channelPlacementId = channelPlacementId;
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

    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getCustomerName() {
        return customerName;
    }


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getChannelName() {
        return channelName;
    }


    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

	public String getSecurityName() {
		return securityName;
	}

	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}
    
    


}