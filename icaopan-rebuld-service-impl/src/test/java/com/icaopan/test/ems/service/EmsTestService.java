package com.icaopan.test.ems.service;

import com.icaopan.ems.service.EmsService;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.test.common.service.BaseTestService;
import com.icaopan.trade.model.ChannelPlacement;
import org.jtester.annotations.SpringBeanByType;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;

public class EmsTestService extends BaseTestService {

	@SpringBeanByType
	private EmsService emsService;
	@Test
	public void testPlacement(){
		ChannelPlacement channelPlacement=new ChannelPlacement();
		channelPlacement.setAccount("1770002643");
		channelPlacement.setAmount(new BigDecimal(100));
		channelPlacement.setCreateTime(new Date());
		channelPlacement.setPrice(new BigDecimal("2.22"));
		channelPlacement.setQuantity(new BigDecimal(200));
		channelPlacement.setSecurityCode("600022");
		channelPlacement.setSide(TradeSide.BUY);
		emsService.placement(channelPlacement);
	}
}
