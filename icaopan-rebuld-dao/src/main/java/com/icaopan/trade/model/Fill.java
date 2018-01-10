package com.icaopan.trade.model;

import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.util.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

public class Fill {
    private Integer id;

    private String securityCode;
    
    private String securityName;
    
    private BigDecimal quantity;

    private TradeSide side;

    private String userName;

    private String channelName;

    private String customerName;

    private BigDecimal price;

    private BigDecimal amount;

    private Date   fillTime;
    /**
     * 券商的资金账号
     **/
    private String account;
    /**
     * 券商委托编号
     **/
    private String placementCode;
    /**
     * 券商成交编号
     **/
    private String fillCode;

    private Integer userId;

    private Integer channelPlacementId;

    private Integer channelId;

    private Integer customerId;

    private Integer placementId;


    public String getFillTimeStr() {
        return DateUtils.formatTime(fillTime);
    }

    public String getSideStr() {
        return side.getDisplay();
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

    public TradeSide getSide() {
        return side;
    }


    public void setSide(TradeSide side) {
        this.side = side;
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

    public Date getFillTime() {
        return fillTime;
    }

    public void setFillTime(Date fillTime) {
        this.fillTime = fillTime;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getChannelPlacementId() {
        return channelPlacementId;
    }

    public void setChannelPlacementId(Integer channelPlacementId) {
        this.channelPlacementId = channelPlacementId;
    }


    public Integer getCustomerId() {
        return customerId;
    }


    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }


    public Integer getPlacementId() {
        return placementId;
    }


    public void setPlacementId(Integer placementId) {
        this.placementId = placementId;
    }

    public Integer getChannelId() {
        return channelId;
    }


    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getChannelName() {
        return channelName;
    }


    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }


    public String getCustomerName() {
        return customerName;
    }


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getFillCode() {
        return fillCode;
    }

    public void setFillCode(String fillCode) {
        this.fillCode = fillCode;
    }

	public String getSecurityName() {
		return securityName;
	}

	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}

    
}