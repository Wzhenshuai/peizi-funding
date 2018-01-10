package com.icaopan.trade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.icaopan.user.model.User;
import com.icaopan.util.DateUtils;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.realm.LoginRealm;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.trade.bean.FillHistoryParams;
import com.icaopan.trade.bean.FillParams;
import com.icaopan.trade.bean.FillSummaryParams;
import com.icaopan.trade.model.FillSummary;
import com.icaopan.trade.service.FillService;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.FillSummaryVO;
import com.icaopan.web.vo.PageBean;

import java.util.Date;


@Controller
@RequestMapping("fill")
public class FillAction {
    
    @Autowired
    FillService fillService;
    
    @Autowired
    AdminUserService adminUserService;
    
    @RequestMapping(value = "/fill", method = RequestMethod.GET)
    @RequiresUser
    public String fillInit(HttpServletRequest request){
        AdminUser adminUser=LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(adminUser)){
            request.setAttribute("admin", "true");
        }
        return "trade/fill/fillList";
    }
    
    @RequestMapping(value = "/fillHistory", method = RequestMethod.GET)
    @RequiresUser
    public String fillHistoryInit(HttpServletRequest request){
        AdminUser adminUser=LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(adminUser)){
            request.setAttribute("admin", "true");
        }
        return "trade/fill/fillHistoryList";
    }
    
    @RequestMapping(value = "/fillSummary", method = RequestMethod.GET)
    @RequiresUser
    public String fillSummary(HttpServletRequest request){
    	String today=DateUtils.getDate();
        AdminUser adminUser=LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(adminUser)){
            request.setAttribute("admin", "true");
        }
        request.getSession().setAttribute("today", today);
        return "trade/fill/fillSummaryList";
    }
    /**
     * 
     * @Description (当日成交查询)
     * @param aoData
     * @param FillParams
     * @return
     */
    @RequestMapping("/fillFind")
    @ResponseBody
    @RequiresUser
    public Page fillFind(@Param("aoData") String aoData, @Param("fillParams")FillParams fillParams){
        Page page = new Page(aoData);
        AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
            fillParams.setCustomerId(user.getCustomerId());
        }
        if (fillParams.getSide()!=null&&fillParams.getSide().length()==0){
            fillParams.setSide(null);
        }
        fillParams.setCreateDate(DateUtils.formatDate(new Date()));
        page = fillService.getFillByPage(page, fillParams);
        return page;
    }
    
    /**
     * 
     * @Description (历史成交查询)
     * @param aoData
     * @param FillHistoryParams
     * @return
     */
    @RequestMapping("/fillHistoryFind")
    @ResponseBody
    @RequiresUser
    public Page fillFindHistory(@Param("aoData") String aoData, @Param("fillHistoryParams")FillHistoryParams fillHistoryParams){
        Page page = new Page(aoData);
        AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
            fillHistoryParams.setCustomerId(user.getCustomerId());
        }
        page = fillService.selectFillHistoryByPage(page, fillHistoryParams);
        
        return page;
    }
    
    @RequestMapping(value = "/exportFillDay")
    @ResponseBody
    @RequiresUser
    public void exportFillDay(HttpServletResponse response,@Param("fillParams")FillParams fillParams){
        Page page=fillService.getFillByPage(null, fillParams);
        JSONArray data=JSONArray.fromObject(page.getAaData());
        AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
            fillParams.setCustomerId(user.getCustomerId());
        }
        com.icaopan.util.ExcelUtil.exportExcel("当日成交", new String[]{"成交id", "用户名", "股票代码", "股票名称", "买卖方向", "成交数量", "成交价格", "成交金额", "成交时间", "通道", "客户"}, new String[]{"id", "userName", "securityCode", "securityName", "sideStr", "quantity", "price", "amount", "fillTimeStr", "channelName", "customerName"}, data, response, "yyyy-MM-dd HH:mm:ss");
    }
    
    @RequestMapping(value = "/exportFillHistory")
    @ResponseBody
    @RequiresUser
    public void exportHistory(HttpServletResponse response,@Param("fillHistoryParams")FillHistoryParams fillHistoryParams){
        Page page=fillService.selectFillHistoryByPage(null, fillHistoryParams);
        JSONArray data=JSONArray.fromObject(page.getAaData());
        AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
            fillHistoryParams.setCustomerId(user.getCustomerId());
        }
        com.icaopan.util.ExcelUtil.exportExcel("历史成交", new String[]{"成交ID", "用户名", "股票代码", "股票名称", "买卖方向", "成交数量", "成交价格", "成交金额", "通道", "客户", "成交时间"}, new String[]{"fillId", "userName", "securityCode", "securityName", "sideStr", "quantity", "price", "amount", "channelName", "customerName", "fillTimeStr"}, data, response, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 成交汇总查询
     *
     * @return
     */
    @RequestMapping(value = "/queryFillSummary")
    @ResponseBody
    public Object queryFillSummary(@Param("aoData") String aoData,String startDate, String endDate, String type,String securityCode,Integer channelId,Integer customerId ) {
    	Page<FillSummary> page=new Page<FillSummary>(aoData);
        FillSummaryParams params = new FillSummaryParams();
        AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
        	params.setCustomerId(user.getCustomerId());
        }
        params.setStartTime(startDate);
        params.setEndTime(endDate);
        params.setType(Integer.valueOf(type));
        params.setSecurityCode(securityCode);
        params.setCustomerId(customerId);
        page = fillService.selectFillSummaryByPage(page,params);
        return page;
    }
    
    
    
    @RequestMapping(value = "/exportFillSummary")
    @ResponseBody
    @RequiresUser
    public void exportFillSummary(HttpServletResponse response,String startDate, String endDate, String type,String securityCode,Integer channelId,Integer customerId){
    	Page<FillSummary> page=new Page<FillSummary>(null);
        FillSummaryParams params = new FillSummaryParams();
        AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
        	params.setCustomerId(user.getCustomerId());
        }
        params.setStartTime(startDate);
        params.setEndTime(endDate);
        params.setType(Integer.valueOf(type));
        params.setSecurityCode(securityCode);
        params.setCustomerId(customerId);
        page.setiDisplayLength(100000000);
        page = fillService.selectFillSummaryByPage(page,params);
        
        JSONArray data=JSONArray.fromObject(page.getAaData());
        if(StringUtils.equals(type, "1")){
            com.icaopan.util.ExcelUtil.exportExcel("成交汇总查询（按通道）", new String[]{"通道名称", "股票代码", "股票名称", "交易方向", "成交数量", "成交金额"}, new String[]{"channelName", "securityCode", "securityName", "side", "quantity", "amount"}, data, response, "yyyy-MM-dd HH:mm:ss");
        }else{
            com.icaopan.util.ExcelUtil.exportExcel("成交汇总查询（按用户）", new String[]{"用户名", "股票代码", "股票名称", "交易方向", "成交数量", "成交金额"}, new String[]{"userName", "securityCode", "securityName", "side", "quantity", "amount"}, data, response, "yyyy-MM-dd HH:mm:ss");
        }
    }
    
    @RequestMapping(value = "/queyrFillSummaryAmount")
    @ResponseBody
    @RequiresUser
    public Object queyrFillSummaryAmount(HttpServletRequest request,String startDate, String endDate ,String securityCode,Integer customerId){
        FillSummaryParams params = new FillSummaryParams();
        AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
        	params.setCustomerId(user.getCustomerId());
        }
        params.setSecurityCode(securityCode);
        params.setStartTime(startDate);
        params.setEndTime(endDate);
        params.setCustomerId(customerId);
        double amount=fillService.selectFillSummaryAmount(params);
        return amount;
    }
    
}
