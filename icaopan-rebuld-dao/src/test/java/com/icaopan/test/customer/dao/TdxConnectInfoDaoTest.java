package com.icaopan.test.customer.dao;

import com.icaopan.customer.dao.TdxConnectInfoMapper;
import com.icaopan.customer.model.TdxConnectInfo;
import com.icaopan.test.common.dao.BaseTestDao;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;


/**
 * @author wangzs
 * @ClassName: TdxConnectInfoDaoTest
 * @Description: (通达信的 连接信息)
 * @date 2016年11月28日 下午5:55:11
 */
public class TdxConnectInfoDaoTest extends BaseTestDao {

    @SpringBeanByType
    private TdxConnectInfoMapper tdxConnectInfoMapper;

    @Test
    @DbFit(when = { "wiki/customer/testcase.clean_trade_tdx_connect_info.when.wiki" }, then = { "wiki/customer/testcase.trade_tdx_connect_info.then.wiki" })
    public void testTdxConnectInfoInsert() {
        TdxConnectInfo tdxConnectInfo = new TdxConnectInfo();
        //tdxConnectInfo.setId(101);
        tdxConnectInfo.setAccountNo("110220330");
        tdxConnectInfo.setTradeAccount("110220330");
        tdxConnectInfo.setGddmSz("001002003");
        tdxConnectInfo.setGddmSh("110220331");
        tdxConnectInfo.setJyPassword("123321");
        tdxConnectInfo.setTxPassword("123321");
        tdxConnectInfo.setIp("10.10.10.10");
        tdxConnectInfo.setPort("8080");
        tdxConnectInfo.setVersion("2.3");
        tdxConnectInfo.setYybId("2");
        tdxConnectInfo.setExchangeIdSz("0");
        tdxConnectInfo.setExchangeIdSh("1");
        tdxConnectInfo.setoName("招商证券");
        tdxConnectInfo.setDescription("单侧");
        tdxConnectInfo.setDllName("dllName");
        tdxConnectInfo.setUpDateTime(new Date());
        tdxConnectInfoMapper.insert(tdxConnectInfo);
    }

    @Test
    @DbFit(when = { "wiki/customer/testcase.trade_tdx_connect_info.when.wiki" })
    public void testSelectAllTdxConnectInfo() {

        List<TdxConnectInfo> list = tdxConnectInfoMapper.selectAll();
        org.testng.Assert.assertTrue(list.size() > 0);
    }

    @Test
    @DbFit(when = { "wiki/customer/testcase.trade_tdx_connect_info.when.wiki" })
    public void testUpdateTdxConnectInfoById() {
        TdxConnectInfo tdxConnectInfo = new TdxConnectInfo();
        tdxConnectInfo.setId(20002);
        tdxConnectInfo.setAccountNo("00000000");
        tdxConnectInfo.setTradeAccount("00000000");
        tdxConnectInfo.setGddmSz("00000000");
        tdxConnectInfo.setGddmSh("00000000");
        tdxConnectInfo.setJyPassword("00000000");
        tdxConnectInfo.setTxPassword("00000000");
        tdxConnectInfo.setIp("00.00.00.00");
        tdxConnectInfo.setPort("0000");
        tdxConnectInfo.setVersion("1.1");
        tdxConnectInfo.setYybId("0");
        tdxConnectInfo.setExchangeIdSz("0");
        tdxConnectInfo.setExchangeIdSh("0");
        tdxConnectInfo.setoName("太仓启宸");
        tdxConnectInfo.setDescription("单侧");
        tdxConnectInfo.setDllName("caicang_qichen");
        tdxConnectInfo.setUpDateTime(new Date());
        tdxConnectInfoMapper.updateBySelectiveId(tdxConnectInfo);
    }
}
