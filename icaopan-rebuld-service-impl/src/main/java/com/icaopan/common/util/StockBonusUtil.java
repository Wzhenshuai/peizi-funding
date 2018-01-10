package com.icaopan.common.util;

import java.math.BigDecimal;

import com.icaopan.customer.model.Channel;
import com.icaopan.enums.enumBean.StockBonusStatus;
import com.icaopan.stock.model.StockSecurity;
import com.icaopan.user.bean.StockBonusScheme;
import com.icaopan.user.model.ChannelSecurityPosition;
import com.icaopan.user.model.User;
import com.icaopan.user.model.UserStockBonus;

public class StockBonusUtil {
	private  BigDecimal positionAmount;
	//每10股送红利数
	private  BigDecimal bonusProfit;
	//每10股送红股数
	private BigDecimal distributeStockAmount;
	//每10股转增股数
	private BigDecimal donationStockAmount;
	
	private User user;
	
	private StockSecurity stock;
	
	private Channel channel;
	
	private ChannelSecurityPosition position;
	
	
	public  StockBonusUtil(User user,ChannelSecurityPosition position,Channel channel,StockBonusScheme scheme,StockSecurity stock){
		this.user=user;
		this.stock=stock;
		this.channel=channel;
		this.position=position;
		this.positionAmount=position.getAmount();
		this.bonusProfit=scheme.getBonusProfit();
		this.distributeStockAmount=scheme.getDistributeStockAmount();
		this.donationStockAmount=scheme.getDonationStockAmount();
	}
	

	private  BigDecimal getBrokerBonusAdjustAmount(){
		BigDecimal bon1=bonusProfit.add(distributeStockAmount).multiply(new BigDecimal("0.2"));
		bon1=bon1.divide(new BigDecimal(4));
		BigDecimal bon2=bonusProfit.subtract(bon1).divide(new BigDecimal(10));
		BigDecimal bon3=bon2.multiply(positionAmount);
		return bon3;
	}
	
	private  BigDecimal getBonusAdjustAmount(){
		BigDecimal bon1=bonusProfit.add(distributeStockAmount).multiply(new BigDecimal("0.2"));
		bon1=bonusProfit.subtract(bon1).divide(new BigDecimal(10));
		bon1=bon1.multiply(positionAmount);
		return bon1;
	}
	
	private  BigDecimal getSecurityAdjustAmount(){
		BigDecimal scale=distributeStockAmount.add(donationStockAmount).divide(new BigDecimal("10"));
		BigDecimal amount=positionAmount.multiply(scale);
		amount=amount.setScale(2, BigDecimal.ROUND_HALF_UP);
		return amount;
	}
	
	public  UserStockBonus getStockBonus(){
		UserStockBonus bonus=new UserStockBonus();
		//获取券商的资金头寸调整数量
		BigDecimal brokerBonusAdjustAmount=getBrokerBonusAdjustAmount();
		//获取平台的资金头寸调整数量
		BigDecimal bonusAdjustAmount=getBonusAdjustAmount();
		//获取股票调整数量
		BigDecimal securityAdjustAmount=getSecurityAdjustAmount();
		
		bonus.setUserId(user.getId());
		bonus.setUserName(user.getUserName());
		bonus.setSecurityCode(stock.getCode());
		bonus.setSecurityName(stock.getName());
		bonus.setSecurityPositionAmount(positionAmount);
		bonus.setChannelName(channel.getName());
		bonus.setChannelSecurityPositionId(position.getId());
		bonus.setSecurityAdjustAmount(securityAdjustAmount);
		bonus.setBonusAdjustAmount(bonusAdjustAmount);
		bonus.setBonusBrokerAdjustAmount(brokerBonusAdjustAmount);
		bonus.setStatus(StockBonusStatus.Wait.getNum());
		
		return bonus;
	}
}
