package com.icaopan.marketdata.market;

/**
 * 红股分配方案
 *
 * @author yong
 * @Description
 * @date 2017年4月20日
 */
public class StockBonus {

    private String     ticker;
    private String secShortName;
    //每股派现
    private double perCashDiv;
    //每股送红比例
    private double perShareDivRatio;
    //每股转增比例
    private double perShareTransRatio;
    //股权登记日
    private String recordDate;
    //除权除息日
    private String exDivDate;
    
    public StockBonus(){}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getSecShortName() {
		return secShortName;
	}

	public void setSecShortName(String secShortName) {
		this.secShortName = secShortName;
	}


	public double getPerCashDiv() {
		return perCashDiv;
	}

	public void setPerCashDiv(double perCashDiv) {
		this.perCashDiv = perCashDiv;
	}

	public double getPerShareDivRatio() {
		return perShareDivRatio;
	}

	public void setPerShareDivRatio(double perShareDivRatio) {
		this.perShareDivRatio = perShareDivRatio;
	}


	public double getPerShareTransRatio() {
		return perShareTransRatio;
	}

	public void setPerShareTransRatio(double perShareTransRatio) {
		this.perShareTransRatio = perShareTransRatio;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	public String getExDivDate() {
		return exDivDate;
	}

	public void setExDivDate(String exDivDate) {
		this.exDivDate = exDivDate;
	}

  

    
}