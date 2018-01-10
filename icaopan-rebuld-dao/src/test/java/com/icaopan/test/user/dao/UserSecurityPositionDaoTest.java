package com.icaopan.test.user.dao;

import com.icaopan.test.common.dao.BaseTestDao;
import com.icaopan.user.bean.PositionParams;
import com.icaopan.user.bean.UserPositionParams;
import com.icaopan.user.dao.UserSecurityPositionMapper;
import com.icaopan.user.model.UserSecurityPosition;
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
public class UserSecurityPositionDaoTest extends BaseTestDao {

    @SpringBeanByType
    private UserSecurityPositionMapper userSecurityPositionMapper;

    /**
     * 保存证券头寸信息
     *
     * @return
     * @Parameter SecurityPosition
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.security.position.when.wiki",
            then = "wiki/user/testcase.user.security.position.insert.then.wiki")
    public void TestInsert() {
        UserSecurityPosition userSecurityPosition = new UserSecurityPosition();
        userSecurityPosition.setInternalSecurityId("601234");
        userSecurityPosition.setAvailable(new BigDecimal(1999));
        userSecurityPosition.setAmount(new BigDecimal(2220));
        userSecurityPosition.setUpdateTime(new Date());
        userSecurityPosition.setCostPrice(new BigDecimal(2011298));
        userSecurityPosition.setUserId(2);
        userSecurityPositionMapper.insert(userSecurityPosition);
    }

    /**
     * 根据头寸编号查询查询证券头寸
     *
     * @Parameter id
     * @returns
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.security.position.when.wiki")
    public void TestSelectById() {
        UserSecurityPosition securityPosition = userSecurityPositionMapper.selectByPrimaryKey(1);
        Assert.assertTrue(securityPosition != null);
    }

    /**
     * 查询所有头寸信息，并做分页处理
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.security.position.when.wiki")
    public void TestSelectByPage() {
        UserPositionParams params = new UserPositionParams();
        params.setUserId(1);
        List<UserSecurityPosition> list = userSecurityPositionMapper.findByPage(new Page(), params);
        Assert.assertTrue(list.size() == 2);
    }

    /**
     * 根据用户编号，股票编号查询股票信息
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.security.position.when.wiki")
    public void TestFindByUserIdAndInternalSecurityId() {
        UserSecurityPosition securityPosition = userSecurityPositionMapper.findByUserIdAndInternalSecurityId(1, "603309");
        Assert.assertTrue(securityPosition != null);
    }

    /**
     * 根据资金方编号，查询股票汇总
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.security.position.when.wiki")
    public void TestQueryByCustomerId(){
        PositionParams params = new PositionParams();
        params.setCustomerId(2);
        List<UserSecurityPosition> positions = userSecurityPositionMapper.queryPositionByCustomerId(new Page(),params);
        Assert.assertTrue(positions.size()==1);
    }

    /**
     * 根据用户编号和股票编号查询股票信息
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.security.position.when.wiki")
    public void TestQueryByCustomerIdAndInternalSecurityId(){
        PositionParams params = new PositionParams();
        params.setCustomerId(3);
        params.setInternalSecurityId("603319");
        List<UserSecurityPosition> positions = userSecurityPositionMapper.queryByCustomerIdAndInternalSecurityId(new Page(),params);
        Assert.assertTrue(positions.size()==1);
    }


    /**
     * 清除空持仓，这是一个计划任务,sql判断amount available字段都为0的时候，删除持仓信息
     *
     * @return
     * @Parameter
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.security.position.when.wiki",
            then = "wiki/user/testcase.user.security.position.delete.then.wiki")
    public void TestDelete() {
        userSecurityPositionMapper.deleteEmptyPosition();
    }


    /**
     * 更新可用头寸
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.security.position.when.wiki",
            then = "wiki/user/testcase.user.security.position.updateAvailable.then.wiki")
    public void updateAvailable() {
        UserPositionParams userPositionParams = new UserPositionParams(1, "603319", new BigDecimal(1000));
        userSecurityPositionMapper.updateAvailable(userPositionParams);
    }

    /**
     * 更新证券头寸：总头寸差值，成本价
     * @Parameter userId amountChanged  costPrice
     * @return
     * (costprice * amount + costpricechanged * amountchanged)  / (amount + amountchenged)
     * 判断 分母为零的时候  null
     */
/*    @Test
    @DbFit(when = "wiki/user/testcase.user.security.position.when.wiki",
            then = "wiki/user/testcase.user.security.position.updateByCostPrice.then.wiki")
    public void TestUpdatePriceByChanged(){
        UserPositionParams userPositionParams = new UserPositionParams(1,"603319",new BigDecimal(1000),new BigDecimal(5));
        userSecurityPositionMapper.updateAmountAndCostPrice(userPositionParams);
    }*/

    /**
     * 测试更新证券头寸：只更新成本价的情况下的测试用例
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.security.position.when.wiki",
            then = "wiki/user/testcase.user.security.position.updateByCostPrice.then.wiki")
    public void TestUpdateSecurityByCostPrice() {
        UserPositionParams userPositionParams = new UserPositionParams(1, "603319", new BigDecimal(-100), new BigDecimal(5));
        userSecurityPositionMapper.updateAmountAndCostPrice(userPositionParams);
    }

    /**
     * 测试更新证券头寸：只更新总头寸变量的情况下的测试用例
     * 只更新股票数量的情况下，成本价不变
     * @param
     * @return
     * */
/*    @Test
    @DbFit(when = "wiki/user/testcase.user.security.position.when.wiki",
            then = "wiki/user/testcase.user.security.position.updateByAmount.then.wiki")
    public void TestUpdateSecurityPositionByAmount(){
        UserPositionParams userPositionParams = new UserPositionParams(3,"603319", new BigDecimal(-100),new BigDecimal(3));
        userSecurityPositionMapper.updateAmountAndCostPrice(userPositionParams);
    }*/

    /**
     * 测试更新证券头寸：只更新总头寸变量的情况下的测试用例
     * 只更新股票数量的情况下，成本价不变
     * @param
     * @return
     * */
/*    @Test
    @DbFit(when = "wiki/user/testcase.user.security.position.when.wiki")
    public void testFindUserBySecurityIdAndUserId(){
        UserSecurityPosition UserSecurityPosition = userSecurityPositionMapper.findByUserIdSecurityId(1,"603309",2);
        Assert.assertTrue(list.size()==1);
    }*/

}
