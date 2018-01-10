package com.icaopan.test.trade.dao;

import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.enums.enumBean.TradeStatus;
import com.icaopan.test.common.dao.BaseTestDao;
import com.icaopan.trade.bean.ChannelForEmsParameter;
import com.icaopan.trade.bean.ChannelPlacementParams;
import com.icaopan.trade.dao.ChannelPlacementMapper;
import com.icaopan.trade.model.ChannelPlacement;
import com.icaopan.util.page.Page;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * @author wangzs
 * @ClassName: ChannelPlacementDaoTest
 * @Description: (通道委托)
 * @date 2016年11月28日 下午5:58:14
 */
public class ChannelPlacementDaoTest extends BaseTestDao {

    @SpringBeanByType
    private ChannelPlacementMapper channelPlacementMapper;

    @Test
    @DbFit(when = { "wiki/trade/testcase.clean_trade_channel_placement.when.wiki" }, then = { "wiki/trade/testcase.trade_channel_placement.then.wiki" })
    public void testChannelPlacementInsert() {
        ChannelPlacement channelPlacement = new ChannelPlacement();
        //channelPlacement.setId(221);
        channelPlacement.setSecurityCode("600022");
        channelPlacement.setSide(TradeSide.BUY);
        channelPlacement.setQuantity(new BigDecimal(1000));
        channelPlacement.setPrice(new BigDecimal("12.23"));
        channelPlacement.setAmount(new BigDecimal(12230));
        channelPlacement.setFillQuantity(new BigDecimal(1000));
        channelPlacement.setFillAmount(new BigDecimal(12230));
        channelPlacement.setFillPrice(new BigDecimal("12.23"));
        channelPlacement.setStatus(TradeStatus.CANCELLED);
        channelPlacement.setAccount("1002003001");
        channelPlacement.setPlacementCode("200161128");
        channelPlacement.setRejectMessage(null);
        //channelPlacement.setUpdateTime(updateTime);
        channelPlacement.setPlacementId(20001);
        channelPlacement.setChannelId(2313);
        channelPlacement.setCustomerId(8001);
        channelPlacement.setUserId(101);
        channelPlacementMapper.insert(channelPlacement);
    }

    @Test
    @DbFit(when = { "wiki/trade/testcase.trade_channel_placement.when.wiki" }, then = { "wiki/trade/testcase.trade_channel_placement.update_status.then.wiki" })
    public void testChannelPlacementUpdateStatus() {

        channelPlacementMapper.updateStatus(20001, TradeStatus.getByName("INVALID"), null);
    }

    @Test
    @DbFit(when = { "wiki/trade/testcase.trade_channel_placement.when.wiki" }, then = { "wiki/trade/testcase.trade_channel_placement.update_amountquantity.then.wiki" })
    public void testChannelPlacementupdateAmountAndQuantity() {

        channelPlacementMapper.updateQuantityAndAmount(20001, new BigDecimal(1000), new BigDecimal(12300));
    }

    @Test
    @DbFit(when = { "wiki/trade/testcase.trade_channel_placement.when.wiki" }, then = { "wiki/trade/testcase.trade_channel_placement.updateRejectMessage.then.wiki" })
    public void testUpdateRejectMessage() {
        channelPlacementMapper.fillRejectMessage("20001", "原因");
    }

    @Test
    @DbFit(when = { "wiki/trade/testcase.trade_channel_placement.when.wiki" }, then = { "wiki/trade/testcase.trade_channel_placement.updatePlacementDock.then.wiki" })
    public void testUpdateForPlacementDock() {
        channelPlacementMapper.fillPlacementCode(20001, "200161129");
    }

    @Test
    @DbFit(when = { "wiki/trade/testcase.trade_channel_placement.when.wiki" })
    public void testChannelPlacementSelectByPage() {
        Page page = new Page();
        ChannelPlacementParams channelPlacementParams = new ChannelPlacementParams();
        //channelPlacementParams.setBrokerAccountCode(1002003001);
        channelPlacementParams.setPlacementId(20001);
        channelPlacementParams.setSecurityCode("600022");
        channelPlacementParams.setSide(TradeSide.BUY.getName());
        channelPlacementParams.setStatus(TradeStatus.CANCELLED.getName());
        channelPlacementParams.setChannelId(2313);
        channelPlacementParams.setCustomerId(8001);
        channelPlacementParams.setUserId(101);
        List<ChannelPlacement> list = channelPlacementMapper.selectChannelPlacementByPage(page, channelPlacementParams);
        org.testng.Assert.assertTrue(list.size() > 0);
    }

    @Test
    @DbFit(when = { "wiki/trade/testcase.trade_channel_placement.when.wiki" })
    public void testChannelPlacementDock() {
        ChannelPlacementParams params = new ChannelPlacementParams();
        params.setAccount("1002003001");
        params.setSide("BUY");
        params.setSecurityCode("600022");
        params.setStatus("CANCELLED");
        List<ChannelPlacement> list = channelPlacementMapper.selectChannelPlacement(params);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    @DbFit(when = { "wiki/trade/testcase.trade_channel_placement.when.wiki" })
    public void testSelectAllForEms() {
        ChannelForEmsParameter channelForEmsParameter = new ChannelForEmsParameter();
        List<Integer> channelList = new ArrayList<Integer>();
        channelList.add(2313);
        channelList.add(633);
        channelList.add(2515);
        List<TradeStatus> tradeStatusList = new ArrayList<TradeStatus>();
        tradeStatusList.add(TradeStatus.CANCELLED);
        tradeStatusList.add(TradeStatus.INVALID);
        channelForEmsParameter.setChannelList(channelList);
        channelForEmsParameter.setStatusList(tradeStatusList);
        channelForEmsParameter.setSecurityCode("600022");

        List<ChannelPlacement> list = channelPlacementMapper.selectAllForEms(channelForEmsParameter);
        org.testng.Assert.assertTrue(list.size() > 0);
    }


}
