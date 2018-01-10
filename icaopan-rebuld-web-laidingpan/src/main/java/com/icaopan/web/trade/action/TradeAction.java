package com.icaopan.web.trade.action;

import com.icaopan.common.util.DataUtils;
import com.icaopan.customer.model.Channel;
import com.icaopan.customer.service.ChannelService;
import com.icaopan.enums.enumBean.TradeFowType;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.marketdata.market.MarketdataService;
import com.icaopan.trade.bean.FillHistoryParams;
import com.icaopan.trade.bean.FillSummaryParams;
import com.icaopan.trade.bean.FlowParams;
import com.icaopan.trade.bean.PlacementHistoryParams;
import com.icaopan.trade.model.Placement;
import com.icaopan.trade.service.FillService;
import com.icaopan.trade.service.FlowService;
import com.icaopan.trade.service.PlacementService;
import com.icaopan.user.model.User;
import com.icaopan.user.model.UserChannelPosition;
import com.icaopan.user.service.ChannelPositionService;
import com.icaopan.user.service.UserPositionService;
import com.icaopan.user.service.UserService;
import com.icaopan.util.page.Page;
import com.icaopan.web.common.BaseAction;
import com.icaopan.web.common.ResponseResult;
import com.icaopan.web.user.realm.LoginRealm;
import com.icaopan.web.vo.*;

import elf.api.marketdata.marketdata.MarketDataSnapshot;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/trade")
public class TradeAction extends BaseAction {

    @Autowired
    private UserPositionService userPositionService;
    @Autowired
    private PlacementService    placementService;
    @Autowired
    private FillService         fillService;
    @Autowired
    private FlowService         flowService;
    @Autowired
    private UserService         userService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private ChannelPositionService channelPositionService;
    @Autowired
    private MarketdataService marketdataService;
    
    /**
     * 我要炒股页面
     *
     * @return
     */
    @RequestMapping(value = "/StocksIndex")
    @RequiresUser
    public String stocksIndex() {
        return "stocks/wycg";
    }

    /**
     * 股票买入页面
     *
     * @return
     */
    @RequestMapping(value = "/BuyStocksInit")
    @RequiresUser
    public String buyStocksInit() {
        return "stocks/wycg_gpmr";
    }
    
    /**
     * 高级股票买入页面
     *
     * @return
     */
    @RequestMapping(value = "/BuyStocksInitSenior")
    @RequiresUser
    public String buyStocksInitSenior(HttpServletRequest request) {
    	User user=LoginRealm.getCurrentUser();
    	List<Channel> channelList=channelService.selectChannelByUserId(user.getId());
    	request.setAttribute("channelList", channelList);
        return "stocks/wycg_gpmr_senior";
    }
    

    /**
     * 股票卖出页面
     *
     * @return
     */
    @RequestMapping(value = "/SellStocksInit")
    @RequiresUser
    public String sellStocksInit() {
        return "stocks/wycg_gpmc";
    }

    /**
     * 高级股票卖出页面
     *
     * @return
     */
    @RequestMapping(value = "/SellStocksInitSenior")
    @RequiresUser
    public String sellStocksInitSenior() {
        return "stocks/wycg_gpmc_senior";
    }
    
    /**
     * 股票撤单页面
     *
     * @return
     */
    @RequestMapping(value = "/CancelStocksInit")
    @RequiresUser
    public String cancelStocksInit() {
        return "stocks/wycg_gpcd";
    }

    /**
     * 我的持仓页面
     *
     * @return
     */
    @RequestMapping(value = "/QueryHoldStocksInit")
    @RequiresUser
    public String queryHoldStocksInit() {
        return "stocks/wycg_wdcc";
    }

    /**
     * 交割单查询页面
     *
     * @return
     */
    @RequestMapping(value = "/QueryJiaoGeDanInit")
    @RequiresUser
    public String queryJiaoGeDanInit() {
        return "stocks/wycg_jgd";
    }

    /**
     * 成交记录查询页面
     *
     * @return
     */
    @RequestMapping(value = "/QueryFillInit")
    @RequiresUser
    public String queryFillInit() {
        return "stocks/wycg_cjjl";
    }

    /**
     * 委托查询页面
     *
     * @return
     */
    @RequestMapping(value = "/QueryPlacementInit")
    @RequiresUser
    public String queryPlacementInit() {
        return "stocks/wycg_wtjl";
    }

    @RequestMapping(value = "/QuerySellStockChannel")
    @RequiresUser
    @ResponseBody
    public Object QuerySellStockChannel(String stockCode){
    	User user = LoginRealm.getCurrentUser();
    	List<UserChannelPosition> list=channelPositionService.queryUserChannelPositionByIdStock(user.getId(), stockCode);
    	return list;
    }
    
    /**
     * 分页查询用户持仓
     *
     * @return
     */
    @RequestMapping(value = "/queryHoldStockListByPage")
    @RequiresUser
    @ResponseBody
    public Object queryHoldStockListByPage(Integer page, Integer pageSize) {
        User user = LoginRealm.getCurrentUser();
        PageBean<SecurityPositionVO> pageBean = userPositionService.findUserPositionByPage(user, page, pageSize);
        return pageBean;
    }

    /**
     * 查询单个用户的持仓信息
     *
     * @param stockCode
     * @return
     */
    @RequestMapping(value = "/QueryHoldedStockByStockCode")
    @RequiresUser
    @ResponseBody
    public Object queryHoldedStockByStockCode(String stockCode,Integer channelId) {
        BigDecimal available = userPositionService.findPositionAvailable(getUser(), stockCode,channelId);
        if (available == null) {
            available = BigDecimal.ZERO;
        }
        return available;
    }

    /**
     * 交割单查询
     *
     * @return
     */
    @RequestMapping(value = "/queryDeliveryOrderList")
    @RequiresUser
    @ResponseBody
    public Object queryDeliveryOrderList() {
        return "";
    }

    /**
     * 成交历史查询
     *
     * @return
     */
    @RequestMapping(value = "/queryFillHistoryByConditionsByPage")
    @RequiresUser
    @ResponseBody
    public Object queryFillHistoryByConditionsByPage() {
        return "";
    }


    /**
     * 分页查询当日或者历史成交记录
     *
     * @return
     */
    @RequestMapping(value = "/queryFillOrFillHistoryByPage")
    @RequiresUser
    @ResponseBody
    public Object queryFillByPage(String startDate, String endDate, String tradeType, String stockCode, Boolean isCurrentDay, Integer page, Integer pageSize) {
        User user = LoginRealm.getCurrentUser();
        PageBean<FillVO> pageBean = null;
        if (isCurrentDay != null && isCurrentDay) {
            pageBean = fillService.queryFillByPage(user, page, pageSize);
        } else {
            FillHistoryParams params = new FillHistoryParams();
            params.setUserId(user.getId());
            params.setSide(tradeType);
            params.setStartTime(startDate);
            params.setEndTime(endDate);
            pageBean = fillService.queryFillHistoryByPage(params, page, pageSize);
        }
        return pageBean;
    }

    /**
     * 成交汇总查询
     *
     * @return
     */
    @RequestMapping(value = "/queryFillSummaryByConditionsByPage")
    @RequiresUser
    @ResponseBody
    public Object queryFillSummaryByConditionsByPage(String startDate, String endDate, String tradeType, String stockCode, Integer page, Integer pageSize) {
        User user = LoginRealm.getCurrentUser();
        PageBean<FillSummaryVO> pageBean = null;
        FillSummaryParams params = new FillSummaryParams();
        params.setUserId(user.getId());
        params.setSide(tradeType);
        params.setStartTime(startDate);
        params.setEndTime(endDate);
        params.setSecurityCode(stockCode);
        pageBean = fillService.queryFillSummaryByPage(params, page, pageSize);
        return pageBean;
    }


    /**
     * 查询当日未结束的委托
     *
     * @return
     */
    @RequestMapping(value = "/queryPlacementCurrentDayNotEnd")
    @RequiresUser
    @ResponseBody
    public Object queryPlacementCurrentDayNotEnd() {
        List<PlacementVO> list = placementService.queryCurrentDayPlacementNotEnd(getUser());
        return list;
    }

    /**
     * 查询当日委托
     *
     * @return
     */
    @RequestMapping(value = "/queryPlacementCurrentDay")
    @RequiresUser
    @ResponseBody
    public Object queryPlacementCurrentDay() {
        List<PlacementVO> list = placementService.queryCurrentDayPlacement(getUser());
        return list;
    }

    /**
     * 分页查询当日委托
     *
     * @return
     */
    @RequestMapping(value = "/queryPlacementCurrentDayByPage")
    @RequiresUser
    @ResponseBody
    public Object queryPlacementCurrentDayListByPage(Integer page, Integer pageSize) {
        User user = LoginRealm.getCurrentUser();
        PageBean<PlacementVO> pageBean = placementService.queryCurrentDayPlacementByPage(user, page, pageSize);
        return pageBean;
    }


    /**
     * 分页查询历史委托
     *
     * @return
     */
    @RequestMapping(value = "/queryPlacementHistoryByPage")
    @RequiresUser
    @ResponseBody
    public Object queryPlacementHistoryByConditionsByPage(String startDate, String endDate, String tradeType, String stockCode, Integer page, Integer pageSize) {
        User user = LoginRealm.getCurrentUser();
        PlacementHistoryParams params = new PlacementHistoryParams();
        params.setUserId(user.getId());
        params.setSide(tradeType);
        params.setStartTime(startDate);
        params.setEndTime(endDate);
        PageBean<PlacementVO> pageBean = placementService.queryPlacementHistoryByPage(params, page, pageSize);
        return pageBean;
    }

    /**
     * 历史委托查询页面
     *
     * @return
     */
    @RequestMapping(value = "/queryPlacementHistoryByConditionsByPage")
    @RequiresUser
    @ResponseBody
    public Object queryPlacementHistoryByConditionsByPage() {
        return "";
    }

    @RequestMapping(value = "/DoPlacementStocksTrade")
    @RequiresUser
    @ResponseBody
    public Object doPlacementStocksTrade(String stockCode, String price, String quantity, String sellOrBuy,String channelId) {
        String tradeResult = "委托成功";
        User currentUser = userService.findUserById(LoginRealm.getCurrentUser().getId());
        if (userService.isLogOut(currentUser)){
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.FAIL, "该账户已经停用，不允许操作", null);
        }
        if("null".equals(channelId)||"undefined".equals(channelId)){
        	channelId=null;
        }
        try {
            //组装委托信息
            Placement placement = new Placement();
            placement.setUserId(getUser().getId());
            placement.setSide(TradeSide.getByCode(sellOrBuy));
            placement.setSecurityCode(stockCode);
            placement.setPrice(new BigDecimal(price));
            placement.setQuantity(new BigDecimal(quantity));
            placement.setAmount(placement.getPrice().multiply(placement.getQuantity()));
            //placement.setChannelId(_channelId);
            placement.setChannelIds(DataUtils.transeStringToListByDotSpliter(channelId, ","));
            //下单
            placementService.placement(placement);
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, tradeResult, null);
        } catch (Exception e) {
            logger.error("下单出错", e);
            tradeResult = e.getMessage();
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.FAIL, tradeResult, null);
        }
    }

    @RequestMapping(value = "/DoPlacementCancel")
    @RequiresUser
    @ResponseBody
    public Object doPlacementCancel(String placementId) {
        try {
            //下单
            placementService.cancelPlacement(Integer.valueOf(placementId));
            return "撤单请求已提交";
        } catch (Exception e) {
            logger.error("撤单失败", e);
            return "撤单请求提交失败";
        }
    }


    /**
     * 交割单查询
     */
    @RequestMapping(value = "/queryDeliveryOrder")
    @RequiresUser
    @ResponseBody
    public Object queryDeliveryOrder(String stockCode, String businessType, String startDate, String endDate, Integer pageNo, Integer pageSize) {
        FlowParams params = new FlowParams();
        params.setUserId(getUser().getId());
        if (businessType!=null&&businessType.length()!=0) params.setType(TradeFowType.getByCode(businessType).getName());
        params.setSecurityCode(stockCode);
        params.setStartTime(startDate);
        params.setEndTime(endDate);
        PageBean<FlowVO> pageBean = flowService.queryTradeFlowByPage(params, pageNo, pageSize);
        return pageBean;
    }

    /**
     * 涨跌停列表
     *
     * @return
     */
    @RequestMapping(value = "/queryUpdownStocksInit")
    @RequiresUser
    public String QueryUpdownStocksInit() {
        return "stocks/wycg_stocks_upDownList";
    }

    /**
     * 涨跌停查询
     *
     * @return
     */
    @RequestMapping(value = "/queryUpdownStocksInitByPage")
    @RequiresUser
    @ResponseBody
    public Object queryUpdownStocksInitByPage(Integer page, Integer pageSize) {
        // 获取昨收涨停股票列表
        Map<String, MarketDataSnapshot> upChangePercentMap = marketdataService.getUpChangePercentMap();
        List<MarketDataSnapshot> mapValueList = new ArrayList<>(upChangePercentMap.values());
        Page page1 = new Page(page, pageSize);
        page1.setAaData(mapValueList);
        //User user = LoginRealm.getCurrentUser();
        //PageBean<SecurityPositionVO> pageBean = userPositionService.findUserPositionByPage(user, page, pageSize);
        PageBean<MarketDataSnapshot> pageBean=new PageBean<MarketDataSnapshot>(page, pageSize, mapValueList.size(), page1.getAaData());
        System.out.print("aa");
        return pageBean;
    }
}
