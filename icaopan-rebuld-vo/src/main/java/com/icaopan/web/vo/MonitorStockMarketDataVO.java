package com.icaopan.web.vo;

public class MonitorStockMarketDataVO {

	private String serverName;
	private String stockCode;
	private String stockName;
	private String updateTime;
	private boolean ok=true;
	
	public MonitorStockMarketDataVO(){}
	
	public MonitorStockMarketDataVO(String serverName, String stockCode,
			String stockName, String updateTime) {
		super();
		this.serverName = serverName;
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.updateTime = updateTime;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
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
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}
}
