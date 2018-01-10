package com.icaopan.customer.bean;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/4/18 0018.
 */
public class CustomerBillFeeResult {

    private BigDecimal fee;

    private Integer customerId;

    private BigDecimal minCost;

    private Integer channelId;

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getMinCost() {
        return minCost;
    }

    public void setMinCost(BigDecimal minCost) {
        this.minCost = minCost;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }
}
