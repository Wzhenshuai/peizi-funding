package com.icaopan.risk.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.icaopan.common.util.SecurityUtil;
import com.icaopan.marketdata.market.DailyLimit;
import com.icaopan.marketdata.market.DailyLimitPriceService;
import com.icaopan.risk.bean.*;
import com.icaopan.sys.dao.DictMapper;
import com.icaopan.sys.model.Dict;
import org.apache.commons.codec.EncoderException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icaopan.log.LogUtil;
import com.icaopan.risk.dao.PrivateRiskCtrlMapper;
import com.icaopan.risk.service.PrivateRiskCtrlService;
import com.icaopan.util.WebUtil;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.TdxFillItemVO;

/**
 * Created by RoyLeong @royleo.xyz on 2017/7/11.
 */
@Service("riskCtrlService")
public class PrivateRiskCtrlServiceImpl implements PrivateRiskCtrlService {

    @Autowired
    DailyLimitPriceService dailyLimitPriceService;

    private Logger logger = LogUtil.getLogger(getClass());

    @Autowired
    PrivateRiskCtrlMapper riskCtrlMapper;

    @Autowired
    DictMapper dictMapper;

    private static String remoteTdxURL;

    /**
     * 分页查询个人风控信息
     * */
    @Override
    public Page findPrivateRiskCtrlByPage(Page page, InfoParam param) {
        page.setAaData(riskCtrlMapper.selectPrivateUsers(param));
        return page;
    }

    @Override
    public TdxBrokerBase getBrokerBaseInfoById(String id) {
        return riskCtrlMapper.getBrokerBaseInfoById(Integer.valueOf(id));
    }

    @Override
    public TdxPrivateUser getTdxPrivateUserInfoById(Integer id) {
        InfoParam param = new InfoParam();
        param.setId(id);
        List<TdxPrivateUser> users = riskCtrlMapper.selectPrivateUsers(param);
        TdxPrivateUser tdxPrivateUser = users.size()>0?users.get(0):null;
        return tdxPrivateUser;
    }

    @Override
    public List<PrivateCustomer> getPrivateCustomers() {
        return riskCtrlMapper.selectPrivateCustomers();
    }

    @Override
    public List<TdxServerInfo> getServerInfoByBelongId(String belongId) {
        return riskCtrlMapper.getServerInfoByBelongId(belongId);
    }

    @Override
    public List<TdxYyb> getYYBByBelongId(String belongId) {
        return riskCtrlMapper.getYYBByBelongId(belongId);
    }

    @Override
    public List<TdxBrokerBase> getAllBrokerBaseInfo() {
        return riskCtrlMapper.getAllBrokerBaseInfo();
    }

    @Override
    public TdxServerInfo getBaseServerInfoById(Integer id) {
        return riskCtrlMapper.getBaseServerInfoById(id);
    }

    @Override
    public TdxYyb getYybById(Integer Id) {
        return riskCtrlMapper.getYybById(Id);
    }

    @Override
    public boolean addUserRiskCtrl(TdxPrivateUser user) {
        return riskCtrlMapper.addPrivateUser(user);
    }

    @Override
    public boolean updatePrivateUserRiskCtrl(TdxPrivateUser user) {
        return riskCtrlMapper.updatePrivateRiskCtrl(user);
    }

    @Override
    public void updatePassWord(PassWordParam param) {
        riskCtrlMapper.updatePassWord(param);
    }

    /**
     * 查询账户交易相关信息
     * @param type 1: 获取当前持仓  2：获取当日委托  3：获取当日成交
     * @param userId
     * */
    @Override
    public String queryInfoByUserIdAndType(Integer userId, String type) {
        if (remoteTdxURL==null||remoteTdxURL.length()==0){
            Dict dict = new Dict();
            dict.setType("remoteUrl");
            List<Dict> dicts = dictMapper.findList(dict);
            if (dicts.size()==0) return null;
            remoteTdxURL = dicts.get(0).getValue();
        }
        WebUtil webUtil = new WebUtil();
        String url = remoteTdxURL+"/lever/queryInfo";
        String parameter = "userId="+userId+"&type="+type;
        String result = "";
        try {
            result = webUtil.readContentFromPost(url,parameter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 成交汇总查询
     * @return
     */
    @Override
    public List<TdxFillItemVO> queryTdxFillSummary(){
    	List<TdxFillItemVO> voList=new ArrayList<TdxFillItemVO>();
    	List<TdxPrivateUser> users=(List<TdxPrivateUser>) findPrivateRiskCtrlByPage(new Page(),new InfoParam()).getAaData();
    	for (TdxPrivateUser tdxPrivateUser : users) {
    		try {
    			voList.addAll(queryOneUserFillSummary(tdxPrivateUser));
			} catch (Exception e) {
				logger.error("",e);
			}
    		
		}
    	return voList;
    }

    /**
     * 平仓接口
     * @param placement
     * @return
     * */
    @Override
    public String openPosition(RiskCtrlPlacement placement) {
        String placementJson = JSON.toJSONString(placement);
        WebUtil webUtil = new WebUtil();
        String url = remoteTdxURL+"/lever/openPisition";
        String parameter = "placementJson="+placementJson;
        String result = "";
        try {
            result = webUtil.readContentFromPost(url,parameter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public String getLimitDownPrice(String code) throws IOException, EncoderException {
        if (code==null||code.length()==0){
            return null;
        }
        String stockCode = SecurityUtil.codeToSelfStockCode(code);
        DailyLimit dailyLimit = dailyLimitPriceService.getDailyLimit(stockCode);
        double limitDownPrice = dailyLimit.getLimitDown();
        return String.valueOf(limitDownPrice);
    }

    @Override
    public String manualUpdateBroker() {
        WebUtil webUtil = new WebUtil();
        String url = remoteTdxURL+"/lever/reloadBroker";
        String result = "";
        try {
            result = webUtil.readContentFromPost(url,"");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String verityRiskInfo(String accountNo, String tradeAccount, String txPassword, String jYPassword, String serverId, String yybId) {
        WebUtil webUtil = new WebUtil();
        String url = remoteTdxURL+"/lever/testLogin";
        String parameters = "accountNo="+accountNo+"&tradeAccount="+tradeAccount+"&jyPassWord="+jYPassword+"&txPassWord="+txPassword+"&serverId="+serverId+"&yybId="+yybId;
        String result = "";
        try {
            result = webUtil.readContentFromPost(url,parameters);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<TdxFillItemVO> queryOneUserFillSummary(TdxPrivateUser tdxPrivateUser){
    	int userId=tdxPrivateUser.getId();
    	List<TdxFillItemVO> list=new ArrayList<TdxFillItemVO>();
    	String str=queryInfoByUserIdAndType(userId,"3");
    	JSONObject json=JSONObject.parseObject(str);
    	JSONArray headerArray=json.getJSONArray("header");
    	JSONArray bodyArray=json.getJSONArray("body");
    	Map<String,TdxFillItemVO> map=new HashMap<String,TdxFillItemVO>();
    	if(bodyArray!=null&&!bodyArray.isEmpty()){
    		//获取字段对应的下标
    		int stockCodeIndext=0;int stockNameIndex=0;int sideIndex=0;int fillQuantityIndex=0;int fillAmountIndex=0;
    		for(int i=0;i<headerArray.size();i++){
    			String head=headerArray.getString(i);
    			if("证券代码".equals(head)){
    				stockCodeIndext=i;
    			}else if("证券名称".equals(head)){
    				stockNameIndex=i;
    			}else if("买卖标志".equals(head)){
    				sideIndex=i;
    			}else if("成交数量".equals(head)){
    				fillQuantityIndex=i;
    			}else if("成交金额".equals(head)){
    				fillAmountIndex=i;
    			}
    		}
    		//统计成交汇总
    		for(int i=0;i<bodyArray.size();i++){
    			JSONArray item=bodyArray.getJSONArray(i);
    			String stockCode=item.getString(stockCodeIndext);
    			String stockName=item.getString(stockNameIndex);
    			String side=item.getString(sideIndex);
    			double fillQuantity=item.getDoubleValue(fillQuantityIndex);
    			double fillAmount=item.getDoubleValue(fillAmountIndex);
    			String key=stockCode;
    			TdxFillItemVO _vo=map.get(key);
    			if(_vo==null){
    				_vo=new TdxFillItemVO();
    				_vo.setAccountName(tdxPrivateUser.getNickName());
    				_vo.setAccountNo(tdxPrivateUser.getAccountNo());
    				_vo.setStockCode(stockCode);
    				_vo.setStockName(stockName);
    				if("买入".equals(side)){
    					_vo.setBuyFillQuantity(fillQuantity);
    					_vo.setBuyFillAmount(fillAmount);
    				}else{
    					_vo.setSellFillQuantity(fillQuantity);
    					_vo.setSellFillAmount(fillAmount);
    				}
    			}else{
    				if("买入".equals(side)){
    					_vo.setBuyFillQuantity(_vo.getBuyFillQuantity()+fillQuantity);
    					_vo.setBuyFillAmount(_vo.getBuyFillAmount()+fillAmount);
    				}else{
    					_vo.setSellFillQuantity(_vo.getSellFillQuantity()+fillQuantity);
    					_vo.setSellFillAmount(_vo.getSellFillAmount()+fillAmount);
    				}
    			}
    			map.put(key, _vo);
    		}
    		//遍历map输出
    		for (String key : map.keySet()) {
    			TdxFillItemVO fvo=map.get(key);
    			list.add(fvo);
			}
    	}
    	return list;
    }
}
