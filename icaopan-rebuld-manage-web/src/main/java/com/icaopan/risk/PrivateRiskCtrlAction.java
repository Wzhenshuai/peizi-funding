package com.icaopan.risk;

import com.alibaba.fastjson.JSON;
import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.other.PermissionConstants;
import com.icaopan.admin.realm.LoginRealm;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.admin.other.ResponseResult;
import com.icaopan.common.util.SecurityUtil;
import com.icaopan.marketdata.market.DailyLimit;
import com.icaopan.marketdata.market.DailyLimitPriceService;
import com.icaopan.risk.bean.*;
import com.icaopan.risk.service.PrivateRiskCtrlService;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.TdxFillItemVO;

import net.sf.json.JSONArray;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.math.BigDecimal;

/**
 * Created by RoyLeong @royleo.xyz on 2017/7/11.
 */
@Controller
@RequestMapping("/privateRiskCtrl")
public class PrivateRiskCtrlAction {

    @Autowired
    PrivateRiskCtrlService riskCtrlService;

    @Autowired
	private AdminUserService adminUserService;

    @Autowired
    DailyLimitPriceService dailyLimitPriceService;

    /**
     * 跳转到个人风控监控页面
     * */
    @RequestMapping("/riskCtrl")
    @RequiresUser
    @RequiresPermissions(value = PermissionConstants.P_RISK+PermissionConstants.FIND)
    public String riskIndex(HttpServletRequest request){
    	AdminUser user=LoginRealm.getCurrentUser();
    	boolean isAdmin=adminUserService.isSuperAdmin(user);
    	request.setAttribute("isAdmin", isAdmin);
        return "risk/privateRiskCtrl";
    }

    /**
     * 跳转到添加个人风控专户页面
     * */
    @RequestMapping("/addRiskCtrlInfo")
    @RequiresUser
    @RequiresPermissions(value = PermissionConstants.P_RISK+PermissionConstants.FIND)
    public String addRiskCtrlUser(HttpServletRequest request){
        AdminUser user=LoginRealm.getCurrentUser();
        boolean isAdmin=adminUserService.isSuperAdmin(user);
        request.setAttribute("admin", isAdmin);
        return "risk/addPrivateRiskCtrl";
    }

    /**
     * 添加个人风控信息
     * */
    @RequestMapping("/addRiskCtrlForm")
    @RequiresUser
    @ResponseBody
    @RequiresPermissions(value = PermissionConstants.P_RISK+PermissionConstants.FIND)
    public ResponseResult addRiskCtrlForm(TdxPrivateUser privateUser){
        TdxYyb yyb = riskCtrlService.getYybById(privateUser.getYybId());
        TdxServerInfo serverInfo = riskCtrlService.getBaseServerInfoById(privateUser.getServerId());
        TdxBrokerBase brokerBase = riskCtrlService.getBrokerBaseInfoById(yyb.getBelongId());
        privateUser.setVersion(brokerBase.getVersion());
        privateUser.setYybName(yyb.getYybName());
        privateUser.setYybCode(yyb.getYybCode());
        privateUser.setServerIp(serverInfo.getServerIp());
        privateUser.setServerName(serverInfo.getServerName());
        privateUser.setDllName("trade_"+privateUser.getAccountNo());
        privateUser.setPort(serverInfo.getPort());
        if (privateUser.getCustomerId()==null||privateUser.getCustomerId()==""){
            AdminUser user=LoginRealm.getCurrentUser();
            privateUser.setCustomerId(String.valueOf(user.getId()));
        }
        if (privateUser.getTxPassword()==null) privateUser.setTxPassword("");
        if (privateUser.getjYPassWord()==null) privateUser.setjYPassWord("");
        boolean bool =  riskCtrlService.addUserRiskCtrl(privateUser);
        return bool ? new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "添加个人风控信息成功！", null) :
                new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "添加个人风控信息失败!请重试", null);
    }


    /**
     * 分页查询个人风控信息
     * @param aoData
     * @param status
     * @param tradeAccount
     * @return
     * */
    @RequestMapping("/find")
    @RequiresUser
    @ResponseBody
    @RequiresPermissions(value = PermissionConstants.P_RISK+PermissionConstants.FIND)
    public Page findRiskCtrlInfo(@Param("aoData") String aoData,
                                 @Param("status") String status,
                                 @Param("tradeAccount")String tradeAccount){
        if (StringUtils.isBlank(status)) status = "Normal";
        if (tradeAccount==""&&tradeAccount.length()==0) tradeAccount=null;
        AdminUser user=LoginRealm.getCurrentUser();
        boolean isAdmin=adminUserService.isSuperAdmin(user);
        InfoParam param = new InfoParam();
        param.setStatus(status);
        param.setTradeAccount(tradeAccount);
        param.setCustomerId(isAdmin?null:String.valueOf(user.getId()));
        Page page = riskCtrlService.findPrivateRiskCtrlByPage(new Page(aoData),param);
        return page;
    }

    /**
     * @description 查询账户信息，包括：用户持仓，当日成交，当日委托
     * @param userId
     * @param type
     * type = 1  获取当前持仓
     * type = 2：获取当日委托
     * type = 3：获取当日成交
     * @return
     * */
    @RequestMapping("/queryInfo")
    @RequiresUser
    @ResponseBody
    @RequiresPermissions(value = PermissionConstants.P_RISK+PermissionConstants.FIND)
    public String queryInfo(@Param("userId")Integer userId,@Param("type")String type){
        if (type==null||userId==null||userId==0||type=="") return null;
        return riskCtrlService.queryInfoByUserIdAndType(userId,type);
    }

    /**
     * 更新风控信息
     * @param modifyId
     * @param status
     * @param nickName
     * @param remark
     * @param openLine
     * @param warnLine
     * @return
     * */
    @RequestMapping("/updateRiskInfo")
    @RequiresUser
    @ResponseBody
    @RequiresPermissions(value = PermissionConstants.P_RISK+PermissionConstants.FIND)
    public ResponseResult updateRiskInfo(@Param("modifyId")Integer modifyId,
                                         @Param("status")String status,
                                         @Param("nickName")String nickName,
                                         @Param("remark")String remark,
                                         @Param("openLine")double openLine,
                                         @Param("warnLine")double warnLine){
        TdxPrivateUser user = new TdxPrivateUser(modifyId,nickName,BigDecimal.valueOf(warnLine),BigDecimal.valueOf(openLine),status,remark);
        Boolean bool = riskCtrlService.updatePrivateUserRiskCtrl(user);
        return bool ? new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "更新风控信息成功！", null) : new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "更新风控信息失败!请重试", null);
    }

    /**
     * 一键平仓功能
     * @param placementStr
     * @return
     * */
    @RequestMapping("/oneKeyPing")
    @RequiresUser
    @ResponseBody
    @RequiresPermissions(value = PermissionConstants.P_RISK+PermissionConstants.FIND)
    public ResponseResult oneKeyPing(@Param("placementStr")String placementStr) throws IOException, EncoderException {
        RiskCtrlPlacement placement = JSON.parseObject(placementStr,RiskCtrlPlacement.class);
        List<Content> contentList = placement.getContents();
        String tempCode = null;
        for (Content con: contentList) {
            tempCode = con.getCode();
            if (tempCode == null) continue;
            DailyLimit dailyLimit = dailyLimitPriceService.getDailyLimit(SecurityUtil.codeToSelfStockCode(tempCode));
            Collections.replaceAll(contentList,con,new Content(con.getCode(),con.getAvailableToSell(),BigDecimal.valueOf(dailyLimit.getLimitDown())));
        }
        placement.setContents(contentList);
        String result = riskCtrlService.openPosition(placement);
        return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, JSON.toJSONString(result),null);
    }

    /**
     * 普通平仓功能
     * @param placementStr
     * @return
     * */
    @RequestMapping("/normalPing")
    @RequiresUser
    @ResponseBody
    @RequiresPermissions(value = PermissionConstants.P_RISK+PermissionConstants.FIND)
    public ResponseResult normalPing(@Param("placementStr")String placementStr){
        RiskCtrlPlacement placement = JSON.parseObject(placementStr,RiskCtrlPlacement.class);
        String result = riskCtrlService.openPosition(placement);
        return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, JSON.toJSONString(result),null);
    }

    /**
     * 获取跌停价
     * @param code
     * @return
     * */
    @RequestMapping("/getLimitDownPrice")
    @RequiresUser
    @ResponseBody
    @RequiresPermissions(value = PermissionConstants.P_RISK+PermissionConstants.FIND)
    public String getLimitDownPrice(@Param("code")String code) throws IOException, EncoderException {
        if (code==null||code.length()==0){
            return null;
        }
        return riskCtrlService.getLimitDownPrice(code);
    }

    /**
     * 根据券商编号获取券商服务器信息
     * @param belongId
     * @return
     * */
    @RequestMapping("/getServerByBelongId")
    @RequiresUser
    @ResponseBody
    @RequiresPermissions(value = PermissionConstants.P_RISK+PermissionConstants.FIND)
    public String getServerByBelongId(@Param("belongId")String belongId){
        List<TdxServerInfo> serverInfos = riskCtrlService.getServerInfoByBelongId(belongId);
        return JSON.toJSONString(serverInfos);
    }

    /**
     * 根据券商编号获取券商营业部信息
     * @param belongId
     * @return
     * */
    @RequestMapping("/getYybByBelongId")
    @RequiresUser
    @ResponseBody
    @RequiresPermissions(value = PermissionConstants.P_RISK+PermissionConstants.FIND)
    public String getYybByBelongId(@Param("belongId")String belongId){
        List<TdxYyb> yybs = riskCtrlService.getYYBByBelongId(belongId);
        return JSON.toJSONString(yybs);
    }

    /**
     * 手动更新个人用户金额数据
     * */
    @RequestMapping("/reloadBroker")
    @RequiresUser
    @ResponseBody
    @RequiresPermissions(value = PermissionConstants.P_RISK+PermissionConstants.FIND)
    public ResponseResult reloadBroker(){
        String result = riskCtrlService.manualUpdateBroker();
        return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, result,null);
    }

    /**
     * 验证用户接口相关信息
     * @param accountNo
     * @param tradeAccount
     * @param jYPassWord
     * @param txPassWord
     * @param yybId
     * @param serverId
     * @return
     * */
    @RequestMapping("/validateInfo")
    @RequiresUser
    @ResponseBody
    @RequiresPermissions(value = PermissionConstants.P_RISK+PermissionConstants.FIND)
    public ResponseResult testLogIn(@Param("accountNo")String accountNo,
                                    @Param("tradeAccount")String tradeAccount,
                                    @Param("jYPassWord")String jYPassWord,
                                    @Param("txPassWord")String txPassWord,
                                    @Param("yybId")String yybId,
                                    @Param("serverId")String serverId){
        String result = null;
        result = riskCtrlService.verityRiskInfo(accountNo,tradeAccount,txPassWord,jYPassWord,serverId,yybId);
        if (result.contains("账户已经存在")){
            result = "验证失败,账户已经存在于本系统中";
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, result,null);
        }else if (result.contains("不")||result.contains("fail")||result.contains("error")){
            result = "您所验证的账户不存在，请确认输入的信息无误";
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, result,null);
        }
        return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, result,null);
    }

    /**
     * 下载所有通道用户股票成交汇总
     * @param response
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/downloadStockFillSummary")
    public void downloadAllChannelStockFillSummaryToExcel(HttpServletResponse response) throws UnsupportedEncodingException{
    	List<TdxFillItemVO> list=riskCtrlService.queryTdxFillSummary();
    	JSONArray data=JSONArray.fromObject(list);
        com.icaopan.util.ExcelUtil.exportExcel("通道当日成交汇总",
                new String[]{"通道名称", "通道账号", "股票代码","股票名称", "买入成交总数量", "买入成交总金额","卖出成交总数量", "卖出成交总金额"},
                new String[]{"accountName", "accountNo", "stockCode", "stockName", "buyFillQuantity", "buyFillAmount", "sellFillQuantity","sellFillAmount"},
                data, response, "HH:mm:ss");
    }



}
