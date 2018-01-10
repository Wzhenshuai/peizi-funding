package com.icaopan.marketdata.provider.tonglian;

import java.util.List;

/**
 * Created by ffff on 2016-09-22.
 */
public class Ticker {
    private static final long serialVersionUID = 1L;
    private String exchangeCD;//	交易所代码，如’XSHE'. 和Ticker一起组成了证券唯一的代码。
    private String ticker;//	证券6位代码， 如‘000001'
    private Long   timestamp;//	交易所时间戳（Unix时间）

    private Long       localTimestamp;// DataYes本地接收时间戳（Unix时间）
    private String     dataDate;//交易日期（yyyy-MM-dd）
    private String     dataTime;//交易时间'HH:mm:ss'
    private String     shortNM;//	证劵简称
    private String     utcOffset;//	UTC 时间偏移
    private String     currencyCD;//货币代码（CNY）
    private Double     prevClosePrice;//昨收盘价格
    private Double     openPrice;//今日开盘价
    private Long       volume;//成交数量(深圳指数和所有股票的单位是股，上海指数的单位是手)
    private Double     value;//成交金额
    private Integer    deal;// 成交笔数（1. 上海的股票，债券，基金该字段为空; 2. 指数该字段为空）
    private Double     highPrice;//	最高价格
    private Double     lowPrice;//最低价格
    private Double     lastPrice;//最新价格
    private List<Book> bidBook;//5个买档信息， 每个买档有Price和Volume两个字段（指数该字段为空）
    private List<Book> askBook;//5个卖档信息， 每个卖档有Price和Volume两个字段（指数，该字段为空）
    private Integer    suspension;//是否停牌(1表示停牌)

    private Integer tradType;//交易类型（港股独有）
    private Double  IEP;//参考平衡价（港股独有）
    private Double  AggQty;//集合竞价成交数量（港股独有）
    private Double  nominalPrice;// 按盘价/收盘价（港股独有）
    private Double  VWAP;// 	加权平均价（港股独有）
    private Double  change; //变动 (最新-前收)
    private Double  changePct;    //变动率(变动/前收)(恒生指数需要除以100倍)
    private Double  Yield;//息率（港股独有）
    private Integer tradStatus;// 交易状态（港股独有）
    private Integer tradSessionID;// 	标识（港股独有）
    private Integer tradSessionSubID;// 子标识（港股独有）
    private Integer tradSessionStatus;// 	状态（港股独有）

    private String orderType;//流通盘类型，即证券所在公司无限售流通A股类型分档（仅针对A股计算）:1	1亿以下;2	1~10亿;3	10亿以上
    private Double smallOrderValue;// 小单成交金额
    private Double mediumOrderValue;// 	中单成交金额
    private Double largeOrderValue;// 大单成交金额
    private Double extraLargeOrderValue;//超大单成交金额
    private Double totalOrderValue;// 本次成交单总金额
    private Double amplitude;// 振幅
    private Double turnoverRate;//换手率
    private Double negMarketValue;//	流通市值
    private Double staticPE;//静态市盈率

    public String getExchangeCD() {
        return exchangeCD;
    }

    public void setExchangeCD(String exchangeCD) {
        this.exchangeCD = exchangeCD;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getLocalTimestamp() {
        return localTimestamp;
    }

    public void setLocalTimestamp(Long localTimestamp) {
        this.localTimestamp = localTimestamp;
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getShortNM() {
        return shortNM;
    }

    public void setShortNM(String shortNM) {
        this.shortNM = shortNM;
    }

    public String getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(String utcOffset) {
        this.utcOffset = utcOffset;
    }

    public String getCurrencyCD() {
        return currencyCD;
    }

    public void setCurrencyCD(String currencyCD) {
        this.currencyCD = currencyCD;
    }

    public Double getPrevClosePrice() {
        return prevClosePrice;
    }

    public void setPrevClosePrice(Double prevClosePrice) {
        this.prevClosePrice = prevClosePrice;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getDeal() {
        return deal;
    }

    public void setDeal(Integer deal) {
        this.deal = deal;
    }

    public Double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Double highPrice) {
        this.highPrice = highPrice;
    }

    public Double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public List<Book> getBidBook() {
        return bidBook;
    }

    public void setBidBook(List<Book> bidBook) {
        this.bidBook = bidBook;
    }

    public List<Book> getAskBook() {
        return askBook;
    }

    public void setAskBook(List<Book> askBook) {
        this.askBook = askBook;
    }

    public Integer getSuspension() {
        return suspension;
    }

    public void setSuspension(Integer suspension) {
        this.suspension = suspension;
    }

    public Integer getTradType() {
        return tradType;
    }

    public void setTradType(Integer tradType) {
        this.tradType = tradType;
    }

    public Double getIEP() {
        return IEP;
    }

    public void setIEP(Double IEP) {
        this.IEP = IEP;
    }

    public Double getAggQty() {
        return AggQty;
    }

    public void setAggQty(Double aggQty) {
        AggQty = aggQty;
    }

    public Double getNominalPrice() {
        return nominalPrice;
    }

    public void setNominalPrice(Double nominalPrice) {
        this.nominalPrice = nominalPrice;
    }

    public Double getVWAP() {
        return VWAP;
    }

    public void setVWAP(Double VWAP) {
        this.VWAP = VWAP;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    public Double getChangePct() {
        return changePct;
    }

    public void setChangePct(Double changePct) {
        this.changePct = changePct;
    }

    public Double getYield() {
        return Yield;
    }

    public void setYield(Double yield) {
        Yield = yield;
    }

    public Integer getTradStatus() {
        return tradStatus;
    }

    public void setTradStatus(Integer tradStatus) {
        this.tradStatus = tradStatus;
    }

    public Integer getTradSessionID() {
        return tradSessionID;
    }

    public void setTradSessionID(Integer tradSessionID) {
        this.tradSessionID = tradSessionID;
    }

    public Integer getTradSessionSubID() {
        return tradSessionSubID;
    }

    public void setTradSessionSubID(Integer tradSessionSubID) {
        this.tradSessionSubID = tradSessionSubID;
    }

    public Integer getTradSessionStatus() {
        return tradSessionStatus;
    }

    public void setTradSessionStatus(Integer tradSessionStatus) {
        this.tradSessionStatus = tradSessionStatus;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Double getSmallOrderValue() {
        return smallOrderValue;
    }

    public void setSmallOrderValue(Double smallOrderValue) {
        this.smallOrderValue = smallOrderValue;
    }

    public Double getMediumOrderValue() {
        return mediumOrderValue;
    }

    public void setMediumOrderValue(Double mediumOrderValue) {
        this.mediumOrderValue = mediumOrderValue;
    }

    public Double getLargeOrderValue() {
        return largeOrderValue;
    }

    public void setLargeOrderValue(Double largeOrderValue) {
        this.largeOrderValue = largeOrderValue;
    }

    public Double getExtraLargeOrderValue() {
        return extraLargeOrderValue;
    }

    public void setExtraLargeOrderValue(Double extraLargeOrderValue) {
        this.extraLargeOrderValue = extraLargeOrderValue;
    }

    public Double getTotalOrderValue() {
        return totalOrderValue;
    }

    public void setTotalOrderValue(Double totalOrderValue) {
        this.totalOrderValue = totalOrderValue;
    }

    public Double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(Double amplitude) {
        this.amplitude = amplitude;
    }

    public Double getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(Double turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    public Double getNegMarketValue() {
        return negMarketValue;
    }

    public void setNegMarketValue(Double negMarketValue) {
        this.negMarketValue = negMarketValue;
    }

    public Double getStaticPE() {
        return staticPE;
    }

    public void setStaticPE(Double staticPE) {
        this.staticPE = staticPE;
    }
}
