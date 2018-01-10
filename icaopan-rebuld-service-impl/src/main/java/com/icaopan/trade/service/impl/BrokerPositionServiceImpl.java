package com.icaopan.trade.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icaopan.common.util.SecurityUtil;
import com.icaopan.customer.model.Channel;
import com.icaopan.customer.service.ChannelService;
import com.icaopan.stock.model.StockSecurity;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.trade.bean.BrokerPositionQueryParams;
import com.icaopan.trade.bean.BrokerPositionResult;
import com.icaopan.trade.dao.BrokerPositionMapper;
import com.icaopan.trade.model.BrokerPosition;
import com.icaopan.trade.service.BrokerPositionService;
import com.icaopan.user.model.ChannelSecurityPosition;
import com.icaopan.user.service.ChannelPositionService;
import com.icaopan.util.DateUtils;
@Service("brokerPositionService")
public class BrokerPositionServiceImpl implements BrokerPositionService {

	@Autowired
	private BrokerPositionMapper brokerPositionMapper;
	@Autowired
	private ChannelPositionService channelPositionService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private SecurityService securityService;
	
	@Override
	public  void saveBrokerPosition(String account,String stockCode,double amount,double costPrice){
		BrokerPosition record=new BrokerPosition();
		record.setAccount(account);
		record.setAmount(amount);
		record.setCostPrice(costPrice);
		record.setStockCode(stockCode);
		record.setCreateDate(DateUtils.parseDate(DateUtils.getDate()));
		brokerPositionMapper.insert(record);
	}
	
	@Override
	public List<BrokerPositionResult> selectByCondition(
			BrokerPositionQueryParams params) {
		List<BrokerPositionResult> result=new ArrayList<BrokerPositionResult>();
		params.setCreateDate(DateUtils.getDate());
		List<BrokerPosition> list=brokerPositionMapper.selectByCondition(params);
		for (BrokerPosition brokerPosition : list) {
			BrokerPositionResult r=new BrokerPositionResult(brokerPosition);
			Double icpAmount=channelPositionService.querySummarySecurityPostionAmount(brokerPosition.getAccount(), brokerPosition.getStockCode());
			if(icpAmount==null){
				icpAmount=0d;
			}
			Channel channel=channelService.getChannelByAccount(brokerPosition.getAccount());
			if(channel==null){
				continue;
			}
			r.setChannelName(channel.getName());
			r.setIcpAmount(icpAmount);
			if (params!=null&&StringUtils.isNotBlank(params.getCheckResult())&&!StringUtils.equals(params.getCheckResult(),r.getCheckResult())){
				continue;
			}
			if (params!=null&&params.getCustomerId()!=null&&!channel.getCustomerId().equals(params.getCustomerId())){
				continue;
			}
			if (params!=null&&params.getChannelId()!=null&&!channel.getId().equals(params.getChannelId())){
				continue;
			}
			String stockName=securityService.queryNameByCode(brokerPosition.getStockCode());
			r.setStockName(stockName);
			result.add(r);
		}
		return result;
	}

	@Override
	public BrokerPosition selectById(Integer id) {
		return brokerPositionMapper.selectByPrimaryKey(id);
	}

	@Override
	public BrokerPositionResult queryDetailByBrokerPositionId(Integer id){
		BrokerPositionResult r=null;
		BrokerPosition brokerPosition=selectById(id);
		if(brokerPosition!=null){
			r=new BrokerPositionResult(brokerPosition);
			String stockCode=brokerPosition.getStockCode();
			String securityId=SecurityUtil.getInternalSecurityIdBySecurityCode(stockCode);
			StockSecurity ss=securityService.findByNameAndCode(null,stockCode);
			Double icpAmount=channelPositionService.querySummarySecurityPostionAmount(brokerPosition.getAccount(), brokerPosition.getStockCode());
			List<ChannelSecurityPosition> detailList=channelPositionService.queryAllByAccount(brokerPosition.getAccount(),securityId);
			Channel channel=channelService.getChannelByAccount(brokerPosition.getAccount());
			if(channel!=null){
				r.setChannelName(channel.getName());
				r.setIcpAmount(icpAmount);
				r.setDetailList(detailList);
			}
			if(ss!=null){
				r.setStockName(ss.getName());
			}
		}
		return r;
	}
	
	@Override
	public void adjustChannelPositionByBrokerPosition(BrokerPositionResult result,List<String> ids,List<String> sides,List<String> amounts,List<String> costPrices){
		BigDecimal adjustAmountSum=BigDecimal.ZERO;
		for(int i=0;i<amounts.size();i++){
			String amount=amounts.get(i);
			String side=sides.get(i);
			BigDecimal _amount=new BigDecimal(side+amount);
			adjustAmountSum=adjustAmountSum.add(_amount);
		}
		BigDecimal icpAmount=new BigDecimal(result.getIcpAmount());
		if(icpAmount.add(adjustAmountSum).doubleValue()==result.getAmount().doubleValue()){
			for(int i=0;i<ids.size();i++){
				int id=Integer.valueOf(ids.get(i));
				String amount=amounts.get(i);
				if(StringUtils.isEmpty(amount)){
					continue;
				}
				String side=sides.get(i);
				BigDecimal _amount=new BigDecimal(side+amount);
				BigDecimal amountChange=_amount;
				String costPriceStr=costPrices.get(i);
				if(StringUtils.isEmpty(costPriceStr)){
					costPriceStr="0";
				}
				BigDecimal costPrice=new BigDecimal(costPriceStr);
				channelPositionService.updatePosition(id,amountChange,amountChange,costPrice,null,null);
			}
		}else{
			throw new RuntimeException("调整后数量仍然与券商数量不一致，请重新输入调整数量");
		}
	}
}
