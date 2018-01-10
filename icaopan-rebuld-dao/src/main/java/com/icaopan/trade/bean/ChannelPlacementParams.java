package com.icaopan.trade.bean;


import java.math.BigDecimal;

public class ChannelPlacementParams {

    private String securityCode;

    private String side;

    private String status;

    private String[] statusArray;

    private String[] channelArray;

    private int[] customerIdArray;

    private Integer placementId;

    private Integer channelId;

    private Integer userId;

    private String userName;

    private Integer customerId;

    private String account;

    private BigDecimal price;

    private BigDecimal quantity;

    private String createDate;

    public ChannelPlacementParams() {
    }

    public ChannelPlacementParams(Integer placementId) {
        this.placementId = placementId;
    }

    public ChannelPlacementParams(String securityCode, String side, Integer channelId, Integer customerId) {
        this.securityCode = securityCode;
        this.side = side;
        this.channelId = channelId;
        this.customerId = customerId;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String[] getStatusArray() {
        return statusArray;
    }

    public void setStatusArray(String[] statusArray) {
        this.statusArray = statusArray;
    }

    public String[] getChannelArray() {
        return channelArray;
    }

    public void setChannelArray(String[] channelArray) {
        this.channelArray = channelArray;
    }

    public int[] getCustomerIdArray() {
        return customerIdArray;
    }

    public void setCustomerIdArray(int[] customerIdArray) {
        this.customerIdArray = customerIdArray;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
