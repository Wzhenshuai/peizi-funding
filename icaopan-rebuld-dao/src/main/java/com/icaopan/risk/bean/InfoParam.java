package com.icaopan.risk.bean;

import java.math.BigDecimal;

public class InfoParam {
	
	private Integer id;
	
	private String status;

	private String tradeAccount;

	private String customerId;
	
	private String nickName;
	
	private BigDecimal openLine;
	
	private BigDecimal warnLine;

	private String remark;

	public InfoParam() {
	}

	public InfoParam(Integer id, String status, String nickName, BigDecimal openLine, BigDecimal warnLine, String remark) {
		this.id = id;
		this.status = status;
		this.nickName = nickName;
		this.openLine = openLine;
		this.warnLine = warnLine;
		this.remark = remark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTradeAccount() {
		return tradeAccount;
	}

	public void setTradeAccount(String tradeAccount) {
		this.tradeAccount = tradeAccount;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public BigDecimal getOpenLine() {
		return openLine;
	}

	public void setOpenLine(BigDecimal openLine) {
		this.openLine = openLine;
	}

	public BigDecimal getWarnLine() {
		return warnLine;
	}

	public void setWarnLine(BigDecimal warnLine) {
		this.warnLine = warnLine;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
