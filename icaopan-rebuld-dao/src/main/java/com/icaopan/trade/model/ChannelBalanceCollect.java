package com.icaopan.trade.model;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/10/27 0027.
 */
public class ChannelBalanceCollect {

    private String channelName;

    private BigDecimal fillAmount;

    private BigDecimal sysCommissionFee;

    private BigDecimal tradeCommissionFee;

    //券商佣金比例
    private BigDecimal tradeCommissionRate;

    /**
     * 通道收费比例
     **/
    private BigDecimal sysCommissionRate;

    private BigDecimal differenceBanlance;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public BigDecimal getFillAmount() {
        return fillAmount;
    }

    public void setFillAmount(BigDecimal fillAmount) {
        this.fillAmount = fillAmount;
    }

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

    public BigDecimal getTradeCommissionRate() {
        return tradeCommissionRate;
    }

    public void setTradeCommissionRate(BigDecimal tradeCommissionRate) {
        this.tradeCommissionRate = tradeCommissionRate;
    }

    public BigDecimal getSysCommissionRate() {
        return sysCommissionRate;
    }

    public void setSysCommissionRate(BigDecimal sysCommissionRate) {
        this.sysCommissionRate = sysCommissionRate;
    }

    public BigDecimal getDifferenceBanlance() {
        if (sysCommissionFee == null || sysCommissionFee.compareTo(BigDecimal.ZERO) == 0) {
            differenceBanlance = BigDecimal.ZERO;
        } else {
            differenceBanlance = sysCommissionFee.subtract(tradeCommissionFee);
        }
        return differenceBanlance;
    }

    public void setDifferenceBanlance(BigDecimal differenceBanlance) {
        this.differenceBanlance = differenceBanlance;
    }
}
