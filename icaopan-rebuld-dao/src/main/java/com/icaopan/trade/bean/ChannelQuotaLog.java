package com.icaopan.trade.bean;

import com.icaopan.enums.enumBean.UserChannelOperateType;
import com.icaopan.enums.enumBean.UserChannelType;
import com.icaopan.util.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

public class ChannelQuotaLog {
    private Integer id;

    private Integer userId;

    private Integer channelId;

    private String operateType;

    private String operateTypeStr;

    private String quotaType;

    private String quotaTypeStr;

    private String operateTimeStr;

    private BigDecimal quota;

    private Date createTime;

    private String remark;

    private Integer customerId;

    private String userName;

    private String customerName;

    private String channelName;

    public ChannelQuotaLog() {
    }

    public ChannelQuotaLog(Integer userId, Integer channelId, String operateType, String quotaType, BigDecimal quota, String remark) {
        this.userId = userId;
        this.channelId = channelId;
        this.operateType = operateType;
        this.quotaType = quotaType;
        this.quota = quota;
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getOperateTypeStr() {
        if (StringUtils.isBlank(operateType)) return null;
        return UserChannelOperateType.getByCode(operateType).getDisplay();
    }

    public String getQuotaTypeStr() {
        if (StringUtils.isBlank(quotaType)) return null;
        return UserChannelType.getByCode(quotaType).getDisplay();
    }

    public String getOperateTimeStr() {
        return DateUtils.formatDateTime(createTime);
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType == null ? null : operateType.trim();
    }

    public String getQuotaType() {
        return quotaType;
    }

    public void setQuotaType(String quotaType) {
        this.quotaType = quotaType == null ? null : quotaType.trim();
    }

    public BigDecimal getQuota() {
        return quota;
    }

    public void setQuota(BigDecimal quota) {
        this.quota = quota;
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
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}