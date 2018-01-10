package com.icaopan.risk.bean;

import com.icaopan.util.BigDecimalUtil;

import java.io.Serializable;
import java.math.BigDecimal;

public class RiskCtrlVO implements Serializable, Comparable<RiskCtrlVO> {

    private static final long serialVersionUID = 1L;

    //用户序号
    private Integer    userId;
    //姓名
    private String     realName;
    //用户名
    private String     userName;
    //现金账户余额
    private BigDecimal cashAmount;
    //股票账户总资产
    private BigDecimal totalAmount;
    //股票账户余额
    private BigDecimal amount;
    //股票市值
    private BigDecimal marketValue;
    //冻结金额
    private BigDecimal frozenAmount;
    //警戒线
    private BigDecimal warnLine;
    //平仓线
    private BigDecimal openLine;
    //交易通道名称
    private String     channelName;
    //融资金额
    private BigDecimal financeAmount;
    //剩余本金
    private BigDecimal leftAmount;
    //距离警戒线金额
    private BigDecimal toWarnLine;
    //距离平仓线金额
    private BigDecimal toOpenLine;
    //持仓盈亏
    private BigDecimal profitValue;
    //现金可用余额
    private BigDecimal cashAvailableAmount;
    //净资产
    private BigDecimal pureAmount;
    //公司ID
    private Integer    customerId;

    private String customerName;

    private boolean suspensionFlag;

    private String     suspensionDisplay;
    private Integer    channelId;
    //占位符
    private String     placeholder;
    private BigDecimal orderRate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
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

    public BigDecimal getPureAmount() {
        if (totalAmount == null || financeAmount == null) {
            return BigDecimal.ZERO;
        }
        pureAmount = totalAmount.subtract(financeAmount);
        return pureAmount;
    }

    public void setPureAmount(BigDecimal pureAmount) {
        this.pureAmount = pureAmount;
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
        totalAmount = amount.add(marketValue);
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getFinanceAmount() {
        return financeAmount;
    }

    public void setFinanceAmount(BigDecimal financeAmount) {
        this.financeAmount = financeAmount;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public BigDecimal getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(BigDecimal leftAmount) {
        this.leftAmount = leftAmount;
    }

    public BigDecimal getToWarnLine() {
        return toWarnLine;
    }

    public void setToWarnLine(BigDecimal toWarnLine) {
        this.toWarnLine = toWarnLine;
    }

    public BigDecimal getToOpenLine() {
        return toOpenLine;
    }

    public void setToOpenLine(BigDecimal toOpenLine) {
        this.toOpenLine = toOpenLine;
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

    public BigDecimal getOrderRate() {
        if (this.getTotalAmount() == null || this.getTotalAmount().compareTo(BigDecimal.ZERO) == 0){
            return BigDecimal.ONE;
        }
        return BigDecimalUtil.divide(this.toWarnLine, this.getTotalAmount());
    }

    public void setOrderRate(BigDecimal orderRate) {
        this.orderRate = orderRate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean isSuspensionFlag() {
        return suspensionFlag;
    }

    public void setSuspensionFlag(boolean suspensionFlag) {
        this.suspensionFlag = suspensionFlag;
    }

    public String getSuspensionDisplay() {
        return this.suspensionFlag ? "是" : "否";
    }

    public void setSuspensionDisplay(String suspensionDisplay) {
        this.suspensionDisplay = suspensionDisplay;
    }

    @Override
    public int compareTo(RiskCtrlVO vo) {
        int compareResult = vo.getOrderRate().compareTo(this.getOrderRate());
        if (compareResult > 0) {
            return -1;
        } else if (compareResult < 0) {
            return 1;
        }
        return 0;
    }
}
