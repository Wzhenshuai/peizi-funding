package com.icaopan.trade.service;

import com.icaopan.enums.enumBean.TradeStatus;
import com.icaopan.enums.enumBean.TransactionType;
import com.icaopan.trade.bean.ChannelForEmsParameter;
import com.icaopan.trade.bean.ChannelPlacemenHistoryParams;
import com.icaopan.trade.bean.ChannelPlacementParams;
import com.icaopan.trade.model.ChannelPlacement;
import com.icaopan.trade.model.ChannelPlacementHistory;
import com.icaopan.util.page.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName ChannelPlacementService
 * @Description (通道委托业务)
 * @Date 2016年12月7日 上午10:42:19
 */
public interface ChannelPlacementService {

    /**
     * @param record
     * @return void
     * @Description (选择通道，生成通道委托成功的时候，通过mq发送状态给EMS)
     * 1. 无论校验是否成功，将记录写入数据库(默认已报)
     * 2. 校验信息：卖出的时候判断证券持仓可用头寸(卖出量比可用大，校验失败)，
     * 3.
     * 2.1 如果校验成功，(如果是卖出操作)则根据实际情况修改可用头寸数量
     * 2.2 如果校验失败，调用废单逻辑(yz提供)
     * 4. 如果校验成功，调用EMS委托接口下单
     * 改名：placement
     */
    public void placement(ChannelPlacement record);

    /**
     * cancelPlacement 撤单操作
     * 1. 校验：判断当前委托状态，如果是已报/部成/正撤状态，则校验通过
     * 2. 校验通过的情况下，修改委托信息状态，改为正撤
     * 3. 校验通过的情况下调用EMS撤单接口
     */
    public void cancelPlacement(Integer placementId);

    /**
     * @param
     * @return
     * @Description (清空通道的当日委托)
     */
    public boolean deletePlacementToday();

    /**
     * 查询通道委托记录
     *
     * @param channelPlacementParams
     * @return
     */
    public List<ChannelPlacement> selectChannelPlacement(ChannelPlacementParams channelPlacementParams);

    /**
     * @param page
     * @param channelPlacementParams
     * @return
     * @Description (查询通道委托)
     */
    public Page getChannelPlacementByPage(Page page, ChannelPlacementParams channelPlacementParams);

    public ChannelPlacement getByPlacementNo(String account, String placementNo);

    public ChannelPlacement getById(Integer id);

    /**
     * @param channelForEmsParameter
     * @return
     * @Description (查询所有的委托)
     */
    public List<ChannelPlacement> getAllForEms(ChannelForEmsParameter channelForEmsParameter);

    /**
     * @param id
     * @param status
     * @return
     * @Description (更新通道委托信息的状态：比如 撤单 已成等)
     * 当委托状态改变的时候，改变委托信息状态
     */
    public boolean updateStatus(Integer id, TradeStatus status, String errorMessage);

    /**
     * @param id
     * @param changeQuantity
     * @param changeAmount
     * @return
     * @Description (更新成交数量和金额)
     */
    public boolean updateQuantityAndAmount(Integer id, BigDecimal changeQuantity, BigDecimal changeAmount);


    /**
     * @param id
     * @param rejectMessage
     * @return
     * @Description 更新拒绝原因
     */
    public boolean fillRejectMessage(String id, String rejectMessage);

    /**
     * @param record
     * @return
     * @Description (生产通道历史委托)
     */
    public boolean saveChannelPlacementHistory(ChannelPlacementHistory record);

    /**
     * @param page
     * @param channelPlacemenParams
     * @return
     * @Description (查询通道历史委托)
     */
    public Page getChannelPlacementHistoryByPage(Page page, ChannelPlacemenHistoryParams channelPlacemenParams);


    /**
     * 更新委托编号(废单原因)
     */
    public void fillPlacementCode(Integer id, String placementCode);


    /**
     * 自动废单
     *
     * @param account
     * @param symbol
     * @param price
     * @param quantity
     * @param side
     * @param executionSno
     * @param errorMessage
     */
    public void sendRejectReportForPB(String account, String symbol, BigDecimal price, BigDecimal quantity, String side, String executionSno, String errorMessage);

    public void fill(ChannelPlacement placement, TransactionType transactionType, BigDecimal quantity, BigDecimal price, String errorMessage,boolean isForceFill);

    ChannelPlacement findDockChannelPlacement(ChannelPlacementParams params);

    List<ChannelPlacement> selectPlacementByStatus(List<String> statusList);

    /**
     * 生成通道历史委托
     */
    public boolean generateHistoryPlacement();

    List<ChannelPlacement> selectNotDockChannelPlacement(ChannelPlacementParams channelPlacementParams);

    public void autoFill();

    public Page findCommissionCollectBypage(Page page, ChannelPlacemenHistoryParams channelParams);
}
