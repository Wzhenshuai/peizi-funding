package com.icaopan.trade;

import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.other.PermissionConstants;
import com.icaopan.admin.realm.LoginRealm;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.trade.bean.ChannelPlacemenHistoryParams;
import com.icaopan.trade.bean.ChannelPlacementParams;
import com.icaopan.trade.service.ChannelPlacementService;
import com.icaopan.util.DateUtils;
import com.icaopan.util.page.Page;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


@Controller
@RequestMapping("channelPlacement")
public class ChannlPlacementAction {
    
    @Autowired
    ChannelPlacementService channelPlacementService;
    @Autowired
    AdminUserService adminUserService;
    
    @RequestMapping(value = "/channelPlacement", method = RequestMethod.GET)
    private String channelPlacement(){
        return "trade/channel/channelPlacementList";
    }
    
    @RequestMapping(value = "/channelPlacementHistory", method = RequestMethod.GET)
    private String channelPlacementHistory(){
        return "trade/channel/channelPlacementHistoryList";
    }

    /**
     * @Description 根据用户当日委托ID跳转到该委托的当日通道委托详细
     *
     * */
    @RequestMapping("/channelPlacementDetail")
    @RequiresUser
    public String channelPlacementDetail(HttpServletRequest request,@Param("placementId")Integer placementId){
        if (placementId==null) return "trade/personPlacement/personPlacementList";
        request.setAttribute("placementId",placementId);
        return "trade/channel/channelPlacementListDetails";
    }


    /**
     * @Description 根据用户历史委托ID跳转到该委托的通道历史委托明细
     * */
    @RequestMapping("/channelPlacementHistoryDetail")
    @RequiresUser
    public String channelPlacementHistoryDetail(HttpServletRequest request,@Param("placementId")Integer placementId){
        if (placementId==null) return "trade/personPlacement/personPlacementHistoryList";
        request.setAttribute("placementId",placementId);
        return "trade/channel/channelPlacementListHistoryDetails";
    }


    /**
     * 
     * @Description (通道当日委托查询)
     * @param aoData
     * @param channelPlacementParams
     * @return
     */
    @RequestMapping("/placementFind")
    @ResponseBody
    @RequiresUser
    public Page findChannelPlacement(@Param("aoData") String aoData,@Param("customerIdList")String customerIdList,@Param("statusList")String statusList,@Param("channelList")String channelList, @Param("channelPlacementParams")ChannelPlacementParams channelPlacementParams){
        Page page = new Page(aoData);
        AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
            channelPlacementParams.setCustomerId(user.getCustomerId());
        }
        if(StringUtils.isNotEmpty(statusList)){
        	String[] statusArray=statusList.split(",");
        	channelPlacementParams.setStatusArray(statusArray);
        }
        if(StringUtils.isNotEmpty(channelList)){
        	String[] channelArray=channelList.split(",");
        	channelPlacementParams.setChannelArray(channelArray);
        }
        if (StringUtils.isNotEmpty(customerIdList)){
            String[] customerIdArray = customerIdList.split(",");
            int[] csIds = new int[customerIdArray.length];
            for (int i = 0; i < customerIdArray.length; i++) {
                String currentIndex = customerIdArray[i];
                if (currentIndex==null||currentIndex.length()==0||StringUtils.equals(currentIndex,"")) continue;
                csIds[i] = Integer.parseInt(currentIndex);
            }
            channelPlacementParams.setCustomerIdArray(csIds);
        }
//        channelPlacementParams.setCreateDate(DateUtils.formatDate(new Date()));
        page = channelPlacementService.getChannelPlacementByPage(page,channelPlacementParams);
        return page;
    }
    
    /**
     * 
     * @Description (通道历史委托查询)
     * @param aoData
     * @param channelPlacemenHistoryParams
     * @return
     */
    @RequestMapping("/placementHistoryFind")
    @ResponseBody
    @RequiresUser
    public Page findChannelPlacementHistory(@Param("aoData") String aoData, @Param("channelPlacemenHistoryParams")ChannelPlacemenHistoryParams channelPlacemenHistoryParams){
        Page page = new Page(aoData);
        AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
           channelPlacemenHistoryParams.setCustomerId(user.getCustomerId());
        }
        page = channelPlacementService.getChannelPlacementHistoryByPage(page, channelPlacemenHistoryParams);
        
        return page;
    }
    
    @RequestMapping(value = "/exportChannelDay")
    @ResponseBody
    @RequiresUser
    public void exportChannelDay(HttpServletResponse response,@Param("channelPlacementParams")ChannelPlacementParams channelPlacementParams){
    	AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
            channelPlacementParams.setCustomerId(user.getCustomerId());
        }
        Page page = channelPlacementService.getChannelPlacementByPage(null,channelPlacementParams);
        JSONArray data=JSONArray.fromObject(page.getAaData());
        com.icaopan.util.ExcelUtil.exportExcel("通道当日委托",
                new String[]{"用户名", "股票代码", "股票名称", "买卖方向", "委托数量", "委托价格", "委托金额", "成交数量", "成交金额", "成交价格", "状态", "资金账号", "委托编号", "拒绝原因", "更新时间", "通道"},
                new String[]{"userName", "securityCode", "securityName", "sideStr", "quantity", "price", "amount", "fillQuantity", "fillAmount", "fillPrice", "statusStr", "brokerAccountCode", "brokerPlacementCode", "rejectMessage", "timeStr", "channelName"},
                data, response, "yyyy-MM-dd HH:mm:ss");
    }
    
    @RequestMapping(value = "/exportChannelHistory")
    @ResponseBody
    @RequiresUser
    public void exportChannelHistory(HttpServletResponse response,@Param("channelPlacementParams")ChannelPlacemenHistoryParams channelPlacemenHistoryParams){
    	AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
            channelPlacemenHistoryParams.setCustomerId(user.getCustomerId());
        }
        Page page = channelPlacementService.getChannelPlacementHistoryByPage(null,channelPlacemenHistoryParams);
        JSONArray data=JSONArray.fromObject(page.getAaData());
        
        com.icaopan.util.ExcelUtil.exportExcel("通道历史委托",
                new String[]{"用户名", "股票代码", "股票名称", "买卖方向", "委托数量", "委托价格", "成交数量", "成交金额", "成交价格", "状态", "资金方", "委托编号", "拒绝原因", "委托日期", "通道", "券商佣金", "系统佣金"},
                new String[]{"userName", "securityCode", "securityName", "sideStr", "quantity", "price", "fillQuantity", "fillAmount", "fillPrice", "statusStr", "customerName", "placementCode", "rejectMessage", "dateTimeStr", "channelName", "tradeCommissionFee", "sysCommissionFee"},
                data,response, "yyyy-MM-dd HH:mm:ss");
    }


    /**
     * 跳转通道自动委托页面
     * */
    @RequestMapping("/autoDoPlacement")
    @RequiresUser
    public String autoDoPlacement(HttpServletRequest request){
        AdminUser user = LoginRealm.getCurrentUser();
        if(adminUserService.isSuperAdmin(user)){
            request.setAttribute("admin", "true");
        }
        return "trade/channel/autoDoPlacement";
    }

    @RequestMapping(value = "/commissionCollectList", method = RequestMethod.GET)
    @RequiresUser
    public String commissionCollectList(HttpServletRequest request) {
        AdminUser adminUser = LoginRealm.getCurrentUser();
        if (!adminUserService.isSuperAdmin(adminUser)) {
            request.setAttribute("admin", "true");
        }
        return "trade/channel/commissionCollectList";
    }

    /**
     * @param
     * @param
     * @return
     * @Description (佣金差额汇总)
     */
    @RequestMapping("/commissionCollect")
    @ResponseBody
    @RequiresUser
    public Page commissionCollect(@Param("aoData") String aoData, @Param("channelPlacemenHistoryParams") ChannelPlacemenHistoryParams channelParams) {
        Page page = new Page(aoData);
        AdminUser user = LoginRealm.getCurrentUser();
        if (!adminUserService.isSuperAdmin(user)) {
            channelParams.setCustomerId(user.getCustomerId());
        }
        page = channelPlacementService.findCommissionCollectBypage(page, channelParams);
        return page;
    }
}
