package com.icaopan.test.user.dao;

import com.icaopan.enums.enumBean.UserStatus;
import com.icaopan.enums.enumBean.UserTradeType;
import com.icaopan.test.common.dao.BaseTestDao;
import com.icaopan.user.bean.UserPageParams;
import com.icaopan.user.dao.UserMapper;
import com.icaopan.user.model.User;
import com.icaopan.util.page.Page;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.springframework.dao.DuplicateKeyException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2016/11/18.
 */
public class UserDaoTest extends BaseTestDao {

    @SpringBeanByType
    private UserMapper userMapper;

    /**
     * 增加用户
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki", then = "wiki/user/testcase.user.then.wiki")
    public void testInsert() {
        User user = new User();
        String temp = String.valueOf((char) (69));
        user.setUserName("royLCO-" + temp);
        user.setRealName("Clo-" + temp);
        user.setPassword(temp + "das21#$" + temp);
        user.setIsDefaultFee("1");
        user.setCustomerId(3);
        user.setStatus("0");
        user.setChannelId(2);
        user.setCustomerId(3);
        user.setUserTradeType(UserTradeType.DIVIDE);
        userMapper.insert(user);
    }

    /**
     * 测试数据库用户名字段的唯一性检测
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki")
    public void TestInserUnique() {
        User user = new User();
        String temp = String.valueOf((char) (69));
        user.setUserName("royLCO-A");
        user.setRealName("Clo-" + temp);
        user.setPassword(temp + "das21#$" + temp);
        user.setIsDefaultFee("1");
        user.setStatus(UserStatus.getByName("Logout").getCode());
        user.setCustomerId(3);
        user.setChannelId(2);
        user.setCustomerId(3);
        user.setUserTradeType(UserTradeType.DIVIDE);
        Exception exception = null;
        try {
            userMapper.insert(user);
        } catch (DuplicateKeyException e) {
            exception = new DuplicateKeyException("ERROR");
        }
        Assert.assertTrue(exception != null);
    }

    /**
     * 验证用户名是否存在
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki")
    public void TestExist() {
        User user = userMapper.verify("royLCO-D");
        Assert.assertTrue(user != null);
    }

    /**
     * 验证用户名是否存在
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki")
    public void TestFindByUserNameAndPassWord() {
        User user = userMapper.findUser("royLCO-G", "Gdas21#$G");
        Assert.assertTrue(user != null);
    }

    /**
     * 更新用户状态
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki"
            , then = "wiki/user/testcase.user.update.then.wiki")
    public void TestUserUpdateStatus() {
        userMapper.updateUserStatus(3, UserStatus.getByName("Logout").getCode());
    }

    /**
     * 更新登陆次数
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki",
            then = "wiki/user/testcase.user.updateLoginCount.then.wiki")
    public void TestUpdateUserLoginCount() {
        userMapper.updateLoginCount(4);
    }

    /**
     * 更新用户信息(编辑功能)
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki", then = "wiki/user/testcase.user.updateUser.then.wiki")
    public void TestUpdateUser() {
        User user = new User();
        user.setId(5);
        String generatedUUID = "04df208b-fb67-4968-baf2-4e5a42a3e73e";
        user.setRelatedUuid(generatedUUID);
        userMapper.updateUser(user);
    }

    /**
     * 根据分页条件查询用户信息
     *
     * @param
     * @return List<User>
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki")
    public void TestFindUserByPage() {
        Page page = new Page();
        UserPageParams params = new UserPageParams();
        List<User> list = userMapper.findAllUsersByPage(page, params);
        Assert.assertTrue(list.size() == 5);
        Assert.assertTrue(page.getiTotalRecords() == 5);
    }

    /**
     * 查询所有用户信息，做分页处理，
     * 获取第二页记录
     * 记录起始位置是3，每页显示记录是3条
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.forPage.when.wiki")
    public void TestFindUserInSecond() {
        Page page = new Page();
        page.setiDisplayStart(3);
        page.setiDisplayLength(3);
        List<User> list = userMapper.findAllUsersByPage(page, new UserPageParams());
        Assert.assertTrue(list.size() == 3);
        Assert.assertTrue(page.getiTotalRecords() == 10);
    }

    /**
     * 根据分页条件参数查询用户信息，执行模糊查询
     * userName中含有f字母
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.forPage.when.wiki")
    public void TestFindUserByName() {
        UserPageParams params = new UserPageParams();
        params.setUserName("f");
        List<User> list = userMapper.findAllUsersByPage(new Page(), params);
        Assert.assertTrue(list.size() == 4);
    }

    /**
     * 根据通道ID查询用户信息
     */
    @Test
    @DbFit(when = { "wiki/user/testcase.user.forPage.when.wiki", "wiki/user/testcase.userChannel.when.wiki" })
    public void TestFindByChannelId() {
        UserPageParams params = new UserPageParams();
        params.setChannelId(2);
        List<User> list = userMapper.findAllUsersByPage(new Page(), params);
        Assert.assertTrue(list.size() == 2);
    }

    /**
     * 查找用户，并找出用户的关联账号(如果有关联账号)
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki")
    public void TestFindUser() {
        List<User> list = userMapper.findRelatedUser(2);
        Assert.assertTrue(list.size() == 1);
    }

    /**
     * 取消关联用户
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki", then = "wiki/user/testcase.user.unLink.then.wiki")
    public void TestUnLink() {
        userMapper.unLinkUser(2);
    }

    /**
     * 根据用户的状态查找用户并分页处理
     * 查找锁定状态下的用户：1条结果
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki")
    public void TestFindByStatus() {
        UserPageParams params = new UserPageParams();
        params.setStatus(UserStatus.getByName("Locked").getCode());
        List<User> list = userMapper.findAllUsersByPage(new Page(), params);
        Assert.assertTrue(list.size() == 1);
    }

    /**
     * 根据用户id查询用户信息
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki")
    public void TestSelectUserById() {
        User user = userMapper.findByUserId(3);
        System.out.println(user.getChannelId());
        Assert.assertTrue(user != null);
    }

    /**
     * 根据用户id查询用户信息
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki")
    public void TestSelectUserByCustomerId() {
        List<User> userList = userMapper.findByCustomerId(3);
        Assert.assertTrue(userList.size() == 1);
    }


    /**
     * 删除收费方案，将收费方式重置为采用默认费率
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki", then = "wiki/user/testcase.user.updateDefaultRatioFee.then.wiki")
    public void TestUpdateUserForDefaultRatio() {
        userMapper.updateRatioFeeToDefault(5);
    }

    /**
     * 风控：更新平仓线，警戒线
     *
     * @Paramete userId warnLine openLine
     * @Return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki", then = "wiki/user/testcase.user.updateRisk.then.wiki")
    public void TestUpdateRiskControl() {
        userMapper.updateRiskControl(3, new BigDecimal("0.5"), new BigDecimal("0.7"));
    }

    /**
     * 更新佣金方案
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki", then = "wiki/user/testcase.user.updateCostFee.then.wiki")
    public void TestUpdateCostFee() {
        userMapper.updateCostFee(4, new BigDecimal(30), new BigDecimal("0.5"));
    }

    /**
     * 比例风险控制设置
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.riskcontrol.when.wiki",
            then = "wiki/user/testcase.user.updateRatioRiskControl.then.wiki")
    public void TestUpdateRatioRiskControl() {
        userMapper.updateRatioRiskControl(1, new BigDecimal("0.5"), new BigDecimal("0.2"), new BigDecimal("0.3"), new BigDecimal("0.4"), new BigDecimal("0.1"));
    }


    /**
     * 保存配资方案：本金金额，融资金额
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki",
            then = "wiki/user/testcase.user.updateCapitalBySave.then.wiki")
    public void TestUpdateCaiptalBySaves() {
        Assert.assertTrue(userMapper.updateCapitalBySave(2, new BigDecimal(10), new BigDecimal(30)));
    }

    /**
     * 更新配资方案：本金变量，融资变量
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki",
            then = "wiki/user/testcase.user.updateCapitalByChanged.then.wiki")
    public void TestUpdateCapitalByChanged() {
        userMapper.updateCapitalByChanged(5, new BigDecimal(-10), new BigDecimal(-5));
    }

    /**
     * 更新可用金额，账户总金额
     *
     * @param
     * @reutrn
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki",
            then = "wiki/user/testcase.user.updateTocalAmount.then.wiki")
    public void TestUpdateAmountFinance() {
        userMapper.updateAvailableAndTotalAmount(1, new BigDecimal(20), new BigDecimal(10));
    }


    /**
     * 更新密码
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/user/testcase.user.when.wiki", then = "wiki/user/testcase.user.updatePwd.then.wiki")
    public void TestUpdatePWD() {
        userMapper.updatePwd(2, "TestUpdatePWD");
    }


}
