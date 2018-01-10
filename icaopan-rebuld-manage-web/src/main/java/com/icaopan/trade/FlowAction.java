package com.icaopan.trade;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

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
import com.icaopan.trade.bean.FlowParams;
import com.icaopan.trade.service.FlowService;
import com.icaopan.util.page.Page;


@Controller
@RequestMapping("flow")
public class FlowAction {
    
    @Autowired
    FlowService flowService;
    
    @Autowired
    AdminUserService adminUserService;
    
    @RequestMapping(value = "/fundFlow", method = RequestMethod.GET)
    private String fundFlowInit(){
        return "trade/flow/fundFlowList";
    }
    
    @RequestMapping(value = "/tradeFlow", method = RequestMethod.GET)
    private String tradeFlowInit(){
        return "trade/flow/tradeFlowList";
    }
    
    /**
     * 
     * @Description (用户资金流水查询)
     * @param aoData
     * @param flowParams
     * @return
     */
    @RequestMapping("/tradeFlowFind")
    @ResponseBody
    @RequiresUser
    public Page tradeFindFlow(@Param("aoData") String aoData, @Param("flowParams")FlowParams flowParams){
        Page page = new Page(aoData);
        AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
            flowParams.setCustomerId(user.getCustomerId());
        }
        page = flowService.getTradeFlowByPage(page, flowParams);
        return page;
    }
    
    /**
     * 
     * @Description (用户交易流水查询)
     * @param aoData
     * @param flowParams
     * @return
     */
    @RequestMapping("/fundFlowFind")
    @ResponseBody
    @RequiresUser
    public Page fundFindFlow(@Param("aoData") String aoData, @Param("flowParams")FlowParams flowParams){
        Page page = new Page(aoData);
        AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
            flowParams.setCustomerId(user.getCustomerId());
        }
        page = flowService.getFundFlowByPage(page, flowParams);
        return page;
    }
    
    @RequestMapping(value = "/exportTradeFlow")
    @ResponseBody
    @RequiresUser
    public void exportTradeHistory(HttpServletResponse response,@Param("flowParams")FlowParams flowParams){
        AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
            flowParams.setCustomerId(user.getCustomerId());
        }
        Page page=flowService.getTradeFlowByPage(null, flowParams);
        JSONArray data=JSONArray.fromObject(page.getAaData());
        com.icaopan.util.ExcelUtil.exportExcel("用户流水",
                new String[]{"流水ID", "用户名", "股票代码", "股票名称", "发生数量", "成交价", "发生金额", "操作类型", "佣金", "印花税", "过户费", "操作时间", "客户", "通道名称", "备注"},
                new String[]{"id", "userName", "securityCode", "securityName", "adjustQuantity", "costPrice", "adjustAmount", "typeStr", "commissionFee", "stampDutyFee", "transferFee", "createTimeStr", "customerName", "channelName", "notesStr"},
                data,response, "yyyy-MM-dd HH:mm:ss");
    }
    
    @RequestMapping(value = "/exportFundFlow")
    @ResponseBody
    @RequiresUser
    public void exportFundHistory(HttpServletResponse response,@Param("flowParams")FlowParams flowParams){
        Page page=flowService.getFundFlowByPage(new Page(), flowParams);
        JSONArray data=JSONArray.fromObject(page.getAaData());
        AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
            flowParams.setCustomerId(user.getCustomerId());
        }
        com.icaopan.util.ExcelUtil.exportExcel("用户流水", new String[]{"流水ID","用户名","发生金额","操作类型","本金金额","融资金额","本金总计","融资总计","操作时间","客户","备注"}, new String[]{"id","userName","adjustAmount","typeStr","cash","financing","cashAmount","financingAmount","createTimeStr","customerName","notesStr"}, data,response, "yyyy-MM-dd HH:mm:ss");
    }
}
