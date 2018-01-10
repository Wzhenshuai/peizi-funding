package com.icaopan.risk;

import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.other.ResponseResult;
import com.icaopan.admin.realm.LoginRealm;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.admin.util.LogUtils;
import com.icaopan.admin.util.ServletUtil;
import com.icaopan.customer.service.ChannelService;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.marketdata.market.MarketdataService;
import com.icaopan.risk.bean.RiskCtrlParam;
import com.icaopan.risk.consts.RiskConsts;
import com.icaopan.risk.util.Container;
import com.icaopan.stock.service.PoolService;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.trade.bean.PlacementParams;
import com.icaopan.trade.model.Placement;
import com.icaopan.trade.service.PlacementService;
import com.icaopan.user.bean.UserPositionParams;
import com.icaopan.user.model.User;
import com.icaopan.user.service.UserPositionService;
import com.icaopan.user.service.UserService;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.PlacementVO;
import com.icaopan.web.vo.SecurityPositionVO;

import elf.api.marketdata.marketdata.MarketDataSnapshot;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * desc:风控管理Action
 */
@Controller
@RequestMapping("/risk")
public class RiskCtrlAction {

    @Autowired
    PoolService poolService;

    @Autowired
    SecurityService securityService;

    @Autowired
    UserService userService;

    @Autowired
    ChannelService channelService;

    @Autowired
    PlacementService placementService;

    @Autowired
    UserPositionService userPositionService;

    @Autowired
    private MarketdataService marketdataService;
    @Autowired
    private AdminUserService adminUserService;

    @RequestMapping("/riskCtrl")
    @RequiresUser
    public String riskIndex(HttpServletRequest request){
        AdminUser user = LoginRealm.getCurrentUser();
        if(adminUserService.isSuperAdmin(user)){
            request.setAttribute("admin", "true");
        }
        return "risk/riskCtrl";
    }

    @RequestMapping("/riskCtrlQuery")
    @RequiresUser
    public String riskCtrlQuery(HttpServletRequest request){
        AdminUser user = LoginRealm.getCurrentUser();
        if(adminUserService.isSuperAdmin(user)){
            request.setAttribute("admin", "true");
        }
        return "risk/riskCtrlQuery";
    }
    
    @RequestMapping("/placementAndPosition")
    @RequiresUser
    public String placementIndex(HttpServletRequest request, String userId){
        request.setAttribute("userId", userId);
        return "risk/placementAndPosition";
    }

    /**
     * @Description 分页查询风控用户
     * @param		userName 用户名
     * @param       customerId 公司ID
     * @return		Page
     */
    @RequestMapping("/find")
    @RequiresUser
    @ResponseBody
    public Page find(HttpServletRequest request, @Param("aoData") String aoData, @Param("userName")String userName, @Param("customerId")Integer customerId, @Param("marketValues")String marketValues){
        Page page = new Page(aoData);
        AdminUser user = LoginRealm.getCurrentUser();
        // 如果非管理员用户,则只能根据登陆的用户查询该资金方的相关用户
        if(!adminUserService.isSuperAdmin(user)){
            customerId = user.getCustomerId();
        }else{
            request.setAttribute("admin", "true");
        }
        return Container.getPageByParams(page, new RiskCtrlParam(userName, customerId, marketValues));
    }

    /**
     * @Description 分页查询风控用户的当日委托
     * @param		userIdParam 用户ID
     * @return		Page
     */
    @RequestMapping("/queryPlacementByPage")
    @RequiresUser
    @ResponseBody
    public Page queryPlacementByPage(@Param("aoData") String aoData, @Param("userIdParam") Integer userIdParam){
        Page page = new Page(aoData);
        if(userIdParam != null){
            PlacementParams param = new PlacementParams();
            param.setUserId(userIdParam);
            return placementService.selectCurrentPlacementByPage(page, param);
        }else{
            return page;
        }
    }

    /**
     * @Description 分页查询风控用户的持仓
     * @param		userIdParam 用户ID
     * @return		Page
     */
    @RequestMapping("/queryPositionByPage")
    @RequiresUser
    @ResponseBody
    public Page queryPositionByPage(@Param("aoData") String aoData, @Param("userIdParam") Integer userIdParam) throws Exception {
    	Page page = new Page(aoData);
        if(userIdParam !=null){
            UserPositionParams params = new UserPositionParams();
            params.setUserId(userIdParam);
            page= userPositionService.findUserPositionByPage(page, params);
        }
        return page;
        
    }

    /**
     * @Description 风控持仓普通卖出
     * @param		userId 用户ID
     * @param		securityCode 股票编号
     * @param		price 股票价格
     * @param		quantity 股票数量
     * @return		ResponseResult
     */
    @RequestMapping("/commonPlacement")
    @RequiresUser
    @ResponseBody
    public ResponseResult commonPlacement(@Param("userId") Integer userId, @Param("securityCode") String securityCode, @Param("price") String price, @Param("quantity") String quantity) throws Exception {
        try {
            AdminUser user = LoginRealm.getCurrentUser();
            User userInfo = userService.findUserById(userId);
            LogUtils.saveLog(ServletUtil.getRequest(), user.getId(), "[风控管理]-[普通卖出]-[用户名:" + userInfo.getUserName() + "]-[股票名称:" + securityCode + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
        	Placement placement = new Placement();
            placement.setSecurityCode(securityCode);
            placement.setUserId(userId);
            placement.setPrice(new BigDecimal(price));
            placement.setQuantity(new BigDecimal(quantity));
            placement.setSide(TradeSide.SELL);
            placementService.placement(placement);
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}
    	
    }

    /**
     * @Description 风控持仓极速卖出
     * @param		userId 用户ID
     * @param		securityCode 股票编号
     * @param		priceType 股票价格类型
     * @param		quantity 股票数量
     * @return		ResponseResult
     */
    @RequestMapping("/speedPlacement")
    @RequiresUser
    @ResponseBody
    public ResponseResult speedPlacement(@Param("userId") Integer userId, @Param("securityCode") String securityCode, @Param("priceType") String priceType, @Param("quantity") String quantity) throws Exception {
        try {
            AdminUser user = LoginRealm.getCurrentUser();
            User userInfo = userService.findUserById(userId);
            LogUtils.saveLog(ServletUtil.getRequest(), user.getId(), "[风控管理]-[极速卖出]-[用户名:" + userInfo.getUserName() + "]-[股票名称:" + securityCode + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Placement placement = new Placement();
            placement.setSecurityCode(securityCode);
            placement.setUserId(userId);
            // 获取卖出价格
            placement.setPrice(getSecurityPriceSnippet(securityCode, priceType));
            placement.setQuantity(new BigDecimal(quantity));
            placement.setSide(TradeSide.SELL);
            placementService.placement(placement);
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}
    	
    }


    /**
     * @Description 撤单
     * @param		placementId 委托ID
     * @return		ResponseResult
     */
    @RequestMapping("/cancelPlacement")
    @RequiresUser
    @ResponseBody
    public ResponseResult cancelPlacement(@Param("placementId") Integer placementId) throws Exception {
        try {
            AdminUser user = LoginRealm.getCurrentUser();
            LogUtils.saveLog(ServletUtil.getRequest(), user.getId(), "[风控管理]-[风控持仓撤单]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            placementService.cancelPlacement(placementId);
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}
        
    }


    /**
     * @Description 单个用户全部撤单
     * @param		userId 用户ID
     * @return		ResponseResult
     */
    @RequestMapping("/cancelUserAllPlacement")
    @RequiresUser
    @ResponseBody
    public ResponseResult cancelUserAllPlacement(@Param("userId") Integer userId) throws Exception {
        try {
            AdminUser CurrentUser = LoginRealm.getCurrentUser();
            User userInfo = userService.findUserById(userId);
            LogUtils.saveLog(ServletUtil.getRequest(), CurrentUser.getId(), "[风控管理]-[单个用户全部撤单]-[用户名" + userInfo.getUserName() + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            User user = new User();
            user.setId(userId);
            List<PlacementVO> data = placementService.queryCurrentDayPlacementNotEnd(user);
            if(data.size() == 0){
                return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "该用户没有可撤单的委托", null);
            }
            for(PlacementVO item : data){
                placementService.cancelPlacement(item.getPlacementId());
            }
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}
        
    }

    /**
     * @Description 单个用户全部卖出
     * @param		userId 用户ID
     * @param       priceType 卖出价格类型
     * @return		ResponseResult
     */
    @RequestMapping("/singleUserSellAll")
    @RequiresUser
    @ResponseBody
    public ResponseResult singleUserSellAll(@Param("userId") Integer userId, @Param("priceType") String priceType) throws Exception {
        try {
            AdminUser CurrentUser = LoginRealm.getCurrentUser();
            User userInfo = userService.findUserById(userId);
            LogUtils.saveLog(ServletUtil.getRequest(), CurrentUser.getId(), "[风控管理]-[单个用户全部卖出]-[用户名：" + userInfo.getUserName() + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            User user = new User();
            user.setId(userId);
            List<SecurityPositionVO> data = userPositionService.findAllPosition(user);
            for(SecurityPositionVO vo : data){
                Placement placement = new Placement();
                placement.setSecurityCode(vo.getSecurityCode());
                placement.setUserId(userId);
                // 获取卖出价格
                placement.setPrice(getSecurityPriceSnippet(vo.getSecurityCode(), priceType));
                placement.setQuantity(vo.getAmount());
                placement.setSide(TradeSide.SELL);
                placementService.placement(placement);
            }
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, e.getMessage(), null);
		}
    	
    }

    private BigDecimal getSecurityPriceSnippet(String securityCode, String priceType){
        MarketDataSnapshot shot = marketdataService.getBySymbol(securityCode);
        BigDecimal price = BigDecimal.ZERO;
        switch (priceType){
            case RiskConsts.LIMITDOWN:
                price = new BigDecimal(String.valueOf(shot.getLimitDown()));
                break;
            case RiskConsts.BIDPRICE1:
                price = new BigDecimal(String.valueOf(shot.getBidPrice1()));
                break;
            case RiskConsts.BIDPRICE2:
                price = new BigDecimal(String.valueOf(shot.getBidPrice2()));
                break;
            case RiskConsts.BIDPRICE3:
                price = new BigDecimal(String.valueOf(shot.getBidPrice3()));
                break;
            case RiskConsts.BIDPRICE4:
                price = new BigDecimal(String.valueOf(shot.getBidPrice4()));
                break;
            case RiskConsts.BIDPRICE5:
                price = new BigDecimal(String.valueOf(shot.getBidPrice5()));
                break;
            default:
                break;
        }
        return price;
    }

}
