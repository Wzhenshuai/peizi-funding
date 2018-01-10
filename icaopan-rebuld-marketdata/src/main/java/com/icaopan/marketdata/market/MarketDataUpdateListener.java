package com.icaopan.marketdata.market;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icaopan.log.LogUtil;

/**
 * Created by ffff on 2017-11-15.
 */
@Service
public class MarketDataUpdateListener implements MessageListener {

    private static Logger log = LogUtil.getTaskLogger();
    @Autowired
    private MarketDataListener dataListener;

    public void setDataListener(MarketDataListener dataListener) {
        this.dataListener = dataListener;
    }
    @Autowired
    private MarketdataService marketdataService;

    @Override
    public void onMessage(Message msg) {
    	String str="";
		try {
			str = new String(msg.getBody(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
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