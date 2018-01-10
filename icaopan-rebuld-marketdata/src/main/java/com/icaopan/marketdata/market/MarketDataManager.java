package com.icaopan.marketdata.market;
import elf.api.marketdata.marketdata.MarketDataSnapshot;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icaopan.log.LogUtil;
import com.icaopan.marketdata.provider.tonglian.HttpUtils;
import com.icaopan.marketdata.provider.tonglian.Ticker;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jiangch
 * @description
 * @create 2013-8-13 下午3:55:21
 */
@Service
public class MarketDataManager {

    private static Logger log = LogUtil.getTaskLogger();
    @Autowired
    private MarketDataListener dataListener;

    public void setDataListener(MarketDataListener dataListener) {
        this.dataListener = dataListener;
    }
    @Autowired
    private MarketdataService marketdataService;
    
    public void readMarketData() throws Exception {
        try {
        	List<Ticker> tickerList = HttpUtils.getTickRTSnapshot();
            for (Ticker ticker : tickerList) {
                MarketDataSnapshot md = MarketDataSnapshotBuilder.convert(ticker);
                this.dataListener.receivedMarketData(md);
            }
		} catch (Exception e) {
			log.error("更新系统行情出错",e);
		}
        
    }

    public synchronized void update(List<MarketDataSnapshot> marketDataSnapshots) {
//        log.info("----marketdata update start:"+marketDataSnapshots.size());
        for (MarketDataSnapshot shot : marketDataSnapshots) {
            try {
                MarketDataSnapshot md = MarketDataSnapshotBuilder.convert(shot);
                this.dataListener.receivedMarketData(md);
            }catch (Exception e){
                log.error("marketdata update fail",e);
            }
        }
//        log.info("----marketdata update start over");
    }
    
    /**
     * 接受行情字符串
     * @param str
     */
    public synchronized void updateByPrice(String str) {
    	if(StringUtils.isNotEmpty(str)){
    		JSONObject jb=JSONObject.parseObject(str);
    		String t=jb.getString("t");
    		if(t.contains(".")){
    			t=t.substring(0,t.indexOf("."));
    		}
    		long lt=Long.valueOf(t);
    		lt=lt*1000;
    		JSONArray array=jb.getJSONArray("d");
    		for(int i=0;i<array.size();i++){
    			JSONObject jo=array.getJSONObject(i);
    			double price=jo.getDoubleValue("p");
    			String code=jo.getString("c");
    			marketdataService.updateMarketDataPrice(lt, code, price);
    		}
    	}
    }
    
}
