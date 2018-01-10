package com.icaopan.trade.bean;

import java.util.List;

import com.icaopan.trade.model.BrokerPosition;
import com.icaopan.user.model.ChannelSecurityPosition;

public class BrokerPositionResult extends BrokerPosition {

	private Double icpAmount;//平台持仓总数量
	
	private String channelName;//通道名称
	
	private String stockName;

	private List<ChannelSecurityPosition> detailList;//平台通道持仓列表
	
	public BrokerPositionResult(){}
	
	public BrokerPositionResult(BrokerPosition position){
		account=position.getAccount();
		stockCode=position.getStockCode();
		id=position.getId();
		costPrice=position.getCostPrice();
		amount=position.getAmount();
		createDate=position.getCreateDate();
	}
	
	public Double getIcpAmount() {
		return icpAmount;
	}

	public void setIcpAmount(Double icpAmount) {
		this.icpAmount = icpAmount;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public double getMinusAmount() {
		return icpAmount-super.amount;
	}

	public List<ChannelSecurityPosition> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<ChannelSecurityPosition> detailList) {
		this.detailList = detailList;
	}
	
	public String getCheckResult(){
		if(getMinusAmount()!=0){
			return "数量不一致";
		}else{
			return "数量一致";
		}
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	

}
