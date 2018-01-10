package com.icaopan.web.openapi.action;

import com.alibaba.fastjson.JSONObject;
import com.icaopan.api.service.ApiService;
import com.icaopan.user.service.SelfStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;


@Controller
@RequestMapping("/openapi/v1")
public class ApiAction extends ApiBaseAction {

    @Autowired
    private ApiService       apiService;
    @Autowired
    private SelfStockService selfStockService;

    @RequestMapping(value = "/login")
    @ResponseBody
    public Object login(HttpServletRequest request, String paraJson) throws Exception {
        // 获取登录参数
        JSONObject obj = parseEncryptedParam(paraJson);
        String userName = obj.getString("userName");
        String passWord = obj.getString("passWord");
        String system = obj.getString("system");
        String vCode = obj.getString("vCode");
        String chanelId = obj.getString("chanelId");
        //执行登录操作
        Map<String, Object> result = apiService.login(system, chanelId, userName, passWord, vCode, request);
        return makeApiResult(result);
    }

    @RequestMapping(value = "/autoLogin")
    @ResponseBody
    public Object autoLogin(HttpServletRequest request, String paraJson) throws Exception {
        // 获取登录参数
        JSONObject obj = parseEncryptedParam(paraJson);
        String userName = obj.getString("userName");
        String passWord = obj.getString("passWord");
        String system = obj.getString("system");
        String vCode = obj.getString("vCode");
        String chanelId = obj.getString("chanelId");
        //执行登录操作
        Map<String, Object> result = apiService.autoLogin(system, chanelId, userName, passWord, vCode, request);
        return makeApiResult(result);
    }

    @RequestMapping(value = "/loginOut")
    @ResponseBody
    public Object loginOut(String paraJson) throws Exception {
        JSONObject obj = parseEncryptedParam(paraJson);
        String token = obj.getString("token");
        String chanelId = obj.getString("chanelId");
        Map<String, Object> result = apiService.logOut(token, chanelId);
        return makeApiResult(result);
    }

    @RequestMapping(value = "/isLogin")
    @ResponseBody
    public Object checkLogin(String paraJson) throws Exception {
        JSONObject obj = parseEncryptedParam(paraJson);
        String token = obj.getString("token");
        Map<String, Object> result = apiService.isLogin(token);
        return makeApiResult(result);
    }

    @RequestMapping(value = "/queryAllSecurityModel")
    @ResponseBody
    public Object queryAllSecurityModel() throws Exception {
        Map<String, Object> result = apiService.getAllSecurity();
        return makeApiResult(result);
    }

    @RequestMapping(value = "/queryAllSecurityModelByCreation")
    @ResponseBody
    public Object queryAllSecurityModelByCreation(String paraJson) throws Exception {
        JSONObject obj = parseEncryptedParam(paraJson);
        String issueDate = obj.getString("issueDate");
        Map<String, Object> result = apiService.queryAllSecurityModelByCreation(issueDate);
        return makeApiResult(result);
    }

    @RequestMapping(value = "/queryMarketDataBySecurityCode")
    @ResponseBody
    public Object queryMarketDataBySecurityCode(String paraJson) throws Exception {
        JSONObject obj = parseEncryptedParam(paraJson);
        String securityCode = obj.getString("securityCode");
        Map<String, Object> result = apiService.queryMarketDataByCode(securityCode);
        return makeApiResult(result);
    }

    @RequestMapping(value = "/queryStockBalance")
    @ResponseBody
    public Object queryStockBalance(String paraJson) {
        JSONObject obj = parseEncryptedParam(paraJson);
        String token = obj.getString("token");
        Map<String, Object> result = apiService.queryAccount(token);
        return makeApiResult(result);
    }

    @RequestMapping(value = "/doPlacementStockTrade")
    @ResponseBody
    public Object doPlacementStockTrade(String paraJson)
            throws Exception {
        //获取参数
        JSONObject obj = parseEncryptedParam(paraJson);
        String token = obj.getString("token");
        String stockCode = obj.getString("stockCode");
        BigDecimal quantity = new BigDecimal(obj.getString("quantity"));
        int sellOrBuy = obj.getInteger("sellOrBuy");
        BigDecimal price = new BigDecimal(obj.getString("price"));
        //设置返回值
        Map<String, Object> result = apiService.doPlacementStockTrade(token, stockCode, quantity, sellOrBuy, price);
        return makeApiResult(result);
    }

    @RequestMapping(value = "/doPlacementCancel")
    @ResponseBody
    public Object doPlacementCancel(String paraJson) throws Exception {
        //获取参数
        JSONObject obj = parseEncryptedParam(paraJson);
        String token = obj.getString("token");
        String placementIds = obj.getString("placementIds");
        if (placementIds.endsWith(",")) {
            placementIds = placementIds.substring(0, placementIds.length() - 1);
        }
        Map<String, Object> result = apiService.cancelPlacement(token, placementIds);
        return makeApiResult(result);
    }

    @RequestMapping(value = "/queryPlacementCurrentDayList")
    @ResponseBody
    public Object queryPlacementCurrentDayList(String paraJson) throws Exception {
        JSONObject obj = parseEncryptedParam(paraJson);
        String token = obj.getString("token");
        String tradeType = obj.getString("tradeType");
        Map<String, Object> result = apiService.queryPlacementCurrentDayList(token, tradeType);
        return makeApiResult(result);
    }

    @RequestMapping(value = "/queryCurrentDayPlacementNotEnd")
    @ResponseBody
    public Object queryCurrentDayPlacementNotEnd(String paraJson) throws Exception {
        JSONObject obj = parseEncryptedParam(paraJson);
        String token = obj.getString("token");
        Map<String, Object> result = apiService.queryCurrentDayPlacementNotEnd(token);
        return makeApiResult(result);
    }

    @RequestMapping(value = "/queryPlacementHistoryByConditionsByPage")
    @ResponseBody
    public Object queryPlacementHistoryByConditionsByPage(String paraJson) throws Exception {
        JSONObject obj = parseEncryptedParam(paraJson);
        String token = obj.getString("token");
        String stockCode = obj.getString("stockCode");
        String tradeType = obj.getString("tradeType");
        String pStatusList = obj.getString("pStatusList");
        String startDate = obj.getString("startDate");
        String endDate = obj.getString("endDate");
        Integer page = obj.getInteger("page");
        Integer pageSize = obj.getInteger("pageSize");
        Map<String, Object> result = apiService.queryPlacementHistoryByPage(token, pStatusList, stockCode, tradeType, startDate, endDate, page, pageSize);
        return makeApiResult(result);
    }

    @RequestMapping(value = "/queryFillCurrentDayByPage")
    @ResponseBody
    public Object queryFillCurrentDayList(String paraJson) throws Exception {
        JSONObject obj = parseEncryptedParam(paraJson);
        String token = obj.getString("token");
        Integer page = obj.getInteger("page");
        Integer pageSize = obj.getInteger("pageSize");
        Map<String, Object> result = apiService.queryFillByPage(token, page, pageSize);
        return makeApiResult(result);
    }

    @RequestMapping(value = "/queryPlacementFillHistoryByConditionsByPage")
    @ResponseBody
    public Object queryFillHistoryByConditionsByPages(String paraJson) throws Exception {
        JSONObject obj = parseEncryptedParam(paraJson);
        String token = obj.getString("token");
        String stockCode = obj.getString("stockCode");
        String tradeType = obj.getString("tradeType");
        String startDate = obj.getString("startDate");
        String endDate = obj.getString("endDate");
        Integer page = obj.getInteger("page");
        Integer pageSize = obj.getInteger("pageSize");
        Map<String, Object> result = apiService.queryFillHistoryByPage(token, stockCode, tradeType, startDate, endDate, page, pageSize);
        return makeApiResult(result);
    }


    @RequestMapping(value = "/queryHoldStockList")
    @ResponseBody
    public Object queryHoldStockList(String paraJson) throws Exception {
        JSONObject obj = parseEncryptedParam(paraJson);
        String token = obj.getString("token");
        Map<String, Object> result = apiService.queryHoldStockList(token);
        return makeApiResult(result);
    }

    @RequestMapping(value = "/queryHoldStockListByPage")
    @ResponseBody
    public Object queryHoldStockListByPage(String paraJson) throws Exception {
        JSONObject obj = parseEncryptedParam(paraJson);
        String token = obj.getString("token");
        int page = obj.getInteger("pageNo") == null ? 1 : obj.getInteger("pageNo");
        int pageSize = obj.getInteger("pageSize") == null ? 1 : obj.getInteger("pageSize");
        Map<String, Object> result = apiService.queryHoldStockListByPage(token, page, pageSize);
        return makeApiResult(result);
    }

    @RequestMapping(value = "/queryHoldedStockByStockCode")
    @ResponseBody
    public Object queryHoldedStockByStockCode(String paraJson) throws Exception {
        JSONObject obj = parseEncryptedParam(paraJson);
        String token = obj.getString("token");
        String stockCode = obj.getString("stockCode");
        Map<String, Object> result = apiService.queryHoldStockByStockCode(token, stockCode);
        return makeApiResult(result);
    }

    /**
     * 保存自选股
     *
     * @param paraJson
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveOptionalUnit")
    @ResponseBody
    public Object saveOptionalUnit(String paraJson) throws Exception {
        JSONObject obj = parseEncryptedParam(paraJson);
        String token = obj.getString("token");
        String stockCodes = obj.getString("stockCodes");
        Map<String, Object> result = apiService.saveOptionalUnit(token, stockCodes);
        return makeApiResult(result);
    }

    /**
     * 获取自选股
     *
     * @param paraJson
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getOptionalUnit")
    @ResponseBody
    public Object getOptionalUnit(String paraJson) throws Exception {
        JSONObject obj = parseEncryptedParam(paraJson);
        String token = obj.getString("token");
        Map<String, Object> result = apiService.getOptionalUnit(token);
        return makeApiResult(result);
    }

    /**
     * 保存自选股顺序
     *
     * @param paraJson
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveOptionalUnitOrder")
    @ResponseBody
    public Object saveOptionalUnitOrder(String paraJson) throws Exception {
        JSONObject obj = parseEncryptedParam(paraJson);
        String token = obj.getString("token");
        String stockCodes = obj.getString("stockCodes");
        Map<String, Object> result = apiService.saveOptionalUnitOrder(token, stockCodes);
        return makeApiResult(result);
    }

    /**
     * 删除自选股
     *
     * @param paraJson
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delOptionalUnit")
    @ResponseBody
    public Object delOptionalUnit(String paraJson) throws Exception {
        JSONObject obj = parseEncryptedParam(paraJson);
        String token = obj.getString("token");
        String stockCodes = obj.getString("stockCodes");
        Map<String, Object> result = apiService.delOptionalUnit(token, stockCodes);
        return makeApiResult(result);
    }

    /**
     * 置顶自选股
     *
     * @param paraJson
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/topOptionalUnit")
    @ResponseBody
    public Object topOptionalUnit(String paraJson) throws Exception {
        JSONObject obj = parseEncryptedParam(paraJson);
        String token = obj.getString("token");
        String stockCodes = obj.getString("stockCodes");
        Map<String, Object> result = apiService.topOptionalUnit(token, stockCodes);
        return makeApiResult(result);
    }

}
