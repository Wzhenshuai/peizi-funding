package com.icaopan.ems.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icaopan.clearing.service.CheckLogService;
import com.icaopan.customer.model.Channel;
import com.icaopan.customer.service.ChannelService;
import com.icaopan.ems.service.EmsService;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.enums.enumBean.TradeStatus;
import com.icaopan.enums.enumBean.TransactionType;
import com.icaopan.log.LogUtil;
import com.icaopan.log.annocation.TradeLogAnnocation;
import com.icaopan.marketdata.market.MarketdataService;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.trade.bean.ChannelPlacementParams;
import com.icaopan.trade.bean.FillDock;
import com.icaopan.trade.bean.PlacementDock;
import com.icaopan.trade.bean.PositionDock;
import com.icaopan.trade.model.ChannelPlacement;
import com.icaopan.trade.model.CheckLog;
import com.icaopan.trade.model.Fill;
import com.icaopan.trade.service.BrokerPositionService;
import com.icaopan.trade.service.ChannelPlacementService;
import com.icaopan.trade.service.FillService;
import com.icaopan.util.DateUtils;
import com.icaopan.web.vo.PlacementCheck;
import com.icaopan.web.vo.TdxFillItemVO;

import elf.api.marketdata.marketdata.MarketDataSnapshot;

@Service("emsService")
@Scope("singleton")
public class EmsServiceImpl extends LogUtil implements EmsService {

	private Map<String,List<TdxFillItemVO>> checkPlacementMap=new HashMap<String,List<TdxFillItemVO>>();
	
    @Autowired
    private FillService fillService;

    @Autowired
    private ChannelPlacementService channelPlacementService;

    @Autowired
    private ChannelService channelService;
    
    @Autowired
    private CheckLogService checkLogService;
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private BrokerPositionService brokderPositionService;

    @Autowired
    private RabbitTemplate amqpTemplate;
    
    @Autowired
    private MarketdataService marketdataService;

    private Logger logger = LogUtil.getLogger(getClass());
    private static final String BUY_EXCHANGE = "elf.execution.manual.buy.broker.exchange";
    private static final String SELL_EXCHANGE = "elf.execution.manual.sell.broker.exchange";
    private static final String CANCEL_EXCHANGE = "elf.execution.manual.cancel.broker.exchange";

    /**
     * 委托下单
     */
    @Override
    @TradeLogAnnocation(tag = "ems-委托下单")
    public void placement(ChannelPlacement channelPlacement) {
        //模拟生成委托回执
        HashMap<String, String> map = new HashMap<String, String>();
        String price = channelPlacement.getPrice().toString();
        price = price.replaceAll("0+?$", "");//去掉后面无用的零
        price = price.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        map.put("price", price);
        map.put("qty", channelPlacement.getQuantity().toString());
        map.put("account", channelPlacement.getAccount());
        map.put("symbol", channelPlacement.getSecurityCode());
        if (StringUtils.equalsIgnoreCase(channelPlacement.getSide().getName(), TradeSide.BUY.getName())) {
            map.put("tradeType", "buy");
            amqpTemplate.convertAndSend(BUY_EXCHANGE, channelPlacement.getAccount(), JSON.toJSONString(map));
        } else {
            map.put("tradeType", "sell");
            amqpTemplate.convertAndSend(SELL_EXCHANGE, channelPlacement.getAccount(), JSON.toJSONString(map));
        }
    }


    /**
     * 委托撤单
     *
     * @param channelPlacement
     */
    @Override
    @TradeLogAnnocation(tag = "ems-委托撤单")
    public void cancel(ChannelPlacement channelPlacement) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("executionSno", channelPlacement.getPlacementCode());
        map.put("tradeType", "cancel");
        map.put("account", channelPlacement.getAccount());
        map.put("symbol", channelPlacement.getSecurityCode());//证券代码
        map.put("side", channelPlacement.getSide().getNum() + "");//交易方向
        map.put("price", channelPlacement.getPrice().toString());//交易价格
        map.put("quantity", channelPlacement.getQuantity().toString());//交易数量
        map.put("size", querySamePlacementSumSize(channelPlacement) + "");//查询相似委托笔数
        map.put("sizeLianchu", querySamePlacementForLianchuSumSize(channelPlacement) + "");//查询相似委托联储专用
        getLogger(getClass()).debug("cancel execution info:" + JSON.toJSONString(map));
        amqpTemplate.convertAndSend(CANCEL_EXCHANGE, channelPlacement.getAccount(), JSON.toJSONString(map));
    }

    /**
     * 委托回报对接
     */
    @Override
    public void dockPlacementCallBack(byte[] json) {
        try {
            PlacementDock placementDock = parsePlacementDockJson(json);
            //LogUtil.getTradeLogger().info("委托回报："+JSONObject.toJSONString(placementDock));
            ChannelPlacement channelPlacement = channelPlacementService.getByPlacementNo(placementDock.getAccount(), placementDock.getExecutionSno());
            if (channelPlacement == null) {//未对接
            	ChannelPlacementParams params = new ChannelPlacementParams();
                params.setAccount(placementDock.getAccount());
                params.setSecurityCode(placementDock.getSymbol());
                params.setStatusArray(new String[]{TradeStatus.SENDACK.getName(),TradeStatus.CANCELLING.getName()} );
                params.setQuantity(placementDock.getQuantity());
                params.setPrice(placementDock.getPrice());
                params.setSide(placementDock.getSide());
                //填写委托编号
                channelPlacement = channelPlacementService.findDockChannelPlacement(params);
                if (channelPlacement == null) {
                    return;
                }
                channelPlacementService.fillPlacementCode(channelPlacement.getId(), placementDock.getExecutionSno());
            } else {
                //终态
                if (StringUtils.equals(channelPlacement.getStatus().getName(), TradeStatus.INVALID.getName()) || StringUtils.equals(channelPlacement.getStatus().getName(), TradeStatus.FILLED.getName()) || StringUtils.equals(channelPlacement.getStatus().getName(), TradeStatus.CANCELLED.getName())) {
                    return;
                }
            }
            if (StringUtils.equals(placementDock.getStatus(), "4")) {       //撤单成交
            	//BigDecimal cancelQuantity=channelPlacement.getQuantity().subtract(channelPlacement.getFillQuantity());
            	if(channelPlacement.getFillQuantity().compareTo(placementDock.getFillQuantity())==0){
            		BigDecimal cancelQuantity=placementDock.getQuantity().subtract(placementDock.getFillQuantity());
                    channelPlacementService.fill(channelPlacement, TransactionType.CANCELLED, cancelQuantity, placementDock.getPrice(), null,false);
            	}
            } else if (StringUtils.equals(placementDock.getStatus(), "8")) { //废单成交
            	BigDecimal invalidQuantity=channelPlacement.getQuantity().subtract(channelPlacement.getFillQuantity());
                channelPlacementService.fill(channelPlacement, TransactionType.INVALID, invalidQuantity, placementDock.getPrice(), placementDock.getErrorMessage(),false);
            }
        } catch (Exception e) {
            //logger.error("委托回报出错：", e);
            throw new RuntimeException(e);
        }

    }

    /**
     * 成交回报对接
     */
    @Override
    public void dockFillCallBack(byte[] json) {
        try {
            FillDock fillDock = parseFillDockJson(json);
            //LogUtil.getTradeLogger().info("成交回报："+JSONObject.toJSONString(fillDock));
            //查看成交记录是否存在
            List<Fill> fillList = fillService.selectFill(fillDock);
            if (!fillList.isEmpty()) {
                return;
            }
            String placementNo = fillDock.getExecutionSno();
            ChannelPlacement placement = channelPlacementService.getByPlacementNo(fillDock.getAccount(), placementNo);
            if (placement == null) {
                logger.info("成交回报，未找到对应的委托记录,委托编号：" + placementNo);
                return;
//                throw new RuntimeException("成交回报，未找到对应的委托记录。");
            }
            //判断成交数量是否超过100%
            BigDecimal hadFillQty = placement.getFillQuantity();
            BigDecimal placementQty = placement.getQuantity();
            if (hadFillQty != null) {
                BigDecimal tmpQty = hadFillQty.add(fillDock.getQuantity());
                tmpQty = tmpQty.setScale(0, BigDecimal.ROUND_HALF_UP);
                if (tmpQty.compareTo(placementQty) > 0) {
                    return;
//                    throw new RuntimeException("此次成交将超过率将超过100%");
                }
            }
            //插入成交记录
            fillService.saveFill(placement, fillDock);
            channelPlacementService.fill(placement, TransactionType.NORMAL, fillDock.getQuantity(), fillDock.getPrice(), null,false);
        } catch (Exception e) {
            //logger.error("成交回报：", e);
            throw new RuntimeException(e);
        }

    }

    @TradeLogAnnocation(tag = "ems-资金余额回报")
    @Override
    public void dockPositionCallBack(byte[] json) {
        try {
            PositionDock positionDock = parsePositionDockJson(json);
            channelService.updateCashAvailable(positionDock.getAvailable(), positionDock.getAccount(), positionDock.getTotalAssets());
        } catch (Exception e) {
            logger.error("头寸回报：", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 成交回报对接
     */
    @Override
    public void dockFillByHand(ChannelPlacement placement, String reportNo, BigDecimal quantity, BigDecimal price,boolean isForceFill) {
        try {
        	//判断成交编号是否已经存在
        	if(fillService.isFillExist(placement.getAccount(), placement.getPlacementCode(), reportNo)){
        		throw new RuntimeException("成交编号已存在，请勿重复提交");
        	}
            //判断成交数量是否超过100%
            BigDecimal hadFillQty = placement.getFillQuantity();
            BigDecimal placementQty = placement.getQuantity();
            if (hadFillQty != null) {
                BigDecimal tmpQty = hadFillQty.add(quantity);
                tmpQty = tmpQty.setScale(0, BigDecimal.ROUND_HALF_UP);
                if (tmpQty.compareTo(placementQty) > 0) {
                    throw new RuntimeException("此次成交率将超过100%");
                }
            }
            //判断成交价是否超过涨跌停
            MarketDataSnapshot shot=marketdataService.getBySymbol(placement.getSecurityCode());
            if(shot!=null){
            	double upLimit=shot.getLimitUp();
            	double downLimit=shot.getLimitDown();
            	if(upLimit>0&&downLimit>0){
            		if(price.doubleValue()<downLimit||price.doubleValue()>upLimit){
            			throw new RuntimeException("成交价超出涨跌停");
            		}
            	}
            }
            //插入成交记录
            fillService.saveFill(placement, reportNo, quantity, price);
            channelPlacementService.fill(placement, TransactionType.NORMAL, quantity, price, null,isForceFill);
        } catch (Exception e) {
            logger.error("成交回报：", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * 查询相似的交易总笔数
     */
    public int querySamePlacementSumSize(ChannelPlacement channelPlacement) {
        ChannelPlacementParams params = new ChannelPlacementParams();
        params.setSide(channelPlacement.getSide().getName());
        params.setAccount(channelPlacement.getAccount());
        params.setSecurityCode(channelPlacement.getSecurityCode());
        params.setPrice(channelPlacement.getPrice());
        //params.setQuantity(channelPlacement.getQuantity());
        params.setCreateDate(DateUtils.getDate());
        params.setStatus(TradeStatus.SENDACK.getName());
        List<ChannelPlacement> snoList = channelPlacementService.selectChannelPlacement(params);
        return snoList.size() + 1;
    }

    /**
     * 查询相似的交易总笔数
     */
    public int querySamePlacementForLianchuSumSize(ChannelPlacement channelPlacement) {
        ChannelPlacementParams params = new ChannelPlacementParams();
        params.setSide(channelPlacement.getSide().getName());
        params.setAccount(channelPlacement.getAccount());
        params.setSecurityCode(channelPlacement.getSecurityCode());
        params.setCreateDate(DateUtils.getDate());
        List<ChannelPlacement> snoList = channelPlacementService.selectChannelPlacement(params);
        return snoList.size() + 1;
    }

    private PositionDock parsePositionDockJson(byte[] json){
        PositionDock positionDock = new PositionDock();
        String data = null;
        try {
            data = new String(json, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("decode error:", e);
            return null;
        }
        JSONObject object = JSON.parseObject(data);
        BigDecimal available = null;
        BigDecimal balance = null;
        BigDecimal marketValue = null;
        String account = object.getString("account");
        try {
            available = object.getBigDecimal("available");
            balance = object.getBigDecimal("balance");
            marketValue = object.getBigDecimal("totalAssets");
        } catch (Exception e) {
            logger.error("get price or quantity error", e);
            return null;
        }
        positionDock.setAccount(account);
        positionDock.setAvailable(available);
        positionDock.setBalance(balance);
        positionDock.setTotalAssets(marketValue);
        return positionDock;
    }

    public FillDock parseFillDockJson(byte[] json) {
        FillDock fillDock = new FillDock();
        String data = null;
        try {
            data = new String(json, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("decode error:", e);
            return null;
        }
        JSONObject object = JSON.parseObject(data);
        String executionSno = object.getString("executionSno");
        String reportSno = object.getString("reportSno");
        String account = object.getString("account");
        BigDecimal price = null;
        BigDecimal quantity = null;
        try {
            price = object.getBigDecimal("price");
            quantity = object.getBigDecimal("quantity");
        } catch (Exception e) {
            logger.error("get price or quantity error", e);
            return null;
        }
        Date createDate = null;
        try {
            createDate = new SimpleDateFormat("yyyyMMdd").parse(object.getString("createDate"));
        } catch (Exception e) {
            createDate = new Date();
        }
        fillDock.setAccount(account);
        fillDock.setCreateDate(createDate);
        fillDock.setExecutionSno(executionSno);
        fillDock.setPrice(price);
        fillDock.setQuantity(quantity);
        fillDock.setReportSno(reportSno);
        return fillDock;
    }

    public PlacementDock parsePlacementDockJson(byte[] json) {
        PlacementDock placementDock = new PlacementDock();
        String data = null;
        try {
            data = new String(json, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("decode error:", e);
        }
        JSONObject object = JSON.parseObject(data);
        BigDecimal price = BigDecimal.ZERO;
        BigDecimal fillQuantity = BigDecimal.ZERO;
        BigDecimal quantity = BigDecimal.ZERO;
        String symbol = object.getString("symbol");
        price = object.getBigDecimal("price");
        fillQuantity = object.getBigDecimal("fillQuantity");
        quantity = object.getBigDecimal("quantity");
        String executionSno = object.getString("executionSno");
        String side = object.getString("side");
        String account = object.getString("account");
        String time = object.getString("time");
        String errorMessage = object.getString("msg");
        if (StringUtils.isNotBlank(errorMessage)) {
            try {
                errorMessage = new String(new BASE64Decoder().decodeBuffer(errorMessage), "utf-8");
                if (StringUtils.isNotBlank(errorMessage) && errorMessage.length() > 400) {
                    errorMessage = errorMessage.substring(0, 400);
                }
            } catch (Exception e) {
                logger.error("BASE64Decoder error:", e);
            }
        }
        Date createDate = null;
        try {
            createDate = new SimpleDateFormat("yyyyMMdd").parse(object.getString("createDate"));
        } catch (Exception e) {
            createDate = new Date();
        }
        String status = object.getString("status");//z表示普通成交;4表示撤单成交;8表示废单
        placementDock.setAccount(account);
        placementDock.setCreateDate(DateUtils.formatDate(createDate, "yyyy-MM-dd"));
        placementDock.setErrorMessage(errorMessage);
        placementDock.setExecutionSno(executionSno);
        placementDock.setPrice(price);
        placementDock.setQuantity(quantity);
        if (side.equals("1")||side.equals("BUY")) {
            placementDock.setSide(TradeSide.BUY.getName());
        } else if (side.equals("2")||side.equals("SELL")) {
            placementDock.setSide(TradeSide.SELL.getName());
        } else {
            throw new RuntimeException("委托对接委托方向解析出错");
        }
        placementDock.setStatus(status);
        placementDock.setSymbol(symbol);
        placementDock.setTime(time);
        placementDock.setFillQuantity(fillQuantity);
        return placementDock;
    }
    
    @Override
    public List<TdxFillItemVO> getCheckPlacementList(){
    	List<TdxFillItemVO> list=new ArrayList<TdxFillItemVO>();
    	if(!this.checkPlacementMap.isEmpty()){
    		for(String account:checkPlacementMap.keySet()){
    			List<TdxFillItemVO> childList=checkPlacementMap.get(account);
    			list.addAll(childList);
    		}
    	}
    	return list;
    }
    
    /**
     * 通道委托对账
     * @param json
     */
    @Override
    public void channelPlacementCheck(byte[] json){
    	StringBuilder  result=new StringBuilder();
    	String data = null;
        try {
            data = new String(json, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("decode error:", e);
            return;
        }
        JSONObject object = JSON.parseObject(data);
        String account=object.getString("account");
        JSONArray exes=object.getJSONArray("data");
        String channelName="";
        Channel channel=channelService.getChannelByAccount(account);
    	if(channel!=null){
    		channelName=channel.getName();
    		result.append(String.format("通道账号:%s,通道名称:%s,对账结果:\r\n", account,channelName));
        	StringBuilder result1=new  StringBuilder("委托未对接的券商委托编号有:");
        	StringBuilder result11=new  StringBuilder("");

        	StringBuilder result2=new  StringBuilder("成交数量不一致的券商委托编号有:");
        	StringBuilder result22=new  StringBuilder("");
        	Map<String,TdxFillItemVO> map=new HashMap<String,TdxFillItemVO>();
            for (Object _obj : exes) {
    			JSONObject obj=(JSONObject) _obj;
    			BigDecimal price = BigDecimal.ZERO;
    	        BigDecimal fillQuantity = BigDecimal.ZERO;
    	        BigDecimal quantity = BigDecimal.ZERO;
    	        String symbol = obj.getString("symbol");
    	        price = obj.getBigDecimal("price");
    	        String side=obj.getString("side");
    	        fillQuantity = obj.getBigDecimal("fillQuantity");
//    	        if(fillQuantity!=null&&fillQuantity.doubleValue()==0){
//    	        	continue;
//    	        }
    	        quantity = obj.getBigDecimal("quantity");
    	        String executionSno = obj.getString("executionSno");
    	        //检测委托
    	        try {
    	        	checkPlacement(account, executionSno, symbol, quantity,price, fillQuantity,side, result,result11,result22,map,channelName);
    			} catch (Exception e) {
    				LogUtil.getTradeLogger().error("委托对账出错",e);
    			}
    		}
            //将map转为list
            List<TdxFillItemVO> list=new ArrayList<TdxFillItemVO>();
    		for (String key : map.keySet()) {
    			TdxFillItemVO fvo=map.get(key);
    			list.add(fvo);
			}
    		PlacementCheck.put(account, list);
    		if(StringUtils.isNotEmpty(result11.toString())){
    			result.append(result1.append(result11).toString()+"\r\n");
    		}
    		if(StringUtils.isNotEmpty(result22.toString())){
    			result.append(result2.append(result22).toString()+"\r\n");
    		}
    		if(StringUtils.isEmpty(result11.toString())&&StringUtils.isEmpty(result22.toString())){
    			result.append("一致\r\n");
    		}
    	}else{
    		result.append(String.format("通道账号:%s在系统中不存在\r\n", account));
    	}
        //将结果存入数据库
        if(StringUtils.isNotEmpty(result.toString())){
        	CheckLog checkLog=new CheckLog(new Date(),"委托记录平台与券商对账",result.toString());
        	checkLogService.saveCheckLog(checkLog);
        }
    }
    
    /**
     * 通道持仓对账
     * @param json
     */
    @Override
    public void channelPositionCheck(byte[] json){
    	String data = null;
        try {
            data = new String(json, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("decode error:", e);
            return;
        }
        JSONObject object = JSON.parseObject(data);
        String account=object.getString("account");
        JSONArray exes=object.getJSONArray("data");
        for(int i=0;i<exes.size();i++){
        	JSONObject obj=exes.getJSONObject(i);
        	String symbol=obj.getString("symbol");
        	boolean isNum = symbol.matches("[0-9]+"); 
        	if(!isNum){
        		continue;
        	}
        	String quantity=obj.getString("quantity");
        	String price=obj.getString("price");
        	brokderPositionService.saveBrokerPosition(account, symbol, Double.valueOf(quantity), Double.valueOf(price));
        }
    }
    
    private void checkPlacement(String account,String executionSno,String symbol,BigDecimal quantity,BigDecimal price,BigDecimal fillQuantity,String side,StringBuilder result,StringBuilder result1,StringBuilder result2,Map<String,TdxFillItemVO> map,String channelName){
    	ChannelPlacement channelPlacement = channelPlacementService.getByPlacementNo(account, executionSno);
    	String stockName=securityService.queryNameByCode(symbol);
    	if (channelPlacement == null) {
        	result1.append(executionSno+"  ");
        	//委托未对接
			TdxFillItemVO _vo=map.get(symbol);
			if(_vo==null){
				_vo=new TdxFillItemVO();
				_vo.setAccountName(channelName);
				_vo.setAccountNo(account);
				_vo.setStockCode(symbol);
				_vo.setStockName(stockName);
				if("1".equals(side)){
					_vo.setBuyFillQuantity(fillQuantity.doubleValue());
				}else{
					_vo.setSellFillQuantity(fillQuantity.doubleValue());
				}
			}else{
				if("1".equals(side)){
					_vo.setBuyFillQuantity(_vo.getBuyFillQuantity()+fillQuantity.doubleValue());
				}else{
					_vo.setSellFillQuantity(_vo.getSellFillQuantity()+fillQuantity.doubleValue());
				}
			}
			map.put(symbol, _vo);
        }else{
        	 BigDecimal _fillQuantity=channelPlacement.getFillQuantity();
             if(fillQuantity.compareTo(_fillQuantity)!=0){
             	result2.append(String.format("委托编号-%s{股票代码:%s,券商成交数量:%s,平台成交数量:%s},",executionSno,symbol,fillQuantity,_fillQuantity));
             }
        }
    }
}
