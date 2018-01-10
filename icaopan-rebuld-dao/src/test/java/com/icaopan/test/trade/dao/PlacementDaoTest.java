package com.icaopan.test.trade.dao;

import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.enums.enumBean.TradeStatus;
import com.icaopan.test.common.dao.BaseTestDao;
import com.icaopan.trade.bean.PlacementParams;
import com.icaopan.trade.dao.PlacementMapper;
import com.icaopan.trade.model.Placement;
import com.icaopan.util.page.Page;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author wangzs
 * @ClassName: PlacementDaoTest
 * @Description: (下单操作)
 * @date 2016年11月28日 下午5:56:02
 */
public class PlacementDaoTest extends BaseTestDao {

    @SpringBeanByType
    private PlacementMapper placementMapper;

    @Test
    @DbFit(when = { "wiki/trade/testcase.clean_trade_placement.when.wiki" }, then = { "wiki/trade/testcase.trade_placement.then.wiki" })
    public void testPlacementInsert() {
        Placement placement = new Placement();
        //placement.setId(221);
        placement.setSecurityCode("600022");
        placement.setSide(TradeSide.BUY);
        placement.setQuantity(new BigDecimal(1000));
        placement.setPrice(new BigDecimal("12.23"));
        placement.setAmount(new BigDecimal(12230));
        placement.setStatus(TradeStatus.SENDACK);
        placement.setIsSzTransferFee(false);
        placement.setRatioFee(new BigDecimal(0.0008));
        placement.setMinCost(new BigDecimal(4000));
        placement.setUserId(101);
        placement.setCustomerId(60001);
        placement.setTime(null);
        placementMapper.insert(placement);
        System.out.println(placement.getId());
    }

    @Test
    @DbFit(when = { "wiki/trade/testcase.trade_placement.when.wiki" })
    public void testPlacementSelectById() {

        Placement placement = placementMapper.selectById(11001);
        Assert.assertTrue(placement != null);
    }

    @Test
    @DbFit(when = { "wiki/trade/testcase.trade_placement.when.wiki" })
    public void testPlacementSelectByPage() {
        Page page = new Page();
        PlacementParams placementParams = new PlacementParams();
        placementParams.setSecurityCode("600022");
        placementParams.setStatus(TradeStatus.SENDACK.getName());
        placementParams.setCustomerId(60001);
        List<Placement> list = placementMapper.placementSelectByPage(page, placementParams);
        org.testng.Assert.assertTrue(list.size() > 0);
    }

    @Test
    @DbFit(when = { "wiki/trade/testcase.trade_placement.when.wiki" }, then = { "wiki/trade/testcase.trade_placement.update_status.then.wiki" })
    public void testPlacementUpdateStatus() {

        placementMapper.updatePlacementStatus(TradeStatus.FILLED, 11001);
    }

    @Test
    @DbFit(when = { "wiki/trade/testcase.trade_placement.when.wiki" }, then = { "wiki/trade/testcase.trade_placement.update_amountquantiry.then.wiki" })
    public void testUpdateQuantityAndAmount() {

        placementMapper.updatePlaceQuantityAndAmount(new BigDecimal(1000), new BigDecimal(12300), 11001);
    }

    @Test
    @DbFit(when = { "wiki/trade/testcase.trade_placement.when.wiki" }, then = { "wiki/trade/testcase.trade_placement_delete.then.wiki" })
    public void testPlacementDeleteDate() {

        placementMapper.deletePlacementToday();
    }

}
