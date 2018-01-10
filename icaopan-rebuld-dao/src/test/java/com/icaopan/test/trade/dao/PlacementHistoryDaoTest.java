package com.icaopan.test.trade.dao;

import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.enums.enumBean.TradeStatus;
import com.icaopan.test.common.dao.BaseTestDao;
import com.icaopan.trade.bean.PlacementHistoryParams;
import com.icaopan.trade.dao.PlacementHistoryMapper;
import com.icaopan.trade.model.PlacementHistory;
import com.icaopan.util.page.Page;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author wangzs
 * @ClassName: PlacementHistoryDaoTest
 * @Description: (历史委托)
 * @date 2016年11月28日 下午5:54:02
 */
public class PlacementHistoryDaoTest extends BaseTestDao {

    @SpringBeanByType
    private PlacementHistoryMapper placementHistoryMapper;

    @Test
    @DbFit(when = { "wiki/trade/testcase.clean_trade_placement_history.when.wiki" }, then = { "wiki/trade/testcase.trade_placement_history.then.wiki" })
    public void testChannelPlacementHistoryInsert() {
        PlacementHistory placementHistory = new PlacementHistory();

        //placementHistory.setId(322);
        placementHistory.setSecurityCode("600022");
        placementHistory.setSide(TradeSide.BUY);
        placementHistory.setQuantity(new BigDecimal(1000));
        placementHistory.setPrice(new BigDecimal("12.23"));
        placementHistory.setAmount(new BigDecimal(12230));
        placementHistory.setStatus(TradeStatus.CANCELLING);
        placementHistory.setCommissionFee(new BigDecimal("5.1"));
        placementHistory.setStampDutyFee(new BigDecimal("5.1"));
        placementHistory.setTransferFee(new BigDecimal("5.1"));
        placementHistory.setFillPrice(new BigDecimal("12.23"));
        placementHistory.setFillQuantity(new BigDecimal(1000));
        placementHistory.setFillAmount(new BigDecimal(12230));
        placementHistory.setUserId(1011);
        placementHistory.setDateTime(new Date());
        placementHistory.setPlacementId(20011);
        placementHistory.setCustomerId(30011);

        placementHistoryMapper.insert(placementHistory);
    }

    @Test
    @DbFit(when = { "wiki/trade/testcase.trade_placement_history.when.wiki" })
    public void testSelectPlacementHistoryByPage() {
        Page page = new Page();
        PlacementHistoryParams placementHistoryParams = new PlacementHistoryParams();
        placementHistoryParams.setSecurityCode("600022");
        placementHistoryParams.setStatus(TradeStatus.CANCELLING.getName());
        placementHistoryParams.setCustomerId(30011);
        placementHistoryParams.setStartTime(null);
        placementHistoryParams.setEndTime(null);
        placementHistoryParams.setSide(TradeSide.BUY.getName());
        List<PlacementHistory> list = placementHistoryMapper.selectPlacementHistoryByPage(page, placementHistoryParams);
        org.testng.Assert.assertTrue(list.size() > 0);
    }
}
