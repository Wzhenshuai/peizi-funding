package com.icaopan.clearing.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.icaopan.clearing.service.CheckLogService;
import com.icaopan.customer.dao.CustomerBillMapper;
import com.icaopan.customer.service.CustomerService;
import com.icaopan.log.LogUtil;
import com.icaopan.trade.model.*;
import com.icaopan.user.service.UserPositionService;
import com.icaopan.user.service.UserService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.icaopan.clearing.service.ClearingService;
import com.icaopan.util.BigDecimalUtil;
import com.icaopan.common.util.TradeFeeUtil;
import com.icaopan.enums.enumBean.TradeFlowNote;
import com.icaopan.enums.enumBean.TradeFowType;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.enums.enumBean.TradeStatus;
import com.icaopan.enums.enumBean.TransactionType;
import com.icaopan.trade.dao.PlacementMapper;
import com.icaopan.trade.service.ChannelPlacementService;
import com.icaopan.trade.service.FillService;
import com.icaopan.trade.service.FlowService;
import com.icaopan.trade.service.PlacementService;
import com.icaopan.trade.service.dto.TradeFeeParam;
import com.icaopan.trade.service.dto.TradeFeeResult;
import com.icaopan.user.service.ChannelPositionService;


@Service("clearingService")
public class ClearingServiceImpl implements ClearingService {

    /**

     * @Description:

     * @author:Wangzs

     * @time:2017年3月3日 下午2:29:19

     */
    @Autowired
    private PlacementMapper placementMapper;
    @Autowired
    private PlacementService placementService;
    @Autowired
    private FillService fillService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChannelPositionService channelPositionService;
    @Autowired
    private UserPositionService userPositionService;
    @Autowired
    private FlowService flowService;
    @Autowired
    private ChannelPlacementService channelPlacementService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerBillMapper customerBillMapper;
    @Autowired
    private CheckLogService checkLogService;

    private static Logger log = LogUtil.getClearLogger();
    @Override
    public void clearingProcess(boolean deleteEmptyPosition){
        try {
            log.info("开始清算");
            log.info("开始取消在途的委托::");
            //取消在途的委托，找出哪些不是终态的，置为废单 递送废单接口
            cancelPlacement();
            log.info("取消在途的委托-完成::");
            log.info("开始资金持仓操作::");
            //资金持仓操作
            processCashPosition();
            log.info("资金持仓操作-完成::");
            log.info("开始证券持仓操作::");
            //证券持仓操作
            processSecurityPosition(deleteEmptyPosition);
            log.info("证券持仓操作-完成::");
            log.info("开始用戶当日委托 生成历史委托 同时生成交割单::");
            //用戶当日委托 生成历史委托 同时生成交割单
            processUserPlacement();
            log.info("用戶当日委托 生成历史委托 同时生成交割单-完成::");
            log.info("开始通道當日委托操作::");
            //通道當日委托操作
            processChannelPlacement();
            log.info("通道當日委托操作-完成::");
            log.info("开始资金方流水操作::");
            // 资金方 流水操作
            processCustomerBill();
            log.info("资金方流水操作-完成::");
            log.info("开始当日成交操作::");
            // 当日成交操作
            processTodayFill();
            log.info("当日成交操作-完成::");
            this.checkCashAndPosition();
            log.info("清算完成");
        } catch (Exception e) {
            log.info("清算异常" + e);
        }

    }

    @Override
    public void clearingProcessTask(){
        clearingProcess(true);
    }


    /**
     * 对账
     */
    public void checkCashAndPosition(){
        log.info("开始对账 用户证券持仓资金和通道证券持仓资金::");
        //对账 用户持仓和通道持仓
        checkLogService.checkPosition();
        checkLogService.checkCashAmount();
        log.info("对账 用户证券持仓资金和通道证券持仓资金-完成::");
    }
    /*public void invalidPlacement(){
        cancelPlacement();
    }*/
    public void processCashPosition(){
        userService.adjustPosition();
    }

    public void processSecurityPosition(boolean deleteEmptyPosition){
        //调平通道证券持仓
        channelPositionService.adjustPosition();
        //调平用户证券持仓
        userPositionService.adjustPosition();
        if(deleteEmptyPosition) {
            this.deleteEmptySecurityPosition();
        }
    }

    public void  deleteEmptySecurityPosition(){
        //删除用户空持仓
        userPositionService.deleteUserSecurityPosition();
        //删除通道空持仓
        channelPositionService.deleteSecurityPosition();
    }

    public void processUserPlacement(){
        //生成用戶历史委托
        List<PlacementHistory> placementHistoryList = generatePlacementHistory();
        //生成交割单，插入数据库
        processExchangeList(placementHistoryList);
        //插入用戶历史委托表
        placementService.insertList(placementHistoryList);
        //删除用戶当日委托
        placementService.deletePlacementToday();
    }

    public void processChannelPlacement(){
        //生成通道历史委托 插入数据库
        channelPlacementService.generateHistoryPlacement();
        //清空通道当日委托
        channelPlacementService.deletePlacementToday();

    }

    //生成交割单
    public void processExchangeList(List<PlacementHistory> placementHistoryList){
        if(placementHistoryList == null || placementHistoryList.isEmpty()){
            return;
        }
        List<Flow> flowList = generateFlowList(placementHistoryList);
        if (flowList == null || flowList.isEmpty()){
            return;
        }
        flowService.saveFlowList(flowList);
    }

    public void processCustomerBill(){

        //生成资金方流水
        customerBillMapper.generateCustomerBill();

        // 扣减资金方当日佣金
        //customerService.updateCustomerBalanceDay();
    }
    public void processTodayFill(){
        //生成當日歷史成交
        fillService.generateHistory();
        //刪除當日成交
        fillService.delete();
    }

    public List<PlacementHistory> generatePlacementHistory(){

        List<Placement> placementList = placementMapper.selectAllPlacement();
        List<PlacementHistory> placementHistoryList = new ArrayList<>();
        for (Placement placement : placementList) {
            PlacementHistory placementHistory = new PlacementHistory();

            TradeFeeParam tradeFeeParam = new TradeFeeParam(placement.getSecurityCode(),placement.getSide(),placement.getFillPrice(),placement.getIsSzTransferFee(),placement.getFillQuantity(),placement.getRatioFee(),placement.getMinCost());
            TradeFeeResult tradeFeeResult = TradeFeeUtil.getFeeByParam(tradeFeeParam);

            placementHistory.setTransferFee(tradeFeeResult.getTransferFee());
            placementHistory.setCommissionFee(tradeFeeResult.getCommissionFee());
            placementHistory.setStampDutyFee(tradeFeeResult.getStampDutyFee());
            placementHistory.setAmount(placement.getAmount());
            placementHistory.setPlacementId(placement.getId());
            //placementHistory.setCustomerId(placement.getChannelId());
            placementHistory.setDateTime(placement.getTime());
            placementHistory.setPrice(placement.getPrice());
            placementHistory.setQuantity(placement.getQuantity());
            placementHistory.setSecurityCode(placement.getSecurityCode());
            placementHistory.setSide(placement.getSide());
            placementHistory.setStatus(placement.getStatus());
            placementHistory.setUserId(placement.getUserId());
            placementHistory.setUserName(placement.getUserName());
            placementHistory.setFillAmount(placement.getFillAmount());
            placementHistory.setFillPrice(placement.getFillPrice());
            placementHistory.setFillQuantity(placement.getFillQuantity());
            placementHistory.setCustomerId(placement.getCustomerId());
            placementHistoryList.add(placementHistory);
        }
        return placementHistoryList;
    }

    public List<Flow> generateFlowList(List<PlacementHistory> placementHistoryList) {
        List<Flow> flowList = new ArrayList<Flow>();
        for (PlacementHistory placementHistory : placementHistoryList) {
            if (BigDecimalUtil.isZero(placementHistory.getFillQuantity())) {
                continue;
            }
            Flow tradeFlow = new Flow();
            tradeFlow.setSecurityCode(placementHistory.getSecurityCode());
            tradeFlow.setAdjustAmount(placementHistory.getFillAmount());
            tradeFlow.setAdjustQuantity(placementHistory.getFillQuantity());
            tradeFlow.setType(StringUtils.equals(placementHistory.getSide().getName(),TradeSide.SELL.getName()) ? TradeFowType.SECURITY_SELL:TradeFowType.SECURITY_BUY);
            //tradeFlow.setFinancing(financing);
            //tradeFlow.setCash(cash);
            //tradeFlow.setCashAmount(cashAmount);
            //tradeFlow.setFinancingAmount(financingAmount);
            tradeFlow.setCustomerId(placementHistory.getCustomerId());
            tradeFlow.setUserId(placementHistory.getUserId());
            tradeFlow.setCommissionFee(placementHistory.getCommissionFee());
            tradeFlow.setTransferFee(placementHistory.getTransferFee());
            tradeFlow.setStampDutyFee(placementHistory.getStampDutyFee());
            tradeFlow.setCreateTime(placementHistory.getDateTime());
            tradeFlow.setNotes(TradeFlowNote.TRADE_TRANSACTION);
            flowList.add(tradeFlow);
        }
        return flowList;
    }

    public void cancelPlacement() {
        List<String> statusList = new ArrayList<>();
        statusList.add(TradeStatus.SENDING.getName());
        statusList.add(TradeStatus.SENDACK.getName());
        statusList.add(TradeStatus.PARTIALLYFILLED.getName());
        statusList.add(TradeStatus.CANCELLING.getName());
        List<ChannelPlacement>  channelPlacements = channelPlacementService.selectPlacementByStatus(statusList);

        for (ChannelPlacement channelPlacement : channelPlacements) {
            BigDecimal quantity = BigDecimalUtil.minus(channelPlacement.getQuantity(), channelPlacement.getFillQuantity());
            //取消在途委托
            channelPlacementService.fill(channelPlacement, TransactionType.INVALID, quantity, channelPlacement.getPrice(), null,false);
        }

    }
}
