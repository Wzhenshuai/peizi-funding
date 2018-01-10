package com.icaopan.trade.model;

import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.enums.enumBean.TradeStatus;
import com.icaopan.util.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

public class ChannelPlacement {
    private Integer id;

    private String securityCode;

    private String securityName;
    
    private TradeSide side;

    private String channelName;

    private String userName;

    private String customerName;

    private BigDecimal quantity;

    private BigDecimal price;

    private BigDecimal amount;

    private BigDecimal fillQuantity;

    private BigDecimal fillAmount;

    private BigDecimal fillPrice;

    private TradeStatus status;
    /**
     * 券商资金账号
     **/
    private String      account;
    /**
     * 券商委托编号
     **/
    private String      placementCode;
    /**
     * 拒绝原因
     **/
    private String      rejectMessage;
    private Date        createTime;
    /**
     * 用户委托ID
     **/
    private Integer     placementId;
    /**
     * 通道ID
     **/
    private Integer     channelId;
    /**
     * 客户ID
     **/
    private Integer     customerId;

    private Integer userId;

    /**
     * 现价
     */
    private BigDecimal latestPrice;

    public ChannelPlacement() {
    }

    public ChannelPlacement(TradeStatus status, Integer placementId) {
        this.status = status;
        this.placementId = placementId;
    }

    public ChannelPlacement(String securityCode, TradeSide side, BigDecimal quantity, BigDecimal price, TradeStatus status, String account, String placementCode) {
        this.securityCode = securityCode;
        this.side = side;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.account = account;
        this.placementCode = placementCode;
    }

    public String getTimeStr() {
        return DateUtils.formatTime(createTime);
    }

    public String getDateTimeStr(){
        return DateUtils.formatDateTime(createTime);
    }

    public String getSideStr() {
        return side.getDisplay();
    }

    public String getStatusStr() {
        if (status == null) {
            return "";
        }
        return status.getDisplay();
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
        this.securityCode = securityCode == null ? null : securityCode.trim();
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
        return fillQuantity == null ? BigDecimal.ZERO : fillQuantity;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPlacementId() {
        return placementId;
    }

    public void setPlacementId(Integer placementId) {
        this.placementId = placementId;
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

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(BigDecimal latestPrice) {
        this.latestPrice = latestPrice;
    }

    
    public String getSecurityName() {
		return securityName;
	}

	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}

	@Override
    public String toString() {
        return "ChannelPlacement [id=" + id + ", securityCode=" + securityCode
               + ", side=" + side + ", channelName=" + channelName
               + ", userName=" + userName + ", customerName=" + customerName
               + ", quantity=" + quantity + ", price=" + price + ", amount="
               + amount + ", fillQuantity=" + fillQuantity + ", fillAmount="
               + fillAmount + ", fillPrice=" + fillPrice + ", status="
               + status + ", account=" + account + ", placementCode="
               + placementCode + ", rejectMessage=" + rejectMessage
               + ", createTime=" + createTime + ", placementId=" + placementId
               + ", channelId=" + channelId + ", customerId=" + customerId
               + ", userId=" + userId
                +",latestPrice="+latestPrice
                + "]";
    }
}