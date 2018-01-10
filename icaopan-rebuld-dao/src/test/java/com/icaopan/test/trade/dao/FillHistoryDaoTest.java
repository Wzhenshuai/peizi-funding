package com.icaopan.test.trade.dao;

import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.test.common.dao.BaseTestDao;
import com.icaopan.trade.bean.FillHistoryParams;
import com.icaopan.trade.dao.FillHistoryMapper;
import com.icaopan.trade.model.FillHistory;
import com.icaopan.util.page.Page;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author wangzs
 * @ClassName: FillHistoryDaoTest
 * @Description: (历史成交)
 * @date 2016年11月28日 下午5:57:07
 */
public class FillHistoryDaoTest extends BaseTestDao {

    @SpringBeanByType
    private FillHistoryMapper fillHistoryMapper;

    @Test
    @DbFit(when = { "wiki/trade/testcase.clean_trade_fill_history.when.wiki" }, then = { "wiki/trade/testcase.trade_fill_history.then.wiki" })
    public void testFillHistoryInsert() {
        FillHistory fillHistory = new FillHistory();

        //fillHistory.setId(322);
        fillHistory.setSecurityCode("600022");
        fillHistory.setQuantity(new BigDecimal(1000));
        fillHistory.setPrice(new BigDecimal("12.23"));
        fillHistory.setAmount(new BigDecimal(12230));
        fillHistory.setSide(TradeSide.SELL);
        fillHistory.setFillTime(new Date());
        fillHistory.setAccount("1002003001");
        fillHistory.setPlacementCode("200161128");
        fillHistory.setFillCode("110200161128");
        fillHistory.setUserId(101);
        fillHistory.setChannelPlacementId(20001);
        fillHistory.setCustomerId(40001);
        fillHistory.setFillId(50001);
        fillHistory.setChannelId(60001);
        fillHistory.setPlacementId(70001);

        fillHistoryMapper.insert(fillHistory);
    }

    @Test
    @DbFit(when = { "wiki/trade/testcase.trade_fill_history.when.wiki" })
    public void testSelectFillHistoryByPage() {
        Page page = new Page();
        FillHistoryParams fillHistoryParams = new FillHistoryParams();
        fillHistoryParams.setCustomerId(40001);
        fillHistoryParams.setSecurityCode("600022");
        fillHistoryParams.setSide(TradeSide.SELL.getName());
        fillHistoryParams.setStartTime(null);
        fillHistoryParams.setEndTime(null);
        List<FillHistory> list = fillHistoryMapper.selectByPage(page, fillHistoryParams);
        org.testng.Assert.assertTrue(list.size() > 0);
    }
}
