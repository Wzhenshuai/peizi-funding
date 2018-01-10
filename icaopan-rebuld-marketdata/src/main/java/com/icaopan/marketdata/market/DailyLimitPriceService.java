package com.icaopan.marketdata.market;

import com.icaopan.marketdata.provider.tonglian.HttpUtils;
import com.icaopan.marketdata.provider.tonglian.MktLimit;
import com.icaopan.marketdata.provider.tonglian.Ticker;

import elf.api.marketdata.marketdata.MarketDataSnapshot;

import org.apache.commons.codec.EncoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangch on 2014/7/15.
 */
@Service
public class DailyLimitPriceService {

    private static Logger log = LoggerFactory.getLogger(DailyLimitPriceService.class);
    @Autowired
    private MarketdataService marketdataService;

//    @PostConstruct
    public void extract() throws ParseException {
//        log.info("httpUtils.getMkLimit start");
//        dailyLimits.clear();
//        try {
//            List<MktLimit> mktLimitList = HttpUtils.getMkLimit();
//            for (MktLimit mktLimit : mktLimitList) {
//                DailyLimit dailyLimit = new DailyLimit(mktLimit.getLimitUpPrice(), mktLimit.getLimitDownPrice());
//                dailyLimits.put(mktLimit.getSecID(), dailyLimit);
//            }
//        } catch (IOException e) {
//            log.error("httpUtils.getMkLimit error", e);
//        } catch (EncoderException e) {
//            log.error("httpUtils.getMkLimit error", e);
//        } catch (Exception e) {
//            log.error("httpUtils.getMkLimit error", e);
//        }
//        log.info("httpUtils.getMkLimit success");
    }


    public DailyLimit getDailyLimit(String secID) throws IOException, EncoderException {
        MarketDataSnapshot md = marketdataService.getBySymbol(secID);
        DailyLimit dl = new DailyLimit(0d, 0d);
        if(md!=null){
            dl.setLimitDown(md.getLimitDown());
            dl.setLimitUp(md.getLimitUp());
        }
        return dl;
    }

    public DailyLimit getDailyLimitPrice(Ticker ticker) {
        String secID = ticker.getTicker() + "." + ticker.getExchangeCD();
        try {
            DailyLimit dailyLimit = getDailyLimit(secID);
            return dailyLimit;
        } catch (IOException e) {
            log.error("httpUtils.getMkLimit error", e);
            return null;
        } catch (EncoderException e) {
            log.error("httpUtils.getMkLimit error", e);
            return null;
        }
    }
}
