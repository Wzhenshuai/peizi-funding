package com.icaopan.trade.model;

import java.util.Date;

import com.icaopan.util.DateUtils;

public class BrokerPosition {
	
    protected Integer id;

    protected String account;

    protected String stockCode;
    
    protected String stockName;

    protected Double  amount;

    protected Date createDate;

    protected Double costPrice;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode == null ? null : stockCode.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}
    
	public String getCreateDateStr(){
		return DateUtils.formatDate(createDate, "yyyy-MM-dd");
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	
	
    
}