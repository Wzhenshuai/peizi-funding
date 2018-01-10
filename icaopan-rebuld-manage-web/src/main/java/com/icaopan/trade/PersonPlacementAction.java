package com.icaopan.trade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.other.PermissionConstants;
import com.icaopan.admin.other.ResponseResult;
import com.icaopan.admin.realm.LoginRealm;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.common.CommonAction;
import com.icaopan.trade.bean.PlacementHistoryParams;
import com.icaopan.trade.bean.PlacementParams;
import com.icaopan.trade.service.PlacementService;
import com.icaopan.user.service.UserService;
import com.icaopan.util.DateUtils;
import com.icaopan.util.LogUtil;
import com.icaopan.util.page.Page;


@Controller
@RequestMapping("personPlacement")
public class PersonPlacementAction extends CommonAction{
    
    @Autowired
    PlacementService placementService;
    @Autowired
    AdminUserService adminUserService;
    @Autowired
    UserService userService;
    private Logger logger = LogUtil.getLogger(getClass());
    
    @RequestMapping(value = "/personPlacement", method = RequestMethod.GET)
    @RequiresUser
    public String personPlacement(HttpServletRequest request){
        AdminUser adminUser=LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(adminUser)){
            request.setAttribute("admin", "true");
        }
        return "trade/personPlacement/personPlacementList";
    }
    
    @RequestMapping(value = "/personPlacementManage", method = RequestMethod.GET)
    @RequiresUser
    public String personPlacementManage(HttpServletRequest request){
        AdminUser adminUser=LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(adminUser)){
            request.setAttribute("admin", "true");
        }
        return "trade/personPlacement/personPlacementListManage";
    }
    
    @RequestMapping(value = "/personPlacementHistory", method = RequestMethod.GET)
    @RequiresUser
    public String personPlacementHistory(HttpServletRequest request){
        AdminUser adminUser=LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(adminUser)){
            request.setAttribute("admin", "true");
        }
        return "trade/personPlacement/personPlacementHistoryList";
    }

    /**
     * 
     * @Description (用户当日委托查询)
     * @param aoData
     * @param placementParams
     * @return
     */
    @RequestMapping("/find")
    @ResponseBody
    @RequiresUser
    public Page findPersonPlacement(@Param("aoData") String aoData, @Param("placementParams")PlacementParams placementParams){
        Page page = new Page(aoData);
        AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
            placementParams.setCustomerId(user.getCustomerId());
        }
        placementParams.setCreateDate(DateUtils.formatDate(new Date()));
        page = placementService.selectPlacementByPage(page, placementParams);
        return page;
    }
    
    /**
     * 
     * @Description (用户历史委托查询)
     * @param aoData
     * @param placementHistoryParams
     * @return
     */
    @RequestMapping("/historyFind")
    @ResponseBody
    @RequiresUser
    public Page findPersonalPlacementHistory(@Param("aoData") String aoData, @Param("placementHistoryParams") PlacementHistoryParams placementHistoryParams){
        Page page = new Page(aoData);
        AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
            placementHistoryParams.setCustomerId(user.getCustomerId());
        }
        page = placementService.selectPlacementHistoryByPage(page, placementHistoryParams);
        
        return page;
    }
    
    @RequestMapping(value = "/exportPlacement")
    @ResponseBody
    @RequiresUser
    public void exportPlacement(HttpServletResponse response,@Param("flowParams")PlacementParams placementParams){
        Page page=placementService.selectPlacementByPage(null, placementParams);
        JSONArray data=JSONArray.fromObject(page.getAaData());
        AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
            placementParams.setCustomerId(user.getCustomerId());
        }
        com.icaopan.util.ExcelUtil.exportExcel("个人当日委托",
                new String[]{"用户名", "股票代码", "股票名称", "买卖方向", "委托数量", "委托价格", "委托金额", "成交数量", "成交金额", "成交价格", "状态", "下单时间"},
                new String[]{"userName", "securityCode", "securityName", "sideStr", "quantity", "price", "amount", "fillQuantity", "fillAmount", "fillPrice", "statusStr", "timeStr"},
                data, response, "HH:mm:ss");
    }
    
    @RequestMapping(value = "/exportPlacementHistory")
    @ResponseBody
    @RequiresUser
    public void exportHistory(HttpServletResponse response,@Param("flowParams")PlacementHistoryParams placementHistoryParams){
        Page page=placementService.selectPlacementHistoryByPage(null, placementHistoryParams);
        JSONArray data=JSONArray.fromObject(page.getAaData());
        AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
            placementHistoryParams.setCustomerId(user.getCustomerId());
        }
        com.icaopan.util.ExcelUtil.exportExcel("个人历史委托",
                new String[]{"用户名", "股票代码", "股票名称", "买卖方向", "委托数量", "委托价格", "委托金额", "成交数量", "成交金额", "成交价格", "状态", "佣金", "印花税", "过户费", "下单时间"},
                new String[]{"userName", "securityCode", "securityName", "sideStr", "quantity", "price", "amount", "fillQuantity", "fillAmount", "fillPrice", "statusStr", "commissionFee", "stampDutyFee", "transferFee", "dateTimeStr"},
                data, response, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 跳转通道自动委托页面
     * */
    @RequestMapping("/autoDoPlacement")
    @RequiresUser
    @ResponseBody
    public ResponseResult autoPlacement( @RequestParam("stockCode") String stockCode,
                                  @RequestParam("quantity") BigDecimal quantity,
                                  @RequestParam("price") BigDecimal price,
                                  @RequestParam("ids") Integer[] ids){
        Map<String, String> map = placementService.autoPlacement(stockCode, quantity, price, ids);
        String stringStringMap = map.entrySet().toString();
        return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, stringStringMap, null);
    }

    /**
     * 跳转通道自动委托页面
     * */
    @RequestMapping("/initAutoPlacement")
    @RequiresUser
    public Object initAutoPlacement(ModelMap modelMap){
        modelMap.addAttribute("message","success");
        return "trade/personPlacement/autoPlacement";
    }

    /**
     * 跳转通道自动委托页面
     * */
    @RequestMapping("/findTestTrader")
    @RequiresUser
    @ResponseBody
    public Object findTestTrader(HttpServletRequest request,@Param("aoData") String aoData) {
        Page page = new Page(aoData);
        AdminUser user = LoginRealm.getCurrentUser();
        if (adminUserService.isSuperAdmin(user)) {
            request.setAttribute("admin", "true");
        }
        page = userService.queryTestTrader(page);

        return page;
    }
    
    @RequestMapping("/doPersonFill")
    @ResponseBody
    public Object doPersonFill(Integer id,String fillPrice){
    	try {
    		BigDecimal _fillPrice=new BigDecimal(fillPrice);
    		placementService.doPersonFill(id,_fillPrice);
			return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
		} catch (Exception e) {
			logger.error("doPersonFill",e);
			return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, getErrorMsg(e), null);
		}
    }
}
