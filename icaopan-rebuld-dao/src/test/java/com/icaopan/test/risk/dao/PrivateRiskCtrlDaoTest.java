package com.icaopan.test.risk.dao;

import com.icaopan.risk.bean.*;
import com.icaopan.risk.dao.PrivateRiskCtrlMapper;
import com.icaopan.test.common.dao.BaseTestDao;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.junit.Test;
import org.testng.Assert;

import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2017/7/11.
 */
public class PrivateRiskCtrlDaoTest extends BaseTestDao {

    @SpringBeanByType
    PrivateRiskCtrlMapper privateRiskCtrlMapper;

    @Test
    @DbFit(when = "wiki/risk/testcase.privateRiskCtrl.forUser.when.wiki")
    public void TestSelectPrivateUser(){
        InfoParam param = new InfoParam();
        param.setId(1);
        List<TdxPrivateUser> privateUserList = privateRiskCtrlMapper.selectPrivateUsers(param);
        Assert.assertTrue(privateUserList.size()==1);
    }

    @Test
    @DbFit(when = "wiki/admin/testcase.admin_user.when.wiki")
    public void TestSelectCustomer(){
        List<PrivateCustomer> customers = privateRiskCtrlMapper.selectPrivateCustomers();
        Assert.assertTrue(customers.size()==1);
    }

    @Test
    public void TestBrokerBaseInfo(){
        List<TdxBrokerBase> brokers = privateRiskCtrlMapper.getAllBrokerBaseInfo();
        Assert.assertTrue(brokers.size()>0);
    }

    @Test
    @DbFit(when = "wiki/risk/testcase.privateRiskCtrl.forUser.when.wiki",then = "wiki/risk/testcase.privateRiskCtrl.addUser.then.wiki")
    public void TestAddPrivateUser(){
        TdxPrivateUser user = new TdxPrivateUser();
        user.setId(4);
        user.setAccountNo("32434");
        user.setTradeAccount("32434");
        user.setCustomerId("33");
        user.setBrokerBaseInfoId(3);
        user.setDllName("trade");
        user.setYybCode(2);
        user.setGddmSz("213");
        user.setYybName("wqr");
        privateRiskCtrlMapper.addPrivateUser(user);
    }

}
