package com.icaopan.trade.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.enums.enumBean.TradeStatus;
import com.icaopan.util.DateUtils;

public class Placement {
    private Integer id;

    private String securityCode;

    private String securityName;
    
    private TradeSide side;

    private String userName;

    private BigDecimal quantity;

    private BigDecimal price;

    private BigDecimal amount;


    private TradeStatus status;
    /**
     * 是否收取深市过户费
     **/
    private Boolean     isSzTransferFee;

    private BigDecimal fillQuantity;

    private BigDecimal fillAmount;

    private BigDecimal fillPrice;
    /**
     * 佣金费率
     **/
    private BigDecimal ratioFee;
    /**
     * 佣金低消
     **/
    private BigDecimal minCost;

    private Integer userId;

    private Integer customerId;

    private Date time;

    private BigDecimal cancelQuantity;

    private BigDecimal invalidQuantity;

    private Integer channelId;
    
    private List<Integer> channelIds;

    private String channelCode;

    private String securityId;

    public Placement() {
    }

    public Placement(String securityCode, TradeSide side, BigDecimal quantity, BigDecimal price, BigDecimal amount, Integer userId) {
        this.securityCode = securityCode;
        this.side = side;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
        this.userId = userId;
    }

    public String getSideStr() {
        return side.getDisplay();
    }

    public String getStatusStr() {
    	if(status!=null){
    		return status.getDisplay();
    	}
    	return "";
    }

    public String getTimeStr() {
        return DateUtils.formatTime(time);
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
        if (quantity == null) {
            return new BigDecimal(0);
        }
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
        if (amount == null) {
            return new BigDecimal(0);
        }
        return amount;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public Boolean getIsSzTransferFee() {
        return isSzTransferFee;
    }


    public void setIsSzTransferFee(Boolean isSzTransferFee) {
        this.isSzTransferFee = isSzTransferFee;
    }


    public BigDecimal getFillQuantity() {
        if (fillQuantity == null) {
            return new BigDecimal(0);
        }
        return fillQuantity;
    }

    public void setFillQuantity(BigDecimal fillQuantity) {
        this.fillQuantity = fillQuantity;
    }

    public void addFillQuantity(BigDecimal fillQuantity) {
        if (this.fillQuantity == null) {
            this.fillQuantity = fillQuantity;
        } else {
            this.fillQuantity = this.fillQuantity.add(fillQuantity);
        }
    }

    public BigDecimal getFillAmount() {
        if (fillAmount == null) {
            return BigDecimal.ZERO;
        }
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


    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Integer getCustomerId() {
        return customerId;
    }


    public void setCustomerId(Integer i) {
        this.customerId = i;
    }


    public Date getTime() {
        return time;
    }


    public void setTime(Date time) {
        this.time = time;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
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

    public BigDecimal getCancelQuantity() {
        if (cancelQuantity == null) {
            return new BigDecimal(0);
        }
        return cancelQuantity;
    }

    public void setCancelQuantity(BigDecimal cancelQuantity) {
        this.cancelQuantity = cancelQuantity;
    }

    public void addCancelQuantity(BigDecimal cancelQuantity) {
        if (this.cancelQuantity == null) {
            this.cancelQuantity = cancelQuantity;
        } else {
            this.cancelQuantity = this.cancelQuantity.add(cancelQuantity);
        }
    }

    public BigDecimal getInvalidQuantity() {
        if (invalidQuantity == null) {
            return new BigDecimal(0);
        }
        return invalidQuantity;
    }

    public void setInvalidQuantity(BigDecimal invalidQuantity) {
        this.invalidQuantity = invalidQuantity;
    }

    public void addInvalidQuantity(BigDecimal invalidQuantity) {
        if (this.invalidQuantity == null) {
            this.invalidQuantity = invalidQuantity;
        } else {
            this.invalidQuantity = this.invalidQuantity.add(invalidQuantity);
        }
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

	public List<Integer> getChannelIds() {
		return channelIds;
	}

	public void setChannelIds(List<Integer> channelIds) {
		this.channelIds = channelIds;
	}

	public String getSecurityName() {
		return securityName;
	}

	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}
    
	
    
}