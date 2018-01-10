package com.icaopan.marketdata.provider.tonglian;

/**
 * Created by ffff on 2016-09-22.
 */
public class MktLimit {
    private static final long serialVersionUID = 14412L;
    private String ticker;//	证券交易代码
    private String secID;//	证券内部编码
    private String secShortName;//	证券简称
    private String secShortNameEn;//	证券英文简称
    private String exchangeCD;//	交易所代码
    private String tradeDate;//	交易日期
    private Double limitUpPrice;//	涨停价
    private Double limitDownPrice;//	跌停价
    private String upLimitReachedTimes;//	当日涨停次数
    private String downLimitReachedTimes;//	当日跌停次数

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getSecID() {
        return secID;
    }

    public void setSecID(String secID) {
        this.secID = secID;
    }

    public String getSecShortName() {
        return secShortName;
    }

    public void setSecShortName(String secShortName) {
        this.secShortName = secShortName;
    }

    public String getSecShortNameEn() {
        return secShortNameEn;
    }

    public void setSecShortNameEn(String secShortNameEn) {
        this.secShortNameEn = secShortNameEn;
    }

    public String getExchangeCD() {
        return exchangeCD;
    }

    public void setExchangeCD(String exchangeCD) {
        this.exchangeCD = exchangeCD;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Double getLimitUpPrice() {
        return limitUpPrice;
    }

    public void setLimitUpPrice(Double limitUpPrice) {
        this.limitUpPrice = limitUpPrice;
    }

    public Double getLimitDownPrice() {
        return limitDownPrice;
    }

    public void setLimitDownPrice(Double limitDownPrice) {
        this.limitDownPrice = limitDownPrice;
    }

    public String getUpLimitReachedTimes() {
        return upLimitReachedTimes;
    }

    public void setUpLimitReachedTimes(String upLimitReachedTimes) {
        this.upLimitReachedTimes = upLimitReachedTimes;
    }

    public String getDownLimitReachedTimes() {
        return downLimitReachedTimes;
    }

    public void setDownLimitReachedTimes(String downLimitReachedTimes) {
        this.downLimitReachedTimes = downLimitReachedTimes;
    }
}
