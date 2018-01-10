package com.icaopan.user.model;

import com.icaopan.enums.enumBean.UserChannelType;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/4/6 0006.
 */
public class UserChannel {
    private int id;

    private int channelId;

    private int userId;

    private int priorityLevel;

    //用户限额
    private BigDecimal quota;

    //用户通道可用类型：限制金额，不限制金额
    private UserChannelType userChannelType;
    private int             userChannelTypeVal;
    private Integer proportion;

    public UserChannel() {
    }

    public UserChannel(int userId, int channelId, int priorityLevel) {
        this.userId = userId;
        this.channelId = channelId;
        this.priorityLevel = priorityLevel;
    }

    public UserChannel(int userId, int channelId, int priorityLevel, BigDecimal quato, UserChannelType userChannelType) {
        this(userId, channelId, priorityLevel);
        this.quota = quato;
        this.userChannelType = userChannelType;
    }

    public UserChannel(int userId, int channelId, int priorityLevel, BigDecimal quato, int userChannelTypeVal, Integer proportion) {
        this(userId, channelId, priorityLevel);
        this.quota = quato;
        this.userChannelTypeVal = userChannelTypeVal;
        this.proportion = proportion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public BigDecimal getQuota() {
        return quota;
    }

    public void setQuota(BigDecimal quota) {
        this.quota = quota;
    }

    public UserChannelType getUserChannelType() {
        return userChannelType;
    }

    public void setUserChannelType(UserChannelType userChannelType) {
        this.userChannelType = userChannelType;
    }

    public void subtractQuota(BigDecimal variable) {
        this.quota = this.quota.subtract(variable);
    }

    public void addQuota(BigDecimal variable) {
        this.quota = this.quota.add(variable);
    }

    public int getUserChannelTypeVal() {
        return userChannelTypeVal;
    }

    public void setUserChannelTypeVal(int userChannelTypeVal) {
        this.userChannelTypeVal = userChannelTypeVal;
    }

    public Integer getProportion() {
        return proportion;
    }

    public void setProportion(int proportion) {
        this.proportion = proportion;
    }
}
