package com.icaopan.trade.bean;

import java.math.BigDecimal;

/**
 * Created by ffff on 2017-04-27.
 */
public class PositionDock {
    //可用
    private BigDecimal available;
    //资金头寸
    private BigDecimal balance;
    //资金账号
    private String     account;
    //总资产
    private BigDecimal totalAssets;

    public BigDecimal getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(BigDecimal totalAssets) {
        this.totalAssets = totalAssets;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
