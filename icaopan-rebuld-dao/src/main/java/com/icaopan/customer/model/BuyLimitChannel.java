package com.icaopan.customer.model;

import com.icaopan.util.BigDecimalUtil;

import java.math.BigDecimal;

/**
 * <P></P>
 * User: <a href="mailto:xuhm@gudaiquan.com">许昊旻</a>
 * Date: 2017/6/22
 * Time: 下午12:30
 */
public class BuyLimitChannel extends Channel {
    private String     securityCode;//证券代码
    private BigDecimal price;//现价
    private BigDecimal amount;//通道限制总价
    private BigDecimal position;//持仓
    private BigDecimal unFilledQuantity;//已下单但未成交数量

    public BuyLimitChannel(){

    }
    public BuyLimitChannel(Integer id, BigDecimal cashAvailable) {
        super(id, cashAvailable);
    }

    public BuyLimitChannel(Integer id, BigDecimal cashAvailable, int priorityLevel, Boolean isAvailable) {
        super(id, cashAvailable, priorityLevel, isAvailable);
    }

    public BuyLimitChannel(Integer id, BigDecimal cashAvailable, BigDecimal quota) {
        super(id, cashAvailable, quota);
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
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

    public BigDecimal getPosition() {
        return position;
    }

    public void setPosition(BigDecimal position) {
        this.position = position;
    }

    public BigDecimal getUnFilledQuantity() {
        return unFilledQuantity;
    }

    public void setUnFilledQuantity(BigDecimal unFilledQuantity) {
        this.unFilledQuantity = unFilledQuantity;
    }

    public BigDecimal getAvailable() {
        if (amount == null) {//说明没有设置通道上该证券的持仓限额
            return super.getAvailable();
        } else {//如果设置了持仓限制，则计算通道该证券的剩余可买资金头寸=限额[amount]-最新价[price]*(持仓[position]+未成交数量[unFilledQuantity])
            BigDecimal available = super.getAvailable();
            if (position == null) {
                position = BigDecimal.ZERO;
            }
            if (unFilledQuantity == null) {
                unFilledQuantity = BigDecimal.ZERO;
            }
            if (price == null) {
                price = BigDecimal.ZERO;
            }
            BigDecimal totalQuantity = BigDecimalUtil.add(position, unFilledQuantity);
            BigDecimal totalAmount = BigDecimalUtil.multiply(totalQuantity, price);
            BigDecimal channelAvailable = BigDecimalUtil.minus(amount, totalAmount);
            if(channelAvailable.compareTo(BigDecimal.ZERO)<0){
                channelAvailable = BigDecimal.ZERO;
            }
            if (available.compareTo(channelAvailable) > 0) {
                return channelAvailable;
            } else {
                return available;
            }
        }
    }
}
