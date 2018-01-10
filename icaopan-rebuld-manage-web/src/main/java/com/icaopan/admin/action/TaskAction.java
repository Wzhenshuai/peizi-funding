package com.icaopan.admin.action;

import com.icaopan.admin.other.ResponseResult;
import com.icaopan.admin.util.SpringContextHolder;
import com.icaopan.clearing.service.CheckLogService;
import com.icaopan.clearing.service.ClearingService;
import com.icaopan.customer.service.CustomerService;
import com.icaopan.marketdata.market.DailyLimitPriceService;
import com.icaopan.marketdata.market.MarketDataManager;
import com.icaopan.marketdata.market.MarketdataService;
import com.icaopan.risk.service.PrivateRiskCtrlService;
import com.icaopan.risk.service.RiskTaskService;
import com.icaopan.stock.service.PoolService;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.task.mytask.service.impl.TaskSwitch;
import com.icaopan.user.service.ChannelPositionService;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by RoyLeong @royleo.xyz on 2017/1/12.
 */

@Controller
@RequestMapping("/task")
public class TaskAction {

    @Autowired
    DailyLimitPriceService dailyLimitPriceService;

    @Autowired
    ChannelPositionService positionService;

    @Autowired
    ClearingService clearingService;

    @Autowired
    CustomerService customerService;

    @Autowired
    SecurityService securityService;

    @Autowired
    PoolService poolService;

    @Autowired
    CheckLogService checkLogService;

    @Autowired
    PrivateRiskCtrlService privateRiskCtrlService;

    @Autowired
    MarketdataService marketdataService;
    
    @Autowired
    RiskTaskService riskTaskService;

   @RequestMapping("/task")
    @RequiresUser
    public String task(){
        return "admin/task";
    }

    @RequestMapping("/quote")
    @RequiresUser
    @ResponseBody
    public ResponseResult quote(){
        MarketDataManager manager = SpringContextHolder.getBean(MarketDataManager.class);
        try {
            manager.readMarketData();
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "更新行情信息成功!", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "更新失败,请重试!", null);
        }
    }

    @RequestMapping("/limit")
    @RequiresUser
    @ResponseBody
    public ResponseResult limit(){
        try {
            dailyLimitPriceService.extract();
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "更新涨跌停成功!", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "更新失败,请重试!", null);
        }
    }

    @RequestMapping("/clearPosition")
    @RequiresUser
    @ResponseBody
    public ResponseResult clearPosition(){
        clearingService.deleteEmptySecurityPosition();
        return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "清除空持仓成功!", null);
    }

    @RequestMapping("/clearing")
    @RequiresUser
    @ResponseBody
    public ResponseResult clearing(){
        try {
            clearingService.clearingProcess(false);
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "清算成功!", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "清算失败,请重试!", null);
        }
    }

    @RequestMapping("/autoFill")
    @RequiresUser
    @ResponseBody
    public ResponseResult autoFill(){
        try {
            if(TaskSwitch.autoFill){
            	TaskSwitch.autoFill=false;
            }else{
            	TaskSwitch.autoFill=true;
            }
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功!", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "操作失败,请重试!", null);
        }
    }
    
    @RequestMapping("/reloadPrivateBroker")
    @RequiresUser
    @ResponseBody
    public ResponseResult reloadPrivateBroker(){
        try {
        	riskTaskService.scheduledFlush();
			riskTaskService.positionComplementaryFlush();
           return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "手动更新风控信息成功!", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "手动更新风控信息失败,请重试!", null);
        }
    }

    @RequestMapping("/updateCustomerBalanceDay")
    @RequiresUser
    @ResponseBody
    public ResponseResult updateCustomerBalanceDay(){

        try {
            customerService.updateCustomerBalanceDay();
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "扣减资金方当日佣金 成功 !", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "扣减资金方当日佣金 失败!", null);
        }
    }

    @RequestMapping("/updateCustomerBalanceMonthly")
    @RequiresUser
    @ResponseBody
    public ResponseResult updateCustomerBalanceMonthly(){

        try {
            customerService.updateCustomerBalanceMonthly();
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "扣减资金方当月佣金 成功!", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "扣减资金方当月佣金 失败!", null);
        }
    }

    @RequestMapping("/updateStockSecurity")
    @RequiresUser
    @ResponseBody
    public ResponseResult updateStockSecurity(){

        try {
            String result = securityService.updateAll();
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "更新股票信息 成功!"+result, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "更新股票信息 失败!", null);
        }
    }

    @RequestMapping("/updateStockPool")
    @RequiresUser
    @ResponseBody
    public ResponseResult updateStockPool(){
        try {
            String result = poolService.updateStockSecurity();
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "更新股票池成功!"+result, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "更新股票池 失败!", null);
        }
    }

    @RequestMapping("/checkPosition")
    @RequiresUser
    @ResponseBody
    public ResponseResult checkPosition() {

        try {
            clearingService.checkCashAndPosition();
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "自动对账操作 完成", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "自动对账操作 失败!", null);
        }
    }

    @RequestMapping("/cancelPlacement")
    @RequiresUser
    @ResponseBody
    public ResponseResult cancelPlacement() {

        try {
            clearingService.cancelPlacement();
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "自动对账操作 完成", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "自动对账操作 失败!", null);
        }
    }

    @RequestMapping("/updateAilyChangePercent")
    @RequiresUser
    @ResponseBody
    public ResponseResult updateAilyChangePercent(){
        try {
            marketdataService.handleAilyChangePercent();
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "更新昨日漲跌停股票成功!", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "更新昨日漲跌停股票 失败!", null);
        }
    }

}
