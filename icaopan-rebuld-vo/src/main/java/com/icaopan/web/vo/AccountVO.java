package com.icaopan.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountVO implements Serializable {

    private static final long serialVersionUID = 1L;
    //股票账户总资产
    private BigDecimal totalAmount;
    //股票市值
    private BigDecimal marketValue;
    //持仓盈亏
    private BigDecimal profitValue;
    //现金余额
    private BigDecimal cashAmount;
    //现金可用余额
    private BigDecimal cashAvailableAmount;
    //冻结金额
    private BigDecimal cashFrozenAmount;
    //净资产
    private BigDecimal pureAmount;
    //警戒线
    private BigDecimal warnLine;
    //平仓线
    private BigDecimal openLine;
    //融资总额
    private BigDecimal financeAmount;

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    public BigDecimal getProfitValue() {
        return profitValue;
    }

    public void setProfitValue(BigDecimal profitValue) {
        this.profitValue = profitValue;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getCashAvailableAmount() {
        return cashAvailableAmount;
    }

    public void setCashAvailableAmount(BigDecimal cashAvailableAmount) {
        this.cashAvailableAmount = cashAvailableAmount;
    }

    public BigDecimal getCashFrozenAmount() {
        return cashFrozenAmount;
    }

    public void setCashFrozenAmount(BigDecimal cashFrozenAmount) {
        this.cashFrozenAmount = cashFrozenAmount;
    }

    public BigDecimal getPureAmount() {
        if (totalAmount == null || financeAmount == null) {
            return BigDecimal.ZERO;
        }
        pureAmount = totalAmount.subtract(financeAmount);
        return pureAmount;
    }

    public BigDecimal getWarnLine() {
        return warnLine;
    }

    public void setWarnLine(BigDecimal warnLine) {
        this.warnLine = warnLine;
    }

    public BigDecimal getOpenLine() {
        return openLine;
    }

    public void setOpenLine(BigDecimal openLine) {
        this.openLine = openLine;
    }

    public BigDecimal getTotalAmount() {
        totalAmount = cashAmount.add(marketValue);
        return totalAmount;
    }

    public BigDecimal getFinanceAmount() {
        return financeAmount;
    }

    public void setFinanceAmount(BigDecimal financeAmount) {
        this.financeAmount = financeAmount;
    }


}
