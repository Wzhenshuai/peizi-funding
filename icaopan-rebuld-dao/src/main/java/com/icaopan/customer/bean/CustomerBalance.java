package com.icaopan.customer.bean;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/4/19 0019.
 */
public class CustomerBalance {

    private Integer    customerId;
    //资金方名称
    private String     customerName;
    //账户余额
    private BigDecimal balance;
    //上月实收费用
    private BigDecimal lastMonthFee;
    //上月发生费用
    private BigDecimal lastMonthTradeFee;
    //本月已发生费用
    private BigDecimal currentMonthTradeFee;


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getLastMonthFee() {
        return lastMonthFee;
    }

    public void setLastMonthFee(BigDecimal lastMonthFee) {
        this.lastMonthFee = lastMonthFee;
    }

    public BigDecimal getLastMonthTradeFee() {
        return lastMonthTradeFee;
    }

    public void setLastMonthTradeFee(BigDecimal lastMonthTradeFee) {
        this.lastMonthTradeFee = lastMonthTradeFee;
    }

    public BigDecimal getCurrentMonthTradeFee() {
        return currentMonthTradeFee;
    }

    public void setCurrentMonthTradeFee(BigDecimal currentMonthTradeFee) {
        this.currentMonthTradeFee = currentMonthTradeFee;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
