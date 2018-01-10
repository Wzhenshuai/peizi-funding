package com.icaopan.marketdata.market;

import java.util.List;

import elf.api.marketdata.marketdata.MarketDataSnapshot;

public interface MarketDataListener {
    /**
     * 处理行情
     *
     * @param md
     */
    public void receivedMarketData(MarketDataSnapshot md);

    public List<MarketDataSnapshot> getAllMarketData();

    public void updateMarketDataPrice(long time, String code, double price);
}
