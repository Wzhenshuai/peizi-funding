package com.icaopan.marketdata.market;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.EncoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icaopan.marketdata.provider.tonghuashun.ThsDataProvider;
import com.icaopan.marketdata.provider.tonglian.HttpUtils;
import com.icaopan.marketdata.provider.tonglian.MktLimit;

import elf.api.marketdata.marketdata.MarketDataSnapshot;

@Service("marketdataService")
public class MarketdataService implements MarketDataListener {

    private static Map<String, MarketDataSnapshot> marketDataSnapshotMap = new ConcurrentHashMap<String, MarketDataSnapshot>();

    private static Map<String, MarketDataSnapshot> upChangePercentMap = new HashMap<>();

    private static Map<String, MarketDataSnapshot> downChangePercentMap = new HashMap<>();

    @Autowired
    private MarketDataManager marketDataManager;
    /**
     * 处理行情，将其存入map当中
     *
     * @param md
     */
    @Override
    public void receivedMarketData(MarketDataSnapshot md) {
        MarketDataSnapshot old = marketDataSnapshotMap.get(md.getSymbol());
        if(old!=null&&md.getPrice()==0){
            md.setPrice(old.getPrice());
        }
        marketDataSnapshotMap.put(md.getSymbol(), md);
    }
    
    @Override
    public void updateMarketDataPrice(long time,String code,double price){
    	MarketDataSnapshot shot=getBySymbol(code);
    	if(shot!=null){
    		long ts=shot.getTime();
    		if(time>ts){
    			if(price>0){
    				shot.setTime(time);
    				shot.setPrice(price);
    			}
    		}
    	}
    }
    
    @Override
    public List<MarketDataSnapshot> getAllMarketData(){
    	List<MarketDataSnapshot> list=new ArrayList<MarketDataSnapshot>();
    	Collection<MarketDataSnapshot> col=marketDataSnapshotMap.values();
    	if(col!=null){
    		list=new ArrayList<MarketDataSnapshot>(col);
    	}
    	return list;
    }

    /**
     * 根据股票代码查找股票行情
     *
     * @param symbol
     * @return
     */
    public MarketDataSnapshot getBySymbol(String symbol) {
        MarketDataSnapshot shot = new MarketDataSnapshot();
        try {
            shot = marketDataSnapshotMap.get(symbol);
        } catch (Exception e) {

        }
        return shot;
    }
    

    /**
     * 查询最新价格
     *
     * @param symbol
     * @return
     */
    public BigDecimal getLatestPrice(String symbol) {
        MarketDataSnapshot shot = getBySymbol(symbol);
        if (shot == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(shot.getPrice() + "");
    }
    
    public String getSecurityName(String symbol) {
        MarketDataSnapshot shot = getBySymbol(symbol);
        if (shot == null) {
            return shot.getStockName();
        }
        return "";
    }
    
    /**
     * 获取每日新红配信息
     * @return
     * @throws IOException
     * @throws EncoderException
     */
    public List<StockBonus> getStockBonus(String tradeDate) throws IOException, EncoderException{
//    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//    	SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMdd");
//    	String dateStr="";
//    	try {
//			Date date=sdf.parse(tradeDate);
//			dateStr=sdf2.format(date);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//    	
//    	return HttpUtils.getTodayStockBonus(dateStr);
    	try {
			return ThsDataProvider.getStockBonus(tradeDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }


    public void handleAilyChangePercent(String tradeDdate) throws Exception{
        if (marketDataSnapshotMap.isEmpty()){
            try {
                marketDataManager.readMarketData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String lastTradeDate = null;
        if (tradeDdate == null || tradeDdate.equals("")){
            lastTradeDate = HttpUtils.getlastTradeDate();
        }else {
            lastTradeDate = tradeDdate;
        }
        if (lastTradeDate != null){
            upChangePercentMap.clear();
            downChangePercentMap.clear();
            List<MktLimit> mktLimitList = HttpUtils.getLastTradeDateMkLimit(lastTradeDate);
            for (MktLimit mktLimit : mktLimitList ) {
                MarketDataSnapshot marketDataSnapshot = marketDataSnapshotMap.get(mktLimit.getTicker());
                if (marketDataSnapshot == null){
                    continue;
                }
                try {
                    Double preClose = marketDataSnapshot.getPreClose();
                    if (mktLimit.getLimitUpPrice().compareTo(preClose) == 0){
                        upChangePercentMap.put(mktLimit.getTicker(),marketDataSnapshot);
                    }else if (mktLimit.getLimitDownPrice().compareTo(preClose) == 0)
                        downChangePercentMap.put(mktLimit.getTicker(),marketDataSnapshot);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 涨跌停的票 放入内存中
     */
    public void handleAilyChangePercent() {
        try {
            handleAilyChangePercent(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 涨停的
     *
     * @return
     */

    public Map<String, MarketDataSnapshot> getUpChangePercentMap() {
        return upChangePercentMap;
    }

    /**
     * 跌停
     *
     * @return
     */
    public Map<String, MarketDataSnapshot> getDownChangePercentMap() {
        return downChangePercentMap;
    }
}
