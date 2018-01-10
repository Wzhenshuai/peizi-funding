package com.icaopan.customer.model;

import com.icaopan.enums.enumBean.ChannelType;
import com.icaopan.enums.enumBean.UserChannelType;
import com.icaopan.util.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Channel implements Serializable {

    private static final long serialVersionUID = 1L;
    public BigDecimal tradeMinCost;
    private Integer id;
    private String code;
    private String     name;
    /**
     * 通道收费比例
     **/
    private BigDecimal tatio;
    /**
     * 最低消费
     **/
    private BigDecimal minCost;
    private Boolean isAvailable;
    private String notes;
    private Date updateTime;
    private Date createTime;
    private BigDecimal cashAvailable;
    /**
     * 通道类型
     **/
    private String channelType;
    private Integer customerId;
    private String customerName;
    private String createTimeStr;
    private String updateTimeStr;
    private String channelTypeStr;
    /**
     * 通道优先级
     **/
    private int    priorityLevel;
    private String isAvailableStr;
    private String userChannelTypeVal;
    /** 通道限额**/
    private BigDecimal quota;
    /** 通道比例**/
    private Integer proportion;
    private double highSinglePositionLimit;
    private Date cashUptime;
    private String cashUptimeStr;
    //通道总资产
    private BigDecimal totalAssets;
    //是否自动成交
    private Boolean autoFill;
    //券商佣金比例
    private BigDecimal tradeCommissionRate;

    public Channel() {
    }

    public Channel(Integer id, BigDecimal cashAvailable) {
        this.id = id;
        this.cashAvailable = cashAvailable;
    }

    public Channel(Integer id, BigDecimal cashAvailable, int priorityLevel, Boolean isAvailable) {
        this.id = id;
        this.cashAvailable = cashAvailable;
        this.priorityLevel = priorityLevel;
        this.isAvailable = isAvailable;
    }

    public Channel(Integer id, BigDecimal cashAvailable, BigDecimal quota) {
        this.id = id;
        this.cashAvailable = cashAvailable;
        this.quota =quota;
    }

    public BigDecimal getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(BigDecimal totalAssets) {
        this.totalAssets = totalAssets;
    }

    public Date getCashUptime() {
        return cashUptime;
    }

    public void setCashUptime(Date cashUptime) {
        this.cashUptime = cashUptime;
    }

    public String getCashUptimeStr() {

        if (cashUptime != null) {
            return DateUtils.formatDateTime(cashUptime);
        }
        return cashUptimeStr;
    }

    public BigDecimal getTradeMinCost() {
        return tradeMinCost;
    }

    public void setTradeMinCost(BigDecimal tradeMinCost) {
        this.tradeMinCost = tradeMinCost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
        isAvailableStr = isAvailable ? "正常" : "停用";
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    public BigDecimal getTatio() {
        return tatio;
    }

    public void setTatio(BigDecimal tatio) {
        this.tatio = tatio;
    }

    public BigDecimal getMinCost() {
        return minCost;
    }

    public void setMinCost(BigDecimal minCost) {
        this.minCost = minCost;
    }

    public Date getCreatTime() {
        return createTime;
    }

    public String getChannelType() {

        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateTimeStr() {
        if (createTime != null) {
            return DateUtils.formatDateTime(createTime);
        }
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUpdateTimeStr() {
        if (updateTime != null) {
            return DateUtils.formatDateTime(updateTime);
        }
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getChannelTypeStr() {
        if (channelType != null) {
            channelTypeStr = ChannelType.getByCode(channelType).getDisplay();
        }
        return channelTypeStr;
    }

    public void setChannelTypeStr(String channelTypeStr) {
        this.channelTypeStr = channelTypeStr;
    }

    public String getIsAvailableStr() {
        return isAvailableStr;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public BigDecimal getCashAvailable() {
        return cashAvailable;
    }

    public void setCashAvailable(BigDecimal cashAvailable) {
        this.cashAvailable = cashAvailable;
    }

    public BigDecimal getQuota() {
        return quota;
    }

    public void setQuota(BigDecimal quota) {
        this.quota = quota;
    }

    public Integer getProportion() {
        return proportion;
    }

    public void setProportion(Integer proportion) {
        this.proportion = proportion;
    }

    public String getUserChannelTypeVal() {
        return userChannelTypeVal;
    }

    public void setUserChannelTypeVal(String userChannelTypeVal) {
        this.userChannelTypeVal = userChannelTypeVal;
    }


    public BigDecimal getAvailable() {
        if (StringUtils.equals(UserChannelType.LIMITED.getCode(), this.getUserChannelTypeVal())) {//限制金额
            if (this.getCashAvailable().compareTo(this.quota) > 0) {
                return this.quota;
            }
        }
        return this.getCashAvailable();
    }

    public double getHighSinglePositionLimit() {
        return highSinglePositionLimit;
    }

    public void setHighSinglePositionLimit(double highSinglePositionLimit) {
        this.highSinglePositionLimit = highSinglePositionLimit;
    }

	public Boolean getAutoFill() {
		if(autoFill==null){
			return false;
		}
		return autoFill;
	}

	public void setAutoFill(Boolean autoFill) {
		if(autoFill==null){
			this.autoFill = false;
		}else{
			this.autoFill=autoFill;
		}
	}
    
	public String getAutoFillStr(){
		if(autoFill!=null&&autoFill.booleanValue()==true){
			return "是";
		}
		return "否";
	}
    
    public BigDecimal getTradeCommissionRate() {
        return tradeCommissionRate;
    }

    public void setTradeCommissionRate(BigDecimal tradeCommissionRate) {
        this.tradeCommissionRate = tradeCommissionRate;
    }
}
