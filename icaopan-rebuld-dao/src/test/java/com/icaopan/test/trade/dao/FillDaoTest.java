package com.icaopan.test.trade.dao;

import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.test.common.dao.BaseTestDao;
import com.icaopan.trade.bean.FillParams;
import com.icaopan.trade.dao.FillMapper;
import com.icaopan.trade.model.Fill;
import com.icaopan.util.page.Page;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author wangzs
 * @ClassName: FillDaoTest
 * @Description:(成交)
 * @date 2016年11月28日 下午5:57:39
 */
public class FillDaoTest extends BaseTestDao {

    @SpringBeanByType
    private FillMapper fillMapper;

    @Test
    @DbFit(when = { "wiki/trade/testcase.clean_trade_fill.when.wiki" }, then = { "wiki/trade/testcase.trade_fill.then.wiki" })
    public void testFillInsert() {
        Fill fill = new Fill();
        //fill.setId(101);
        fill.setSecurityCode("600022");
        fill.setQuantity(new BigDecimal(1000));
        fill.setSide(TradeSide.BUY);
        fill.setPrice(new BigDecimal("12.23"));
        fill.setAmount(new BigDecimal(12230));
        fill.setFillTime(new Date());
        fill.setAccount("1002003001");
        fill.setPlacementCode("200161128");
        fill.setFillCode("110200161128");
        fill.setUserId(101);
        fill.setChannelPlacementId(20001);
        fill.setChannelId(50001);
        fill.setCustomerId(60011);
        fill.setPlacementId(60022);
        fillMapper.insert(fill);
    }

    @Test
    @DbFit(when = { "wiki/trade/testcase.trade_fill.when.wiki" })
    public void testFillSelectById() {

        Fill fill = fillMapper.selectById(30001);
        Assert.assertTrue(fill != null);
    }

    @Test
    @DbFit(when = { "wiki/trade/testcase.trade_fill.when.wiki" })
    public void testFillByPage() {

        Page page = new Page();

        FillParams fillParams = new FillParams();
        fillParams.setChannelId(50001);
        fillParams.setSecurityCode("600022");
        fillParams.setCustomerId(60011);

        List<Fill> list = fillMapper.selectFillByPage(page, fillParams);
        org.testng.Assert.assertTrue(list.size() > 0);
    }
}
