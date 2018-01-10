package com.icaopan.risk.service.impl;

import com.icaopan.common.util.SecurityUtil;
import com.icaopan.enums.enumBean.RiskResult;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.marketdata.market.MarketdataService;
import com.icaopan.risk.service.RiskFilterChain;
import com.icaopan.risk.service.RiskService;
import com.icaopan.stock.service.PoolService;
import com.icaopan.trade.dao.PlacementMapper;
import com.icaopan.trade.model.ComplianceResult;
import com.icaopan.trade.model.Placement;
import com.icaopan.trade.service.ComplianceService;
import com.icaopan.user.model.User;
import com.icaopan.user.model.UserSecurityPosition;
import com.icaopan.user.service.UserPositionService;
import com.icaopan.user.service.UserService;
import com.icaopan.util.BigDecimalUtil;
import com.icaopan.web.vo.AccountVO;

import elf.api.marketdata.marketdata.MarketDataSnapshot;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.icaopan.enums.enumBean.StockPoolType.Gem;
import static com.icaopan.enums.enumBean.StockPoolType.SmallPlates;

/**
 * desc 比例风控管理
 *
 * Created by kanglj on 17/3/7.
 */
@Service("proportionRiskService")
public class ProportionRiskServiceImpl implements RiskService {

    @Autowired
    private UserService userService;

    @Autowired
    private PoolService poolService;

    @Autowired
    private UserPositionService userPositionService;

    @Autowired
    private PlacementMapper placementMapper;
    @Autowired
    private MarketdataService marketdataService;
    
    @Autowired
    private ComplianceService complianceService;

    private static Logger log = com.icaopan.log.LogUtil.getTradeLogger();

    @Override
    public RiskResult doRiskFilter(User user, Placement placement, RiskFilterChain chain) {
        if(StringUtils.equals(placement.getSide().getCode(), TradeSide.SELL.getCode())){
            // 继续执行过滤链的下一节点
            return chain.doRiskFilter(user, placement, chain);
        }
        // 查询用户的总资产
        AccountVO vo = userService.queryUserAccount(user);
        // 单支股票持仓比例风控
        List<UserSecurityPosition> userSecurityPositions = userPositionService.queryPositionByUserIdStockCode(user.getId(), placement.getSecurityCode());
        BigDecimal positionsAmount = calculatePositionsSum(userSecurityPositions);
        // 查询用户所有非终态的委托
        List<Placement> placements = placementMapper.selectBuyPlacementNotEnd(user.getId());
        // 根据股票代码筛选所有委托,计算可能成交的金额
        BigDecimal  placementsAmount = processBySecurityCode(placements, placement.getSecurityCode());
        // 单支股票/用户总资产
        BigDecimal placementAmount = BigDecimalUtil.multiply(placement.getPrice(), placement.getQuantity());
        BigDecimal placementSum = placementsAmount.add(placementAmount);
        BigDecimal rate = BigDecimalUtil.divide(placementSum.add(positionsAmount), vo.getTotalAmount());
        if(BigDecimal.ONE.compareTo(user.getSingleStockScale()) > 0 && rate.compareTo(user.getSingleStockScale()) >= 0){
        	String reason=String.format("{比例%s=(%s[该股票所有委托金额]+%s[持仓金额])/%s[总资产]},超过单只股票持仓设定比例%s", rate,placementSum,positionsAmount,vo.getTotalAmount(),user.getSingleStockScale());
        	saveLog(user, placement, vo, reason);
            return RiskResult.SingleStockRiskFail;
        }
        String SecurityId = SecurityUtil.getInternalSecurityIdBySecurityCode(placement.getSecurityCode());
        // 中小板
        if(poolService.isSmallPlatesStock(SecurityId)){
            // 中小板单支股票市值比例风控
            if(BigDecimal.ONE.compareTo(user.getSmallSingleStockScale()) > 0 && rate.compareTo(user.getSmallSingleStockScale()) >= 0){
            	String reason=String.format("{比例%s=(%s[该股票所有委托金额]+%s[持仓金额])/%s[总资产]},超过中小板单只股票持仓设定比例%s", rate,placementSum,positionsAmount,vo.getTotalAmount(),user.getSmallSingleStockScale());
            	saveLog(user, placement, vo, reason);
                return RiskResult.SmallSingleStockRiskFail;
            }
            if(BigDecimal.ONE.compareTo(user.getSmallStockScale()) <= 0){
                // 继续执行过滤链的下一节点
                return chain.doRiskFilter(user, placement, chain);
            }
            // 中小板持仓总市值比例风控
            List<UserSecurityPosition> smallSecurityPositions = userPositionService.queryPositionByUserIdStockType(user.getId(), SmallPlates.getCode());
            BigDecimal smallPositionsAmount = calculatePositionsSum(smallSecurityPositions);

            // 查询用户所有中小板的非终态委托
            List<Placement> smallPlacements = placementMapper.selectPlacementNotEndByStockType(user.getId(), SmallPlates.getNum());
            // 根据股票代码筛选所有中小板的委托,计算可能成交的金额
            BigDecimal  smallPlacementsAmount = processPossible(smallPlacements);

            BigDecimal smallPlacementSum = placementAmount.add(smallPlacementsAmount);
            // 中小板股票持仓/用户总资产
            BigDecimal smallStockRate = BigDecimalUtil.divide(smallPlacementSum.add(smallPositionsAmount), vo.getTotalAmount());
            if(smallStockRate.compareTo(user.getSmallStockScale()) >= 0){
            	String reason=String.format("{比例%s=%s[中小板所有委托金额]+%s[中小板持仓金额]/%s[总资产]},超过中小板总持仓设定比例%s", smallStockRate,smallPlacementSum,smallPositionsAmount,vo.getTotalAmount(),user.getSmallStockScale());
            	saveLog(user, placement, vo, reason);
                return RiskResult.SmallStockRiskFail;
            }
        }
        // 创业板
        else if(poolService.isGemStock(SecurityId)){
            // 创业板单支股票市值比例风控
            if(BigDecimal.ONE.compareTo(user.getCreateSingleStockScale()) > 0 && rate.compareTo(user.getCreateSingleStockScale()) >= 0){
            	String reason=String.format("{比例%s=(%s[该股票所有委托金额]+%s[持仓金额])/%s[总资产]},超过创业板单只股票持仓设定比例%s", rate,placementSum,positionsAmount,vo.getTotalAmount(),user.getCreateSingleStockScale());
            	saveLog(user, placement, vo, reason);
                return RiskResult.CreateSingleStockRiskFail;
            }
            if(BigDecimal.ONE.compareTo(user.getCreateStockScale()) <= 0){
                // 继续执行过滤链的下一节点
                return chain.doRiskFilter(user, placement, chain);
            }
            // 创业板持仓总市值比例风控
            List<UserSecurityPosition> gemSecurityPositions = userPositionService.queryPositionByUserIdStockType(user.getId(), Gem.getCode());
            BigDecimal gemPositionsAmount = calculatePositionsSum(gemSecurityPositions);

            // 查询用户所有创业板的非终态委托
            List<Placement> gemPlacements = placementMapper.selectPlacementNotEndByStockType(user.getId(), Gem.getNum());
            // 根据股票代码筛选所有创业板的委托,计算可能成交的金额
            BigDecimal  gemPlacementsAmount = processPossible(gemPlacements);

            BigDecimal gemPlacementSum = placementAmount.add(gemPlacementsAmount);
            // 创业板股票持仓/用户总资产
            BigDecimal createStockRate = BigDecimalUtil.divide(gemPlacementSum.add(gemPositionsAmount), vo.getTotalAmount());
            if(createStockRate.compareTo(user.getCreateStockScale()) >= 0){
            	String reason=String.format("{比例%s=(%s[中小板所有委托金额]+%s[中小板持仓金额])/%s总资产},超过创业板总持仓设定比例%s", createStockRate,gemPlacementSum,gemPositionsAmount,vo.getTotalAmount(),user.getCreateStockScale());
            	saveLog(user, placement, vo, reason);
                return RiskResult.CreateStockRiskFail;
            }
        }

        // 继续执行过滤链的下一节点
        return chain.doRiskFilter(user, placement, chain);
    }

    private void saveLog(User user, Placement placement,AccountVO vo,String reason){
    	try {
    		ComplianceResult record=new ComplianceResult();
        	record.setOpType("股票买入");
        	record.setStockCode(placement.getSecurityCode());
        	record.setQuantity(placement.getQuantity().toString());
        	record.setUserName(user.getUserName());
        	reason=String.format(reason, vo.getTotalAmount(),vo.getWarnLine());
        	record.setReason(reason);
        	complianceService.saveCompliance(record);
		} catch (Exception e) {
			
		}
    }
    
    private BigDecimal processBySecurityCode(List<Placement> placements, String securityCode){
        BigDecimal amount = BigDecimal.ZERO;
        for(Placement placement : placements){
            if(StringUtils.equals(placement.getSecurityCode(), securityCode)){
                amount = amount.add(calculateAmountPossible(placement));
            }
        }
        return amount;
    }

    private BigDecimal processPossible(List<Placement> placements){
        BigDecimal amount = BigDecimal.ZERO;
        for(Placement placement : placements){
           amount = amount.add(calculateAmountPossible(placement));
        }
        return amount;
    }


    private BigDecimal processSmallPlates(List<Placement> placements){
        BigDecimal amount = BigDecimal.ZERO;
        for(Placement placement : placements){
            if(poolService.isSmallPlatesStock(SecurityUtil.getInternalSecurityIdBySecurityCode(placement.getSecurityCode()))){
                amount = amount.add(calculateAmountPossible(placement));
            }
        }
        return amount;
    }

    private BigDecimal processGemPlacement(List<Placement> placements){
        BigDecimal amount = BigDecimal.ZERO;
        for(Placement placement : placements){
            if(poolService.isGemStock(SecurityUtil.getInternalSecurityIdBySecurityCode(placement.getSecurityCode()))){
                amount = amount.add(calculateAmountPossible(placement));
            }
        }
        return amount;
    }

    private BigDecimal calculateAmountPossible(Placement placement){
        // 委托数量 - 成交数量 - 撤单数量 - 废单数量
        BigDecimal quantity = placement.getQuantity().subtract(placement.getFillQuantity())
                .subtract(placement.getCancelQuantity()).subtract(placement.getInvalidQuantity());
        return quantity.multiply(placement.getPrice());
    }

    private BigDecimal calculatePositionsSum(List<UserSecurityPosition> data){
        BigDecimal result = BigDecimal.ZERO;
        for(UserSecurityPosition item : data){
            // 将股票ID转换为股票代码
            String securityCode = SecurityUtil.getSecurityCodeById(item.getInternalSecurityId());
            // 查询股票的现价
            BigDecimal latestPrice = BigDecimal.ZERO;
            MarketDataSnapshot shot = marketdataService.getBySymbol(securityCode);
            if (shot != null) {
                latestPrice = new BigDecimal(String.valueOf(shot.getPrice()));
            }
            result = result.add(item.getAmount().multiply(latestPrice));
        }
        return result;
    }
}
