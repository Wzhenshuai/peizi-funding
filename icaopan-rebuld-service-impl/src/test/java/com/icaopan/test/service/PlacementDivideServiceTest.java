package com.icaopan.test.service;

import com.icaopan.common.util.ExceptionConstants;
import com.icaopan.customer.model.BuyLimitChannel;
import com.icaopan.customer.service.ChannelService;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.enums.enumBean.UserTradeType;
import com.icaopan.test.common.service.BaseTestService;
import com.icaopan.trade.model.Placement;
import com.icaopan.trade.service.PlacementService;
import com.icaopan.trade.service.dto.PlacementChannelDto;
import com.icaopan.trade.service.impl.PlacementConvertServiceImpl;
import com.icaopan.user.model.ChannelSecurityPosition;
import com.icaopan.user.model.User;
import org.apache.commons.lang.StringUtils;
import org.jtester.annotations.SpringBeanByType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wangzs on 2017-02-17.
 */
public class PlacementDivideServiceTest extends BaseTestService{

    @SpringBeanByType
    PlacementService placementService;
    @SpringBeanByType
    PlacementConvertServiceImpl placementConvertServiceImpl;
    @SpringBeanByType
    ChannelService channelService;
    /**
     * 可用余额：1000
     * 输入股票代码600022、委托价格2.0以及委托数量100
     * 结果：下单成功，默认通道中有一笔委托，买入100股。
     *
     */
    @Test
    public void testPlacementBuy2(){
        User user = new User();
        user.setUserTradeType(UserTradeType.DIVIDE);
        user.setAvailable(new BigDecimal(1000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(100),new BigDecimal(2.0),new BigDecimal(200),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), new BigDecimal(1000));
        channelList.add(channel1);
        List<PlacementChannelDto> placementChannelDtoList = placementConvertServiceImpl.generateBuyPlacementChannel(user, channelList, placement);
        Assert.assertTrue(placementChannelDtoList.size() == 1);
    }

    /**
     * 可用余额：1000
     * 输入股票代码600022、委托价格2.0以及委托数量100
     * 结果：下单成功，默认通道中有一笔委托，买入100股。
     *
     */
    @Test
    public void testPlacementBuy3(){
        User user = new User();
        user.setUserTradeType(UserTradeType.DIVIDE);
        user.setAvailable(new BigDecimal(1000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(500),new BigDecimal(2.0),new BigDecimal(200),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), new BigDecimal(1000));
        channelList.add(channel1);
        List<PlacementChannelDto> placementChannelDtoList = placementConvertServiceImpl.generateBuyPlacementChannel(user, channelList, placement);
        Assert.assertTrue(placementChannelDtoList.size() == 1);
    }

    /**
     * 用户可用余额：1000
     * 通道可用资金：3000
     * 输入股票代码600022、委托价格2.0以及委托数量1000
     * 结果：不能下单，提示：可用资金不足。
     */
    @Test
    public void testPlacementBuy4(){
        User user = new User();
        user.setUserTradeType(UserTradeType.DIVIDE);
        user.setAvailable(new BigDecimal(3000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(1000),new BigDecimal(2.0),new BigDecimal(2000),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(3000), new BigDecimal(1000));
        channelList.add(channel1);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateBuyPlacementChannel(user,channelList,placement);
        Assert.assertTrue(placementChannelDtoList.size() == 1);
       /* try {
            List<PlacementChannelDto> placementChannelDtoList = placementConvertServiceImpl.generateBuyPlacementChannel(user, channelList, placement);
            Assert.fail();
        }catch (Exception e){
            Assert.assertTrue(StringUtils.endsWith(e.getMessage(),ExceptionConstants.CASH_NOT_ENOUGH));
        }*/
    }
    /**
     * 限额：2000
     * 通道可用资金：1000
     * 输入股票代码600022、委托价格2.0以及委托数量1000
     * 结果：废单，原因是：可用资金不足 限额：1000。
     */
    @Test
    public void testPlacementBuy5(){
        User user = new User();
        user.setUserTradeType(UserTradeType.DIVIDE);
        user.setAvailable(new BigDecimal(3000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(1000),new BigDecimal(2.0),new BigDecimal(2000),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), new BigDecimal(2000));
        channelList.add(channel1);
            List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateBuyPlacementChannel(user,channelList,placement);
            Assert.assertTrue(placementChannelDtoList.size() == 1);
    }

    /**
     * 各通道限额：4000
     * 用户可用余额：5000
     * 通道1可用资金：1000
     * 通道2可用资金：2000
     * 通道3可用资金：3000
     * 通道4可用资金：4000
     * 输入股票代码600022、委托价格2.0以及委托数量100
     * 结果：下单成功，共一笔通道委托，任意一个通道买入100股。有委托的通道限额：3800，其他通道限额不变
     */
    @Test
    public void testPlacementBuy6(){
        User user = new User();
        user.setUserTradeType(UserTradeType.DIVIDE);
        user.setAvailable(new BigDecimal(5000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(100),new BigDecimal(2.0),new BigDecimal(200),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), new BigDecimal(4000));
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(1000), new BigDecimal(4000));
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(1000), new BigDecimal(4000));
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(1000), new BigDecimal(4000));

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateBuyPlacementChannel(user,channelList,placement);
        Assert.assertTrue(placementChannelDtoList.size() == 1);
        /*for (BuyLimitChannel channel : channelList) {
            if (channel.getId().intValue() == placementChannelDtoList.get(0).getChannelId().intValue()){
                Assert.assertTrue(channel.getQuota().compareTo(new BigDecimal(3800)) == 0);
                break;
            }
        }*/
    }

    /**
     * 各通道限额：4000
     * 用户可用余额：10000
     * 通道1可用资金：1000
     * 通道2可用资金：1000
     * 通道3可用资金：1000
     * 通道4可用资金：1000
     * 输入股票代码600022、委托价格2.0以及委托数量5000
     * 结果：下单成功，共四笔通道委托，每个通道各买入100股。
     各通道限额：3800
     */
    @Test
    public void testPlacementBuy7() {
        User user = new User();
        user.setUserTradeType(UserTradeType.DIVIDE);
        user.setAvailable(new BigDecimal(6000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(400),new BigDecimal(2.0),new BigDecimal(600),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), new BigDecimal(4000));
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(1000), new BigDecimal(4000));
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(1000), new BigDecimal(4000));
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(1000), new BigDecimal(4000));

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateBuyPlacementChannel(user,channelList,placement);
        Assert.assertTrue(placementChannelDtoList.size() == 4);
    }
    /**
     * 各通道限额：4000
     * 用户可用余额：5000
     * 通道1可用资金：399
     * 通道2可用资金：399
     * 通道3可用资金：399
     * 通道4可用资金：399
     * 输入股票代码600022、委托价格2.0以及委托数量400
     * 结果 废单，原因是：可用资金不足
     各通道限额：4000
     */
    @Test
    public void testPlacementBuy8() {
        User user = new User();
        user.setUserTradeType(UserTradeType.DIVIDE);
        user.setAvailable(new BigDecimal(6000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(500),new BigDecimal(2.0),new BigDecimal(600),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(399), new BigDecimal(4000));
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(399), new BigDecimal(4000));
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(399), new BigDecimal(4000));
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(399), new BigDecimal(4000));

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateBuyPlacementChannel(user,channelList,placement);
        Assert.assertTrue(placementChannelDtoList.size() == 4);
    }


    /**
     * 各通道限额：4000
     * 用户可用余额：5000
     * 通道1可用资金：1000
     * 通道2可用资金：1000
     * 通道3可用资金：1000
     * 通道4可用资金：1000
     * 输入股票代码600022、委托价格2.0以及委托数量500
     * 结果：下单成功，共一笔通道委托，
     1：通道4买入1000股；
     */
    @Test
    public void testPlacementBuy9() {
        User user = new User();
        user.setUserTradeType(UserTradeType.DIVIDE);
        user.setAvailable(new BigDecimal(5000));
        Placement placement = new Placement("600022", TradeSide.BUY, new BigDecimal(500), new BigDecimal(2.0), new BigDecimal(2000), 3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), new BigDecimal(4000));
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(1000), new BigDecimal(4000));
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(1000), new BigDecimal(4000));
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(1000), new BigDecimal(4000));

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList = placementConvertServiceImpl.generateBuyPlacementChannel(user, channelList, placement);

        Assert.assertTrue(placementChannelDtoList.size() == 4);
    }
    /**
     * 各通道限额：500
     * 用户可用余额：10000
     * 通道1可用资金：1000
     * 通道2可用资金：1000
     * 通道3可用资金：1000
     * 通道4可用资金：1000
     * 输入股票代码600022、委托价格2.0以及委托数量4000
     * 结果：下单成功，共三笔通道委托
     1：通道2买入500股；
     2：通道3买入1500股；
     3：通道4买入2000股。
     */
    @Test
    public void testPlacementBuy10() {
        User user = new User();
        user.setUserTradeType(UserTradeType.DIVIDE);
        user.setAvailable(new BigDecimal(6000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(500),new BigDecimal(2.0),new BigDecimal(600),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), new BigDecimal(4000));
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(1000), new BigDecimal(4000));
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(1000), new BigDecimal(4000));
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(1000), new BigDecimal(4000));

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateBuyPlacementChannel(user,channelList,placement);
        Assert.assertTrue(placementChannelDtoList.size() == 4);
    }

    /**
     * 各通道限额：10000
     * 用户可用余额：20000
     * 通道1可用资金：1199
     * 通道2可用资金：2199
     * 通道3可用资金：3199
     * 通道4可用资金：4199
     * 输入股票代码600022、委托价格2.0以及委托数量5300
     * 结果：废单，原因是：可用资金不足
     */
    @Test
    public void testPlacementBuy11() {
        User user = new User();
        user.setUserTradeType(UserTradeType.DIVIDE);
        user.setAvailable(new BigDecimal(6000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(2000),new BigDecimal(2.0),new BigDecimal(600),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), new BigDecimal(500));
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(1000), new BigDecimal(500));
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(1000), new BigDecimal(500));
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(1000), new BigDecimal(500));

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateBuyPlacementChannel(user,channelList,placement);
        Assert.assertTrue(placementChannelDtoList.size() == 4);
    }

    /**
     * 各通道限额：10000
     * 用户可用余额：20000
     通道1可用资金：199
     通道2可用资金：199
     通道3可用资金：199
     通道4可用资金：199
     * 输入股票代码600022、委托价格2.0以及委托数量100
     * 结果：废单，原因是：可用资金不足
     */
    @Test
    public void testPlacementBuy12() {
        User user = new User();
        user.setUserTradeType(UserTradeType.DIVIDE);
        user.setAvailable(new BigDecimal(6000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(400),new BigDecimal(2.0),new BigDecimal(600),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), new BigDecimal(10000));
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(2000), new BigDecimal(10000));
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(3000), new BigDecimal(10000));
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(4000), new BigDecimal(10000));

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateBuyPlacementChannel(user,channelList,placement);
        Assert.assertTrue(placementChannelDtoList.size() == 4);
    }
    /**
     * 各通道限额：10000
     * 用户可用余额：20000
     通道1可用资金：1000
     通道2可用资金：2000
     通道3可用资金：3000
     通道4可用资金：4000
     * 输入股票代码600022、委托价格2.0以及委托数量6000
     * 结果：废单，原因是：可用资金不足
     */
    @Test
    public void testPlacementBuy13() {
        User user = new User();
        user.setUserTradeType(UserTradeType.DIVIDE);
        user.setAvailable(new BigDecimal(6000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(500),new BigDecimal(2.0),new BigDecimal(600),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), new BigDecimal(10000));
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(2000), new BigDecimal(10000));
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(3000), new BigDecimal(10000));
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(4000), new BigDecimal(10000));

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateBuyPlacementChannel(user,channelList,placement);
        Assert.assertTrue(placementChannelDtoList.size() == 4);
    }

    /**
     * 各通道限额：10000
     * 用户可用余额：20000
     通道1可用资金：1000
     通道2可用资金：2000
     通道3可用资金：3000
     通道4可用资金：4000
     * 输入股票代码600022、委托价格2.0以及委托数量4100
     * 结果：废单，原因是：可用资金不足
     */
    @Test
    public void testPlacementBuy14() {
        User user = new User();
        user.setUserTradeType(UserTradeType.DIVIDE);
        user.setAvailable(new BigDecimal(6000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(2400),new BigDecimal(2.0),new BigDecimal(600),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), new BigDecimal(10000));
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(2000), new BigDecimal(10000));
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(3000), new BigDecimal(10000));
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(4000), new BigDecimal(10000));

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateBuyPlacementChannel(user,channelList,placement);
        Assert.assertTrue(placementChannelDtoList.size() == 4);
    }
    /**
     * 各通道限额：10000
     * 用户可用余额：20000
     通道1可用资金：1000
     通道2可用资金：2000
     通道3可用资金：3000
     通道4可用资金：4000
     * 输入股票代码600022、委托价格2.0以及委托数量4100
     * 结果：废单，原因是：可用资金不足
     */
    @Test
    public void testPlacementBuy15() {
        User user = new User();
        user.setUserTradeType(UserTradeType.DIVIDE);
        user.setAvailable(new BigDecimal(6000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(4100),new BigDecimal(2.0),new BigDecimal(600),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), new BigDecimal(10000));
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(2000), new BigDecimal(10000));
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(3000), new BigDecimal(10000));
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(4000), new BigDecimal(10000));

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateBuyPlacementChannel(user,channelList,placement);
        Assert.assertTrue(placementChannelDtoList.size() == 4);
    }

    /**
     * 各通道限额：10000
     * 用户可用余额：20000
     通道1可用资金：1000
     通道2可用资金：2000
     通道3可用资金：3000
     通道4可用资金：4000
     * 输入股票代码600022、委托价格2.0以及委托数量4100
     * 结果：废单，原因是：可用资金不足
     */
    @Test
    public void testPlacementBuy16() {
        User user = new User();
        user.setUserTradeType(UserTradeType.DIVIDE);
        user.setAvailable(new BigDecimal(6000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(4200),new BigDecimal(2.0),new BigDecimal(600),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), new BigDecimal(10000));
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(2000), new BigDecimal(10000));
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(3000), new BigDecimal(10000));
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(4000), new BigDecimal(10000));

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateBuyPlacementChannel(user,channelList,placement);
        Assert.assertTrue(placementChannelDtoList.size() == 4);
    }
    /**
     * 各通道限额：10000
     * 用户可用余额：20000
     通道1可用资金：1000
     通道2可用资金：2000
     通道3可用资金：3000
     通道4可用资金：4000
     * 输入股票代码600022、委托价格2.0以及委托数量4100
     * 结果：废单，原因是：可用资金不足
     */
    @Test
    public void testPlacementBuy17() {
        User user = new User();
        user.setUserTradeType(UserTradeType.DIVIDE);
        user.setAvailable(new BigDecimal(6000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(5000),new BigDecimal(2.0),new BigDecimal(600),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), new BigDecimal(10000));
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(2000), new BigDecimal(10000));
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(3000), new BigDecimal(10000));
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(4000), new BigDecimal(10000));

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateBuyPlacementChannel(user,channelList,placement);
        Assert.assertTrue(placementChannelDtoList.size() == 4);
        for (PlacementChannelDto placementChannelDto:placementChannelDtoList) {
            int channelId = placementChannelDto.getChannelId().intValue();
            int handleQuantity = placementChannelDto.getHandleQuantity().intValue();
            if (channelId == 1001){
                Assert.assertTrue(handleQuantity == 500);
            }else if (channelId == 1002){
                Assert.assertTrue(handleQuantity == 1000);
            }
            else if (channelId == 1003){
                Assert.assertTrue(handleQuantity == 1500);
            }else {
                Assert.assertTrue(channelId == 1004);
                Assert.assertTrue(handleQuantity == 2000);
            }

        }
    }

    /**
     * 各通道限额：10000
     * 用户可用余额：20000
     通道1可用资金：1000
     通道2可用资金：2000
     通道3可用资金：3000
     通道4可用资金：4000
     * 输入股票代码600022、委托价格2.0以及委托数量4100
     * 结果：废单，原因是：可用资金不足
     */
    @Test
    public void testPlacementBuy18() {
        User user = new User();
        user.setUserTradeType(UserTradeType.DIVIDE);
        user.setAvailable(new BigDecimal(6000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(6000),new BigDecimal(2.0),new BigDecimal(600),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), new BigDecimal(10000));
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(2000), new BigDecimal(10000));
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(3000), new BigDecimal(10000));
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(4000), new BigDecimal(10000));

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateBuyPlacementChannel(user,channelList,placement);
        Assert.assertTrue(placementChannelDtoList.size() == 4);
    }

    /**
     * 各通道限额：10000
     * 用户可用余额：20000
     通道1可用资金：1000
     通道2可用资金：2000
     通道3可用资金：3000
     通道4可用资金：4000
     * 输入股票代码600022、委托价格2.0以及委托数量4100
     * 结果：废单，原因是：可用资金不足
     */
    @Test
    public void testPlacementBuy19() {
        User user = new User();
        user.setUserTradeType(UserTradeType.DIVIDE);
        user.setAvailable(new BigDecimal(6000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(5000),new BigDecimal(2.0),new BigDecimal(600),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), new BigDecimal(800));
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(2000), new BigDecimal(800));
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(3000), new BigDecimal(800));
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(4000), new BigDecimal(800));

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateBuyPlacementChannel(user,channelList,placement);
        Assert.assertTrue(placementChannelDtoList.size() == 4);
    }
        /**
         * 用例4
         * 卖出1
         * 通道中用户可用：1000
         * 输入股票代码600022、委托价格2.65以及委托数量1000
         * 结果：下单成功，通道中有一笔委托，卖出1000股。
         */
    @Test
    public void testPlacementSells20(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(1000),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition channelSecurityPosition = new ChannelSecurityPosition(new BigDecimal(1000),221,"77077");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(channelSecurityPosition);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable, placement, channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList.size() == 1);
    }
    /**
     * 用例5
     * 卖出2
     * 通道中用户可用：1000
     * 输入股票代码600022、委托价格2.65以及委托数量2000
     * 结果：不能下单，提示：可卖数量不足。
     */
    @Test
    public void testPlacementSells21(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(2000),new BigDecimal("2.65"),new BigDecimal(5300),3);

        ChannelSecurityPosition channelSecurityPosition = new ChannelSecurityPosition(new BigDecimal(1000),221,"77077");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(channelSecurityPosition);
        BigDecimal userAvailable = new BigDecimal(1000);
        try {
            List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
            Assert.fail();
        }catch ( Exception e){
            Assert.assertTrue(StringUtils.endsWith(e.getMessage(),ExceptionConstants.SELL_QUANTITY_NOT_ENOUGH));
        }
    }
    /**
     * 用例6
     * 卖出
     * 通道中用户可用：1000
     * 输入股票代码600022、委托价格2.65以及委托数量510
     * 结果：不能下单，提示：委托中有零股。
     */
    @Test
    public void testPlacementSells22(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(510),new BigDecimal("2.65"),new BigDecimal(1351.5),3);

        ChannelSecurityPosition channelSecurityPosition = new ChannelSecurityPosition(new BigDecimal(1000),221,"77077");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(channelSecurityPosition);
        BigDecimal userAvailable = new BigDecimal(10000);
        try {
            List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
            Assert.fail();
        }catch ( Exception e){
            Assert.assertTrue(StringUtils.endsWith(e.getMessage(), ExceptionConstants.PLACEMENT_QUANTITY_LEFT));
        }
    }
    /**
     * 用例7
     * 卖出
     * 通道中用户可用：500
     * 输入股票代码600022、委托价格2.65以及委托数量1000
     * 结果：不能下单，提示：可卖数量不足。
     */
    @Test
    public void testPlacementSells23(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(1000),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition channelSecurityPosition = new ChannelSecurityPosition(new BigDecimal(500),221,"77077");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(channelSecurityPosition);
        BigDecimal userAvailable = new BigDecimal(100);
        try {
            List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
            Assert.fail();
        }catch ( Exception e){
            Assert.assertTrue(StringUtils.endsWith(e.getMessage(),ExceptionConstants.SELL_QUANTITY_NOT_ENOUGH));
        }
    }
    /**
     * 用例8
     * 卖出
     * 通道中用户可用：1050
     * 输入股票代码600022、委托价格2.65以及委托数量1000
     * 结果：下单成功，通道中有一笔委托，卖出1000股。
     */
    @Test
    public void testPlacementSells24(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(1000),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition channelSecurityPosition = new ChannelSecurityPosition(new BigDecimal(1050),221,"77077");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(channelSecurityPosition);
        BigDecimal userAvailable = new BigDecimal(1050);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(placementChannelDtoList.size() == 1);

        Assert.assertTrue(placementChannelDtoList.get(0).getHandleQuantity().intValue() == 1000);
    }
    /**
     * 用例9
     * 卖出
     * 通道中用户可用：1050
     * 输入股票代码600022、委托价格2.65以及委托数量1050
     * 结果：下单成功，通道中有一笔委托，卖出1050股。
     */
    @Test
    public void testPlacementSells25(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(1050),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition channelSecurityPosition = new ChannelSecurityPosition(new BigDecimal(1050),221,"77077");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(channelSecurityPosition);
        BigDecimal userAvailable = new BigDecimal(1050);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(placementChannelDtoList.size() == 2);
        /*Assert.assertTrue(placementChannelDtoList.get(0).getHandleQuantity().intValue() == 1050);*/
    }
    /**
     * 用例10
     * 卖出
     * 通道中用户可用：1050
     * 输入股票代码600022、委托价格2.65以及委托数量550
     * 结果：不能下单，提示：零股必须全部卖出。
     */
    @Test
    public void testPlacementSells26(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(550),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition channelSecurityPosition = new ChannelSecurityPosition(new BigDecimal(1050),221,"77077");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(channelSecurityPosition);
        BigDecimal userAvailable = new BigDecimal(1050);
        try {
            List<PlacementChannelDto> placementChannelDtoList = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
            Assert.fail();
        }catch ( Exception e){
            Assert.assertTrue(StringUtils.endsWith(e.getMessage(),ExceptionConstants.PLACEMENT_QUANTITY_LEFT));
        }
    }
    /**
     * 用例11
     * 卖出
     * 通道中用户可用：1050
     * 输入股票代码600022、委托价格2.65以及委托数量1000
     * 结果：下单成功，共一笔通道委托，任意一个通道卖出1000股
     */
    @Test
    public void testPlacementSells27(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(100),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition channelSecurityPosition1 = new ChannelSecurityPosition(new BigDecimal(1000),221,"77071");
        ChannelSecurityPosition channelSecurityPosition2= new ChannelSecurityPosition(new BigDecimal(1000),222,"77071");
        ChannelSecurityPosition channelSecurityPosition3 = new ChannelSecurityPosition(new BigDecimal(1000),223,"77071");
        ChannelSecurityPosition channelSecurityPosition4 = new ChannelSecurityPosition(new BigDecimal(1000),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(channelSecurityPosition1);
        channelSecurityPositionList.add(channelSecurityPosition2);
        channelSecurityPositionList.add(channelSecurityPosition3);
        channelSecurityPositionList.add(channelSecurityPosition4);
        BigDecimal userAvailable = new BigDecimal(4000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size() == 1);
        Assert.assertTrue(channelSecurityPositionList1.get(0).getHandleQuantity().intValue() == 100);
    }
    /**
     * 用例12
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量5000
     * 结果：不能下单，提示：可卖数量不足
     */
    @Test
    public void testPlacementSells28(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(400),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition channelSecurityPosition1 = new ChannelSecurityPosition(new BigDecimal(1000),221,"77071");
        ChannelSecurityPosition channelSecurityPosition2 = new ChannelSecurityPosition(new BigDecimal(1000),222,"77071");
        ChannelSecurityPosition channelSecurityPosition3 = new ChannelSecurityPosition(new BigDecimal(1000),223,"77071");
        ChannelSecurityPosition channelSecurityPosition4 = new ChannelSecurityPosition(new BigDecimal(1000),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(channelSecurityPosition1);
        channelSecurityPositionList.add(channelSecurityPosition2);
        channelSecurityPositionList.add(channelSecurityPosition3);
        channelSecurityPositionList.add(channelSecurityPosition4);
        BigDecimal userAvailable = new BigDecimal(4000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size() == 4);
        Assert.assertTrue(channelSecurityPositionList1.get(0).getHandleQuantity().intValue() == 100);
        Assert.assertTrue(channelSecurityPositionList1.get(1).getHandleQuantity().intValue() == 100);
        Assert.assertTrue(channelSecurityPositionList1.get(2).getHandleQuantity().intValue() == 100);
        Assert.assertTrue(channelSecurityPositionList1.get(3).getHandleQuantity().intValue() == 100);

    }
    /**
     * 用例13
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量800
     * 结果：下单成功，共一笔通道委托，通道4卖出800股；
     */
    @Test
    public void testPlacementSells29(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(400),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(1000),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(0),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(0),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(0),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size() == 1);
        Assert.assertTrue(channelSecurityPositionList1.get(0).getHandleQuantity().intValue() == 400);
    }
    /**
     * 用例14
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量550
     * 结果：下单成功，共两笔通道委托：
     1：通道4卖出1500股；
     2：通道2卖出500。
     */
    @Test
    public void testPlacementSells30(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(500),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(500),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1000),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(1500),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(2000),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size() == 4);
        for (PlacementChannelDto channelSecurityPosition : channelSecurityPositionList1) {
            if(channelSecurityPosition.getChannelId() == 224){
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 100);
            }else if (channelSecurityPosition.getChannelId() == 223) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 100);
            }
        }
    }
    /**
     * 用例15
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量4000
     * 结果：下单成功，共四笔通道委托：
     1：通道4卖出1500股；
     2：通道2卖出1200；
     3：通道1卖出800股；
     4：通道3卖出500股。
     */
    @Test
    public void testPlacementSells31(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(2000),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(500),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1000),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(1500),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(2000),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size() == 4);

        Assert.assertTrue(channelSecurityPositionList1.get(0).getHandleQuantity().intValue() == 500);
        Assert.assertTrue(channelSecurityPositionList1.get(1).getHandleQuantity().intValue() == 500);
        Assert.assertTrue(channelSecurityPositionList1.get(2).getHandleQuantity().intValue() == 500);
        Assert.assertTrue(channelSecurityPositionList1.get(3).getHandleQuantity().intValue() == 500);
    }
    /**
     * 用例16
     * 卖出
     * 通道中用户可用：1100
     * 输入股票代码600022、委托价格2.65以及委托数量1110
     * 不能下单，提示：委托中有零股。
     */
    @Test
    public void testPlacementSells32(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(2500),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(500),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1000),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(1500),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(2000),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size() == 4);
        for (PlacementChannelDto channelSecurityPosition : channelSecurityPositionList1) {
            if (channelSecurityPosition.getChannelId() == 224) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 600);
            }else if (channelSecurityPosition.getChannelId() == 222) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 800);
            }else if (channelSecurityPosition.getChannelId() == 221) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 500);
            }
        }
    }
    /**
     * 用例17
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量1500
     * 下单成功，共一笔通道委托，通道4卖出1500股；。
     */
    @Test
    public void testPlacementSells33(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(5000),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(500),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1000),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(1500),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(2000),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size() == 4);
        for (PlacementChannelDto channelSecurityPosition : channelSecurityPositionList1) {
            if (channelSecurityPosition.getChannelId() == 224) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 2000);
            }else if (channelSecurityPosition.getChannelId() == 222) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 1000);
            }else if (channelSecurityPosition.getChannelId() == 221) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 500);
            }
        }
    }

    /**
     * 用例18
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量2600
     * 下单成功，共两笔通道委托：
     1：通道4卖出1500股；
     2：通道2卖出1100。
     */
    @Test
    public void testPlacementSells34(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(1110),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(800),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1200),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(500),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(1500),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        try {
            List<PlacementChannelDto> placementChannelDtoList = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
            Assert.fail();
        }catch ( Exception e){
            Assert.assertTrue(StringUtils.endsWith(e.getMessage(),ExceptionConstants.PLACEMENT_QUANTITY_LEFT));
        }
    }

    /**
     * 用例19
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量3300
     * 下单成功，共三笔通道委托：
     1：通道4卖出1500股；
     2：通道2卖出1100；
     3：通道1卖出700股。
     */
    @Test
    public void testPlacementSells35(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(2000),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(540),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1060),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(1530),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(2070),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size() == 4);

        Assert.assertTrue(channelSecurityPositionList1.get(0).getHandleQuantity().intValue() == 500);
        Assert.assertTrue(channelSecurityPositionList1.get(1).getHandleQuantity().intValue() == 500);
        Assert.assertTrue(channelSecurityPositionList1.get(2).getHandleQuantity().intValue() == 500);
        Assert.assertTrue(channelSecurityPositionList1.get(3).getHandleQuantity().intValue() == 500);
    }
    /**
     * 用例20
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量3800
     * 下单成功，共三笔通道委托：
     1：通道4卖出1500股；
     2：通道2卖出1100；
     3：通道1卖出700股；
     4：通道3卖出500股。
     */
    @Test
    public void testPlacementSells36(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(2500),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(540),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1060),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(1530),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(2070),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size() == 4);

        for (PlacementChannelDto channelSecurityPosition : channelSecurityPositionList1) {
            if (channelSecurityPosition.getChannelId() == 224) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 600);
            }else if (channelSecurityPosition.getChannelId() == 222) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 800);
            }else if (channelSecurityPosition.getChannelId() == 221) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 500);
            }else if (channelSecurityPosition.getChannelId() == 223) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 600);
            }
        }
    }
    /**
     * 用例21
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量3900
     * 下单成功，共六笔通道委托：
     1：通道4卖出1500股；
     2：通道2卖出1100；
     3：通道1卖出700股；
     4：通道3卖出500股；
     5：通道4卖出70股；
     6：通道3卖出30股。
     */
    @Test
    public void testPlacementSells37(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(5000),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(540),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1060),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(1530),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(2070),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size() == 4);

        for (PlacementChannelDto channelSecurityPosition : channelSecurityPositionList1) {
            if (channelSecurityPosition.getChannelId() == 224) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 2000);
            }else if (channelSecurityPosition.getChannelId() == 222) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 1000);
            }else if (channelSecurityPosition.getChannelId() == 221) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 500);
            }else if (channelSecurityPosition.getChannelId() == 223) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 1500);
            }
        }
    }

    /**
     * 用例21
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量3900
     * 下单成功，共六笔通道委托：
     1：通道4卖出1500股；
     2：通道2卖出1100；
     3：通道1卖出700股；
     4：通道3卖出500股；
     5：通道4卖出70股；
     6：通道3卖出30股。
     */
    @Test
    public void testPlacementSells38(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(5100),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(540),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1060),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(1530),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(2070),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size() == 6);

        for (PlacementChannelDto channelSecurityPosition : channelSecurityPositionList1) {
            if (channelSecurityPosition.getChannelId() == 221) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 500);
            }else if (channelSecurityPosition.getChannelId() == 222) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 1000);
            }/*else if (channelSecurityPosition.getChannelId() == 223) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 1500);
            }else if (channelSecurityPosition.getChannelId() == 224) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 2000);
            }*//*else if (channelSecurityPosition.getChannelId() == 221) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 40);
            }else if (channelSecurityPosition.getChannelId() == 222) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 60);
            }*/
        }
    }

    /**
     * 用例22
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量4000
     * 下单成功，共八笔通道委托：
     1：通道4卖出1500股；
     2：通道2卖出1100；
     3：通道1卖出700股；
     4：通道3卖出500股；
     5：通道4卖出70股；
     6：通道3卖出30股；
     7：通道2卖出60股；
     8：通道1卖出40股。
     */
    @Test
    public void testPlacementSells39(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(5200),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(540),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1060),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(1530),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(2070),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size() == 8);
            /*for (ChannelSecurityPosition channelSecurityPosition : channelSecurityPositionList1) {
                if (channelSecurityPosition.getChannelId() == 224) {
                    switch (channelSecurityPosition.getHandleQuantity().intValue()) {
                    case 1500:
                        Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 1500);
                        break;
                    case 70:
                        Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 1500);
                        break;
                    default:
                        break;
                    }
                }else if (channelSecurityPosition.getChannelId() == 222) {
                    Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 1100);
                }else if (channelSecurityPosition.getChannelId() == 221) {
                    Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 700);
                }else if (channelSecurityPosition.getChannelId() == 223) {
                    Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 500);
                }
            }*/
    }

    @Test
    public void testPlacementSells40(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(2700),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(500),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1000),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(1530),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(2070),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size() == 4);

        for (PlacementChannelDto channelSecurityPosition : channelSecurityPositionList1) {
            if (channelSecurityPosition.getChannelId() == 224) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 600);
            }else if (channelSecurityPosition.getChannelId() == 222) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 1000);
            }else if (channelSecurityPosition.getChannelId() == 221) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 500);
            }else if (channelSecurityPosition.getChannelId() == 223) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 600);
            }
        }
    }

    /**
     * 用例23
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量3850
     * 不能下单，提示：零股必须全部卖出。。
     */
    @Test
    public void testPlacementSells41(){
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(3850),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(540),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1060),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(1530),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(2070),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        try {
            List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(StringUtils.equals(e.getMessage(), ExceptionConstants.PLACEMENT_QUANTITY_LEFT));
        }

    }

    /**
     * 用例24
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量3900
     * 下单成功，共六笔通道委托：
     1：通道4卖出1500股；
     2：通道2卖出1100；
     3：通道1卖出700股；
     4：通道3卖出500股；
     5：通道4卖出80股；
     6：通道1或者通道2或者通道3卖出20股；
     */
    @Test
    public void testPlacementSells42(){
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(2000),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(580),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1060),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(1530),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(2080),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(4050);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size() == 4);

        Assert.assertTrue(channelSecurityPositionList1.get(0).getHandleQuantity().intValue() == 500);
        Assert.assertTrue(channelSecurityPositionList1.get(1).getHandleQuantity().intValue() == 500);
        Assert.assertTrue(channelSecurityPositionList1.get(2).getHandleQuantity().intValue() == 500);
        Assert.assertTrue(channelSecurityPositionList1.get(3).getHandleQuantity().intValue() == 500);

    }

    /**
     * 用例25
     * 卖出
     * 通道中用户可用：4050
     * 输入股票代码600022、委托价格2.65以及委托数量4050
     * 下单成功，共八笔通道委托：
     1：通道4卖出1500股；
     2：通道2卖出1100；
     3：通道1卖出700股；
     4：通道3卖出500股；
     5：通道4卖出80股；
     6：通道1卖出80股；
     7：通道2卖出60股；
     8：通道3卖出30股。
     */
    @Test
    public void testPlacementSells43(){

        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(5100),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(580),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1060),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(1530),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(2080),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(4050);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size()==6);

    }

    /**
     * 用例26
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量5250
     * 下单成功，共8笔通道委托；
     */
    @Test
    public void testPlacementSells44(){
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(5250),new BigDecimal("2.65"),new BigDecimal("13912.5"),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(580),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1060),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(1530),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(2080),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(5250);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.DIVIDE,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size()==8);

    }

}
