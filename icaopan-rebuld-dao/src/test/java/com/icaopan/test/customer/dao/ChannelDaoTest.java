package com.icaopan.test.customer.dao;

import com.icaopan.customer.bean.ChannelParams;
import com.icaopan.customer.dao.ChannelLimitMapper;
import com.icaopan.customer.dao.ChannelMapper;
import com.icaopan.customer.model.Channel;
import com.icaopan.customer.model.ChannelLimit;
import com.icaopan.enums.enumBean.ChannelType;
import com.icaopan.test.common.dao.BaseTestDao;
import com.icaopan.util.page.Page;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author Wangzs
 * @ClassName: ChannelDaoTest
 * @Description:(通道管理)
 * @date 2016年11月28日 下午5:59:43
 */
public class ChannelDaoTest extends BaseTestDao {

    @SpringBeanByType
    private ChannelMapper channelMapper;

    @SpringBeanByType
    private ChannelLimitMapper channelLimitMapper;

    @Test
    @DbFit(when = { "wiki/customer/testcase.clean_trade_channel.when.wiki" }, then = { "wiki/customer/testcase.trade_channel.then.wiki" })
    public void testChannelInsert() {
        Channel channel = new Channel();
        //channel.setId(100);
        channel.setCode("10080");
        channel.setName("张三");
        channel.setTatio(new BigDecimal("0.003"));
        channel.setMinCost(new BigDecimal(100000));
        channel.setIsAvailable(true);
        channel.setNotes("通道管理");
        channel.setCustomerId(2001);
        channel.setChannelType(ChannelType.getByName("PB").getCode());
        channel.setUpdateTime(null);
        channel.setCreateTime(null);
        channel.setCashAvailable(BigDecimal.ZERO);
        channelMapper.insert(channel);

    }

    @Test
    @DbFit(when = { "wiki/customer/testcase.trade_channel.when.wiki" }, then = { "wiki/customer/testcase.trade_channel.update.then.wiki" })
    public void testChannelUpdate() {
        Channel channel = new Channel();
        channel.setId(1001);
        channel.setTatio(new BigDecimal("0.004"));
        channel.setMinCost(new BigDecimal(200000));
        channel.setIsAvailable(false);
        channel.setNotes("通道管理2");
        channel.setCustomerId(2002);
        channel.setChannelType(ChannelType.getByName("PB").getCode());
        channelMapper.updateBySelective(channel);
    }


    @Test
    @DbFit(when = { "wiki/customer/testcase.trade_channel.when.wiki" })
    public void testChannelSelectById() {
        channelMapper.seleChannelById(1001);

    }

    @Test
    @DbFit(when = { "wiki/customer/testcase.trade_channel.when.wiki" })
    public void testChannelSelectByCustomerId() {
        List<Channel> channels = channelMapper.selectChannelsByCustomerId(2002);
        Assert.assertTrue(channels.size() == 1);

    }

    @Test
    @DbFit(when = { "wiki/customer/testcase.trade_channel.when.wiki" })
    public void testChannelSelectByCustomerIds() {
        Integer[] csId = new Integer[1];
        csId[0] = 2002;
        List<Channel> channels = channelMapper.selectChanelsByCustomerIds(csId);
        Assert.assertTrue(channels.size() == 1);

    }

    @Test
    @DbFit(when = { "wiki/customer/testcase.trade_channel.when.wiki" })
    public void testSelectByPage() {
        Page page = new Page();
        ChannelParams channelParams = new ChannelParams();
        channelParams.setName("张三");
        channelParams.setIsAvailable(true);
        channelParams.setCustomerId(2002);
        List<Channel> list = channelMapper.selectChannelByPage(page, channelParams);
        org.testng.Assert.assertTrue(list.size() > 0);
    }

    public void testChannelLimitInsert() {
        ChannelLimit channelLimit = new ChannelLimit();
        channelLimit.setSecurityCode("600033");
        channelLimit.setChannelId(1065);
        channelLimit.setSecurityName("不知道");
        channelLimit.setAmount(new BigDecimal(99999));

        channelLimitMapper.insert(channelLimit);

    }



    public void testSelectChannelLimit() {
        ChannelLimit channelLimit = new ChannelLimit();
        // channelLimit.setSecurityCode("600033");
//        /channelLimit.setChannelId(1065);
        //channelLimit.setSecurityName("不知道");
        channelLimit.setAmount(new BigDecimal(9999900));
        channelLimit.setId(2);

        List<ChannelLimit> list = channelLimitMapper.selectByChannelId(1065);
        System.out.print(list.toString());

    }

}
