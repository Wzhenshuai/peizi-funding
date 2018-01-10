package com.icaopan.trade.service.impl;

import com.icaopan.common.util.PlacementUtil;
import com.icaopan.common.util.SecurityUtil;
import com.icaopan.common.util.TradeFeeUtil;
import com.icaopan.customer.model.Customer;
import com.icaopan.customer.service.ChannelService;
import com.icaopan.customer.dao.ChannelMapper;
import com.icaopan.customer.model.Channel;
import com.icaopan.customer.service.CustomerService;
import com.icaopan.ems.service.EmsService;
import com.icaopan.enums.enumBean.TradeFeeConsts;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.enums.enumBean.TradeStatus;
import com.icaopan.enums.enumBean.TransactionType;
import com.icaopan.log.LogUtil;
import com.icaopan.log.annocation.TradeLogAnnocation;
import com.icaopan.marketdata.market.MarketdataService;
import com.icaopan.task.mytask.service.impl.TaskSwitch;
import com.icaopan.trade.bean.ChannelForEmsParameter;
import com.icaopan.trade.bean.ChannelPlacemenHistoryParams;
import com.icaopan.trade.bean.ChannelPlacementParams;
import com.icaopan.trade.dao.ChannelPlacementHistoryMapper;
import com.icaopan.trade.dao.ChannelPlacementMapper;
import com.icaopan.trade.model.ChannelPlacement;
import com.icaopan.trade.model.ChannelPlacementHistory;
import com.icaopan.trade.model.Placement;
import com.icaopan.trade.service.ChannelPlacementService;
import com.icaopan.trade.service.FillService;
import com.icaopan.trade.service.PlacementService;
import com.icaopan.trade.service.dto.TradeFeeParam;
import com.icaopan.trade.service.dto.TradeFeeResult;
import com.icaopan.user.dao.ChannelSecurityPositionMapper;
import com.icaopan.user.model.ChannelSecurityPosition;
import com.icaopan.user.model.User;
import com.icaopan.user.service.ChannelPositionService;
import com.icaopan.user.service.UserChannelService;
import com.icaopan.user.service.UserService;
import com.icaopan.util.BigDecimalUtil;
import com.icaopan.util.DateUtils;
import com.icaopan.util.page.Page;







import elf.api.marketdata.marketdata.MarketDataSnapshot;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("channelPlacementService")
public class ChannelPlacementServiceImpl implements ChannelPlacementService {
    private Logger logger = LogUtil.getLogger(getClass());
    @Autowired
    private ChannelPlacementMapper channelPlacementMapper;

    @Autowired
    private ChannelPlacementHistoryMapper channelPlacementHistoryMapper;

    @Autowired
    private ChannelSecurityPositionMapper channelSecurityPositionMapper;

    @Autowired
    private PlacementService placementService;

    @Autowired
    private ChannelPositionService channelPositionService;

    @Autowired
    private EmsService emsService;

    @Autowired
    private UserChannelService userChannelService;

    @Autowired
    private MarketdataService marketdataService;

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private ChannelService channelService;
    
    @Autowired
    private FillService fillService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;
    
    
    @Override
    @TradeLogAnnocation(tag="通道委托")
    public void placement(ChannelPlacement record) {
        //1. 先将委托记录记录在数据库
        channelPlacementMapper.insert(record);
        //2.判断委托方向
        ChannelSecurityPosition position = null;
        switch (record.getSide()) {
            case SELL:
                //持仓可用头寸与委托数量比较
                if (StringUtils.isBlank(record.getSecurityCode())) {
                    throw new RuntimeException("委托信息有误");
                }
                String internalSecurityId = SecurityUtil.getInternalSecurityIdBySecurityCode(record.getSecurityCode());
                if (StringUtils.isBlank(internalSecurityId)) {
                    throw new RuntimeException("根据股票编号" + record.getSecurityCode() + "找不到对应的股票ID");
                }
                position = channelPositionService.find(record.getUserId(), record.getChannelId(), internalSecurityId);
                if (position == null) {
                    throw new RuntimeException("查询通道持仓信息失败");
                }
                if ((position.getAvailable().compareTo(record.getQuantity()) >= 0)) {
                    //校验通过，修改对应的证券头寸信息
                    channelPositionService.changeAvailable(position.getId(), record.getQuantity().negate());
                    //调用EMS委托下单
                    emsService.placement(record);
                } else {
                    //对改委托记录进行废单处理
                    sendRejectReportForPB(record.getAccount(), record.getSecurityCode(), record.getPrice(), record.getQuantity(), record.getSide().getName(), record.getPlacementCode(), record.getRejectMessage());
                }
                break;
            case BUY:
                //调用EMS委托下单
                emsService.placement(record);
                break;
            default:
                LogUtil.getLogger(getClass()).error("委托(方向)信息有误");
                throw new RuntimeException("委托(方向)信息有误!");
        }
    }

    @Override
    public void cancelPlacement(Integer placementId) {
        ChannelPlacementParams channelPlacementParams = new ChannelPlacementParams(placementId);
        List<ChannelPlacement> channelPlacementList = selectChannelPlacement(channelPlacementParams);
        for (ChannelPlacement placement : channelPlacementList) {
            //校验：委托状态(已报，部成，正撤),校验通过之后修改委托状态为正撤
            switch (placement.getStatus()) {
                case SENDACK:
                case PARTIALLYFILLED:
                case CANCELLING:
                    channelPlacementMapper.updateStatus(placement.getId(), TradeStatus.CANCELLING, null);
                    //校验通过 调用EMS接口对接(废单接口)
                    emsService.cancel(placement);        //对委托记录里面标记的placementId的记录，都进行撤单处理
                    break;
                default:
                    continue;
            }
        }
    }


    @Override
    public Page getChannelPlacementByPage(Page page, ChannelPlacementParams channelPlacementParams) {
        List<ChannelPlacement> list;
        if (page == null) {
            Page page1 = new Page();
            list = channelPlacementMapper.selectChannelPlacementByPage(null, channelPlacementParams);
            page = page1;
        } else {
            list = channelPlacementMapper.selectChannelPlacementByPage(page, channelPlacementParams);
        }
        for (ChannelPlacement placement : list) {
            //查询现价
            BigDecimal latestPrice = marketdataService.getLatestPrice(placement.getSecurityCode());
            placement.setLatestPrice(latestPrice);
            //placement.setSecurityName(marketdataService.getSecurityName(placement.getSecurityCode()));
        }
        page.setAaData(list);
        return page;
    }

    @Override
    public List<ChannelPlacement> getAllForEms(ChannelForEmsParameter channelForEmsParameter) {
        return channelPlacementMapper.selectAllForEms(channelForEmsParameter);
    }

    @Override
    public boolean updateStatus(Integer id, TradeStatus status, String errorMessage) {
        return channelPlacementMapper.updateStatus(id, status, errorMessage);
    }

    @Override
    public boolean updateQuantityAndAmount(Integer id, BigDecimal changeQuantity, BigDecimal changeAmount) {

        return channelPlacementMapper.updateQuantityAndAmount(id, changeQuantity, changeAmount);
    }

    @Override
    public boolean fillRejectMessage(String id, String rejectMessage) {
        return channelPlacementMapper.fillRejectMessage(id, rejectMessage);
    }

    @Override
    public boolean saveChannelPlacementHistory(ChannelPlacementHistory record) {

        return channelPlacementHistoryMapper.insert(record);
    }

    @Override
    public Page getChannelPlacementHistoryByPage(Page page, ChannelPlacemenHistoryParams channelPlacemenHistoryParams) {
        if (page == null) {
            Page page1 = new Page();
            page1.setAaData(channelPlacementHistoryMapper.selectByPage(null, channelPlacemenHistoryParams));
            page = page1;
        } else {
            page.setAaData(channelPlacementHistoryMapper.selectByPage(page, channelPlacemenHistoryParams));
        }
        return page;
    }

    @Override
    public void fillPlacementCode(Integer id, String placementCode) {
        channelPlacementMapper.fillPlacementCode(id, placementCode);
    }

    @Override
    public boolean deletePlacementToday() {
        return channelPlacementMapper.deletePlacementToday();
    }

    @Override
    public List<ChannelPlacement> selectChannelPlacement(ChannelPlacementParams channelPlacementParams) {
        return channelPlacementMapper.selectChannelPlacement(channelPlacementParams);
    }
    
    @Override
    public List<ChannelPlacement> selectNotDockChannelPlacement(ChannelPlacementParams channelPlacementParams) {
        return channelPlacementMapper.selectNotDockChannelPlacement(channelPlacementParams);
    }


    /**
     * 自动废单处理，根据检索委托记录的条件检索出对应的委托记录，并对其进行废单处理
     *
     * @param account
     * @param symbol
     * @param price
     * @param quantity
     * @param side
     * @param executionSno
     * @param errorMessage
     */
    public void sendRejectReportForPB(String account, String symbol, BigDecimal price, BigDecimal quantity, String side, String executionSno, String errorMessage) {
        ChannelPlacementParams params = new ChannelPlacementParams();
        params.setAccount(account);
        params.setSecurityCode(symbol);
        params.setPrice(price);
        params.setQuantity(quantity);
        if (side.equals("1")) {
        	params.setSide(TradeSide.BUY.getName());
        } else if (side.equals("2")) {
        	params.setSide(TradeSide.SELL.getName());
        } else {
            throw new RuntimeException("委托对接委托方向解析出错");
        }
        //params.setStatus(TradeStatus.SENDACK.getName());
        ChannelPlacement channelPlacement = findDockChannelPlacement(params);
        if (channelPlacement != null) {
        	//填写委托编号
        	fillPlacementCode(channelPlacement.getId(), System.currentTimeMillis()+"");
        	//废单处理
            fill(channelPlacement, TransactionType.INVALID, quantity, price, errorMessage,false);
        }
    }

    /**
     * 普通成交
     * 1.更新通道委托成交数量,成交价格,成交金额
     * 2.更新通道委托状态(已报->部成,部成->已成)
     * 3.更新用户证券持仓的总头寸[quantity]及amount
     * 3.1买入:amount+quantity,可用不变
     * 3.2卖出:amount-quantity,可用不变
     * 4.调用用户委托的fill接口(普通成交,价格,数量)
     * 撤单成交
     * 2.更新通道委托状态为已撤
     * 3.更新用户证券持仓的可用及amount
     * 3.1买入:amount不变,可用不变
     * 3.2卖出:amount不变,可用+quantity
     * 4.调用用户委托的fill接口(撤单成交,价格,数量)
     * 废单成交
     * 2.更新通道委托状态为废单
     * 3.更新用户证券持仓的可用及amount
     * 3.1买入:amount不变,可用不变
     * 3.2卖出:amount不变,可用+quantity
     * 4.调用用户委托的fill接口(废单成交,价格,数量)
     *
     * @param placement
     * @param placement
     * @param transactionType 类型：普通(NORMAL)成交  撤单(CANCELED)成交  废单(INVALID)成交
     * @param quantity
     * @param price
     */
    public void fill(ChannelPlacement placement, TransactionType transactionType, BigDecimal quantity, BigDecimal price, String errorMessage,boolean isForceFill) {
        if (StringUtils.isBlank(placement.getSecurityCode())) {
            throw new RuntimeException("委托信息有误");
        }
        String internalSecurityId = SecurityUtil.getInternalSecurityIdBySecurityCode(placement.getSecurityCode());
        if (internalSecurityId == null || internalSecurityId.length() == 0) {
            throw new RuntimeException("根据股票编号" + placement.getSecurityCode() + "找不到对应的股票ID");
        }
        ChannelSecurityPosition position = channelPositionService.find(placement.getUserId(), placement.getChannelId(), internalSecurityId);
        if (position == null) {
            position = channelPositionService.initialPosition(placement);
        }
        TradeStatus statusOld=placement.getStatus();
        switch (transactionType) {
            case NORMAL:
                BigDecimal amount = BigDecimalUtil.multiply(quantity, price);
                // 更新通道委托成交数量,成交价格,成交金额
                channelPlacementMapper.updateQuantityAndAmount(placement.getId(), quantity, amount);
                // 更新通道委托状态(已报->部成,部成->已成)
                logger.info("check status:" + placement.getStatus().getName() +",placementId:"+placement.getId()+ ",code:" + placement.getSecurityCode() + ",quantity:" + placement.getQuantity() +
                        ",fillQuantity:" + placement.getFillQuantity() + ",eventQuantity:" + quantity + ",compare:" + placement.getQuantity().compareTo(quantity.add(placement.getFillQuantity())));

                if (StringUtils.equals(placement.getStatus().getName(), TradeStatus.SENDACK.getName())) { //已报状态
                    if (placement.getQuantity().compareTo(quantity.add(placement.getFillQuantity())) < 0) {
                        //委托数量小于（成交数量+已成数量） --- > 报错，抛异常
                        throw new RuntimeException("错误:成交数量超出委托数量!");
                    }
                } else {
                    if (placement.getQuantity().compareTo(quantity.add(placement.getFillQuantity())) < 0) { //委托数量小于（成交数量+已成数量），抛异常， 当委托数量大于成交数量，状态不变，依然是部成状态
                        throw new RuntimeException("错误:成交数量超出委托数量!");
                    }
                }
                // 更新用户证券持仓的总头寸[quantity]及amount
                switch (placement.getSide()) {
                    case BUY:
                        channelPositionService.changeAmountAndCostPrice(position.getId(), quantity, price);
                        // 更新用户的通道限额，普通买入成交，若成交价不等于委托价，增加差价的限额 许昊旻 2017.6.10
                        BigDecimal deltaPrice = BigDecimalUtil.minus(placement.getPrice(), price);
                        if (deltaPrice.doubleValue() != 0) {
                            userChannelService.addUserChannelQuota(placement.getUserId(), placement.getChannelId(), BigDecimalUtil.multiply(quantity, deltaPrice));
                        }
                        break;
                    case SELL:
                    	//判断可用是否足够
                    	if(position.getAmount()!=null&&position.getAmount().doubleValue()<quantity.doubleValue()){
                    		throw new RuntimeException("通道可卖数量不足");
                    	}
                    	if(!PlacementUtil.isFinished(statusOld)){
                    		channelPositionService.changeAmountAndCostPrice(position.getId(), quantity.negate(), price);
                    	}else{
                    		channelPositionService.changeAmountAndCostPrice(position.getId(), quantity.negate(), price);
                    		channelPositionService.changeAvailable(position.getId(), quantity.negate());
                    	}
                        // 更新用户的通道限额，普通卖出成交限额增加 许昊旻 2017.6.10
                        userChannelService.addUserChannelQuota(placement.getUserId(), placement.getChannelId(), amount);
                        break;
                }
                //后续计算佣金用 2017.09.18 xuhaomin
                placement = channelPlacementMapper.selectById(placement.getId());
                break;
            case CANCELLED:
            	if (placement == null || PlacementUtil.isFinished(placement.getStatus())) {
                    throw new RuntimeException("该委托状态已经为终态");
                }
                //更新通道委托状态为已撤
                TradeStatus status = TradeStatus.CANCELLED;
                if(placement.getQuantity().compareTo(placement.getFillQuantity())==0){//考虑并发情况下会造成全部成交但是没有将状态更改为已成，需要手工废单，这种情况下的撤单操作状态应该改为已成
                    status = TradeStatus.FILLED;
                }
                channelPlacementMapper.updateStatus(placement.getId(), status, errorMessage);
                dealCancelledOrInvalidFill(placement, position, quantity);
                break;
            case INVALID:
            	if (placement == null || PlacementUtil.isFinished(placement.getStatus())) {
                    throw new RuntimeException("该委托状态已经为终态");
                }
                //更新通道委托状态为废单
                status = TradeStatus.INVALID;
                if(placement.getQuantity().compareTo(placement.getFillQuantity())==0){//考虑并发情况下会造成全部成交但是没有将状态更改为已成，需要手工废单，这种情况下的废单操作状态应该改为已成
                    status = TradeStatus.FILLED;
                }
                channelPlacementMapper.updateStatus(placement.getId(), status, errorMessage);
                dealCancelledOrInvalidFill(placement, position, quantity);
                break;
        }
        //增加佣金等成本价 2017.09.18 xuhaomin
        if(PlacementUtil.isFinished(placement.getStatus())){
            Placement userPlacement = placementService.getPlacementById(placement.getPlacementId());
            TradeFeeParam tradeFeeParam = new TradeFeeParam(placement.getSecurityCode(), placement.getSide(), placement.getFillPrice(), userPlacement.getIsSzTransferFee(), placement.getFillQuantity(), userPlacement.getRatioFee(), userPlacement.getMinCost());
            TradeFeeResult tradeFee = TradeFeeUtil.getFeeByParam(tradeFeeParam);
            position = channelPositionService.find(placement.getUserId(), placement.getChannelId(), internalSecurityId);
            if (position.getAmount().intValue() > 0) {//卖完的时候补更新成本价，只更新总成本
                BigDecimal _costPrice = BigDecimalUtil.add(BigDecimalUtil.divide(tradeFee.getTotalFee(), position.getAmount()), position.getCostPrice());
                channelSecurityPositionMapper.updateAmountAndCostPrice(position.getId(), null, _costPrice);
            } else {
                channelSecurityPositionMapper.updateTotalCost(position.getId(), tradeFee.getTotalFee());
            }
        }

        //调用用户委托的fill接口(成交类型,价格,数量)
        placementService.fill(placement.getPlacementId(), transactionType, quantity, price,isForceFill);

    }

    /**
     * 撤单或废单回报时，处理头寸和通道限额
     *
     * @param placement
     * @param position
     * @param quantity
     */
    private void dealCancelledOrInvalidFill(ChannelPlacement placement, ChannelSecurityPosition position, BigDecimal quantity) {
        //更新用户证券持仓的可用及amount
        if (StringUtils.equals(placement.getSide().getName(), TradeSide.SELL.getName())) {  //卖出的情景，如果是买入的情况，amount available都保存不变，所以不用处理
            channelPositionService.changeAvailable(position.getId(), quantity); //卖出:amount不变,可用+quantity
        } else {
            // 更新用户的通道限额，撤单或废单买入成交限额增加 许昊旻 2017.6.10
            userChannelService.addUserChannelQuota(placement.getUserId(), placement.getChannelId(), BigDecimalUtil.multiply(quantity, placement.getPrice()));
        }
    }

    /**
     * 根据委托编号查找当日委托记录
     */
    @Override
    public ChannelPlacement getByPlacementNo(String account,
                                             String placementNo) {
        List<ChannelPlacement> list = channelPlacementMapper.selectByPlacementNo(account, placementNo);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查找未对接的通道委托
     */
    @Override
    public ChannelPlacement findDockChannelPlacement(ChannelPlacementParams params) {
        ChannelPlacement channelPlacement = null;
        List<ChannelPlacement> channelPlacements = selectNotDockChannelPlacement(params);
        if (channelPlacements.size() == 1) {
            channelPlacement = channelPlacements.get(0);
        } else {
            for (ChannelPlacement cp : channelPlacements) {
                if (cp.getPlacementCode() == null) {
                    channelPlacement = cp;
                    break;
                }
            }
        }
        return channelPlacement;
    }
    
    /**
     * 自动成交
     */
    @Override
    public void autoFill(){
    	if(TaskSwitch.autoFill==false){
    		return;
    	}
    	if(!DateUtils.isTimeInTrade()){
    		return;
    	}
    	ChannelPlacementParams params=new ChannelPlacementParams();
    	List<ChannelPlacement> channelPlacements = selectNotDockChannelPlacement(params);
    	//查找出需要自动成交的通道
    	for (ChannelPlacement channelPlacement : channelPlacements) {
    		try {
    			if(channelPlacement.getStatus()==TradeStatus.SENDACK){
        			Integer channelId=channelPlacement.getChannelId();
            		if(channelId!=null){
            			Channel channel=channelService.getChannelById(channelId);
                		if(channel!=null){
                			boolean autoFill=channel.getAutoFill();
                			if(autoFill){
                				//判断价格是否达到现价
                				BigDecimal _pltPrice=channelPlacement.getPrice();
                				double pltPrice=_pltPrice.doubleValue();
                				//查询现价
                				MarketDataSnapshot shot=marketdataService.getBySymbol(channelPlacement.getSecurityCode());
                				if(shot==null){
                					continue;
                				}
                				double curtPrice=shot.getPrice();
                				double b1Price=shot.getBidPrice1();
                				double s1Price=shot.getAskPrice1();
                				double limitDown=shot.getLimitDown();
                				double limitUp=shot.getLimitUp();
                				//判断买卖方向
                				TradeSide side=channelPlacement.getSide();
                				String reportNo=System.currentTimeMillis()+"";
                				BigDecimal quantity=channelPlacement.getQuantity();
                				if(side==TradeSide.BUY){
                					if(pltPrice>=limitUp||curtPrice>=limitUp){
                						continue;
                					}
                					if(pltPrice>=b1Price){
                						//填写委托编号
                			        	fillPlacementCode(channelPlacement.getId(), "-1");
                						//插入成交记录
                						BigDecimal price=new BigDecimal(curtPrice+"");
                        	            fillService.saveFill(channelPlacement, reportNo, quantity, price);
                        	            fill(channelPlacement, TransactionType.NORMAL, quantity, price, "自动成交",false);
                					}
                					
                				}else if(side==TradeSide.SELL){
                					if(pltPrice<=limitDown||curtPrice<=limitDown){
                						continue;
                					}
                					if(pltPrice<=s1Price){
                						//填写委托编号
                			        	fillPlacementCode(channelPlacement.getId(), "-1");
                						//插入成交记录
                						BigDecimal price=new BigDecimal(curtPrice+"");
                        	            fillService.saveFill(channelPlacement, reportNo, quantity, price);
                        	            fill(channelPlacement, TransactionType.NORMAL, quantity, price, "自动成交",false);
                					}
                				}
                				Thread.sleep(10);
                			}
                		}
            		}
        		}
			} catch (Exception e) {
				e.printStackTrace();
			}
    		
		}
    }

    @Override
    public ChannelPlacement getById(Integer id) {
        return channelPlacementMapper.selectById(id);
    }

    @Override
    public List<ChannelPlacement> selectPlacementByStatus(List<String> statusList) {
        return channelPlacementMapper.selectPlacementByStatus(statusList);
    }

    @Override
    public boolean generateHistoryPlacement() {
        List<ChannelPlacement> channelPlacementList = channelPlacementMapper.selectChannelPlacement(null);
        List<ChannelPlacementHistory> channelPlacementHistoryList = new ArrayList<>();
        for (ChannelPlacement channelPlacement : channelPlacementList) {
            User user = userService.findUserById(channelPlacement.getUserId());
            Channel channel = channelMapper.seleChannelById(channelPlacement.getChannelId());
            ChannelPlacementHistory channelPlacementHistory = new ChannelPlacementHistory();
            channelPlacementHistory.setAccount(channelPlacement.getAccount());
            channelPlacementHistory.setAmount(channelPlacement.getAmount());
            channelPlacementHistory.setChannelId(channelPlacement.getChannelId());
            channelPlacementHistory.setChannelName(channelPlacement.getChannelName());
            channelPlacementHistory.setChannelPlacementId(channelPlacement.getPlacementId());
            channelPlacementHistory.setCustomerId(channelPlacement.getCustomerId());
            channelPlacementHistory.setCustomerName(channelPlacement.getCustomerName());
            channelPlacementHistory.setDateTime(channelPlacement.getCreateTime());
            channelPlacementHistory.setFillAmount(channelPlacement.getFillAmount());
            channelPlacementHistory.setFillPrice(channelPlacement.getFillPrice());
            channelPlacementHistory.setFillQuantity(channelPlacement.getFillQuantity());
            channelPlacementHistory.setPlacementCode(channelPlacement.getPlacementCode());
            channelPlacementHistory.setPlacementId(channelPlacement.getPlacementId());
            channelPlacementHistory.setPrice(channelPlacement.getPrice());
            channelPlacementHistory.setQuantity(channelPlacement.getQuantity());
            channelPlacementHistory.setRejectMessage(channelPlacement.getRejectMessage());
            channelPlacementHistory.setSecurityCode(channelPlacement.getSecurityCode());
            channelPlacementHistory.setSide(channelPlacement.getSide());
            channelPlacementHistory.setStatus(channelPlacement.getStatus());

            channelPlacementHistory.setUserId(channelPlacement.getUserId());
            channelPlacementHistory.setUserName(channelPlacement.getUserName());
            if (BigDecimalUtil.compareTo(BigDecimal.ZERO, channelPlacement.getFillQuantity()) == 0) {
                channelPlacementHistory.setSysCommissionFee(new BigDecimal(0));
                channelPlacementHistory.setTradeCommissionFee(new BigDecimal(0));
            } else {
                if ("1".equals(user.getIsDefaultFee())) {
                    Customer customer = customerService.getCustomerById(channelPlacement.getCustomerId());
                    channelPlacementHistory.setSysCommissionFee(TradeFeeUtil.getCommission(channelPlacement.getFillAmount(), customer.getDefaultTatio(), customer.getDefaultMinCost()));
                } else {
                    channelPlacementHistory.setSysCommissionFee(TradeFeeUtil.getCommission(channelPlacement.getFillAmount(), user.getRatioFee(), user.getMinCost()));
                }
                channelPlacementHistory.setTradeCommissionFee(TradeFeeUtil.getCommission(channelPlacement.getFillAmount(), channel.getTradeCommissionRate(), channel.getTradeMinCost()));
            }
            channelPlacementHistoryList.add(channelPlacementHistory);
        }
        return channelPlacementHistoryMapper.insertList(channelPlacementHistoryList);
    }

    @Override
    public Page findCommissionCollectBypage(Page page, ChannelPlacemenHistoryParams channelParams) {

        if (page == null) {
            Page page1 = new Page();
            page1.setAaData(channelPlacementHistoryMapper.selectCommissionCollectBypage(null, channelParams));
            page = page1;
        } else {
            page.setAaData(channelPlacementHistoryMapper.selectCommissionCollectBypage(page, channelParams));
        }
        return page;
    }
}
