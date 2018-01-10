package com.icaopan.test.trade.dao;

import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.enums.enumBean.TradeStatus;
import com.icaopan.test.common.dao.BaseTestDao;
import com.icaopan.trade.bean.ChannelPlacemenHistoryParams;
import com.icaopan.trade.dao.ChannelPlacementHistoryMapper;
import com.icaopan.trade.model.ChannelPlacementHistory;
import com.icaopan.util.page.Page;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author wangzs
 * @ClassName: ChannelPlaceHistoryDaoTest
 * @Description: (通道历史委托)
 * @date 2016年11月28日 下午5:59:08
 */
public class ChannelPlaceHistoryDaoTest extends BaseTestDao {

    @SpringBeanByType
    private ChannelPlacementHistoryMapper channelPlacementHistoryMapper;

    @Test
    @DbFit(when = { "wiki/trade/testcase.clean_trade_channel_placement_history.when.wiki" }, then = { "wiki/trade/testcase.trade_channel_placement_history.then.wiki" })
    public void testChannelPlacementHistoryInsert() {
        ChannelPlacementHistory channelPlacementHistory = new ChannelPlacementHistory();

        //channelPlacementHistory.setId(311);
        channelPlacementHistory.setSecurityCode("600022");
        channelPlacementHistory.setSide(TradeSide.BUY);
        channelPlacementHistory.setQuantity(new BigDecimal(1000));
        channelPlacementHistory.setPrice(new BigDecimal("12.23"));
        channelPlacementHistory.setAmount(new BigDecimal(122300));
        channelPlacementHistory.setFillQuantity(new BigDecimal(1000));
        channelPlacementHistory.setFillAmount(new BigDecimal(122300));
        channelPlacementHistory.setFillPrice(new BigDecimal("12.23"));
        channelPlacementHistory.setStatus(TradeStatus.FILLED);
        channelPlacementHistory.setAccount("1002003001");
        channelPlacementHistory.setPlacementCode("200161128");
        channelPlacementHistory.setRejectMessage(null);
        channelPlacementHistory.setDateTime(null);
        channelPlacementHistory.setChannelId(101);
        channelPlacementHistory.setChannelPlacementId(2313);
        channelPlacementHistory.setPlacementId(6011);
        channelPlacementHistory.setCustomerId(6022);
        channelPlacementHistory.setUserId(101);
        channelPlacementHistoryMapper.insert(channelPlacementHistory);
    }

    @Test
    @DbFit(when = { "wiki/trade/testcase.trade_channel_placement_history.when.wiki" })
    public void testCustomerSelectByPage() {
        Page page = new Page();

        ChannelPlacemenHistoryParams channelPlacemenHistoryParams = new ChannelPlacemenHistoryParams();
        channelPlacemenHistoryParams.setCustomerId(6022);
        channelPlacemenHistoryParams.setSecurityCode("600022");
        channelPlacemenHistoryParams.setSide(TradeSide.BUY.getName());
        channelPlacemenHistoryParams.setStatus(TradeStatus.FILLED.getName());
        channelPlacemenHistoryParams.setAccount("1002003001");
        List<ChannelPlacementHistory> list = channelPlacementHistoryMapper.selectByPage(page, channelPlacemenHistoryParams);
        org.testng.Assert.assertTrue(list.size() > 0);
    }
}
