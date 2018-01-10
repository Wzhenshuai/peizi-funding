package com.icaopan.user.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.icaopan.common.util.SecurityUtil;
import com.icaopan.enums.enumBean.TradeFlowNote;
import com.icaopan.enums.enumBean.TradeFowType;
import com.icaopan.log.LogUtil;
import com.icaopan.log.annocation.MoneyAdjustLogAnnocation;
import com.icaopan.marketdata.market.MarketdataService;
import com.icaopan.stock.model.StockSecurity;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.trade.dao.FlowMapper;
import com.icaopan.trade.model.ChannelPlacement;
import com.icaopan.trade.model.Flow;
import com.icaopan.trade.service.FlowService;
import com.icaopan.user.bean.PositionParams;
import com.icaopan.user.bean.UserPositionParams;
import com.icaopan.user.dao.ChannelSecurityPositionMapper;
import com.icaopan.user.dao.UserSecurityPositionMapper;
import com.icaopan.user.model.ChannelSecurityPosition;
import com.icaopan.user.model.User;
import com.icaopan.user.model.UserChannelPosition;
import com.icaopan.user.model.UserSecurityPosition;
import com.icaopan.user.service.ChannelPositionService;
import com.icaopan.user.service.UserPositionService;
import com.icaopan.util.BigDecimalUtil;
import com.icaopan.util.DateUtils;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.PageBean;
import com.icaopan.web.vo.SecurityPositionVO;

import elf.api.marketdata.marketdata.MarketDataSnapshot;

/**
 * Created by RoyLeong @royleo.xyz on 2016/12/19.
 */
@Service("channelPositionService")
public class ChannelPositionServiceImpl implements ChannelPositionService {

    private Logger logger=LogUtil.getLogger(getClass());
    @Autowired
    FlowMapper flowMapper;
    @Autowired
    private ChannelSecurityPositionMapper channelSecurityPositionMapper;
    @Autowired
    private UserSecurityPositionMapper userSecurityPositionMapper;
    @Autowired
    private MarketdataService marketdataService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserPositionService userPositionService;

    @Autowired
    private FlowService flowService;

    /**
     * 保存通道持仓信息
     * @param channelSecurityPosition
     * @return
     * */
    @Override
    public boolean saveSecurityPosition(ChannelSecurityPosition channelSecurityPosition) {
        return channelSecurityPositionMapper.insert(channelSecurityPosition);
    }

    /**
     * 初始化通道持仓(创建一条新的通道持仓记录)
     * @param placement
     * @return
     * */
    @Override
    public ChannelSecurityPosition initialPosition(ChannelPlacement placement) {
        StockSecurity stockSecurity = securityService.findByNameAndCode(null, placement.getSecurityCode());
        ChannelSecurityPosition position = new ChannelSecurityPosition(placement.getCustomerId(),stockSecurity.getInternalSecurityId(),placement.getChannelId(),placement.getUserId());
        saveSecurityPosition(position);
        return position;
    }

    /**
     * 验证股票编号是否存在，如果存在，则返回true
     * */
    @Override
    public boolean verify(Integer userId,String InternalSecurityId) {
        ChannelSecurityPosition securityPosition = channelSecurityPositionMapper.findByUserIdAndInternalSecurityId(userId,InternalSecurityId);
        return securityPosition!=null;
    }

    /**
     * 清除空持仓
     * */
    @Override
    public boolean deleteSecurityPosition() {
        return channelSecurityPositionMapper.deleteEmptyPosition();
    }

    /**
     * 通道持仓分页查询
     */
    @Override
    public Page<SecurityPositionVO> findPositionByPage(Page<SecurityPositionVO> page,PositionParams params) {
        List<ChannelSecurityPosition> listData = channelSecurityPositionMapper.findByPage(page, params);
        List<SecurityPositionVO> list=new ArrayList<SecurityPositionVO>();
        for (ChannelSecurityPosition position : listData){
            //查询股票信息
            StockSecurity stock=securityService.findStockSecurityById(position.getInternalSecurityId());
            if(stock==null){
                logger.error("持仓查询未找到对应的股票信息："+position.toString());
                continue;
            }
            //查询现价
            BigDecimal latestPrice=BigDecimal.ZERO;
            MarketDataSnapshot  shot=marketdataService.getBySymbol(stock.getCode());
            boolean suspensionFlag = false;
            if(shot!=null){
                latestPrice= new BigDecimal(shot.getPrice()+"");
                if(shot.isSuspensionFlag()){
                    suspensionFlag = true;
                }
            }
            //转换VO
            SecurityPositionVO vo= convertSecurityPositionToVO(position, stock, latestPrice);
            vo.setSuspensionFlag(suspensionFlag);
            list.add(vo);
        }
        page.setAaData(list);
        return page;
    }

    @Override
    public BigDecimal getMaketValueByUserId(Integer userId) {
        Page<SecurityPositionVO> page = new Page<SecurityPositionVO>();
        PositionParams params = new PositionParams();
        params.setUserId(userId);
        page = findPositionByPage(page,params);
        BigDecimal result = BigDecimal.ZERO;
        List<SecurityPositionVO> vo = page.getAaData();
        for (SecurityPositionVO v : vo){
            result = result.add(v.getMarketValue());
        }
        return result;
    }

    /**
     * 转换数据格式
     * */
    private SecurityPositionVO convertSecurityPositionToVO(ChannelSecurityPosition position, StockSecurity stock, BigDecimal latestPrice){
        SecurityPositionVO vo=new SecurityPositionVO();
        vo.setId(position.getId());
        vo.setAmount(position.getAmount());
        vo.setAvailableAmount(position.getAvailable());
        vo.setCostPrice(position.getCostPrice());
        vo.setTotalCost(position.getTotalCost());
        vo.setLatestPrice(latestPrice);
        vo.setSecurityCode(stock.getCode());
        vo.setSecurityName(stock.getName());
        vo.setChannelName(position.getChannelName());
        vo.setUserName(position.getUserName());
        vo.setCustomerName(position.getCustomerName());
        vo.setCustomerId(position.getCustomerId());
        vo.setChannelId(position.getChannelId());
        vo.setTotalCost(position.getTotalCost());
        vo.setCustomerNotes(position.getCustomerNotes());
        return vo;
    }

    /**
     * 网站前台分页查询用户持仓
     * @param user
     * @return
     */
    @Override
    public PageBean<SecurityPositionVO> findPositionByPage(User user,Integer pageNo,Integer pageSize){
        Page<SecurityPositionVO> page=new Page<SecurityPositionVO>(pageNo, pageSize);
        //查询参数
        PositionParams params=new PositionParams();
        params.setUserId(user.getId());
        page= findPositionByPage(page,params);
        //转换page->pageBean
        PageBean<SecurityPositionVO> pageBean=new PageBean<SecurityPositionVO>(pageNo, pageSize, page.getiTotalRecords(), page.getAaData());
        return pageBean;
    }

    /**
     * 查询所有的证券持仓
     * @param user
     * @return
     */
    @Override
    public List<SecurityPositionVO> findAllPosition(User user){
        List<SecurityPositionVO> voList=new ArrayList<SecurityPositionVO>();
        List<ChannelSecurityPosition> list=channelSecurityPositionMapper.findByUserId(user.getId());
        for (ChannelSecurityPosition position : list) {
            //查询股票信息
            StockSecurity stock=securityService.findStockSecurityById(position.getInternalSecurityId());
            if(stock==null){
                logger.error("持仓查询未找到对应的股票信息："+position.toString());
                continue;
            }
            //查询现价
            BigDecimal latestPrice =BigDecimal.ZERO;
            MarketDataSnapshot shot=marketdataService.getBySymbol(stock.getCode());
            if(shot!=null){
                latestPrice = new BigDecimal(shot.getPrice()+"");
            }
            //转换VO
            SecurityPositionVO vo= convertSecurityPositionToVO(position, stock, latestPrice);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public List<ChannelSecurityPosition> findAllPositionBySecurityId(String securityId){
    	return channelSecurityPositionMapper.findAllPostionBySecurityId(securityId);
    }

    @Override
    public boolean verifyChannel(Integer userId,Integer channelId, String internalSecurityId) {
        ChannelSecurityPosition securityPosition = channelSecurityPositionMapper.verifyChannel(userId,channelId,internalSecurityId);
        return securityPosition!=null;
    }

    @Override
    public List<ChannelSecurityPosition> findByUserIdSecurityId(Integer userId, String internalSecurityId, Integer customerId) {
        return channelSecurityPositionMapper.findByUserIdSecurityId(userId,internalSecurityId,customerId);
    }


    /**
     * 根据主键编号查询通道持仓信息
     * @param id
     * @return
     * */
    @Override
    public ChannelSecurityPosition findSecurityPositionById(Integer id) {
        return channelSecurityPositionMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据用户编号，通道编号，股票编号查询通道持仓信息
     * @param userId
     * @param channelId
     * @param internalSecurityId
     * @return
     * */
    @Override
    public ChannelSecurityPosition find(Integer userId, Integer channelId, String internalSecurityId) {
        return channelSecurityPositionMapper.findByUserIdAndChannelIdAndInternalSecurityId(userId, channelId, internalSecurityId);
    }

    @Override
    public Page<SecurityPositionVO> findByCustomerIdAndInternalId(Page<SecurityPositionVO> page, PositionParams params) {
        List<ChannelSecurityPosition> listData = channelSecurityPositionMapper.findByCustomerIdAndInternalSecurityId(page,params);
        List<SecurityPositionVO> list=new ArrayList<SecurityPositionVO>();
        for (ChannelSecurityPosition position : listData){
            //查询股票信息
            StockSecurity stock=securityService.findStockSecurityById(position.getInternalSecurityId());
            if(stock==null){
                continue;
            }
            //转换VO
            SecurityPositionVO vo= new SecurityPositionVO();
            vo.setAmount(position.getAmount());
            vo.setAvailableAmount(position.getAvailable());
            vo.setChannelName(position.getChannelName());
            vo.setSecurityCode(stock.getCode());
            vo.setSecurityName(stock.getName());
            vo.setCustomerName(position.getCustomerName());
            vo.setCustomerId(position.getCustomerId());
            vo.setTotalCost(position.getTotalCost());
            list.add(vo);
        }
        page.setAaData(list);
        return page;
    }

    /**
     * 更新可用金额
     * @param id
     * @param availableChanged
     * @return
     * */
    @Override
    public void changeAvailable(Integer id, BigDecimal availableChanged) {
        int ups= channelSecurityPositionMapper.updateAvailable(id, availableChanged);
        if(ups<1){
        	throw new RuntimeException("通道持仓调整数量不正确");
        }
    }

    /**
     * 更新总金额和成本价
     * @param id
     * @param amount
     * @param costPrice
     * @return
     * */
    @Override
    public void changeAmountAndCostPrice(Integer id, BigDecimal amount, BigDecimal costPrice) {
        int ups =channelSecurityPositionMapper.updateAmountAndCostPrice(id, amount, costPrice);
        if(ups<1){
        	throw new RuntimeException("通道持仓调整数量不正确");
        }
    }

    @Override
    public boolean movePosition(ChannelSecurityPosition cp, Integer targetId, BigDecimal moveAmount,String isHiddenRecord) {
        if (cp==null) return false;
        ChannelSecurityPosition targetPosition = find(cp.getUserId(),targetId,cp.getInternalSecurityId());
        Boolean bool = false;
        if (targetPosition==null){      //目标通道没有改持仓，新建持仓
            targetPosition = new ChannelSecurityPosition();
            targetPosition.setUserId(cp.getUserId());
            targetPosition.setChannelId(targetId);
            targetPosition.setInternalSecurityId(cp.getInternalSecurityId());
            targetPosition.setAmount(moveAmount);
            targetPosition.setAvailable(moveAmount);
            targetPosition.setCustomerId(cp.getCustomerId());
            targetPosition.setTotalCost(BigDecimalUtil.multiply(cp.getCostPrice(),moveAmount));
            targetPosition.setCostPrice(cp.getCostPrice());
            bool = saveSecurityPosition(targetPosition);
        }else {                         //该用户在目标通道有持仓，加仓，调成本
            int css = channelSecurityPositionMapper.updateAmountAndCostPrice(targetPosition.getId(),moveAmount,cp.getCostPrice());  //调整总持仓和成本价
            int cvs = channelSecurityPositionMapper.updateAvailable(targetPosition.getId(),moveAmount);     //调整可用
            bool = (css>0)&&(cvs>0);
        }
        int current = channelSecurityPositionMapper.updateAmountAndCostPrice(cp.getId(),moveAmount.negate(),null);  //调整总持仓
        int currentAVS = channelSecurityPositionMapper.updateAvailable(cp.getId(),moveAmount.negate());     //调整可用持仓
        bool  = (current>0)&&(currentAVS>0)&&bool;
        if(!bool){
            throw new RuntimeException("通道转移失败");
        }
        //加仓记录
        saveMoveStockFlowLog(cp.getUserId(),moveAmount,cp.getCostPrice(),cp.getCustomerId(),targetId,cp.getInternalSecurityId(),isHiddenRecord);
        //减仓记录
        saveMoveStockFlowLog(cp.getUserId(),moveAmount.negate(),cp.getCostPrice(),cp.getCustomerId(),cp.getChannelId(),cp.getInternalSecurityId(),isHiddenRecord);
        return bool;
    }

    /**
     * 保存持仓迁移交割单数据
     * */
    public boolean saveMoveStockFlowLog(Integer userId,BigDecimal amountChanged,BigDecimal costPrice,Integer customerId,Integer channelId,String internalSecurityId,String isHiddenRecord){
        boolean bool = false;
        //添加交割单流水信息
        Flow flow = new Flow();
        flow.setUserId(userId);
        if (amountChanged.compareTo(BigDecimal.ZERO)==1){
            flow.setType(TradeFowType.STOCK_ADD);
            flow.setNotes(TradeFlowNote.CASH_ADDCREATEHAND_MV);
        }else {
            flow.setType(TradeFowType.STOCK_REDUCE);
            flow.setNotes(TradeFlowNote.CASH_REDUCEBYHAND_MV);
        }
        flow.setCostPrice(costPrice);
        flow.setCustomerId(customerId);
        flow.setAdjustQuantity(amountChanged);
        flow.setChannelId(channelId);
        flow.setAdjustAmount(BigDecimalUtil.multiply(flow.getCostPrice(),flow.getAdjustQuantity()));        //根据成交价乘发生数量获得发生金额
        flow.setSecurityCode(SecurityUtil.getSecurityCodeById(internalSecurityId));
        if(StringUtils.isEmpty(isHiddenRecord)){
            isHiddenRecord="0";
        }
        flow.setIsHidden(isHiddenRecord);
        bool = flowService.saveFlow(flow);
        return bool;
    }

    @Override
    @MoneyAdjustLogAnnocation(tag="通道持仓调整")
    public void updatePosition(Integer channelPositionId, BigDecimal availableChanged, BigDecimal amountChanged, BigDecimal costPrice, String createTime,String isHiddenRecord) {
        int ups=channelSecurityPositionMapper.updateAvailable(channelPositionId,availableChanged);
        if(ups<1){
        	throw new RuntimeException("通道持仓调整数量不正确");
        }
        int css = channelSecurityPositionMapper.updateAmountAndCostPrice(channelPositionId,amountChanged,costPrice);
        if(css<1){
        	throw new RuntimeException("通道持仓调整数量不正确");
        }
        ChannelSecurityPosition channelSecurityPosition = channelSecurityPositionMapper.selectByPrimaryKey(channelPositionId);
        UserSecurityPosition userPosition = userSecurityPositionMapper.findByUserIdSecurityId(channelSecurityPosition.getUserId(), null, channelSecurityPosition.getInternalSecurityId());
        if (userPosition==null){
            userPosition = new UserSecurityPosition(channelSecurityPosition.getCustomerId(),channelSecurityPosition.getInternalSecurityId(),
                    channelSecurityPosition.getUserId(),availableChanged,amountChanged,channelSecurityPosition.getCostPrice());
            userSecurityPositionMapper.insert(userPosition);
        }else {
            UserPositionParams userPositionParams = new UserPositionParams(channelSecurityPosition.getUserId(), channelSecurityPosition.getInternalSecurityId(), amountChanged, availableChanged, costPrice);
            int  items = userSecurityPositionMapper.updateAvailable(userPositionParams);
            if(items<1){
            	throw new RuntimeException("头寸数量更新不正确");
            }
            //查询
            int uss = userSecurityPositionMapper.updateAmountAndCostPrice(userPositionParams);
            if(uss<1){
            	throw new RuntimeException("用户持仓调整数量不证券");
            }
        }
        //添加交割单流水信息
        Flow flow = new Flow();
        flow.setUserId(channelSecurityPosition.getUserId());
        if (amountChanged.compareTo(BigDecimal.ZERO)==1){
            flow.setType(TradeFowType.STOCK_ADD);
            flow.setNotes(TradeFlowNote.CASH_ADDCREATEHAND);
        }else {
            flow.setType(TradeFowType.STOCK_REDUCE);
            flow.setNotes(TradeFlowNote.CASH_REDUCEBYHAND);
        }
        flow.setCostPrice(channelSecurityPosition.getCostPrice());
        flow.setCustomerId(channelSecurityPosition.getCustomerId());
        flow.setAdjustQuantity(amountChanged);
        flow.setChannelId(channelSecurityPosition.getChannelId());
        flow.setAdjustAmount(BigDecimalUtil.multiply(flow.getCostPrice(),flow.getAdjustQuantity()));        //根据成交价乘发生数量获得发生金额
        flow.setSecurityCode(SecurityUtil.getSecurityCodeById(channelSecurityPosition.getInternalSecurityId()));
        flow.setCreateTime(DateUtils.parseDate(createTime));
        if(StringUtils.isEmpty(isHiddenRecord)){
        	isHiddenRecord="0";
        }
        flow.setIsHidden(isHiddenRecord);
        flowService.saveFlow(flow);
    }

    @Override
    @MoneyAdjustLogAnnocation(tag="通道持仓成本价调整")
    public void updatePositionPrice(Integer channelPositionId, BigDecimal costPrice) {
        ChannelSecurityPosition channelSecurityPosition = channelSecurityPositionMapper.selectByPrimaryKey(channelPositionId);
        channelSecurityPositionMapper.updateAmountAndCostPrice(channelPositionId, null, costPrice);
        BigDecimal priceDelta = costPrice.subtract(channelSecurityPosition.getCostPrice());
        UserPositionParams userPositionParams = new UserPositionParams(channelSecurityPosition.getUserId(), channelSecurityPosition.getInternalSecurityId(), channelSecurityPosition.getAmount(), channelSecurityPosition.getAvailable(), priceDelta);
        userSecurityPositionMapper.updateCostPriceDelta(userPositionParams);

        //添加交割单流水信息
        Flow flow = new Flow();
        flow.setUserId(channelSecurityPosition.getUserId());

        flow.setType(TradeFowType.COST_PRICE_ADJUST);
        flow.setNotes(TradeFlowNote.HAND_ADJUST);
        flow.setCostPrice(costPrice);
        flow.setChannelId(channelSecurityPosition.getChannelId());
        flow.setCustomerId(channelSecurityPosition.getCustomerId());
        //flow.setChannelName(channelSecurityPosition.getChannelName());
        flow.setSecurityCode(SecurityUtil.getSecurityCodeById(channelSecurityPosition.getInternalSecurityId()));
        flow.setCreateTime(DateUtils.parseDate(new Date()));
        flowService.saveFlow(flow);
    }

    /**
     * 查询可用金额
     * @param user
     * @param stockCode
     * @return
     * */
    @Override
    public BigDecimal findPositionAvailable(User user, String stockCode) {
        StockSecurity security=securityService.findByNameAndCode(null, stockCode);
        if(security==null){
            return null;
        }
        return channelSecurityPositionMapper.findPostionAvailable(user.getId(), security.getInternalSecurityId());
    }

    /**
     * 日切调整持仓
     * */
    @Override
    public void adjustPosition() {
        channelSecurityPositionMapper.adjustPosition();
    }

    /**
     * 根据用户编号，股票编号查询总金额
     * @param userId
     * @param stockCode
     * @return BigDecimal
     * */
    @Override
    public BigDecimal queryAmountByUserIdStockCode(Integer userId, String stockCode) {
        return channelSecurityPositionMapper.queryAmountByUserIdStockCode(userId, stockCode);
    }

    /**
     * 根据用户编号，股票类型查询总金额
     * @param userId
     * @param stockType
     * @return
     * */
    @Override
    public BigDecimal queryAmountByUserIdStockType(Integer userId, String stockType) {
        return channelSecurityPositionMapper.queryAmountByUserIdStockType(userId, stockType);
    }

    @Override
    public List<SecurityPositionVO> findPositionByParams(PositionParams params){
        List<ChannelSecurityPosition> listData = channelSecurityPositionMapper.findByPage(null, params);
        List<SecurityPositionVO> list=new ArrayList<SecurityPositionVO>();
        for (ChannelSecurityPosition position : listData){
            //查询股票信息
            StockSecurity stock=securityService.findStockSecurityById(position.getInternalSecurityId());
            if(stock==null){
                logger.error("持仓查询未找到对应的股票信息："+position.toString());
                continue;
            }
            //查询现价
            BigDecimal latestPrice=BigDecimal.ZERO;
            MarketDataSnapshot  shot=marketdataService.getBySymbol(stock.getCode());
            if(shot!=null){
                latestPrice= new BigDecimal(shot.getPrice()+"");
            }
            //转换VO
            SecurityPositionVO vo= convertSecurityPositionToVO(position, stock, latestPrice);
            list.add(vo);
        }
        return list;
    }

    @Override
    public List<UserChannelPosition> queryUserChannelPositionByIdStock(Integer userId, String stockCode) {
        String internalSecurityId = SecurityUtil.getInternalSecurityIdBySecurityCode(stockCode);
        if (StringUtils.isNotEmpty(internalSecurityId)){
            return channelSecurityPositionMapper.queryUserChannelPositionByIdStock(userId,internalSecurityId);
        }else {
            return null;
        }
    }
    
    @Override
    public Double querySummarySecurityPostionAmount(String account,String stockCode){
    	String securityId=SecurityUtil.getInternalSecurityIdBySecurityCode(stockCode);
    	return channelSecurityPositionMapper.querySummarySecurityPostionAmount(account, securityId);
    }
    
    @Override
    
    public  List<ChannelSecurityPosition> queryAllByAccount(String account,String securityId){
    	return channelSecurityPositionMapper.queryAllByAccount(account,securityId);
    }

    @Override
    public List<ChannelSecurityPosition> findByChannelId(Integer channelId) {
        return channelSecurityPositionMapper.find(null,null,channelId.toString(),null);
    }

    @Override
    public boolean clearPositionByChannelId(Integer channelId) {
        List<ChannelSecurityPosition> channelSecurityPositions = findByChannelId(channelId);
        try {
            for (ChannelSecurityPosition csp : channelSecurityPositions){
                logger.info("更新持仓数据：将通道数据"+ JSON.toJSONString(csp)+"的可用头寸和中头寸调整为0，调整对应的成本价");
                updatePosition(csp.getId(),csp.getAvailable().negate(),csp.getAmount().negate(),csp.getCostPrice(),null,null);
            }
            deleteSecurityPosition();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<String> queryAllStockCode(){
    	List<String> ls=new ArrayList<String>();
    	List<String> list=channelSecurityPositionMapper.queryAllStockCode();
    	for (String secId : list) {
			if(StringUtils.isNotEmpty(secId)&&secId.length()>=6){
				String code=secId.substring(0,6);
				ls.add(code);
			}
		}
    	return ls;
    }
}
