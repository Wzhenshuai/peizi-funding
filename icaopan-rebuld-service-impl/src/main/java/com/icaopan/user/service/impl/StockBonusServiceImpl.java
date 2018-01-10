package com.icaopan.user.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icaopan.common.util.DataUtils;
import com.icaopan.common.util.StockBonusUtil;
import com.icaopan.customer.model.Channel;
import com.icaopan.customer.service.ChannelService;
import com.icaopan.enums.enumBean.StockBonusStatus;
import com.icaopan.marketdata.market.MarketdataService;
import com.icaopan.marketdata.market.StockBonus;
import com.icaopan.stock.model.StockSecurity;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.user.bean.StockBonusParams;
import com.icaopan.user.bean.StockBonusScheme;
import com.icaopan.user.dao.UserStockBonusMapper;
import com.icaopan.user.model.ChannelSecurityPosition;
import com.icaopan.user.model.User;
import com.icaopan.user.model.UserStockBonus;
import com.icaopan.user.service.ChannelPositionService;
import com.icaopan.user.service.StockBonusService;
import com.icaopan.user.service.UserService;
import com.icaopan.util.BigDecimalUtil;
import com.icaopan.util.page.Page;

import elf.api.marketdata.marketdata.MarketDataSnapshot;

@Service("stockBonusServiceImpl")
public class StockBonusServiceImpl implements StockBonusService {

	@Autowired
	private UserStockBonusMapper userStockBonusMapper;
	@Autowired
	private ChannelPositionService channelPostionService;
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private MarketdataService marketdataService;
	/**
	 * 新红配调整记录生成
	 */
	@Override
	public void makeBonus(StockBonusScheme scheme) {
		StockSecurity security=securityService.findByNameAndCode(null, scheme.getSecurityCode());
		if(security==null){
			throw new RuntimeException("证券信息不存在");
		}
		List<ChannelSecurityPosition> list=channelPostionService.findAllPositionBySecurityId(security.getInternalSecurityId());
		for (ChannelSecurityPosition channelSecurityPosition : list) {
			Channel channel=channelService.getChannelById(channelSecurityPosition.getChannelId());
			BigDecimal positionAmount=channelSecurityPosition.getAmount();
			if(positionAmount!=null && positionAmount.doubleValue()>0){
				User user=userService.findUserById(channelSecurityPosition.getUserId());
				if(user!=null){
					StockBonusUtil bonusUtil=new StockBonusUtil(user, channelSecurityPosition, channel, scheme, security);
					UserStockBonus bonus=bonusUtil.getStockBonus();
					userStockBonusMapper.insert(bonus);
				}
			}
		}
		
	}
	
	/**
	 * 批量生成新红配分配记录
	 */
	@Override
	public void makeBonusBatch(List<StockBonusScheme> list) {
		for (StockBonusScheme stockBonusScheme : list) {
			makeBonus(stockBonusScheme);
		}
	}
	
	/**
	 * 从行情服务获取今日新红配分配方案
	 * @return
	 */
	@Override
	public List<StockBonusScheme> getDistributeFromMarketData(String tradeDate){
		List<StockBonusScheme> result=new ArrayList<StockBonusScheme>();
		try {
			List<StockBonus> list=marketdataService.getStockBonus(tradeDate);
			for (StockBonus stockBonus : list) {
				StockBonusScheme s=transeStockBonus(stockBonus);
				result.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	private StockBonusScheme transeStockBonus(StockBonus sb){
		StockBonusScheme s=new StockBonusScheme();
		if(sb!=null){
			s.setSecurityCode(sb.getTicker());
			if(StringUtils.isNotEmpty(sb.getSecShortName())){
				s.setSecurityName(sb.getSecShortName());
			}else{
				MarketDataSnapshot shot=marketdataService.getBySymbol(s.getSecurityCode());
				if(shot!=null){
					s.setSecurityName(shot.getStockName());
				}
			}
			s.setBonusProfit(BigDecimalUtil.valueOfScale2(new BigDecimal(sb.getPerCashDiv()+"")));
			s.setDistributeStockAmount(BigDecimalUtil.valueOfScale2(new BigDecimal(sb.getPerShareDivRatio()+"")));
			s.setDonationStockAmount(BigDecimalUtil.valueOfScale2(new BigDecimal(sb.getPerShareTransRatio()+"")));
			s.setRecordDate(sb.getRecordDate());
			s.setExDivDate(sb.getExDivDate());
		}
		return s;
	}
	
	
	/**
	 * 新红配确认调整
	 */
	@Override
	public void doBonusAdjust(Integer id){
		if(id!=null&&id.intValue()==0){
			List<UserStockBonus> list=userStockBonusMapper.findAllToDeal();
			for (UserStockBonus userStockBonus : list) {
				doBonusAdjust( userStockBonus);
			}
		}else{
			UserStockBonus userStockBonus=userStockBonusMapper.findById(id);
			if(userStockBonus!=null){
				doBonusAdjust( userStockBonus);
			}
		}
		
	}
	
	private void doBonusAdjust(UserStockBonus userStockBonus){
		if(userStockBonus.getStatus().intValue()==StockBonusStatus.Wait.getNum()){
			BigDecimal securityAmount=userStockBonus.getSecurityAdjustAmount();
			BigDecimal bonusAmount=userStockBonus.getBonusAdjustAmount();
			if(securityAmount.compareTo(BigDecimal.ZERO)>0){
				//调整持仓
                channelPostionService.updatePosition(userStockBonus.getChannelSecurityPositionId(), securityAmount, securityAmount, BigDecimal.ZERO, null,null);
            }
			if(bonusAmount.compareTo(BigDecimal.ZERO)>0){
				//调整资金
				userService.updateAmountAndAvailable(userStockBonus.getUserId(), bonusAmount, bonusAmount,null);
			}
			//更改状态
			userStockBonusMapper.updateStatus(userStockBonus.getId(), StockBonusStatus.Finish.getNum(), "");
		}else{
			throw new RuntimeException("已经处理完成，请勿重复操作");
		}
	}

	/**
	 * 分页查找数据
	 */
	@Override
	public Page<UserStockBonus> findStockBonusByPage(Page<UserStockBonus> page,
			StockBonusParams params) {
		List<UserStockBonus> list=userStockBonusMapper.findByPage(page, params);
		page.setAaData(list);
		return page;
	}
	
	@Override
	public void updateUserStockBonusToInvalid() {
        List<UserStockBonus> list=userStockBonusMapper.findAllToDeal();
        for (UserStockBonus userStockBonus : list) {
        	userStockBonusMapper.updateStatus(userStockBonus.getId(), StockBonusStatus.Invalid.getNum(), "手工处理失败");
		}
	}

}
