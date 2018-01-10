package com.icaopan.marketdata.market;

import com.icaopan.marketdata.provider.tonglian.HttpUtils;
import com.icaopan.marketdata.provider.tonglian.Ticker;

import elf.api.marketdata.marketdata.MarketDataSnapshot;

/**
 * @author jiangch
 * @description
 * @create 2013-8-9 下午4:56:11
 */
public class MarketDataSnapshotBuilder {

    public static MarketDataSnapshot convert(Ticker ticker) {
        MarketDataSnapshot marketData = new MarketDataSnapshot();
        marketData.setSymbol(ticker.getTicker());
        long date = HttpUtils.parseDate(ticker.getDataDate() + ticker.getDataTime());
        marketData.setTime(date);
        marketData.setExchange(ticker.getExchangeCD());
        marketData.setStockName(ticker.getShortNM());
        marketData.setPrice(ticker.getLastPrice());
        marketData.setVolume(ticker.getVolume());
        marketData.setOpen(ticker.getOpenPrice());
        marketData.setPreClose(ticker.getPrevClosePrice());
        marketData.setDayHigh(ticker.getHighPrice());
        marketData.setDayLow(ticker.getLowPrice());
        marketData.setAmount(ticker.getValue());
        marketData.setBidPrice1(ticker.getBidBook().get(0).getPrice());
        marketData.setBidVolume1(ticker.getBidBook().get(0).getVolume());
        marketData.setBidPrice2(ticker.getBidBook().get(1).getPrice());
        marketData.setBidVolume2(ticker.getBidBook().get(1).getVolume());
        marketData.setBidPrice3(ticker.getBidBook().get(2).getPrice());
        marketData.setBidVolume3(ticker.getBidBook().get(2).getVolume());
        marketData.setBidPrice4(ticker.getBidBook().get(3).getPrice());
        marketData.setBidVolume4(ticker.getBidBook().get(3).getVolume());
        marketData.setBidPrice5(ticker.getBidBook().get(4).getPrice());
        marketData.setBidVolume5(ticker.getBidBook().get(4).getVolume());
        marketData.setAskPrice1(ticker.getAskBook().get(0).getPrice());
        marketData.setAskVolume1(ticker.getAskBook().get(0).getVolume());
        marketData.setAskPrice2(ticker.getAskBook().get(1).getPrice());
        marketData.setAskVolume2(ticker.getAskBook().get(1).getVolume());
        marketData.setAskPrice3(ticker.getAskBook().get(2).getPrice());
        marketData.setAskVolume3(ticker.getAskBook().get(2).getVolume());
        marketData.setAskPrice4(ticker.getAskBook().get(3).getPrice());
        marketData.setAskVolume4(ticker.getAskBook().get(3).getVolume());
        marketData.setAskPrice5(ticker.getAskBook().get(4).getPrice());
        marketData.setAskVolume5(ticker.getAskBook().get(4).getVolume());
        marketData.setMarketDataDate(ticker.getDataDate());
        marketData.setMarketDataTime(ticker.getDataTime());
        marketData.setDailyChange(ticker.getChange());
        marketData.setDailyChangePercent(ticker.getChangePct());
        //marketData.setAmplitude(ticker.getAmplitude());
        if (ticker.getSuspension() != null) {
            marketData.setSuspensionFlag(ticker.getSuspension() == 1);
        }
//        setLimitPrice(ticker, marketData);
        return marketData;
    }

//    private static void setLimitPrice(Ticker ticker, MarketDataSnapshot marketData) {
//        DailyLimitPriceService dailyLimitPriceService = BeanFactory.getBean(DailyLimitPriceService.class);
//        DailyLimit limit = dailyLimitPriceService.getDailyLimitPrice(ticker);
//        if (limit != null) {
//            marketData.setLimitUp(limit.getLimitUp());
//            marketData.setLimitDown(limit.getLimitDown());
//        }
//    }


    public static MarketDataSnapshot convert(MarketDataSnapshot s) {
        MarketDataSnapshot marketData = new MarketDataSnapshot();
        marketData.setSymbol(s.getSymbol());
        long date = s.getTime();
        marketData.setTime(date);
        marketData.setExchange(s.getExchange());
        marketData.setStockName(s.getStockName());
        marketData.setPrice(s.getPrice());
        marketData.setVolume(s.getVolume());
        marketData.setOpen(s.getOpen());
        marketData.setPreClose(s.getPreClose());
        marketData.setDayHigh(s.getDayHigh());
        marketData.setDayLow(s.getDayLow());
        marketData.setAmount(s.getAmount());
        marketData.setBidPrice1(s.getBidPrice1());
        marketData.setBidVolume1(s.getBidVolume1());
        marketData.setBidPrice2(s.getBidPrice2());
        marketData.setBidVolume2(s.getBidVolume2());
        marketData.setBidPrice3(s.getBidPrice3());
        marketData.setBidVolume3(s.getBidVolume3());
        marketData.setBidPrice4(s.getBidPrice4());
        marketData.setBidVolume4(s.getBidVolume4());
        marketData.setBidPrice5(s.getBidPrice5());
        marketData.setBidVolume5(s.getBidVolume5());
        marketData.setAskPrice1(s.getAskPrice1());
        marketData.setAskVolume1(s.getAskVolume1());
        marketData.setAskPrice2(s.getAskPrice2());
        marketData.setAskVolume2(s.getAskVolume2());
        marketData.setAskPrice3(s.getAskPrice3());
        marketData.setAskVolume3(s.getAskVolume3());
        marketData.setAskPrice4(s.getAskPrice4());
        marketData.setAskVolume4(s.getAskVolume4());
        marketData.setAskPrice5(s.getAskPrice5());
        marketData.setAskVolume5(s.getAskVolume5());
        marketData.setMarketDataDate(s.getMarketDataDate());
        marketData.setMarketDataTime(s.getMarketDataTime());
        marketData.setDailyChange(s.getDailyChange());
        marketData.setDailyChangePercent(s.getDailyChangePercent());
        marketData.setSuspensionFlag(s.isSuspensionFlag());
//        if(s.getDayHigh()!=0&&s.getDayLow()!=0&&s.getPreClose()!=0){
//            double amplitude = (s.getDayHigh()-s.getDayLow())/s.getPreClose();
//            marketData.setAmplitude(amplitude);
//        }
        marketData.setLimitUp(s.getLimitUp());
        marketData.setLimitDown(s.getLimitDown());
        return marketData;
    }

}
