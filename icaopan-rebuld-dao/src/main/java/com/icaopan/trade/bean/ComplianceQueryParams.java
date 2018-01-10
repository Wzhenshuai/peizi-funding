package com.icaopan.trade.bean;

import java.io.Serializable;
import java.util.Date;

public class ComplianceQueryParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer customerId;
	private String stockCode;
	private String userName;
	private String startTime;
	private String endTime;
	
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
