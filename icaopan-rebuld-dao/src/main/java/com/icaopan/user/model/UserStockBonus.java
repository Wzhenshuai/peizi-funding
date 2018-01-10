package com.icaopan.user.model;

import com.icaopan.enums.enumBean.StockBonusStatus;
import com.icaopan.util.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

public class UserStockBonus {

    private Integer    id;
    private Integer    userId;
    private String     userName;
    //通道持仓id
    private Integer    channelSecurityPositionId;
    //持仓通道名称
    private String     channelName;
    //股票代码
    private String     securityCode;
    //股票名称
    private String     securityName;
    //当前通道持仓数量
    private BigDecimal securityPositionAmount;
    //券商资金调整数量
    private BigDecimal bonusBrokerAdjustAmount;
    //资金调整数量
    private BigDecimal bonusAdjustAmount;
    //证券调整数量
    private BigDecimal securityAdjustAmount;
    //状态
    private Integer    status;
    //创建时间
    private Date       createTime;
    private String     remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChannelSecurityPositionId() {
        return channelSecurityPositionId;
    }

    public void setChannelSecurityPositionId(Integer channelSecurityPositionId) {
        this.channelSecurityPositionId = channelSecurityPositionId;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public BigDecimal getBonusBrokerAdjustAmount() {
        return bonusBrokerAdjustAmount;
    }

    public void setBonusBrokerAdjustAmount(BigDecimal bonusBrokerAdjustAmount) {
        this.bonusBrokerAdjustAmount = bonusBrokerAdjustAmount;
    }

    public BigDecimal getBonusAdjustAmount() {
        return bonusAdjustAmount;
    }

    public void setBonusAdjustAmount(BigDecimal bonusAdjustAmount) {
        this.bonusAdjustAmount = bonusAdjustAmount;
    }

    public BigDecimal getSecurityAdjustAmount() {
        return securityAdjustAmount;
    }

    public void setSecurityAdjustAmount(BigDecimal securityAdjustAmount) {
        this.securityAdjustAmount = securityAdjustAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public BigDecimal getSecurityPositionAmount() {
        return securityPositionAmount;
    }

    public void setSecurityPositionAmount(BigDecimal securityPositionAmount) {
        this.securityPositionAmount = securityPositionAmount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatusStr() {
        return StockBonusStatus.getByCode(status + "").getDisplay();
    }

    public String getCreateTimeStr() {
        return DateUtils.formatDateTime(createTime);
    }

}
