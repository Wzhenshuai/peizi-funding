package com.icaopan.customer.service.impl;

import com.icaopan.common.util.SecurityUtil;
import com.icaopan.customer.bean.ChannelApplyParams;
import com.icaopan.customer.bean.ChannelParams;
import com.icaopan.customer.dao.ChannelApplyMapper;
import com.icaopan.customer.dao.ChannelMapper;
import com.icaopan.customer.model.BuyLimitChannel;
import com.icaopan.customer.model.Channel;
import com.icaopan.customer.model.ChannelApply;
import com.icaopan.customer.service.ChannelService;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.enums.enumBean.TradeStatus;
import com.icaopan.log.LogUtil;
import com.icaopan.marketdata.market.MarketdataService;
import com.icaopan.stock.model.StockSecurity;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.trade.bean.ChannelPlacementParams;
import com.icaopan.trade.model.ChannelPlacement;
import com.icaopan.trade.service.ChannelPlacementService;
import com.icaopan.user.bean.PositionParams;
import com.icaopan.user.model.ChannelSecurityPosition;
import com.icaopan.user.service.ChannelPositionService;
import com.icaopan.util.BigDecimalUtil;
import com.icaopan.util.page.Page;
import com.icaopan.customer.dao.ChannelLimitMapper;
import com.icaopan.customer.model.ChannelLimit;
import com.icaopan.web.vo.SecurityPositionVO;

import elf.api.marketdata.marketdata.MarketDataSnapshot;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;

import java.math.BigDecimal;
import java.util.*;

@Service("channelService")
public class ChannelServiceImpl implements ChannelService {

	private Logger logger= LogUtil.getLogger(getClass());

	@Autowired
	private ChannelMapper channelMapper;

	@Autowired
	private RabbitConfiguration rabbitConfiguration;

    @Autowired
    private MarketdataService marketdataService;

    @Autowired
	private SecurityService securityService;

    @Autowired
	private ChannelPositionService channelPositionService;

    @Autowired
	private ChannelPlacementService channelPlacementService;

	@Autowired
	private ChannelLimitMapper channelLimitMapper;

	@Autowired
	private ChannelApplyMapper channelApplyMapper;

    @Override
    public void saveChannel(Channel record) {
    	if(record.getUpdateTime()==null){
    		record.setUpdateTime(new Date());
    	}
		if(record.getCreateTime()==null){
			record.setCreateTime(new Date());
		}
        if (record.getTradeMinCost() == null) {
            record.setTradeMinCost(new BigDecimal(5));
        }
        if(record.getId()==null){
			if(record.getCashAvailable()==null){
				record.setCashAvailable(BigDecimal.ZERO);
			}
        	channelMapper.insert(record);
    	}else{
    		channelMapper.updateBySelective(record);
    	}
        
    }

    @Override
    public Page getChannelByPage(Page page, ChannelParams channelParams) {
    	 List<Channel> data= channelMapper.selectChannelByPage(page, channelParams);
    	 for (Channel channel : data){
    	 	List<ChannelLimit> channelLimits = findChannelLimit(channel.getId(),channel.getCustomerId());
    	 	if (channelLimits.size()==0) continue;
    	 	double highLimit = 0.0;
    	 	for (ChannelLimit channelLimit : channelLimits){
				if (highLimit < channelLimit.getLimitRatio().doubleValue()){
					highLimit = channelLimit.getLimitRatio().doubleValue();
				}
			}
			channel.setHighSinglePositionLimit(highLimit);
		 }
    	 page.setAaData(data);
    	 return page;
    }

    @Override
    public Channel getChannelById(Integer id) {
        return channelMapper.seleChannelById(id);
    }

	@Override
	public List<Channel> selectAll() {
		return channelMapper.selectAll();
	}

	@Override
	public void updateCashAvailable(BigDecimal cashAvailable, String code, BigDecimal totalAssets) {
		if(cashAvailable!=null&&cashAvailable.doubleValue()>=0){
			channelMapper.updateCashAvailable(cashAvailable, code, totalAssets);
		}else{
			channelMapper.updateCashAvailable(BigDecimal.ZERO, code, totalAssets);
		}
	}

	@Override
	public List<Channel> selectChannelByUserId(Integer userId) {
		return channelMapper.selectChannelByUserId(userId);
	}

	@Override
	public List<Channel> selectChannelsBuCustomerId(Integer customerId) {
		return channelMapper.selectChannelsByCustomerId(customerId);
	}

	@Override
	public List<Channel> selectChanelsByCustomerIds(Integer[] customerIds) {
		return channelMapper.selectChanelsByCustomerIds(customerIds);
	}

	@Override
	public List<Channel> findByCustomerIdNotInUserId(Integer customerId, Integer userId) {
		return channelMapper.selectChannelsByCustomerIdNotInUserId(customerId,userId);
	}

	@Override
	public Page findUnchecked_AM(Page page) {
    	List<Channel> channels = channelMapper.selectUnchecked_AM(page);
    	page.setAaData(channels);
		return page;
	}

	@Override
	public String addMQ(String code, String channelType){
		String queues = "elf.execution.manual.placement.queue.";
		String[] exchanges = {"elf.execution.manual.buy.broker.exchange",
				"elf.execution.manual.sell.broker.exchange",
				"elf.execution.manual.cancel.broker.exchange",
		};
//		String cashString = "elf.execution.manual.cash.position.broker.exchange";
		String queueDlq="elf.execution.manual.DLQ";
		String exchangeDlq="elf.execution.manual.DLX";

		RabbitAdmin rabbitAdmin = rabbitConfiguration.getRabbitAdmin();

		String result = "添加失败！";
			if(StringUtils.isNotBlank(code)) {

				org.springframework.amqp.core.Queue queueDLQ = new Queue(queueDlq);
				rabbitAdmin.declareQueue(queueDLQ);
				Binding bindingDLQ = BindingBuilder.bind(queueDLQ).to(new FanoutExchange(exchangeDlq));
				rabbitAdmin.declareBinding(bindingDLQ);

				String queueId = queues + code;
				Map<String, Object> args = new HashMap<String, Object>();
				args.put("x-dead-letter-exchange", exchangeDlq);
				args.put("x-message-ttl", 30000);
				Queue queue = new Queue(queueId, true, false, false, args);
				rabbitAdmin.declareQueue(queue);

				for (int i = 0; i < exchanges.length; i++) {
					Binding binding = BindingBuilder.bind(queue).to(new DirectExchange(exchanges[i])).with(code);
					rabbitAdmin.declareBinding(binding);
				}

//				if (channelType.equals("0")) {
//					Binding binding = BindingBuilder.bind(queue).to(new DirectExchange(cashString)).with(code);
//					rabbitAdmin.declareBinding(binding);
//				}
				result = "success";
			}
    	return result;
	}

	@Override
	public boolean verifyCode(String code) {
		return channelMapper.verifyCode(code);
	}

    @Override
    public List<BuyLimitChannel> selectBuyLimitChannels(Integer userId, String securityCode) {
        String internalSecurityId = SecurityUtil.getInternalSecurityIdBySecurityCode(securityCode);
        List<BuyLimitChannel> list = channelMapper.selectBuyLimitChannels(userId, securityCode, internalSecurityId);
        if (list != null && list.size() != 0) {
            BigDecimal price = marketdataService.getLatestPrice(securityCode);
            for (BuyLimitChannel channel : list) {
                channel.setPrice(price);
            }
        }
        return list;
    }

	@Override
	public List<ChannelLimit> findChannelLimitByChannelId(Integer channelId) {
		return channelLimitMapper.selectByChannelId(channelId);
	}

	@Override
	public List<ChannelLimit> findChannelLimit(Integer channelId,Integer customerId) {
		List<ChannelLimit> channelLimits = channelLimitMapper.selectByChannelId(channelId);
		List<ChannelLimit> channelLimitList = new ArrayList<>();
		for (ChannelLimit channelLimit : channelLimits){
			String internalSecurityId = SecurityUtil.getInternalSecurityIdBySecurityCode(channelLimit.getSecurityCode());
			//查询现价-->计算总市值
			MarketDataSnapshot shot=marketdataService.getBySymbol(channelLimit.getSecurityCode());
			if (shot==null) continue;
			List<SecurityPositionVO> securityPositionVOS = channelPositionService.findByCustomerIdAndInternalId(
					new Page<SecurityPositionVO>(),new PositionParams(internalSecurityId,channelId,customerId)).getAaData();
			BigDecimal secAmount = BigDecimal.ZERO;
			for (SecurityPositionVO securityPositionVO : securityPositionVOS){
				secAmount = secAmount.add(securityPositionVO.getAmount());
			}
			channelLimit.setPositionSummaryAmount(secAmount.multiply(new BigDecimal(shot.getPrice())));
			//计算未成交总额
			List<ChannelPlacement> channelPlacementList = channelPlacementService.selectChannelPlacement(
					new ChannelPlacementParams(channelLimit.getSecurityCode(), TradeSide.BUY.getName(),channelId,customerId));
			BigDecimal notTradedAmount = BigDecimal.ZERO;
			for (ChannelPlacement channelPlacement : channelPlacementList){
				if (channelPlacement.getStatus().getNum() != TradeStatus.SENDACK.getNum() &&
						channelPlacement.getStatus().getNum() != TradeStatus.PARTIALLYFILLED.getNum() &&
						channelPlacement.getStatus().getNum() != TradeStatus.CANCELLING.getNum()) continue;		//过滤终态数据
				notTradedAmount = notTradedAmount.add(channelPlacement.getQuantity().subtract(channelPlacement.getFillQuantity()).multiply(channelPlacement.getPrice()));
			}
			channelLimit.setNotTradedAmount(notTradedAmount);
			BigDecimal limitRatio = BigDecimal.ZERO;
			if (channelLimit.getAmount().compareTo(BigDecimal.ZERO)==1&&channelLimit.getAmount()!=null){
				limitRatio = BigDecimalUtil.divide(channelLimit.getPositionSummaryAmount().add(notTradedAmount),channelLimit.getAmount());
			}
			channelLimit.setLimitRatio(limitRatio);
			channelLimitList.add(channelLimit);
		}
		return channelLimitList;
	}

	@Override
    public boolean updateChannelLimit(ChannelLimit channelLimit) {
        return channelLimitMapper.update(channelLimit);
    }

    @Override
    public boolean deleteChannelLimit(Integer id) {
        return channelLimitMapper.deleteChannelLimit(id);
    }

	@Override
	public boolean addChannelLimit(ChannelLimit channelLimit) {

		return channelLimitMapper.insert(channelLimit);
	}

	@Override
	public boolean addChannelApply(ChannelApply channelApply) {
		return channelApplyMapper.save(channelApply);
	}

	@Override
	public boolean updateApplyStatus(String status,String adminNotes, Integer applyId) {
		return channelApplyMapper.updateStatus(status,adminNotes,applyId);
	}

	@Override
	public Page findChannelApplyByPage(Page page, String status, Integer customerId){
		List<ChannelApply> data = channelApplyMapper.queryByStatus(status,customerId);
		page.setAaData(data);
		return page;

	}

	@Override
	public Channel getChannelByAccount(String account) {
		return channelMapper.selectChannelByAccount(account);
	}

}