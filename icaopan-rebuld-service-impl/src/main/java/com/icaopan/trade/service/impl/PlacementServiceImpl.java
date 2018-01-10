package com.icaopan.trade.service.impl;

import static com.icaopan.enums.enumBean.TradeStatus.CANCELLING;
import static com.icaopan.enums.enumBean.TradeStatus.PARTIALLYFILLED;
import static com.icaopan.enums.enumBean.TradeStatus.SENDACK;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icaopan.user.dao.UserSecurityPositionMapper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.icaopan.common.util.ExceptionConstants;
import com.icaopan.common.util.PlacementUtil;
import com.icaopan.common.util.SecurityUtil;
import com.icaopan.common.util.TradeFeeUtil;
import com.icaopan.customer.model.BuyLimitChannel;
import com.icaopan.customer.service.ChannelService;
import com.icaopan.customer.service.CustomerService;
import com.icaopan.ems.service.EmsService;
import com.icaopan.enums.enumBean.RiskResult;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.enums.enumBean.TradeStatus;
import com.icaopan.enums.enumBean.TransactionType;
import com.icaopan.enums.enumBean.UserChannelType;
import com.icaopan.framework.sync.UserFillSyncLock;
import com.icaopan.log.LogUtil;
import com.icaopan.log.annocation.TradeLogAnnocation;
import com.icaopan.marketdata.market.MarketdataService;
import com.icaopan.risk.service.RiskFilterChain;
import com.icaopan.risk.service.RiskService;
import com.icaopan.stock.model.StockSecurity;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.trade.bean.ChannelPlacementParams;
import com.icaopan.trade.bean.PlacementHistoryParams;
import com.icaopan.trade.bean.PlacementParams;
import com.icaopan.trade.dao.PlacementHistoryMapper;
import com.icaopan.trade.dao.PlacementMapper;
import com.icaopan.trade.model.ChannelPlacement;
import com.icaopan.trade.model.Placement;
import com.icaopan.trade.model.PlacementHistory;
import com.icaopan.trade.service.ChannelPlacementService;
import com.icaopan.trade.service.PlacementService;
import com.icaopan.trade.service.dto.PlacementChannelDto;
import com.icaopan.trade.service.dto.TradeFeeParam;
import com.icaopan.trade.service.dto.TradeFeeResult;
import com.icaopan.user.bean.UserPositionParams;
import com.icaopan.user.model.ChannelSecurityPosition;
import com.icaopan.user.model.User;
import com.icaopan.user.model.UserChannel;
import com.icaopan.user.model.UserSecurityPosition;
import com.icaopan.user.service.ChannelPositionService;
import com.icaopan.user.service.UserChannelService;
import com.icaopan.user.service.UserPositionService;
import com.icaopan.user.service.UserService;
import com.icaopan.util.BigDecimalUtil;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.PageBean;
import com.icaopan.web.vo.PlacementVO;

import elf.api.marketdata.marketdata.MarketDataSnapshot;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName PlacementServiceImpl
 * @Description (这里用一句话描述这个类的作用)
 * @Date 2016年12月7日 上午11:08:39
 */
@Service("placementService")
public class PlacementServiceImpl implements PlacementService {

    @Autowired
    PlacementMapper placementMapper;
    @Autowired
    SecurityService securityService;
    @Autowired
    PlacementHistoryMapper placementHistoryMapper;
    @Autowired
    UserSecurityPositionMapper userSecurityPositionMapper;
    @Autowired
    UserService userService;
    @Autowired
    CustomerService customerService;
    @Autowired
    ChannelPlacementService channelPlacementService;
    @Autowired
    PlacementConvertServiceImpl placementConvertServiceImpl;
    @Autowired
    ChannelPositionService positionService;
    @Autowired
    ChannelService channelService;
    @Autowired
    UserChannelService userChannelService;
    @Autowired
    UserPositionService userPositionService;
    @Autowired
    @Qualifier("openRiskService")
    RiskService openRiskService;
    @Autowired
    @Qualifier("warningRiskService")
    RiskService warningRiskService;
    @Autowired
    @Qualifier("proportionRiskService")
    RiskService proportionRiskService;
    @Autowired
    @Qualifier("bannedStockRiskService")
    RiskService bannedStockRiskService;
    @Autowired
    @Qualifier("amountQuantityQuotaStockRiskService")
    RiskService amountQuantityQuotaStockRiskService;
    @Autowired
    @Qualifier("amplitudeRiskService")
    RiskService amplitudeRiskService;
    @Autowired
    @Qualifier("blacklistedStockRiskService")
    RiskService blacklistedStockRiskService;
    @Autowired
    @Qualifier("whitelistedStockRiskService")
    RiskService whitelistedStockRiskService;
    @Autowired
    UserFillSyncLock userFillSyncLock;
    @Autowired
    EmsService emsService;
    @Autowired
    private MarketdataService marketdataService;
    
    private Logger logger = LogUtil.getLogger(getClass());

    /**
     * @param placement
     * @return
     * @Description (插入用户委托)
     * 1、校验
     * 1.1参数信息的校验（价格、数量  格式校验，证券代码的校验-是否存在）
     * 1.2 业务逻辑校验
     * 1.2.1 买入 ：资金头寸的可用是否大于当前委托的金额+佣金（需要计算）
     * 1.2.2 卖出 ：通道证券头寸的可用之和 是否大于当前委托数量
     * 1.3 调用风控接口校验
     * 2、 校验失败 抛异常
     * 3、 校验成功，保存用户委托
     * 4、 更改用户资金头寸
     * 买入 ：减用户的资金可用头寸（当前委托的金额+佣金）
     * 卖出 ：无操作
     * 5、 调用通道委托下单接口
     * 5.1 买入：直接调用通道委托下单接口
     * 5.2 卖出：
     * 5.2.1 （定义卖出 通道选择规则[通道分配数量]）{1、考虑有碎股的情况}
     * 5.2.2 调用5.2.1的规则结果调用通道委托下单接口
     */

    @Override
    @TradeLogAnnocation(tag="用户委托")
    public void placement(Placement placement) {
        BigDecimal price = placement.getPrice();
        String securityCode = placement.getSecurityCode();
        BigDecimal quantity = placement.getQuantity();
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException(ExceptionConstants.PLACEMENT_PRICE_ERROR);
        }
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0 || quantity.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) != 0) {
            throw new RuntimeException(ExceptionConstants.PLACEMENT_QUANTITY_ERROR);
        }
        if (StringUtils.isEmpty(securityCode) || !securityService.containCode(securityCode)) {
            throw new RuntimeException(ExceptionConstants.SECURITY_NOT_EXIST);
        }
        User user = userService.findUserById(placement.getUserId());
        if (userService.isBuySellLocked(user)){ //检查用户状态，判断用户判断锁定状态  author royleong@2017-08-16
            throw new RuntimeException(ExceptionConstants.USER_BUY_SELL_LOCKED);
        }else if (TradeSide.BUY.getCode().equals(placement.getSide().getCode())&&userService.isBuyLocked(user)){
            throw new RuntimeException(ExceptionConstants.USER_BUY_LOCKED);
        }else if (userService.isLogOut(user)){
            throw new RuntimeException(ExceptionConstants.USER_LOGOUT);
        }
        placement = placementConvertServiceImpl.reWrapPlacement(placement, user);
        StockSecurity security = securityService.findByNameAndCode(null, securityCode);
        placement.setSecurityId(security.getInternalSecurityId());
        BigDecimal fee = TradeFeeUtil.getFee(placement);
        BigDecimal amount = BigDecimalUtil.multiplyScale4(quantity, price);
        BigDecimal summaryAmount = BigDecimalUtil.add(fee, amount);
        placement.setAmount(summaryAmount);
        //调用风控接口
        RiskFilterChain chain = new RiskFilterChain();
        //平仓线风控
        RiskResult result = chain.addFilter(openRiskService)
                //警告线风控
                .addFilter(warningRiskService)
                //禁买股风控
                .addFilter(bannedStockRiskService)
                //比例风控
                .addFilter(proportionRiskService)
                //用户单笔金额、数量上限风控
                .addFilter(amountQuantityQuotaStockRiskService)
                //昨收涨跌停股票振幅风控
                .addFilter(amplitudeRiskService)
                //股票黑名单风控
                .addFilter(blacklistedStockRiskService)
                //股票白名单风控
                .addFilter(whitelistedStockRiskService)
                //执行风控过滤
                .doRiskFilter(user, placement, chain);
        if (!StringUtils.equals(RiskResult.Success.getCode(), result.getCode())) {
            throw new RuntimeException(result.getDisplay());
        }
        //校验通过 保存用户 委托
        placementMapper.insert(placement);
        switch (placement.getSide()) {
            case BUY:
                List<BuyLimitChannel> channelList = channelService.selectBuyLimitChannels(user.getId(), placement.getSecurityCode());
                //用于通道上限额情况下，减去用户买入资金 
                // 许昊旻 2017.6.9
                Map<Integer, UserChannel> limitedChannelMap = new HashMap<Integer, UserChannel>();

                if (channelList == null || channelList.isEmpty()) {
                    throw new RuntimeException(ExceptionConstants.CHANNEL_NOT_EXIST);
                }

                for (BuyLimitChannel channel : channelList) {
                    if (StringUtils.equals(channel.getUserChannelTypeVal(), UserChannelType.LIMITED.getCode())) {
                        UserChannel userChannel = new UserChannel(user.getId(), channel.getId(), channel.getPriorityLevel(), channel.getQuota(), UserChannelType.LIMITED);
                        userChannel.setId(channel.getId());
                        limitedChannelMap.put(channel.getId(), userChannel);
                    }
                }

                List<PlacementChannelDto> placementChannelList = placementConvertServiceImpl.generateBuyPlacementChannel(user, channelList, placement);
                //更新用户资金头寸
                userService.updateAvailableAndTotalAmount(user.getId(), summaryAmount.negate(), BigDecimal.ZERO);
                //下单买入 调用通道委托的下单
                if (placementChannelList != null && !placementChannelList.isEmpty()) {
                    for (PlacementChannelDto placementChannelDto : placementChannelList) {
                        placement.setChannelId(placementChannelDto.getChannelId());
                        placement.setChannelCode(placementChannelDto.getChannelCode());
                        placement.setQuantity(placementChannelDto.getHandleQuantity());
                        ChannelPlacement channelPlacement = placementConvertServiceImpl.wrapChannelPlacement(placement);
                        channelPlacementService.placement(channelPlacement);
                        //计算通道上用户限额减去的数量
                        //许昊旻 2017.6.9
                        UserChannel userChannel = limitedChannelMap.get(placementChannelDto.getChannelId());
                        if (userChannel != null) {
                            BigDecimal val = BigDecimalUtil.multiplyScale4(channelPlacement.getPrice(), channelPlacement.getQuantity()).negate();
                            userChannelService.addUserChannelQuota(user.getId(), placement.getChannelId(), val);
                        }
                    }
                }

                break;
            case SELL:
                //查询用户可用
                BigDecimal userAvailable = userPositionService.findPositionAvailable(user, placement.getSecurityCode());
                //减去用户可用证券头寸
                UserPositionParams params = new UserPositionParams(user.getId(), security.getInternalSecurityId(), quantity.negate());
                userPositionService.changeAvailable(params);
                //查询可卖的通道
                List<ChannelSecurityPosition> positionList = positionService.findByUserIdSecurityId(user.getId(), placement.getSecurityId(), placement.getCustomerId());
                if (positionList == null || positionList.isEmpty()) {
                    throw new RuntimeException(ExceptionConstants.SECURITY_AVAILABLE_NOT_ENOUGH);
                }

                // 各通道卖多少
                placementChannelList = placementConvertServiceImpl.generateSellPlacementChannel(user.getUserTradeType(), userAvailable, placement, positionList);

                placement.setCustomerId(user.getCustomer().getId());

                for (PlacementChannelDto placementChannelDto : placementChannelList) {
                    placement.setChannelId(placementChannelDto.getChannelId());
                    placement.setQuantity(placementChannelDto.getHandleQuantity());
                    placement.setChannelCode(placementChannelDto.getChannelCode());
                    ChannelPlacement channelPlacement = placementConvertServiceImpl.wrapChannelPlacement(placement);
                    channelPlacementService.placement(channelPlacement);
                }

        }
    }

    //查找对应的通道委托，调用通道委托撤单接口
    @Override
    public void cancelPlacement(Integer placementId) {
        Placement placement = placementMapper.selectById(placementId);
        switch (placement.getStatus()) {
            case SENDACK:
            case PARTIALLYFILLED:
            case CANCELLING:
                channelPlacementService.cancelPlacement(placementId);
                updatePlacementStatus(CANCELLING, placementId);
                break;
            default:
                throw new RuntimeException(ExceptionConstants.CANCEL_FAIL);
        }

    }

    @Override
    public boolean deletePlacementToday() {
        return placementMapper.deletePlacementToday();
    }

    @Override
    public Placement getPlacementById(Integer id) {
        return placementMapper.selectById(id);
    }

    @Override
    public Page<Placement> selectPlacementByPage(Page page, PlacementParams placementParams) {
        if (page == null) {
            page = new Page();
            page.setAaData(placementMapper.placementSelectByPage(null, placementParams));
        } else {
            page.setAaData(placementMapper.placementSelectByPage(page, placementParams));
        }
        return page;
    }

    @Override
    public boolean updatePlacementStatus(TradeStatus tradeStatus, Integer placementId) {
        return placementMapper.updatePlacementStatus(tradeStatus, placementId);
    }

    @Override
    public boolean updatePlaceQuantityAndAmount(BigDecimal changeQuantity, BigDecimal changeAmount, Integer placementId) {

        return placementMapper.updatePlaceQuantityAndAmount(changeQuantity, changeAmount, placementId);
    }

    @Override
    public boolean insert(PlacementHistory record) {
        return placementHistoryMapper.insert(record);
    }

    @Override
    public Page selectPlacementHistoryByPage(Page page, PlacementHistoryParams placementHistoryParams) {
        if (page == null) {
            Page page1 = new Page();
            page1.setAaData(placementHistoryMapper.selectPlacementHistoryByPage(null, placementHistoryParams));
            page = page1;
        } else {
            page.setAaData(placementHistoryMapper.selectPlacementHistoryByPage(page, placementHistoryParams));
        }
        return page;
    }

    @Override
    public boolean isPlacementEnd(Placement placement) {
        return false;
    }

    /**
     * 1.更新数量金额
     * 1.1.普通委托:更新成交数量,成交金额
     * 1.2.撤单委托:更新撤单数量
     * 1.3.废单委托:更新废单数量
     * 2.更新状态(单独方法:
     * 2.1,当已成数量+已撤数量+废单数量=委托数量时,更新状态,
     * 撤单数量不为0则为撤单,
     * 委托数量=成交数量为已成,
     * 其他为废单;
     * 2.2 已报->部成)
     * 3.普通委托情况下,更新用户资金头寸的amount,证券头寸的amount
     * 3.1买入:资金头寸amount扣减成交金额,证券头寸amount增加成交数量
     * 3.2卖出:资金头寸amount和可用available都增加成交金额,证券头寸amount减少成交数量
     * 4.若状态更新为终态,更新用户资金头寸头寸可用,更新证券头寸及可用
     * 4.1资金可用:若委托为买入委托，+未成交部分的金额[委托金额-成交金额]+原先计算的费用-实际发生的费用，若委托为卖出委托，-实际发生的费用
     * 4.2资金头寸:扣减佣金:减去佣金,佣金按成交金额进行计算
     * 4.3证券可用:若委托为卖出委托，+未成交部分的数量
     * 4.4证券持仓:不变
     *
     * @param placementId,type,quantity,price
     */
    @TradeLogAnnocation(tag="用户委托成交")
    public void fill(Integer placementId, TransactionType transactionType, BigDecimal quantity, BigDecimal price,boolean isForceFill) {
        String userPlacementIdStr = placementId.toString();
        logger.debug("开始【{placementService.fill}】" + userPlacementIdStr);
        userFillSyncLock.lock(userPlacementIdStr);
        try {
            Placement placement = this.getPlacementById(placementId);
            TradeStatus statusOld=placement.getStatus();
        	//如果用户委托已经是终态了，则不处理用户委托逻辑（通道委托bug引起多调用）
            if(!isForceFill){
            	if (PlacementUtil.isFinished(statusOld)) {
                    //return;
                	throw new RuntimeException("用户委托状态已经是終态");
                }
            }else{
            	if(statusOld==TradeStatus.FILLED){
            		throw new RuntimeException("用户委托状态已经是已成状态");
            	}
            }
            String securityCode = placement.getSecurityCode();
            String internalSecurityId = SecurityUtil.getInternalSecurityIdBySecurityCode(securityCode);
            // StockSecurity security = securityService.findByNameAndCode(null, placement.getSecurityCode());
            User user = userService.findUserById(placement.getUserId());
            //1、更新数量和金额
            switch (transactionType) {
                case CANCELLED:
                    placement.addCancelQuantity(quantity);
                    placementMapper.updateCancelQuantity(quantity, placement.getId());
                    break;
                case INVALID:
                    placement.addInvalidQuantity(quantity);
                    placementMapper.updateInvalidQuantity(quantity, placement.getId());
                    break;
                case NORMAL:
                    placement.addFillQuantity(quantity);
                    BigDecimal fillCashAmount = BigDecimalUtil.multiply(quantity, price);
                    placementMapper.updatePlaceQuantityAndAmount(quantity, fillCashAmount, placement.getId());
                    break;
            }
            
            //2、更新状态
            updateStatus(placement,isForceFill);

            //3、普通委托情况下,更新用户资金头寸的amount,证券头寸的amount
            if (StringUtils.equals(transactionType.getName(), TransactionType.NORMAL.getName())) {
                UserPositionParams params = null;
                switch (placement.getSide()) {
                    case BUY:
                        //3.1买入:资金头寸amount扣减成交金额,证券头寸amount增加成交数量
                    	if(PlacementUtil.isFinished(statusOld)){
                    		userService.updateAvailableAndTotalAmount(user.getId(), BigDecimalUtil.multiply(quantity, price).negate(), BigDecimalUtil.multiply(quantity, price).negate());
                    	}else{
                    		userService.updateAvailableAndTotalAmount(user.getId(), BigDecimal.ZERO, BigDecimalUtil.multiply(quantity, price).negate());
                    	}
                        UserSecurityPosition userSecurityPosition = userPositionService.findUserSecurityPositionById(user.getId(), placement.getCustomerId(), internalSecurityId);
                        if (userSecurityPosition == null) {
                            UserSecurityPosition record = new UserSecurityPosition(placement.getCustomerId(), internalSecurityId, user.getId(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
                            userPositionService.saveUserSecurityPosition(record);
                        }
                        params = new UserPositionParams(user.getId(), internalSecurityId, quantity, price);
                        break;
                    case SELL:
                        //3.2卖出:资金头寸amount和available都增加成交金额,证券头寸amount减少成交数量
                        BigDecimal amount = BigDecimalUtil.multiply(quantity, price);
                        userService.updateAvailableAndTotalAmount(user.getId(), amount, amount);
                        params = new UserPositionParams(user.getId(), internalSecurityId, quantity.negate(), price);
                        break;
                }
                userPositionService.changeAmountAndCostPrice(params);
            }

            //4.若状态更新为终态,更新用户资金头寸,可用头寸
            if (PlacementUtil.isFinished(placement.getStatus())) {
                placement = placementMapper.selectById(placement.getId());
                BigDecimal cashAvailable = BigDecimal.ZERO;
                TradeFeeParam tradeFeeParam = new TradeFeeParam(placement.getSecurityCode(), placement.getSide(), placement.getFillPrice(), placement.getIsSzTransferFee(), placement.getFillQuantity(), placement.getRatioFee(), placement.getMinCost());
                TradeFeeResult tradeFee = TradeFeeUtil.getFeeByParam(tradeFeeParam);
                if(!PlacementUtil.isFinished(statusOld)){
                    //4.1资金可用:资金可用:若委托为买入委托，+未成交部分的金额[委托金额-成交金额]+原先计算的费用-实际发生的费用，若委托为卖出委托，-实际发生的费用
                    switch (placement.getSide()) {
                        case BUY:
                            cashAvailable = placement.getAmount().subtract(placement.getFillAmount()).subtract(tradeFee.getTotalFee());
                            break;
                        case SELL:
                            cashAvailable = tradeFee.getTotalFee().negate();
                            break;
                    }
                    //4.2资金头寸:扣减佣金:减去佣金,佣金按成交金额进行计算
                    BigDecimal cashAmount = tradeFee.getTotalFee().negate();
                	//初始的委托状态如果不是終态
                	userService.updateAvailableAndTotalAmount(user.getId(), cashAvailable, cashAmount);
                }else{
                	//初始的委托状态如果是終态，可用和总金额都要扣除佣金
                	tradeFeeParam = new TradeFeeParam(placement.getSecurityCode(), placement.getSide(), placement.getFillPrice(), placement.getIsSzTransferFee(),quantity , placement.getRatioFee(), placement.getMinCost());
                    tradeFee = TradeFeeUtil.getFeeByParam(tradeFeeParam);
                    cashAvailable = tradeFee.getTotalFee().negate();
                	userService.updateAvailableAndTotalAmount(user.getId(), cashAvailable, cashAvailable);
                }


                //4.3证券可用:若委托为卖出委托，+未成交部分的数量
                //4.4证券持仓:不变
                switch (placement.getSide()) {
                    case SELL:
                    	if(!PlacementUtil.isFinished(statusOld)){
                    		 BigDecimal securityAvailable = placement.getQuantity().subtract(placement.getFillQuantity());
                             if (securityAvailable.compareTo(BigDecimal.ZERO) > 0) {
                                 UserPositionParams param = new UserPositionParams(user.getId(), internalSecurityId, securityAvailable);
                                 userPositionService.changeAvailable(param);
                             }
                             break;
                    	}else{
                    		BigDecimal securityAvailable = quantity.negate();
                    		UserPositionParams param = new UserPositionParams(user.getId(), internalSecurityId, securityAvailable);
                            userPositionService.changeAvailable(param);
                    	}
                    default:
                        break;
                }

                //4.5 将佣金等计算入成本价 2017.09.18 xuhaomin
                UserPositionParams userPositionParams = new UserPositionParams(placement.getUserId(), internalSecurityId, BigDecimal.ONE, null, tradeFee.getTotalFee());
                userSecurityPositionMapper.updateCostPriceDelta(userPositionParams);
            }
        } finally {
            userFillSyncLock.unlock(userPlacementIdStr);
            logger.debug("结束【{placementService.fill}】" + userPlacementIdStr);
        }
    }

    public void updateStatus(Placement placement,boolean isForceFill) {
        TradeStatus tradeStatus = null;
        BigDecimal sumQuantity = null;
        if(!PlacementUtil.isFinished(placement.getStatus())){//如果是非終态
        	sumQuantity=BigDecimalUtil.add(placement.getFillQuantity(), placement.getCancelQuantity(), placement.getInvalidQuantity());
        	if (sumQuantity.compareTo(placement.getQuantity()) == 0) {
                if (!BigDecimalUtil.isZero(placement.getCancelQuantity())) {
                    updatePlacementStatus(TradeStatus.CANCELLED, placement.getId());
                    tradeStatus = TradeStatus.CANCELLED;
                } else if (placement.getFillQuantity().compareTo(placement.getQuantity()) == 0) {
                    updatePlacementStatus(TradeStatus.FILLED, placement.getId());
                    tradeStatus = TradeStatus.FILLED;
                } else {
                    updatePlacementStatus(TradeStatus.INVALID, placement.getId());
                    tradeStatus = TradeStatus.INVALID;
                }

            } else if (StringUtils.equals(placement.getStatus().getName(), SENDACK.getName()) && placement.getFillQuantity().compareTo(BigDecimal.ZERO) > 0) {//只有当原先委托状态是“已报”且成交数量不为0时才需要将状态改为“部成”
                updatePlacementStatus(PARTIALLYFILLED, placement.getId());
                tradeStatus = PARTIALLYFILLED;
            } else {
                tradeStatus = placement.getStatus();
            }
        }else{//如果是終态
        	//判断是否是强制成交
        	if(isForceFill){
        		tradeStatus=placement.getStatus();
            	if(tradeStatus==TradeStatus.CANCELLED||tradeStatus==TradeStatus.INVALID){
            		sumQuantity=placement.getFillQuantity();
                	if (sumQuantity.compareTo(placement.getQuantity()) == 0) {
                		updatePlacementStatus(TradeStatus.FILLED, placement.getId());
                        tradeStatus = TradeStatus.FILLED;
                	}
            	}
        	}
        }
        placement.setStatus(tradeStatus);
    }

    @Override
    public boolean updateInvalidQuantity(BigDecimal invalidQuantity, Integer placementId) {
        return placementMapper.updateInvalidQuantity(invalidQuantity, placementId);
    }

    @Override
    public boolean updateCancelQuantity(BigDecimal cancelQuantity, Integer placementId) {
        return placementMapper.updateCancelQuantity(cancelQuantity, placementId);
    }


    /**
     * 网站前台分页查询用户当日委托
     *
     * @param user
     * @return
     */
    @Override
    public PageBean<PlacementVO> queryCurrentDayPlacementByPage(User user, Integer pageNo, Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        //查询参数
        PlacementParams params = new PlacementParams();
        params.setUserId(user.getId());
        page = selectPlacementByPage(page, params);
        //转换page->pageBean
        List<PlacementVO> voList = convertToPlacementVO(page.getAaData());
        PageBean<PlacementVO> pageBean = new PageBean<PlacementVO>(pageNo, pageSize, page.getiTotalRecords(), voList);
        return pageBean;
    }


    @Override
    public List<PlacementVO> queryCurrentDayPlacementNotEnd(User user) {
        List<Placement> list = placementMapper.selectPlacementNotEnd(user.getId());
        return convertToPlacementVO(list);
    }

    /**
     * 查询当日所有委托
     *
     * @param user
     * @return
     */
    @Override
    public List<PlacementVO> queryCurrentDayPlacement(User user) {
        List<Placement> list = placementMapper.selectPlacement(user.getId());
        return convertToPlacementVO(list);
    }

    @Override
    public Page selectCurrentPlacementByPage(Page page, PlacementParams placementParams) {
        List<Placement> list = placementMapper.placementSelectByPage(page, placementParams);
        page.setAaData(convertToPlacementVO(list));
        return page;
    }

    private List<PlacementVO> convertToPlacementVO(List<Placement> list) {
        List<PlacementVO> voList = new ArrayList<PlacementVO>();
        for (Placement placement : list) {
            String securityName = "";
            String securityCode = placement.getSecurityCode();
            StockSecurity stock = securityService.findByNameAndCode(null, securityCode);

            if (stock == null) {
                logger.error("查询当日委托，证券" + securityCode + "不存在");
            } else {
                securityName = stock.getName();
            }
            //组装VO对象
            PlacementVO vo = new PlacementVO();
            vo.setCreateTime(placement.getTime());
            vo.setFilledPrice(placement.getFillPrice());
            vo.setFilledQty(placement.getFillQuantity());
            vo.setFilledAmount(placement.getFillAmount());
            vo.setPlacementId(placement.getId());
            vo.setPlacementPrice(placement.getPrice());
            vo.setPlacementQty(placement.getQuantity());
            vo.setPlacementStatusDisplay(placement.getStatus().getDisplay());
            vo.setSecurityCode(placement.getSecurityCode());
            vo.setSecurityName(securityName);
            vo.setTradeTypeDisplay(placement.getSide().getDisplay());
            vo.setTradeType(placement.getSide().getName());
            vo.setPlacementStatusDisplay(placement.getStatus().getDisplay());
            vo.setPlacementStatus(placement.getStatus().getName());
            voList.add(vo);
        }
        return voList;
    }

    private List<PlacementVO> convertToPlacementHistoryVO(List<PlacementHistory> list) {
        List<PlacementVO> voList = new ArrayList<PlacementVO>();
        for (PlacementHistory placement : list) {
            String securityName = "";
            String securityCode = placement.getSecurityCode();
            StockSecurity stock = securityService.findByNameAndCode(null, securityCode);

            if (stock == null) {
                logger.error("查询当日委托，证券" + securityCode + "不存在");
            } else {
                securityName = stock.getName();
            }
            //组装VO对象
            PlacementVO vo = new PlacementVO();
            vo.setCreateTime(placement.getDateTime());
            vo.setFilledPrice(placement.getFillPrice());
            vo.setFilledQty(placement.getFillQuantity());
            vo.setFilledAmount(placement.getFillAmount());
            vo.setPlacementId(placement.getId());
            vo.setPlacementPrice(placement.getPrice());
            vo.setPlacementQty(placement.getQuantity());
            vo.setPlacementStatusDisplay(placement.getStatus().getDisplay());
            vo.setSecurityCode(placement.getSecurityCode());
            vo.setSecurityName(securityName);
            vo.setTradeTypeDisplay(placement.getSide().getDisplay());
            vo.setTradeType(placement.getSide().getName());
            vo.setPlacementStatusDisplay(placement.getStatus().getDisplay());
            vo.setPlacementStatus(placement.getStatus().getName());
            voList.add(vo);
        }
        return voList;
    }


    @Override
    public PageBean<PlacementVO> queryPlacementHistoryByPage(
            PlacementHistoryParams params, Integer pageNo, Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        //查询参数
        page = selectPlacementHistoryByPage(page, params);
        //转换page->pageBean
        List<PlacementVO> voList = convertToPlacementHistoryVO(page.getAaData());
        PageBean<PlacementVO> pageBean = new PageBean<PlacementVO>(pageNo, pageSize, page.getiTotalRecords(), voList);
        return pageBean;
    }

    @Override
    public boolean insertList(List<PlacementHistory> placementHistoryList) {
        if (placementHistoryList == null || placementHistoryList.isEmpty()) {
            return false;
        }
        return placementHistoryMapper.insertList(placementHistoryList);
    }

    @Override
    public Map<String, String> autoPlacement(String stockCode, BigDecimal quantity, BigDecimal price, Integer[] ids) {
        Map<String, String> map = new HashMap<String, String>();
        if (stockCode == null || "".equals(stockCode)) {
            map.put(stockCode, "输入的股票代码有问题");
            return map;
        } else if (quantity.compareTo(new BigDecimal(100)) == -1) {

            map.put(quantity.toString(), "下单数量有问题");
            return map;
        } else if (ids.length < 1) {
            map.put(Arrays.toString(ids), "选择的用户有问题");
            return map;
        }

        List<Integer> userIds = Arrays.asList(ids);

        for (Integer userId : userIds) {
            //查出对应的 channelID,customerID

            Placement placement = new Placement();

            placement.setAmount(quantity.multiply(price));
            placement.setPrice(price);
            placement.setQuantity(quantity);
            placement.setSide(TradeSide.getByNum(0));
            placement.setSecurityCode(stockCode);
            placement.setUserId(userId);
            User user = userService.findUserById(userId);

            try {
                placement(placement);
            } catch (Exception e) {
                map.put(user.getUserName() + "", e.getMessage());
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 用户委托成交
     */
	@Override
	public void doPersonFill(Integer placementId,BigDecimal fillPrice) {
		Placement placement=getPlacementById(placementId);
		if(placement.getFillQuantity()!=null&&placement.getFillQuantity().doubleValue()>0){//部成不处理
    		throw new RuntimeException("用户委托状态是部成状态，不允许手动成交");
    	}
		//判断成交价格是否超出涨跌停
		MarketDataSnapshot shot=marketdataService.getBySymbol(placement.getSecurityCode());
		if(shot!=null){
			double down=shot.getLimitDown();
			double up=shot.getLimitUp();
			double _fillPrice=fillPrice.doubleValue();
			if(_fillPrice<down||_fillPrice>up){
				throw new RuntimeException("成交价格超过今日涨跌停");
			}
		}
		ChannelPlacementParams channelPlacementParams=new ChannelPlacementParams();
		channelPlacementParams.setPlacementId(placementId);
		List<ChannelPlacement> list=channelPlacementService.selectChannelPlacement(channelPlacementParams);
		for (ChannelPlacement channelPlacement : list) {
			long time=new Date().getTime();
			BigDecimal quantity=channelPlacement.getQuantity().subtract(channelPlacement.getFillQuantity());
			emsService.dockFillByHand(channelPlacement, time+"", quantity,fillPrice,true);
		}
	}

}
