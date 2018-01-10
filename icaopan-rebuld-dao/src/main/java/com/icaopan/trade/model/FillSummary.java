package com.icaopan.trade.model;

import com.icaopan.enums.enumBean.TradeSide;

import java.math.BigDecimal;

public class FillSummary {

	private String channelName;
	
	private String userName;
	
    private String securityCode;
    
    private String securityName;

    private BigDecimal quantity;

    private BigDecimal price;

    private BigDecimal amount;

    private TradeSide side;

    private Integer fillTimes;

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

    public String getSide() {
        return side.getDisplay();
    }

    public void setSide(TradeSide side) {
        this.side = side;
    }

    public Integer getFillTimes() {
        return fillTimes;
    }

    public void setFillTimes(Integer fillTimes) {
        this.fillTimes = fillTimes;
    }

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
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