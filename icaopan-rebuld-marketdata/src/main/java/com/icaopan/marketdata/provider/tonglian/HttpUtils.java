package com.icaopan.marketdata.provider.tonglian;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.icaopan.util.DateUtils;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.icaopan.marketdata.constant.ExchangeConsts;
import com.icaopan.marketdata.market.StockBonus;
import com.icaopan.msg.MsgServer;

/**
 * Created by ffff on 2016-09-22.
 */
public class HttpUtils {
	//取上交所股票错误次数
	private static int shang_jiao_wrong_times=0;
	//取深交所股票错误次数
	private static int shen_jiao_wrong_times=0;
	
    private static final String              ACCESS_TOKEN   = "cc2a02835497fb6110002cbdb32a7c0d939e49fbb7aea62609ca1fca0c238092";
    //股票行情查询
    private static final String              TICKER_URL     = "https://api.wmcloud.com/data/v1/api/market/getTickRTSnapshot.json?securityID=&assetClass=E&exchangeCD=";
    //单只股票行情查询
    private static final String              TICKER_ONE_URL = "https://api.wmcloud.com/data/v1/api/market/getTickRTSnapshot.json?exchangeCD=&assetClass=&securityID=";
    //股票涨跌停查询
    private static final String              MK_LIMIT_URL   = "https://api.wmcloud.com/data/v1/api/market/getMktLimit.json?field=";
    //新红配查询url
    private static final String              EQU_DIV  = "https://api.wmcloud.com/data/v1/api/equity/getEquDiv.json?field=ticker,secShortName,perCashDiv,perShareDivRatio,perShareTransRatio,recordDate,exDivDate";
    
    private static       Logger              log            = LoggerFactory.getLogger(HttpUtils.class);
    //创建http client
    private static       CloseableHttpClient httpClient     = createHttpsClient();

    public static long parseDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        try {
            Date mdDate = sdf.parse(dateStr);
            return mdDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    private static String formatSimpleDate() throws IOException, EncoderException {
        Ticker ticker = getOneTickRTSnapshot("000001.XSHE");
        return StringUtils.remove(ticker.getDataDate(), "-");
    }

    public static List<Ticker> getTickRTSnapshot() throws IOException, EncoderException {
        List<Ticker> list = new ArrayList<Ticker>();
        List<Ticker> listShangJiao=getTickRTSnapshot(ExchangeConsts.SHANG_JIAO_SUO);
        List<Ticker> listShenJiao=getTickRTSnapshot(ExchangeConsts.SHEN_JIAO_SUO);
        checkShangJiaoTicker(listShangJiao);
        checkShenJiaoTicker(listShenJiao);
        list.addAll(listShangJiao);
        list.addAll(listShenJiao);
        return list;
    }

    private static void checkShangJiaoTicker(List<Ticker> listShangJiao){
    	if(listShangJiao==null||listShangJiao.isEmpty()){
    		shang_jiao_wrong_times++;
    	}else{
    		Ticker ticker=listShangJiao.get(0);
    		if(ticker!=null){
    			String tradeDate=ticker.getDataDate();
    			long datetime=ticker.getTimestamp();
    			if(!isTimeOk(datetime,tradeDate)){
    				shang_jiao_wrong_times++;
    			}
    		}
    	}
    	if(shang_jiao_wrong_times>30){
    		MsgServer.sendMarketDataMsgToManager("【行情监控】获取上交所股票行情异常。");
    		shang_jiao_wrong_times=0;
    	}
    }
    
    private static void checkShenJiaoTicker(List<Ticker> listShenJiao){
    	if(listShenJiao==null||listShenJiao.isEmpty()){
    		shen_jiao_wrong_times++;
    	}else{
    		Ticker ticker=listShenJiao.get(0);
    		if(ticker!=null){
    			String tradeDate=ticker.getDataDate();
    			long datetime=ticker.getTimestamp();
    			if(!isTimeOk(datetime,tradeDate)){
    				shen_jiao_wrong_times++;
    			}
    		}
    	}
    	if(shen_jiao_wrong_times>30){
    		MsgServer.sendMarketDataMsgToManager("【行情监控】获取深交所股票行情异常。");
    		shen_jiao_wrong_times=0;
    	}
    } 
    
    public static  boolean isTimeOk(long time,String tradeDate){
    	Date now=new Date();
    	long nowTime=now.getTime();
    	String todayDate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    	if(todayDate.equals(tradeDate)){
    		//判断现在是交易时间
    		String _tradeTimeStart1=new SimpleDateFormat("yyyy-MM-dd 09:30:00").format(now);
    		String _tradeTimeEnd1=new SimpleDateFormat("yyyy-MM-dd 11:30:00").format(now);
    		
    		String _tradeTimeStart2=new SimpleDateFormat("yyyy-MM-dd 13:00:00").format(now);
    		String _tradeTimeEnd2=new SimpleDateFormat("yyyy-MM-dd 15:00:00").format(now);
    		Date tradeTimeStart1 = null;
    		Date tradeTimeEnd1 = null;
    		Date tradeTimeStart2 = null;
    		Date tradeTimeEnd2 = null;
			try {
				tradeTimeStart1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(_tradeTimeStart1);
				tradeTimeEnd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(_tradeTimeEnd1);
				tradeTimeStart2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(_tradeTimeStart2);
				tradeTimeEnd2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(_tradeTimeEnd2);
			} catch (ParseException e) {
				e.printStackTrace();
			}
        	if((nowTime>=tradeTimeStart1.getTime()&&nowTime<=tradeTimeEnd1.getTime())||(nowTime>=tradeTimeStart2.getTime()&&nowTime<=tradeTimeEnd2.getTime())){
        		long minus=nowTime-time;
        		if(minus>30*1000){
        			return false;
        		}
        	}
    	}
    	return true;
    }
    
    public static List<Ticker> getTickRTSnapshot(String exchange) throws IOException, EncoderException {
        String ticks = getHttpData(TICKER_URL + exchange);
        Entity<Ticker> entity = JSON.parseObject(ticks, new TypeReference<Entity<Ticker>>() {
        });
        if (entity.getData() == null || entity.getData().size() == 0) {
            return new ArrayList<Ticker>();
        }
        return entity.getData();
    }

    public static MktLimit getMkLimit(String secID) throws IOException, EncoderException {
        String url = MK_LIMIT_URL + "&secID=" + secID + "&tradeDate=" + formatSimpleDate();
        String results = getHttpData(url);
        Entity<MktLimit> mktLimitEntity = JSON.parseObject(results, new TypeReference<Entity<MktLimit>>() {
        });
        if (mktLimitEntity.getData() == null || mktLimitEntity.getData().size() == 0) {
            return null;
        }
        return mktLimitEntity.getData().get(0);
    }

    public static String getlastTradeDate() throws IOException, EncoderException {
        String url = MK_LIMIT_URL + "&secID=000001.XSHE";
        String results = getHttpData(url);
        String lastTradeDate = null;
        MktLimit mktLimit = null;
        Entity<MktLimit> mktLimitEntity = JSON.parseObject(results, new TypeReference<Entity<MktLimit>>() {
        });
        if (mktLimitEntity.getData() == null || mktLimitEntity.getData().size() == 0) {
            return null;
        }
        MktLimit lastDateMktLimit = mktLimitEntity.getData().get(mktLimitEntity.getData().size()-1);
        lastDateMktLimit.getTradeDate();
        if (lastDateMktLimit.getTradeDate().equals(DateUtils.getDate())) {
            mktLimit = mktLimitEntity.getData().get(mktLimitEntity.getData().size()-2);
        } else {
            mktLimit = mktLimitEntity.getData().get(mktLimitEntity.getData().size()-1);
        }
        if (mktLimit != null){
            lastTradeDate  = StringUtils.remove(mktLimit.getTradeDate(), "-");
        }
        return lastTradeDate;
    }

    public static List<MktLimit> getLastTradeDateMkLimit(String lastTradeDate) throws IOException, EncoderException {
        String url = MK_LIMIT_URL +"&tradeDate=" + lastTradeDate;
        String results = getHttpData(url);
        Entity<MktLimit> mktLimitEntity = JSON.parseObject(results, new TypeReference<Entity<MktLimit>>() {
        });
        if (mktLimitEntity.getData() == null || mktLimitEntity.getData().size() == 0) {
            return null;
        }
        return mktLimitEntity.getData();
    }


    public static List<MktLimit> getMkLimit() throws IOException, EncoderException {
        String url = MK_LIMIT_URL + "&tradeDate=" + formatSimpleDate();
        String results = getHttpData(url);
        Entity<MktLimit> mktLimitEntity = JSON.parseObject(results, new TypeReference<Entity<MktLimit>>() {
        });
        if (mktLimitEntity.getData() == null) {
            return new ArrayList<MktLimit>();
        }
        return mktLimitEntity.getData();
    }
    
    public static List<StockBonus> getTodayStockBonus(String exDivDate) throws IOException, EncoderException {
        String url = EQU_DIV + "&exDivDate=" + exDivDate;
        String results = getHttpData(url);
        Entity<StockBonus> mktLimitEntity = JSON.parseObject(results, new TypeReference<Entity<StockBonus>>() {
        });
        if (mktLimitEntity.getData() == null) {
            return new ArrayList<StockBonus>();
        }
        return mktLimitEntity.getData();
    }

    public static Ticker getOneTickRTSnapshot(String securityID) throws IOException, EncoderException {
        String ticks = getHttpData(TICKER_ONE_URL + securityID);
        Entity<Ticker> entity = JSON.parseObject(ticks, new TypeReference<Entity<Ticker>>() {
        });
        if (entity.getData() == null || entity.getData().size() == 0) {
            return null;
        }
        return entity.getData().get(0);
    }


    public static String getHttpData(String url) throws IOException, EncoderException {
        //根据api store页面上实际的api url来发送get请求，获取数据
        HttpGet httpGet = new HttpGet(url);
        //在header里加入 Bearer {token}，添加认证的token，并执行get请求获取json数据
        httpGet.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        return body;
    }

    //创建http client
    @SuppressWarnings("deprecation")
	public static CloseableHttpClient createHttpsClient() {
        X509TrustManager x509mgr = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] xcs, String string) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] xcs, String string) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        //因为java客户端要进行安全证书的认证，这里我们设置ALLOW_ALL_HOSTNAME_VERIFIER来跳过认证，否则将报错
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] { x509mgr }, null);
            sslsf = new SSLConnectionSocketFactory(
                    sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }


    public static void main(String[] args) throws IOException, EncoderException {
//        String url = "https://api.wmcloud.com/data/v1/api/market/getTickRTSnapshot.json?securityID=600022.XSHG,000001.XSHE&assetClass=&exchangeCD=&field=";
        //String url = MK_LIMIT_URL + "&secID=000001.XSHE";
        String url = MK_LIMIT_URL + "&tradeDate=20170904";
    	 // String url=TICKER_URL + ExchangeConsts.SHANG_JIAO_SUO;
    	  //String today=new SimpleDateFormat("yyyyMMdd").format(new Date());
    	  //String url=TRADING_DAY+"&beginDate=20170825";
            getLastTradeDateMkLimit("ss");
    	  //System.out.println(url);
    	  String body = getHttpData(url);
    	  System.out.println(body);
    }
}
