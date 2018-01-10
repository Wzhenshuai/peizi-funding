package com.icaopan.user.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.icaopan.common.util.ComparatorPositionVO;
import com.icaopan.common.util.SecurityUtil;
import com.icaopan.enums.enumBean.TradeFlowNote;
import com.icaopan.enums.enumBean.TradeFowType;
import com.icaopan.trade.model.Flow;
import com.icaopan.trade.service.FlowService;
import com.icaopan.util.BigDecimalUtil;
import com.icaopan.util.DateUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icaopan.log.LogUtil;
import com.icaopan.log.annocation.MoneyAdjustLogAnnocation;
import com.icaopan.marketdata.market.MarketdataService;
import com.icaopan.stock.model.StockSecurity;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.user.bean.PositionParams;
import com.icaopan.user.bean.UserPositionParams;
import com.icaopan.user.dao.UserSecurityPositionMapper;
import com.icaopan.user.model.ChannelSecurityPosition;
import com.icaopan.user.model.User;
import com.icaopan.user.model.UserSecurityPosition;
import com.icaopan.user.service.ChannelPositionService;
import com.icaopan.user.service.UserPositionService;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.PageBean;
import com.icaopan.web.vo.SecurityPositionVO;

import elf.api.marketdata.marketdata.MarketDataSnapshot;

/**
 * Created by Administrator on 2017/3/16 0016.
 */
@Service("userPositionService")
public class UserPositionServiceImpl implements UserPositionService{

	private Logger logger=LogUtil.getLogger(getClass());
    @Autowired
    private UserSecurityPositionMapper userSecurityPositionMapper;

    @Autowired
    private MarketdataService marketdataService;

    @Autowired
    private SecurityService securityService;
    @Autowired
    private ChannelPositionService channelPositionService;

    @Autowired
    private FlowService flowService;

    @Override
    public boolean saveUserSecurityPosition(UserSecurityPosition userSecurityPosition) {
        return userSecurityPositionMapper.insert(userSecurityPosition);
    }

    @Override
    public boolean deleteUserSecurityPosition() {
        return userSecurityPositionMapper.deleteEmptyPosition();
    }

    /**
     * 用户持仓查询
     */
	@Override
    public Page<SecurityPositionVO> findUserPositionByPage(Page<SecurityPositionVO> page, UserPositionParams params) {
		List<UserSecurityPosition> listData = userSecurityPositionMapper.findByPage(page, params);
        List<SecurityPositionVO> list=new ArrayList<SecurityPositionVO>();
        for (UserSecurityPosition position : listData){
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
//            if(position.getAmount()==null||position.getAmount().doubleValue()<=0){
//            	continue;
//            }
            //转换VO
            SecurityPositionVO vo= convertSecurityPostionToVO(position, stock, latestPrice);
            vo.setUserName(position.getUserName());
            vo.setCustomerName(position.getCustomerName());
            vo.setCustomerId(position.getCustomerId());
            vo.setSuspensionFlag(suspensionFlag);
            list.add(vo);
        }
        page.setAaData(list);
        return page;
    }

    /**
     * 数据类型转换
     * @param position
     * @param stock
     * @param latestPrice
     * @return
     * */
	private SecurityPositionVO convertSecurityPostionToVO(UserSecurityPosition position, StockSecurity stock, BigDecimal latestPrice){
		SecurityPositionVO vo=new SecurityPositionVO();
		vo.setId(position.getId());
        vo.setAmount(position.getAmount());
        vo.setAvailableAmount(position.getAvailable());
        vo.setCostPrice(position.getCostPrice());
        vo.setTotalCost(position.getTotalCost());
        vo.setLatestPrice(latestPrice);
        vo.setSecurityCode(stock.getCode());
        vo.setSecurityName(stock.getName());
        return vo;
	}

    /**
     * 根据用户持仓编号查询用户持仓信息
     * @param userId,customerId,internalSecurityId
     * @return
     * */
    @Override
    public UserSecurityPosition findUserSecurityPositionById(Integer userId, Integer customerId, String internalSecurityId) {
        return userSecurityPositionMapper.findByUserIdSecurityId(userId, customerId, internalSecurityId);
    }

    @Override
    public UserSecurityPosition findById(Integer id) {
        return userSecurityPositionMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新可用金额
     * @param params
     * @return
     * */
    @Override
    @MoneyAdjustLogAnnocation(tag="用户持仓调整可用")
    public void changeAvailable(UserPositionParams params) {
         userSecurityPositionMapper.updateAvailable(params);
    }

    /**
     * 更新总金额和成本价
     * @param params
     * @return
     * */
    @Override
    @MoneyAdjustLogAnnocation(tag="用户持仓调整")
    public void changeAmountAndCostPrice(UserPositionParams params) {
        int ups =userSecurityPositionMapper.updateAmountAndCostPrice(params);
        if(ups<1){
        	throw new RuntimeException("用户持仓调整数量不正确");
        }
    }

    @Override
    public void updatePositionPrice(Integer userPositionId, BigDecimal costPrice) {
        UserSecurityPosition userPosition = userSecurityPositionMapper.selectByPrimaryKey(userPositionId);
        BigDecimal priceDelta = costPrice.subtract(userPosition.getCostPrice());
        UserPositionParams params = new UserPositionParams(userPosition.getUserId(),userPosition.getInternalSecurityId(),userPosition.getAmount(),userPosition.getAvailable(),priceDelta);
        userSecurityPositionMapper.updateCostPriceDelta(params);

        //添加交割单流水信息
        Flow flow = new Flow();
        flow.setUserId(userPosition.getUserId());
        flow.setType(TradeFowType.COST_PRICE_ADJUST);
        flow.setNotes(TradeFlowNote.HAND_ADJUST);
        flow.setCostPrice(costPrice);
        flow.setCustomerId(userPosition.getCustomerId());
        flow.setAdjustQuantity(userPosition.getAmount());
        flow.setAdjustAmount(BigDecimalUtil.multiply(userPosition.getAmount(),priceDelta));
        //flow.setChannelName(channelSecurityPosition.getChannelName());
        flow.setSecurityCode(SecurityUtil.getSecurityCodeById(userPosition.getInternalSecurityId()));
        flow.setCreateTime(DateUtils.parseDate(new Date()));
        flowService.saveFlow(flow);
    }

    /**
     * 分页查询用户持仓信息
     * @param user
     * @param pageNo
     * @param pageSize
     * @return pagebean
     * */
	@Override
    public PageBean<SecurityPositionVO> findUserPositionByPage(User user, Integer pageNo, Integer pageSize) {
    	Page<SecurityPositionVO> page=new Page<SecurityPositionVO>(pageNo, pageSize);
        //查询参数
        UserPositionParams params=new UserPositionParams();
        params.setUserId(user.getId());
        page= findUserPositionByPage(page,params);
        //转换page->pageBean
		PageBean<SecurityPositionVO> pageBean=new PageBean<SecurityPositionVO>(pageNo, pageSize, page.getiTotalRecords(), page.getAaData());
        return pageBean;
    }

    /**
     * 根据用户信息查询旗下所有持仓信息
     * @param user
     * @return
     * */
    @Override
    public List<SecurityPositionVO> findAllPosition(User user) {
    	 List<SecurityPositionVO> voList=new ArrayList<SecurityPositionVO>();
         List<UserSecurityPosition> list=userSecurityPositionMapper.findByUserId(user.getId());
         for (UserSecurityPosition position : list) {
             //查询股票信息
             StockSecurity stock=securityService.findStockSecurityById(position.getInternalSecurityId());
             if(stock==null){
                 logger.error("持仓查询未找到对应的股票信息："+position.toString());
                 continue;
             }
             //查询现价
             BigDecimal latestPrice =marketdataService.getLatestPrice(stock.getCode());
             //转换VO
             SecurityPositionVO vo= convertSecurityPostionToVO(position, stock, latestPrice);
             voList.add(vo);
         }
         return voList;
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
            return BigDecimal.ZERO;
        }
        return userSecurityPositionMapper.findPostionAvailable(user.getId(), security.getInternalSecurityId());
    }

    @Override
    public BigDecimal findPositionAvailable(User user, String stockCode,Integer channelId) {
    	StockSecurity security=securityService.findByNameAndCode(null, stockCode);
        if(security==null){
            return BigDecimal.ZERO;
        }
        if(channelId!=null){
        	ChannelSecurityPosition position=channelPositionService.find(user.getId(), channelId, security.getInternalSecurityId());
        	if(position==null){
        		return BigDecimal.ZERO;
        	}
        	return position.getAvailable();
        }else{
        	return userSecurityPositionMapper.findPostionAvailable(user.getId(), security.getInternalSecurityId());
        }
    }
    
    /**
     * 调整持仓:日切调平证券头寸
     * */
    @Override
    public void adjustPosition() {
        userSecurityPositionMapper.adjustPosition();
    }

    // 根据用户和证券代码查询用户该股票持仓
    @Override
    public List<UserSecurityPosition> queryPositionByUserIdStockCode(Integer userId, String stockCode) {
        return userSecurityPositionMapper.queryPositionByUserIdStockCode(userId, stockCode);
    }

    // 根据用户和证券类型查询用户改类型的股票持仓
    @Override
    public List<UserSecurityPosition> queryPositionByUserIdStockType(Integer userId, String stockType) {
        return userSecurityPositionMapper.queryPositionByUserIdStockType(userId, stockType);
    }

    @Override
    public List<SecurityPositionVO> queryUserPositionByParams(UserPositionParams params){
        List<UserSecurityPosition> listData = userSecurityPositionMapper.findByPage(null, params);
        List<SecurityPositionVO> list=new ArrayList<SecurityPositionVO>();
        for (UserSecurityPosition position : listData){
            //查询股票信息
            StockSecurity stock=securityService.findStockSecurityById(position.getInternalSecurityId());
            if(stock==null){
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
            SecurityPositionVO vo= convertSecurityPostionToVO(position, stock, latestPrice);
            vo.setUserName(position.getUserName());
            vo.setCustomerName(position.getCustomerName());
            vo.setCustomerId(position.getCustomerId());
            vo.setSuspensionFlag(suspensionFlag);
            list.add(vo);
        }
        return list;
    }

    @Override
    public Page<SecurityPositionVO> querySummaryPositionByCustomerId(Page<SecurityPositionVO> page,PositionParams params) {
        List<UserSecurityPosition> listData = null;
        if (page==null){
            page = new Page<SecurityPositionVO>();
            listData = userSecurityPositionMapper.queryPositionByCustomerId(null,params);
        }else {
            listData = userSecurityPositionMapper.queryPositionByCustomerId(page,params);
        }
        List<SecurityPositionVO> list=new ArrayList<SecurityPositionVO>();
        for (UserSecurityPosition position : listData){
            //查询股票信息
            StockSecurity stock=securityService.findStockSecurityById(position.getInternalSecurityId());
            if(stock==null){
                continue;
            }
            //查询现价
            BigDecimal latestPrice =marketdataService.getLatestPrice(stock.getCode());
            //转换VO
            SecurityPositionVO vo= convertSecurityPostionToVO(position, stock, latestPrice);
            vo.setCustomerName(position.getCustomerName());
            vo.setCustomerId(position.getCustomerId());
            vo.setUserName(position.getUserName());
            list.add(vo);
        }
        ComparatorPositionVO comparatorPositionVO = new ComparatorPositionVO();
        Collections.sort(list,comparatorPositionVO);
        page.setAaData(list);
        return page;
    }

    @Override
    public Page<SecurityPositionVO> queryByCustomerIdAndInternalSecurityId(Page<SecurityPositionVO> page,PositionParams params) {
        List<UserSecurityPosition> listData = userSecurityPositionMapper.queryByCustomerIdAndInternalSecurityId(page,params);
        List<SecurityPositionVO> list=new ArrayList<SecurityPositionVO>();
        for (UserSecurityPosition position : listData){
            //查询股票信息
            StockSecurity stock=securityService.findStockSecurityById(position.getInternalSecurityId());
            if(stock==null){
                continue;
            }
            //转换VO
            SecurityPositionVO vo= convertSecurityPostionToVO(position, stock, null);
            vo.setCustomerName(position.getCustomerName());
            vo.setCustomerId(position.getCustomerId());
            vo.setUserName(position.getUserName());
            list.add(vo);
        }
        page.setAaData(list);
        return page;
    }
}
