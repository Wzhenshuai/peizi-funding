package com.icaopan.user.model;

import com.alibaba.druid.util.StringUtils;
import com.icaopan.customer.model.Channel;
import com.icaopan.customer.model.Customer;
import com.icaopan.enums.enumBean.UserClassType;
import com.icaopan.enums.enumBean.UserStatus;
import com.icaopan.enums.enumBean.UserTradeType;
import com.icaopan.util.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String userName;

    private String realName;

    private String password;

    private BigDecimal warnLine;

    private BigDecimal openLine;

    private BigDecimal singleStockScale;

    private BigDecimal smallStockScale;

    private BigDecimal createStockScale;

    private BigDecimal smallSingleStockScale;

    private BigDecimal createSingleStockScale;

    private String userClassTypeVal;

    private String userClassTypeDisplay;

    private String status;

    private Integer loginCount;

    private String relatedUuid;

    private String isDefaultFee;

    private Integer channelId;

    private String channelIds;

    private Integer customerId;

    private BigDecimal ratioFee;

    private BigDecimal minCost;

    private BigDecimal amount;

    private BigDecimal totalMaketValue; //股票市值

    private BigDecimal available;

    private BigDecimal frozen;

    private BigDecimal cashAmount;

    private BigDecimal financeAmount;

    private Date lastLoginTime;

    private String lastLoginTimeStr;

    private Date createTime;

    private Date modifyTime;

    private Channel channel;

    private Customer customer;

    private String createTimeStr;

    private String modifyTimeStr;

    private String statusStr;

    private String stockCode;

    private BigDecimal quantity;

    private BigDecimal unitPrice;

    private Boolean isSzTransferFee;

    private String placeholder;

    private String customerName;

    private String channelName;


    //用户交易类型：通道优先下单、通道平分下单
    private UserTradeType userTradeType;
    private int           userTradeTypeVal;
    //高级风控标志位
    private int riskFlag;
    //用户单笔金额上限
    private BigDecimal riskAmountQuota;
    //用户单笔下单数量上限
    private BigDecimal riskQuantityQuota;
    //涨停振幅比例
    private BigDecimal riskUpAmplitude;
    //跌停振幅比例
    private BigDecimal riskDownAmplitude;
    //黑名单
    private String blackList;
    //白名单
    private String whiteList;

    public User() {
        this.userTradeType = UserTradeType.PRIOR;
    }

    public User(Integer id, String relatedUuid) {
        this.relatedUuid = relatedUuid;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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

    public BigDecimal getSingleStockScale() {
        return singleStockScale;
    }

    public void setSingleStockScale(BigDecimal singleStockScale) {
        this.singleStockScale = singleStockScale;
    }

    public BigDecimal getSmallStockScale() {
        return smallStockScale;
    }

    public void setSmallStockScale(BigDecimal smallStockScale) {
        this.smallStockScale = smallStockScale;
    }

    public BigDecimal getCreateStockScale() {
        return createStockScale;
    }

    public void setCreateStockScale(BigDecimal createStockScale) {
        this.createStockScale = createStockScale;
    }

    public BigDecimal getSmallSingleStockScale() {
        return smallSingleStockScale;
    }

    public void setSmallSingleStockScale(BigDecimal smallSingleStockScale) {
        this.smallSingleStockScale = smallSingleStockScale;
    }

    public BigDecimal getCreateSingleStockScale() {
        return createSingleStockScale;
    }

    public void setCreateSingleStockScale(BigDecimal createSingleStockScale) {
        this.createSingleStockScale = createSingleStockScale;
    }

    public String getLine(){
        return warnLine.toString() + "/" + openLine.toString();
    }

    public String getStockScale(){
        return singleStockScale.toString()+"/"+smallSingleStockScale.toString()+"/"+smallStockScale.toString()+"/"+createSingleStockScale.toString()+"/"+createStockScale.toString();
    }

    public String getUserClassTypeVal() {
        return userClassTypeVal;
    }

    public void setUserClassTypeVal(String userClassTypeVal) {
        this.userClassTypeVal = userClassTypeVal;
    }

    public String getUserClassTypeDisplay() {
        if (userClassTypeVal == null) return null;
        UserClassType userClassType = UserClassType.getByCode(userClassTypeVal);
        userClassTypeDisplay = userClassType.getDisplay();
        return userClassTypeDisplay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public String getRelatedUuid() {
        return relatedUuid;
    }

    public void setRelatedUuid(String relatedUuid) {
        this.relatedUuid = relatedUuid == null ? null : relatedUuid.trim();
    }

    public String getIsDefaultFee() {
        return isDefaultFee;
    }

    public void setIsDefaultFee(String isDefaultFee) {
        this.isDefaultFee = isDefaultFee == null ? null : isDefaultFee.trim();
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelIds() {
        return channelIds;
    }

    public void setChannelIds(String channelIds) {
        this.channelIds = channelIds;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getRatioFee() {
        return ratioFee;
    }

    public void setRatioFee(BigDecimal ratioFee) {
        this.ratioFee = ratioFee;
    }

    public String getRatioFeeDisplay(){
        if(StringUtils.equals(isDefaultFee,"1")){
            return "默认费率";
        }
        return ratioFee.toString();
    }

    public BigDecimal getMinCost() {
        return minCost;
    }

    public void setMinCost(BigDecimal minCost) {
        this.minCost = minCost;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }

    public BigDecimal getFrozen() {
        return frozen;
    }

    public void setFrozen(BigDecimal frozen) {
        this.frozen = frozen;
    }

    public BigDecimal getCashAmount() {
        if (cashAmount == null) {
            cashAmount = BigDecimal.ZERO;
        }
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getFinanceAmount() {
        if (financeAmount == null) {
            financeAmount = BigDecimal.ZERO;
        }
        return financeAmount;
    }

    public void setFinanceAmount(BigDecimal financeAmount) {
        this.financeAmount = financeAmount;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginTimeStr() {
        return (lastLoginTime == null) ? null : DateUtils.formatDateTime(lastLoginTime);
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCreateTimeStr() {
        return DateUtils.formatDateTime(createTime);
    }

    public String getModifyTimeStr() {
        return DateUtils.formatDateTime(modifyTime);
    }

    public String getStatusStr() {
        return UserStatus.getByCode(status).getDisplay();
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Boolean getSzTransferFee() {
        return isSzTransferFee;
    }

    public void setSzTransferFee(Boolean szTransferFee) {
        isSzTransferFee = szTransferFee;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public UserTradeType getUserTradeType() {
        return userTradeType;
    }

    public void setUserTradeType(UserTradeType userTradeType) {
        this.userTradeType = userTradeType;
    }

    public String getUserTradeTypeDisplay(){
        if(userTradeType!=null){
            return userTradeType.getDisplay();
        }
        return "";
    }

    public int getUserTradeTypeVal() {
        return userTradeTypeVal;
    }

    public void setUserTradeTypeVal(int userTradeTypeVal) {
        this.userTradeTypeVal = userTradeTypeVal;
    }

    public String getCustomerName(){
        return customer.getName();
    }

    public String getChannelName(){
        return channel.getName();
    }

    public int getRiskFlag() {
        return riskFlag;
    }

    public void setRiskFlag(int riskFlag) {
        this.riskFlag = riskFlag;
    }

    public BigDecimal getRiskAmountQuota() {
        return riskAmountQuota;
    }

    public void setRiskAmountQuota(BigDecimal riskAmountQuota) {
        this.riskAmountQuota = riskAmountQuota;
    }

    public BigDecimal getRiskQuantityQuota() {
        return riskQuantityQuota;
    }

    public void setRiskQuantityQuota(BigDecimal riskQuantityQuota) {
        this.riskQuantityQuota = riskQuantityQuota;
    }

    public BigDecimal getRiskUpAmplitude() {
        return riskUpAmplitude;
    }

    public void setRiskUpAmplitude(BigDecimal riskUpAmplitude) {
        this.riskUpAmplitude = riskUpAmplitude;
    }

    public BigDecimal getRiskDownAmplitude() {
        return riskDownAmplitude;
    }

    public void setRiskDownAmplitude(BigDecimal riskDownAmplitude) {
        this.riskDownAmplitude = riskDownAmplitude;
    }

    public String getBlackList() {
        return blackList;
    }

    public void setBlackList(String blackList) {
        this.blackList = blackList;
    }

    public String getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(String whiteList) {
        this.whiteList = whiteList;
    }

    public BigDecimal getTotalMaketValue() {
        return totalMaketValue;
    }

    public void setTotalMaketValue(BigDecimal totalMaketValue) {
        this.totalMaketValue = totalMaketValue;
    }
}