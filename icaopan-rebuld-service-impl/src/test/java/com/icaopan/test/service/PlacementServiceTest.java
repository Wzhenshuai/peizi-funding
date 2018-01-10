package com.icaopan.test.service;

import com.icaopan.common.util.ExceptionConstants;
import com.icaopan.customer.model.BuyLimitChannel;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.enums.enumBean.UserChannelType;
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
public class PlacementServiceTest extends BaseTestService{

    @SpringBeanByType
    PlacementService placementService;
    @SpringBeanByType
    PlacementConvertServiceImpl placementConvertServiceImpl;
    /**
     * 可用余额：1000
     * 输入股票代码600022、委托价格2.0以及委托数量100
     * 结果：下单成功，默认通道中有一笔委托，买入100股。
     *
     */
    @Test
    public void testPlacementBuy2(){
        User user = new User();
        user.setAvailable(new BigDecimal(1000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(100),new BigDecimal(2.0),new BigDecimal(200),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), 1, Boolean.TRUE);
        channelList.add(channel1);
        List<PlacementChannelDto> placementChannelDtoList = placementConvertServiceImpl.generateBuyPlacementChannel(user, channelList, placement);
        Assert.assertTrue(placementChannelDtoList.size() == 1);
    }

    /**
     * 用户可用余额：1000
     * 通道可用资金：3000
     * 输入股票代码600022、委托价格2.0以及委托数量1000
     * 结果：不能下单，提示：可用资金不足。
     *
     * Placement(String securityCode, TradeSide side, BigDecimal quantity, BigDecimal price, BigDecimal amount, Integer userId)
     * ChannelSecurityPosition(BigDecimal available, Integer channelId, String channelCode)
     */
    @Test
    public void testPlacementBuy3(){
        User user = new User();
        user.setAvailable(new BigDecimal(1000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(1000),new BigDecimal(2.0),new BigDecimal(2000),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(3000), 1, Boolean.TRUE);
        channelList.add(channel1);
        try {
            List<PlacementChannelDto> placementChannelDtoList = placementConvertServiceImpl.generateBuyPlacementChannel(user, channelList, placement);
            Assert.fail();
        }catch (Exception e){
            Assert.assertTrue(StringUtils.endsWith(e.getMessage(),ExceptionConstants.CASH_NOT_ENOUGH));
        }
    }
    /**
     * 用户可用余额：3000
     * 通道可用资金：1000
     * 输入股票代码600022、委托价格2.0以及委托数量1000
     * 结果：废单 提示：可用资金不足。
     */
    @Test
    public void testPlacementBuy4(){
        User user = new User();
        user.setAvailable(new BigDecimal(3000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(1000),new BigDecimal(2.0),new BigDecimal(2000),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(3000), 1, Boolean.TRUE);
        channelList.add(channel1);
            List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateBuyPlacementChannel(user,channelList,placement);
            Assert.assertTrue(placementChannelDtoList.size() == 1);
    }

    /**
     * 通道优先级（1=2=3=4）
     * 用户可用余额：5000
     * 通道1可用资金：1000
     * 通道2可用资金：2000
     * 通道3可用资金：3000
     * 通道4可用资金：4000
     * 输入股票代码600022、委托价格2.0以及委托数量100
     * 结果：下单成功，共一笔通道委托，任意一个通道买入100股。
     */
    @Test
    public void testPlacementBuy5(){
        User user = new User();
        user.setAvailable(new BigDecimal(5000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(100),new BigDecimal(2.0),new BigDecimal(200),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), 1, Boolean.TRUE);
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(2000), 1, Boolean.TRUE);
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(3000), 1, Boolean.TRUE);
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(4000), 1, Boolean.TRUE);

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateBuyPlacementChannel(user,channelList,placement);
        Assert.assertTrue(placementChannelDtoList.size() == 1);
    }

    /**
     * 通道优先级（1=2=3=4）
     * 用户可用余额：5000
     * 通道1可用资金：1000
     * 通道2可用资金：2000
     * 通道3可用资金：3000
     * 通道4可用资金：4000
     * 输入股票代码600022、委托价格2.0以及委托数量3000
     * 结果：下单成功，共二笔通道委托，
     1：通道4买入2000股；
     2：通道3或者通道2买入1000股。
     */
    @Test
    public void testPlacementBuy6(){
        User user = new User();
        user.setAvailable(new BigDecimal(6000));
        Placement placement = new Placement("600022",TradeSide.BUY,new BigDecimal(3000),new BigDecimal(2.0),new BigDecimal(6000),3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), 1, Boolean.TRUE);
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(2000), 1, Boolean.TRUE);
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(3000), 1, Boolean.TRUE);
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(4000), 1, Boolean.TRUE);

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateBuyPlacementChannel(user,channelList,placement);
        Assert.assertTrue(placementChannelDtoList.size() == 2);
    }

    /**
     * 通道优先级（1=2=3=4）
     * 用户可用余额：10000
     * 通道1可用资金：1000
     * 通道2可用资金：2000
     * 通道3可用资金：3000
     * 通道4可用资金：4000
     * 输入股票代码600022、委托价格2.0以及委托数量5000
     * 结果：下单成功，共二笔通道委托，
     1：通道1买入500股；
     2：通道2买入500股。
     */
    @Test
    public void testPlacementBuy7() {
        User user = new User();
        user.setAvailable(new BigDecimal(10000));
        Placement placement = new Placement("600022", TradeSide.BUY, new BigDecimal(5000), new BigDecimal(2.0), new BigDecimal(10000), 3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), 1, Boolean.TRUE);
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(2000), 1, Boolean.TRUE);
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(3000), 1, Boolean.TRUE);
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(4000), 1, Boolean.TRUE);

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList = placementConvertServiceImpl.generateBuyPlacementChannel(user, channelList, placement);

        Assert.assertTrue(placementChannelDtoList.size() == 4);
    }
    /**
     * 通道优先级（1>2>3>4）
     * 用户可用余额：5000
     * 通道1可用资金：1000
     * 通道2可用资金：2000
     * 通道3可用资金：3000
     * 通道4可用资金：4000
     * 输入股票代码600022、委托价格2.0以及委托数量100
     * 结果 下单成功，共一笔通道委托，通道1买入100股
     */
    @Test
    public void testPlacementBuy8() {
        User user = new User();
        user.setAvailable(new BigDecimal(5000));
        Placement placement = new Placement("600022", TradeSide.BUY, new BigDecimal(100), new BigDecimal(2.0), new BigDecimal(200), 1);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), 4, Boolean.TRUE);
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(2000), 3, Boolean.TRUE);
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(3000), 2, Boolean.TRUE);
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(4000), 1, Boolean.TRUE);

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList = placementConvertServiceImpl.generateBuyPlacementChannel(user, channelList, placement);

        Assert.assertTrue(placementChannelDtoList.size() == 1);
    }


    /**
     * 通道优先级（1=2=3=4）
     * 用户可用余额：5000
     * 通道1可用资金：1000
     * 通道2可用资金：2000
     * 通道3可用资金：3000
     * 通道4可用资金：4000
     * 输入股票代码600022、委托价格2.0以及委托数量1000
     * 结果：下单成功，共一笔通道委托，
     1：通道4买入1000股；
     */
    @Test
    public void testPlacementBuy9() {
        User user = new User();
        user.setAvailable(new BigDecimal(5000));
        Placement placement = new Placement("600022", TradeSide.BUY, new BigDecimal(1000), new BigDecimal(2.0), new BigDecimal(2000), 3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), 1, Boolean.TRUE);
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(2000), 1, Boolean.TRUE);
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(3000), 1, Boolean.TRUE);
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(4000), 1, Boolean.TRUE);

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList = placementConvertServiceImpl.generateBuyPlacementChannel(user, channelList, placement);

        Assert.assertTrue(placementChannelDtoList.size() == 1);
    }
    /**
     * 通道优先级（1=2=3=4）
     * 用户可用余额：10000
     * 通道1可用资金：1000
     * 通道2可用资金：2000
     * 通道3可用资金：3000
     * 通道4可用资金：4000
     * 输入股票代码600022、委托价格2.0以及委托数量4000
     * 结果：下单成功，共三笔通道委托
     1：通道2买入500股；
     2：通道3买入1500股；
     3：通道4买入2000股。
     */
    @Test
    public void testPlacementBuy10() {
        User user = new User();
        user.setAvailable(new BigDecimal(10000));
        Placement placement = new Placement("600022", TradeSide.BUY, new BigDecimal(4000), new BigDecimal(2.0), new BigDecimal(8000), 3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1000), 1, Boolean.TRUE);
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(2000), 1, Boolean.TRUE);
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(3000), 1, Boolean.TRUE);
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(4000), 1, Boolean.TRUE);

        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList = placementConvertServiceImpl.generateBuyPlacementChannel(user, channelList, placement);

        Assert.assertTrue(placementChannelDtoList.size() == 3);
    }

    /**
     * 通道优先级（1>2>3>4）
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
        user.setAvailable(new BigDecimal(20000));
        Placement placement = new Placement("600022", TradeSide.BUY, new BigDecimal(5300), new BigDecimal(2.0), new BigDecimal(10600), 3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(1199), 4, Boolean.TRUE);
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(2199), 3, Boolean.TRUE);
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(3199), 2, Boolean.TRUE);
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(4199), 1, Boolean.TRUE);
        channel1.setUserChannelTypeVal(UserChannelType.UNLIMITED.getCode());
        channel2.setUserChannelTypeVal(UserChannelType.UNLIMITED.getCode());
        channel3.setUserChannelTypeVal(UserChannelType.UNLIMITED.getCode());
        channel4.setUserChannelTypeVal(UserChannelType.UNLIMITED.getCode());
        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList = placementConvertServiceImpl.generateBuyPlacementChannel(user, channelList, placement);

        Assert.assertTrue(placementChannelDtoList.size() == 4);
    }

    /**
     * 通道优先级（1>2>3>4）
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
        user.setAvailable(new BigDecimal(20000));
        Placement placement = new Placement("600022", TradeSide.BUY, new BigDecimal(100), new BigDecimal(2.0), new BigDecimal(200), 3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(199), 4, Boolean.TRUE);
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(199), 3, Boolean.TRUE);
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(199), 2, Boolean.TRUE);
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(199), 1, Boolean.TRUE);
        channel1.setUserChannelTypeVal(UserChannelType.UNLIMITED.getCode());
        channel2.setUserChannelTypeVal(UserChannelType.UNLIMITED.getCode());
        channel3.setUserChannelTypeVal(UserChannelType.UNLIMITED.getCode());
        channel4.setUserChannelTypeVal(UserChannelType.UNLIMITED.getCode());
        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList = placementConvertServiceImpl.generateBuyPlacementChannel(user, channelList, placement);

        Assert.assertTrue(placementChannelDtoList.size() == 1);
    }
    /**
     * 通道优先级（1>2>3>4）
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
        user.setAvailable(new BigDecimal(20000));
        Placement placement = new Placement("600022", TradeSide.BUY, new BigDecimal(6000), new BigDecimal(2.0), new BigDecimal(1200), 3);
        List<BuyLimitChannel> channelList = new ArrayList<>();
        BuyLimitChannel channel1 = new BuyLimitChannel(1001, new BigDecimal(199), 4, Boolean.TRUE);
        BuyLimitChannel channel2 = new BuyLimitChannel(1002, new BigDecimal(299), 3, Boolean.TRUE);
        BuyLimitChannel channel3 = new BuyLimitChannel(1003, new BigDecimal(399), 2, Boolean.TRUE);
        BuyLimitChannel channel4 = new BuyLimitChannel(1004, new BigDecimal(499), 1, Boolean.TRUE);
        channel1.setUserChannelTypeVal(UserChannelType.UNLIMITED.getCode());
        channel2.setUserChannelTypeVal(UserChannelType.UNLIMITED.getCode());
        channel3.setUserChannelTypeVal(UserChannelType.UNLIMITED.getCode());
        channel4.setUserChannelTypeVal(UserChannelType.UNLIMITED.getCode());
        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        List<PlacementChannelDto> placementChannelDtoList = placementConvertServiceImpl.generateBuyPlacementChannel(user, channelList, placement);

        Assert.assertTrue(placementChannelDtoList.size() == 3);
    }

        /**
         * 用例4
         * 卖出1
         * 通道中用户可用：1000
         * 输入股票代码600022、委托价格2.65以及委托数量1000
         * 结果：下单成功，通道中有一笔委托，卖出1000股。
         */
    @Test
    public void testPlacementSell4(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(1000),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition channelSecurityPosition = new ChannelSecurityPosition(new BigDecimal(1000),221,"77077");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(channelSecurityPosition);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable, placement, channelSecurityPositionList);
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
    public void testPlacementSell5(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(2000),new BigDecimal("2.65"),new BigDecimal(5300),3);

        ChannelSecurityPosition channelSecurityPosition = new ChannelSecurityPosition(new BigDecimal(1000),221,"77077");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(channelSecurityPosition);
        BigDecimal userAvailable = new BigDecimal(1000);
        try {
            List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
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
    public void testPlacementSell6(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(510),new BigDecimal("2.65"),new BigDecimal(1351.5),3);

        ChannelSecurityPosition channelSecurityPosition = new ChannelSecurityPosition(new BigDecimal(1000),221,"77077");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(channelSecurityPosition);
        BigDecimal userAvailable = new BigDecimal(10000);
        try {
            List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
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
    public void testPlacementSell7(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(1000),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition channelSecurityPosition = new ChannelSecurityPosition(new BigDecimal(500),221,"77077");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(channelSecurityPosition);
        BigDecimal userAvailable = new BigDecimal(100);
        try {
            List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
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
    public void testPlacementSell8(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(1000),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition channelSecurityPosition = new ChannelSecurityPosition(new BigDecimal(1050),221,"77077");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(channelSecurityPosition);
        BigDecimal userAvailable = new BigDecimal(1050);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
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
    public void testPlacementSell9(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(1050),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition channelSecurityPosition = new ChannelSecurityPosition(new BigDecimal(1050),221,"77077");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(channelSecurityPosition);
        BigDecimal userAvailable = new BigDecimal(1050);
        List<PlacementChannelDto> placementChannelDtoList  = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
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
    public void testPlacementSell10(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(550),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition channelSecurityPosition = new ChannelSecurityPosition(new BigDecimal(1050),221,"77077");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(channelSecurityPosition);
        BigDecimal userAvailable = new BigDecimal(1050);
        try {
            List<PlacementChannelDto> placementChannelDtoList = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
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
    public void testPlacementSell11(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(1000),new BigDecimal("2.65"),new BigDecimal(2650),3);

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
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size() == 1);
        Assert.assertTrue(channelSecurityPositionList1.get(0).getHandleQuantity().intValue() == 1000);
    }
    /**
     * 用例12
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量5000
     * 结果：不能下单，提示：可卖数量不足
     */
    @Test
    public void testPlacementSell12(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(5000),new BigDecimal("2.65"),new BigDecimal(2650),3);

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
        try {
            List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
            Assert.fail();
        }catch ( Exception e){
            Assert.assertTrue(StringUtils.endsWith(e.getMessage(),ExceptionConstants.SELL_QUANTITY_NOT_ENOUGH));
        }
    }
    /**
     * 用例13
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量800
     * 结果：下单成功，共一笔通道委托，通道4卖出800股；
     */
    @Test
    public void testPlacementSell13(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(800),new BigDecimal("2.65"),new BigDecimal(2650),3);

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
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size() == 1);
        Assert.assertTrue(channelSecurityPositionList1.get(0).getHandleQuantity().intValue() == 800);
        Assert.assertTrue(channelSecurityPositionList1.get(0).getChannelId() == 224);
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
    public void testPlacementSell14(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(2000),new BigDecimal("2.65"),new BigDecimal(2650),3);

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
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size() == 2);
        for (PlacementChannelDto channelSecurityPosition : channelSecurityPositionList1) {
            if(channelSecurityPosition.getChannelId() == 224){
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 1500);
            }else if (channelSecurityPosition.getChannelId() == 223) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 500);
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
    public void testPlacementSell15(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(4000),new BigDecimal("2.65"),new BigDecimal(2650),3);

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
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size() == 4);
        for (PlacementChannelDto channelSecurityPosition : channelSecurityPositionList1) {
            if(channelSecurityPosition.getChannelId() == 221){
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 800);
            }else if (channelSecurityPosition.getChannelId() == 222) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 1200);
            }else if (channelSecurityPosition.getChannelId() == 223) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 500);
            }else if (channelSecurityPosition.getChannelId() == 224) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 1500);
            }
        }

    }
    /**
     * 用例16
     * 卖出
     * 通道中用户可用：1100
     * 输入股票代码600022、委托价格2.65以及委托数量1110
     * 不能下单，提示：委托中有零股。
     */
    @Test
    public void testPlacementSell16(){

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
            List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(StringUtils.equals(e.getMessage(), ExceptionConstants.PLACEMENT_QUANTITY_LEFT));
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
    public void testPlacementSell17(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(1500),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(740),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1160),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(530),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(1570),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size()==1);
        Assert.assertTrue(channelSecurityPositionList1.get(0).getHandleQuantity().intValue() == 1500);
        Assert.assertTrue(channelSecurityPositionList1.get(0).getChannelId() == 224);
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
    public void testPlacementSell18(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(2600),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(740),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1160),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(530),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(1570),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size()==2);
        for (PlacementChannelDto channelSecurityPosition : channelSecurityPositionList1) {
            if (channelSecurityPosition.getChannelId() == 224) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 1500);
            }else if (channelSecurityPosition.getChannelId() == 222) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 1100);
            }
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
    public void testPlacementSell19(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(3300),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(740),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1160),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(530),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(1570),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size()==3);
        for (PlacementChannelDto channelSecurityPosition : channelSecurityPositionList1) {
            if (channelSecurityPosition.getChannelId() == 224) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 1500);
            }else if (channelSecurityPosition.getChannelId() == 222) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 1100);
            }else if (channelSecurityPosition.getChannelId() == 221) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 700);
            }
        }
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
    public void testPlacementSell20(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(3800),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(740),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1160),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(530),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(1570),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size()==4);
        for (PlacementChannelDto channelSecurityPosition : channelSecurityPositionList1) {
            if (channelSecurityPosition.getChannelId() == 224) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 1500);
            }else if (channelSecurityPosition.getChannelId() == 222) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 1100);
            }else if (channelSecurityPosition.getChannelId() == 221) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 700);
            }else if (channelSecurityPosition.getChannelId() == 223) {
                Assert.assertTrue(channelSecurityPosition.getHandleQuantity().intValue() == 500);
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
    public void testPlacementSell21(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(3900),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(740),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1160),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(530),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(1570),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size()==6);
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
    public void testPlacementSell22(){

        User user = new User();
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(4000),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(740),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1160),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(530),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(1570),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size()==8);
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
    /**
     * 用例23
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量3850
     * 不能下单，提示：零股必须全部卖出。。
     */
    @Test
    public void testPlacementSell23(){
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(3850),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(740),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1160),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(530),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(1570),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        try {
            List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
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
    public void testPlacementSell24(){
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(3900),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(780),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1160),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(530),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(1570),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(4050);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size()==6);

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
    public void testPlacementSell25(){

        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(4050),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(780),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1160),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(530),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(1580),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(4050);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size()==8);

    }

    /**
     * 用例26
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量1600
     * 下单成功，共一笔通道委托，通道2卖出1600股；
     */
    @Test
    public void testPlacementSell26(){
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(1600),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(1000),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1650),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(450),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(1400),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(4500);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size()==1);
        Assert.assertTrue(channelSecurityPositionList1.get(0).getChannelId()==222);
        Assert.assertTrue(channelSecurityPositionList1.get(0).getHandleQuantity().intValue() == 1600);

    }

    /**
     * 用例27
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量1500
     * 下单成功，共一笔通道委托，通道4卖出1500股
     */
    @Test
    public void testPlacementSell27(){
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(1500),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(1000),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1550),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(450),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(1500),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(4000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size()==1);
        Assert.assertTrue(channelSecurityPositionList1.get(0).getChannelId()==222);
        Assert.assertTrue(channelSecurityPositionList1.get(0).getHandleQuantity().intValue() == 1500);

    }

    /**
     * 用例28
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量3000
     * 下单成功，共两笔通道委托：
     1：通道2卖出1500股；
     2：通道4卖出1500。
     */
    @Test
    public void testPlacementSell28(){
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(3000),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(1000),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1550),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(450),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(1500),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size()==2);
        for (PlacementChannelDto channelSecurityPosition:channelSecurityPositionList1) {
            if (channelSecurityPosition.getChannelId() == 222){
                Assert.assertTrue(channelSecurityPositionList1.get(0).getHandleQuantity().intValue() == 1500);
            }else if (channelSecurityPosition.getChannelId() == 224){
                Assert.assertTrue(channelSecurityPositionList1.get(0).getHandleQuantity().intValue() == 1500);
            }

        }

    }
    /**
     * 用例29
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量4400
     * 下单成功，共四笔通道委托：
     1：通道2卖出1500股；
     2：通道4卖出1500；
     3：通道1卖出1000股；
     4：通道3卖出400股。
     */
    @Test
    public void testPlacementSell29(){
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(3000),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(1000),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1550),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(450),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(1500),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(10000);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size()==2);
        for (PlacementChannelDto channelSecurityPosition:channelSecurityPositionList1) {
            if (channelSecurityPosition.getChannelId() == 222){
                Assert.assertTrue(channelSecurityPositionList1.get(0).getHandleQuantity().intValue() == 1500);
            }else if (channelSecurityPosition.getChannelId() == 224){
                Assert.assertTrue(channelSecurityPositionList1.get(0).getHandleQuantity().intValue() == 1500);
            }else if (channelSecurityPosition.getChannelId() == 221){
                Assert.assertTrue(channelSecurityPositionList1.get(0).getHandleQuantity().intValue() == 1000);
            }else if (channelSecurityPosition.getChannelId() == 223){
                Assert.assertTrue(channelSecurityPositionList1.get(0).getHandleQuantity().intValue() == 400);
            }

        }

    }
    /**
     * 用例40
     * 卖出
     * 通道中用户可用：4500
     * 通道1中用户可用：1000
     通道2中用户可用：1550
     通道3中用户可用：450
     通道4中用户可用：1500
     * 输入股票代码600022、委托价格2.65以及委托数量4450
     * 提示：零股必须全部卖出。
     */
    @Test
    public void testPlacementSell30(){

        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(4500),new BigDecimal("2.65"),new BigDecimal(11925),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(1000),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1550),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(450),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(1500),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(4500);

        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size()==6);

    }

    /**
     * 用例41
     * 卖出
     * 通道1中用户可用：1000
     通道2中用户可用：1550
     通道3中用户可用：450
     通道4中用户可用：1500
     * 通道中用户可用：4500
     * 输入股票代码600022、委托价格2.65以及委托数量4450
     * 提示：零股必须全部卖出。
     */
    @Test
    public void testPlacementSell31(){
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(4450),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(1000),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1550),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(450),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(1500),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(4500);

        try {
            List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(StringUtils.equals(e.getMessage(), ExceptionConstants.PLACEMENT_QUANTITY_LEFT));
        }

    }
    /**
     * 用例32
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量4500
     * 下单成功，共六笔通道委托：
     1：通道2卖出1500股；
     2：通道4卖出1500；
     3：通道1卖出1000股；
     4：通道3卖出400股；
     5：通道2卖出70股；
     6：通道4卖出20股；
     7：通道3卖出10股。
     */
    @Test
    public void testPlacementSell32(){

        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(4500),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(1000),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1570),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(460),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(1520),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(4550);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size()==7);


    }
    /**
     * 用例33
     * 卖出
     * 通道中用户可用：4000
     * 输入股票代码600022、委托价格2.65以及委托数量4550
     * 下单成功，共七笔通道委托：
     1：通道2卖出1500股；
     2：通道4卖出1500；
     3：通道1卖出1000股；
     4：通道3卖出400股；
     5：通道2卖出70股；
     6：通道3卖出60股；
     7：通道4卖出20股
     */
    @Test
    public void testPlacementSell33(){
        Placement placement = new Placement("600022",TradeSide.SELL,new BigDecimal(4550),new BigDecimal("2.65"),new BigDecimal(2650),3);

        ChannelSecurityPosition securityPosition1 = new ChannelSecurityPosition(new BigDecimal(1000),221,"77071");
        ChannelSecurityPosition securityPosition2 = new ChannelSecurityPosition(new BigDecimal(1550),222,"77071");
        ChannelSecurityPosition securityPosition3 = new ChannelSecurityPosition(new BigDecimal(460),223,"77071");
        ChannelSecurityPosition securityPosition4 = new ChannelSecurityPosition(new BigDecimal(1520),224,"77071");

        List<ChannelSecurityPosition> channelSecurityPositionList = new ArrayList<>();
        channelSecurityPositionList.add(securityPosition1);
        channelSecurityPositionList.add(securityPosition2);
        channelSecurityPositionList.add(securityPosition3);
        channelSecurityPositionList.add(securityPosition4);
        BigDecimal userAvailable = new BigDecimal(4550);
        List<PlacementChannelDto> channelSecurityPositionList1 = placementConvertServiceImpl.generateSellPlacementChannel(UserTradeType.PRIOR,userAvailable,placement,channelSecurityPositionList);
        Assert.assertTrue(channelSecurityPositionList1.size()==7);
        /*for (ChannelSecurityPosition channelSecurityPosition:channelSecurityPositionList1) {
            if (channelSecurityPosition.getChannelId() == 222){
                Assert.assertTrue(channelSecurityPositionList1.get(0).getHandleQuantity().intValue() == 1500);
            }else if (channelSecurityPosition.getChannelId() == 224){
                Assert.assertTrue(channelSecurityPositionList1.get(0).getHandleQuantity().intValue() == 1500);
            }
        }*/

    }
}
