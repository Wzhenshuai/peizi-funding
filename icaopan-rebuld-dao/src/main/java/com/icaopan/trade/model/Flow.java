package com.icaopan.trade.model;

import com.icaopan.enums.enumBean.TradeFlowNote;
import com.icaopan.enums.enumBean.TradeFowType;
import com.icaopan.util.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class Flow {
    private Integer      id;
    /**
     * 股票
     **/
    private String       securityCode;
    
    private String securityName;
    /**
     * 发生数量
     **/
    private BigDecimal   adjustQuantity;
    /**
     * 发生金额
     **/
    private BigDecimal   adjustAmount;
    /**
     * 0证券买入，1证券卖出，2资金减少，3资金增加，4新红配，
     **/

    private TradeFowType type;

    private String userName;

    private String     customerName;
    /**
     * 本金金额
     **/
    private BigDecimal cash;
    /**
     * 融资金额
     **/
    private BigDecimal financing;
    /**
     * 本金总计
     **/
    private BigDecimal cashAmount;
    /**
     * 融资总计
     **/
    private BigDecimal financingAmount;

    private Date createTime;

    private Integer       userId;
    /**
     * 备注：增加(手工)，减少(手工)，交易成交
     **/
    private TradeFlowNote notes;

    private String changeCause;
    
    private String isHidden;

    public String getChangeCause() {
        return changeCause;
    }

    public void setChangeCause(String changeCause) {
        this.changeCause = changeCause;
    }

    private Integer customerId;

    private BigDecimal commissionFee;

    private BigDecimal stampDutyFee;

    private BigDecimal transferFee;

    private String channelName;

    private BigDecimal costPrice;

    private String costPriceStr;

    private Integer channelId;

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public String getCostPriceStr() {
        if (costPrice==null||BigDecimal.ZERO.compareTo(costPrice) ==0){
            return "0.00";
        }
        return String.valueOf(costPrice.setScale(2,BigDecimal.ROUND_HALF_UP));
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getCreateTimeStr() {
        return DateUtils.formatDateTime(createTime);
    }

    public String getNotesStr() {
        return changeCause == null ? notes.getDisploy()+"":notes.getDisploy()+changeCause;
        //return notes.getDisploy();
    }

    public String getTypeStr() {
        return type.getDisplay();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSecurityCode() {
        return securityCode;
    }


    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public BigDecimal getAdjustQuantity() {
        return adjustQuantity;
    }

    public void setAdjustQuantity(BigDecimal adjustQuantity) {
        this.adjustQuantity = adjustQuantity;
    }

    public BigDecimal getAdjustAmount() {
        return adjustAmount;
    }

    public void setAdjustAmount(BigDecimal adjustAmount) {
        this.adjustAmount = adjustAmount;
    }


    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getFinancing() {
        return financing;
    }

    public void setFinancing(BigDecimal financing) {
        this.financing = financing;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getFinancingAmount() {
        return financingAmount;
    }

    public void setFinancingAmount(BigDecimal financingAmount) {
        this.financingAmount = financingAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public TradeFowType getType() {
        return type;
    }

    public void setType(TradeFowType type) {
        this.type = type;
    }

    public TradeFlowNote getNotes() {
        return notes;
    }

    public void setNotes(TradeFlowNote notes) {
        this.notes = notes;
    }

    public BigDecimal getCommissionFee() {
        return commissionFee;
    }

    public void setCommissionFee(BigDecimal commissionFee) {
        this.commissionFee = commissionFee;
    }

    public BigDecimal getStampDutyFee() {
        return stampDutyFee;
    }

    public void setStampDutyFee(BigDecimal stampDutyFee) {
        this.stampDutyFee = stampDutyFee;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

	public String getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(String isHidden) {
		this.isHidden = isHidden;
	}
	
	public String getHiddenStr(){
		if(StringUtils.isNotEmpty(isHidden)){
			if("1".equals(isHidden)){
				return "不显示";
			}
		}
		return "显示";
	}

	public String getSecurityName() {
		return securityName;
	}

	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}

	
}