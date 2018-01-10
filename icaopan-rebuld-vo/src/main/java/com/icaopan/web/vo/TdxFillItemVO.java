package com.icaopan.web.vo;

public class TdxFillItemVO {

	private String accountName;
	private String accountNo;
	private String stockCode;
	private String stockName;
	private String side;
	private double buyFillQuantity;
	private double buyFillAmount;
	private double sellFillQuantity;
	private double sellFillAmount;
	
	public TdxFillItemVO(){}
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getSide() {
		return side;
	}
	public void setSide(String side) {
		this.side = side;
	}

	public double getBuyFillQuantity() {
		return buyFillQuantity;
	}

	public void setBuyFillQuantity(double buyFillQuantity) {
		this.buyFillQuantity = buyFillQuantity;
	}

	public double getBuyFillAmount() {
		return buyFillAmount;
	}

	public void setBuyFillAmount(double buyFillAmount) {
		this.buyFillAmount = buyFillAmount;
	}

	public double getSellFillQuantity() {
		return sellFillQuantity;
	}

	public void setSellFillQuantity(double sellFillQuantity) {
		this.sellFillQuantity = sellFillQuantity;
	}

	public double getSellFillAmount() {
		return sellFillAmount;
	}

	public void setSellFillAmount(double sellFillAmount) {
		this.sellFillAmount = sellFillAmount;
	}
	
	
}
