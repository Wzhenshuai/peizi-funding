package com.icaopan.trade.service.dto;

import java.math.BigDecimal;

/**
 * Created by Wangzs on 2017-02-14.
 */
public class PlacementChannelDto {

    private Integer channelId;

    private String channelCode;

    //通道委托要 委托的数量
    private  BigDecimal handleQuantity;

    private int whole;

    private int left;

    private int priorityLevel;

    private  int cashAvailable;

    public int getCashAvailable() {
        return cashAvailable;
    }

    public void setCashAvailable(int cashAvailable) {
        this.cashAvailable = cashAvailable;
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

    public BigDecimal getHandleQuantity() {
        return handleQuantity;
    }

    public void setHandleQuantity(BigDecimal handleQuantity) {
        this.handleQuantity = handleQuantity;
    }

    public int getWhole() {
        return whole;
    }

    public void setWhole(int whole) {
        this.whole = whole;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public PlacementChannelDto() {}

    public PlacementChannelDto(int left, Integer channelId, String channelCode) {
        this.channelId = channelId;
        this.channelCode = channelCode;
        this.left = left;
    }

    public PlacementChannelDto(Integer channelId, String channelCode, int whole, int priorityLevel) {
        this.channelId = channelId;
        this.channelCode = channelCode;
        this.whole = whole;
        this.priorityLevel = priorityLevel;
    }

    public PlacementChannelDto(Integer channelId, String channelCode, int whole, int priorityLevel,int cashAvailable) {
        this.channelId = channelId;
        this.channelCode = channelCode;
        this.whole = whole;
        this.priorityLevel = priorityLevel;
        this.cashAvailable = cashAvailable;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
}
