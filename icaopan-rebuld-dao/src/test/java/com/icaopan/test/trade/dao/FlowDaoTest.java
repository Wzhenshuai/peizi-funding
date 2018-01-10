package com.icaopan.test.trade.dao;

import com.icaopan.enums.enumBean.TradeFowType;
import com.icaopan.test.common.dao.BaseTestDao;
import com.icaopan.trade.bean.FlowParams;
import com.icaopan.trade.dao.FlowMapper;
import com.icaopan.trade.model.Flow;
import com.icaopan.util.DateUtils;
import com.icaopan.util.page.Page;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wangzs
 * @ClassName: FlowDaoTest
 * @Description: (客户流水)
 * @date 2016年11月28日 下午5:56:34
 */
public class FlowDaoTest extends BaseTestDao {

    @SpringBeanByType
    private FlowMapper flowMapper;

    @Test
    @DbFit(when = { "wiki/trade/testcase.clean_trade_flow.when.wiki" }, then = { "wiki/trade/testcase.trade_flow.then.wiki" })
    public void testFlowInsert() {
        Flow flow = new Flow();
        flow.setSecurityCode("600022");
        flow.setAdjustQuantity(new BigDecimal(1000));
        flow.setAdjustAmount(new BigDecimal(12230));
        flow.setType(TradeFowType.CASH_ADD);
        flow.setCash(new BigDecimal(1000000));
        flow.setFinancing(new BigDecimal(500000));
        flow.setCashAmount(new BigDecimal(1000000));
        flow.setFinancingAmount(new BigDecimal(500000));
        flow.setCreateTime(new Date());
        flow.setUserId(101);
        flow.setNotes(null);
        flow.setCustomerId(60011);
        flow.setCreateTime(new Date());
        DateUtils.formatDateTime(new Date());
        flowMapper.insert(flow);
    }

    @Test
    @DbFit(when = { "wiki/trade/testcase.trade_flow.when.wiki" })
    public void testSelectFundFlowByPage() {
        Page page = new Page();

        FlowParams flowParams = new FlowParams();
        flowParams.setEndTime(null);
        flowParams.setStartTime(null);
        flowParams.setType(TradeFowType.CASH_ADD.getName());
        flowParams.setUserId(101);
        flowParams.setCustomerId(60011);
        flowParams.setSecurityCode("600022");

        List<Flow> list = flowMapper.selectFundFlowByPage(page, flowParams);
        org.testng.Assert.assertTrue(list.size() > 0);
    }

    @Test
    @DbFit(when = { "wiki/trade/testcase.trade_flow.when.wiki" })
    public void testSelectTradeFlowByPage() {
        Page page = new Page();
        FlowParams flowParams = new FlowParams();
        flowParams.setEndTime(null);
        flowParams.setStartTime(null);
        flowParams.setType(TradeFowType.CASH_ADD.getName());
        flowParams.setUserId(101);
        flowParams.setCustomerId(60011);
        flowParams.setSecurityCode("600022");
        List<Flow> list = flowMapper.selectTradeFlowByPage(page, flowParams);
        org.testng.Assert.assertTrue(list.size() > 0);
    }
}
