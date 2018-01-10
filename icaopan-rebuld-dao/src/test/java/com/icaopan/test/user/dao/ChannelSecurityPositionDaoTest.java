package com.icaopan.test.user.dao;

import com.icaopan.test.common.dao.BaseTestDao;
import com.icaopan.user.bean.PositionParams;
import com.icaopan.user.dao.ChannelSecurityPositionMapper;
import com.icaopan.user.model.ChannelSecurityPosition;
import com.icaopan.util.page.Page;

import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2016/11/21.
 */
public class ChannelSecurityPositionDaoTest extends BaseTestDao {

    @SpringBeanByType
    private ChannelSecurityPositionMapper channelSecurityPositionMapper;

    /**
     * 保存证券头寸信息
     *
     * @return
     * @Parameter ChannelSecurityPosition
     */
    @Test
    @DbFit(when = "wiki/user/testcase.channel.security.position.when.wiki",
            then = "wiki/user/testcase.channel.security.position.insert.then.wiki")
    public void TestInsert() {
        ChannelSecurityPosition channelsecurityPosition = new ChannelSecurityPosition();
        channelsecurityPosition.setInternalSecurityId("601234");
        channelsecurityPosition.setAvailable(new BigDecimal(1999));
        channelsecurityPosition.setAmount(new BigDecimal(2220));
        channelsecurityPosition.setChannelId(1);
        channelsecurityPosition.setUpdateTime(new Date());
        channelsecurityPosition.setCostPrice(new BigDecimal(2011298));
        channelsecurityPosition.setUserId(2);
        channelSecurityPositionMapper.insert(channelsecurityPosition);
    }

    /**
     * 根据头寸编号查询查询证券头寸
     *
     * @Parameter id
     * @returns
     */
    @Test
    @DbFit(when = "wiki/user/testcase.channel.security.position.when.wiki")
    public void TestSelectById() {
        ChannelSecurityPosition securityPosition = channelSecurityPositionMapper.selectByPrimaryKey(1);
        Assert.assertTrue(securityPosition != null);
    }

    /**
     * 查询所有头寸信息，并做分页处理
     */
    @Test
    @DbFit(when = "wiki/user/testcase.channel.security.position.when.wiki")
    public void TestSelectByPage() {
        PositionParams params = new PositionParams();
        params.setCustomerId(2);
        List<ChannelSecurityPosition> list = channelSecurityPositionMapper.findByPage(new Page(), params);
        Assert.assertTrue(list.size() == 2);
    }

    /**
     * 根据条件查询所有头寸信息，并做分页处理
     */
    @Test
    @DbFit(when = "wiki/user/testcase.channel.security.position.when.wiki")
    public void TestSelectByPageParams() {
        PositionParams params = new PositionParams(null, 1, null);
        List<ChannelSecurityPosition> list = channelSecurityPositionMapper.findByPage(new Page(), params);
        Assert.assertTrue(list.size() == 1);
    }

    /**
     * 根据用户编号，股票编号查询股票信息
     */
    @Test
    @DbFit(when = "wiki/user/testcase.channel.security.position.when.wiki")
    public void TestFindByUserIdAndInternalSecurityId() {
        ChannelSecurityPosition securityPosition = channelSecurityPositionMapper.findByUserIdAndInternalSecurityId(1, "603309");
        Assert.assertTrue(securityPosition != null);
    }

    /**
     * 根据用户编号，股票代码，交易通道查询证券头寸信息
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.channel.security.position.when.wiki")
    public void TestSelectSecurityPosition() {
        List<ChannelSecurityPosition> list = channelSecurityPositionMapper.find("royLCO-D", "690433", "2", null);
        Assert.assertTrue(list.size() == 1);
    }

    /**
     * 测试findSecurityPosition 这个方法，只传输一个参数的情况下的测试用例测试
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.channel.security.position.when.wiki")
    public void TestSelectSecurityPositionByChannelId() {
        List<ChannelSecurityPosition> list = channelSecurityPositionMapper.find(null, null, "2", null);
        Assert.assertTrue(list.size() == 3);
    }

    /**
     * 测试findSecurityPosition 这个方法，只传输一个参数(customerId)的情况下的测试用例测试
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.channel.security.position.when.wiki")
    public void TestSelectSecurityPositionByCustomerId() {
        List<ChannelSecurityPosition> list = channelSecurityPositionMapper.find(null, null, null, 2);
        Assert.assertTrue(list.size() == 2);
    }

    /**
     * 测试findSecurityPosition 这个方法，只传输一个参数的情况下的测试用例测试
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = { "wiki/user/testcase.channel.security.position.when.wiki", "wiki/user/testcase.user.when.wiki" })
    public void TestSelectSecurityPositionByUserName() {
        List<ChannelSecurityPosition> list = channelSecurityPositionMapper.find("royLCO-A", null, null, null);
        Assert.assertTrue(list.size() == 1);
    }

    /**
     * 测试根据userId,customerId,security_code查找用户信息
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.channel.security.position.when.wiki")
    public void TestFindByUserIdAndCustomerIdAndInternalSecurityId() {
        Assert.assertTrue(channelSecurityPositionMapper.findByUserIdAndChannelIdAndInternalSecurityId(2, 1, "603308") != null);
    }

    @Test
    @DbFit(when = "wiki/user/testcase.channel.security.position.when.wiki")
    public void TestFindByCustomerIdAndInternalSecurityId(){
        List<ChannelSecurityPosition> positions = channelSecurityPositionMapper.findByCustomerIdAndInternalSecurityId(new Page(),new PositionParams(3,"603308"));
        Assert.assertTrue(positions.size()==1);
    }

    /**
     * 测试findSecurityPosition 这个方法，只传输2个参数的情况下的测试用例测试
     * 测试条件：用户名进行模糊筛选，customerId协助筛选
     * 验证条件：从准备好的Wiki数据表中返回两条符合条件的数据
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = { "wiki/user/testcase.channel.security.position.when.wiki", "wiki/user/testcase.user.when.wiki" })
    public void TestSelectByCustomerIdAndUserName() {
        List<ChannelSecurityPosition> list = channelSecurityPositionMapper.find("roy", null, null, 1);
        System.out.println(list.size() == 1);
    }

    /**
     * 测试findSecurityPosition 这个方法，只传输1个参数的情况下的测试用例测试,只验证传入customerId的情况
     * 测试条件：customerId协助筛选
     * 验证条件：从准备好的Wiki数据表中返回两条符合条件的数据
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = { "wiki/user/testcase.channel.security.position.when.wiki", "wiki/user/testcase.user.when.wiki" })
    public void TestSelectByCustomerId() {
        List<ChannelSecurityPosition> list = channelSecurityPositionMapper.find(null, null, null, 1);
        Assert.assertTrue(list.size() == 1);
    }

    /**
     * 清除空持仓，这是一个计划任务,sql判断amount available字段都为0的时候，删除持仓信息
     *
     * @return
     * @Parameter
     */
    @Test
    @DbFit(when = "wiki/user/testcase.channel.security.position.when.wiki",
            then = "wiki/user/testcase.channel.security.position.delete.then.wiki")
    public void TestDelete() {
        channelSecurityPositionMapper.deleteEmptyPosition();
    }


    /**
     * 更新可用头寸
     */
    @Test
    @DbFit(when = "wiki/user/testcase.channel.security.position.when.wiki",
            then = "wiki/user/testcase.channel.security.position.updateAvailable.then.wiki")
    public void updateAvailable() {
        channelSecurityPositionMapper.updateAvailable(3, new BigDecimal(-300));
    }


    /**
     * 更新证券头寸：总头寸差值，成本价
     *
     * @return (costprice * amount + costpricechanged * amountchanged)  / (amount + amountchenged)
     * 判断 分母为零的时候  null
     * @Parameter userId amountChanged  costPrice
     */
    @Test
    @DbFit(when = "wiki/user/testcase.channel.security.position.when.wiki",
            then = "wiki/user/testcase.channel.security.position.updateAmountAndCostPrice.then.wiki")
    public void TestUpdatePriceByChanged() {
        channelSecurityPositionMapper.updateAmountAndCostPrice(3, new BigDecimal(-100), new BigDecimal(9));
    }

    /**
     * 测试更新证券头寸：只更新成本价的情况下的测试用例
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.channel.security.position.when.wiki",
            then = "wiki/user/testcase.channel.security.position.updateByCostPrice.then.wiki")
    public void TestUpdateSecurityByCostPrice() {
        channelSecurityPositionMapper.updateAmountAndCostPrice(3, null, new BigDecimal(3));
    }

    /**
     * 测试更新证券头寸：只更新总头寸变量的情况下的测试用例
     * 只更新股票数量的情况下，成本价不变
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.channel.security.position.when.wiki",
            then = "wiki/user/testcase.channel.security.position.updateByAmount.then.wiki")
    public void TestUpdateSecurityPositionByAmount() {
        channelSecurityPositionMapper.updateAmountAndCostPrice(3, new BigDecimal(-100), null);
    }

    /**
     * 测试更新证券头寸：只更新总头寸变量的情况下的测试用例
     * 只更新股票数量的情况下，成本价不变
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.channel.security.position.when.wiki")
    public void testFindUserBySecurityIdAndUserId() {
        List<ChannelSecurityPosition> list = channelSecurityPositionMapper.findByUserIdSecurityId(1, "603309", 2);
        Assert.assertTrue(list.size() == 1);
    }

    
    public void testUpateChannelPostion(){
    	try {
    		channelSecurityPositionMapper.updateAvailable(1, new BigDecimal(1));
    		for(int i=0;i<20;i++){
        		new Thread(new Runnable() {
					
					@Override
					public void run() {
						channelSecurityPositionMapper.updateAvailable(1, new BigDecimal(1));
						channelSecurityPositionMapper.updateAvailable(2, new BigDecimal(1));
					}
				}).start();
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
}
