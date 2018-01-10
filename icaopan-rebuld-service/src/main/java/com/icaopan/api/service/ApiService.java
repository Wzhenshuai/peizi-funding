package com.icaopan.api.service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;


public interface ApiService {

    //登录
    public Map<String, Object> login(String system, String chanelId, String userName, String passWord, String vCode,
                                     HttpServletRequest request);

    //自动登录
    public Map<String, Object> autoLogin(String system, String chanelId, String userName, String passWord, String vCode,
                                         HttpServletRequest request);

    //登出
    public Map<String, Object> logOut(String token, String chanelId);

    //是否已经登录
    public Map<String, Object> isLogin(String token);

    //查询所有股票列表
    public Map<String, Object> getAllSecurity();

    //根据股票代码查出股票行情
    public Map<String, Object> queryMarketDataByCode(String securityCode);

    //增量查询股票更新列表
    public Map<String, Object> queryAllSecurityModelByCreation(String issueDate);

    //查询账户信息
    public Map<String, Object> queryAccount(String token);

    public Map<String, Object> queryAccountAvailableBalance(String token);

    //委托下单
    public Map<String, Object> doPlacementStockTrade(String token, String stockCode, BigDecimal quantity, int sellorbuy,
                                                     BigDecimal price);

    //委托撤单
    public Map<String, Object> cancelPlacement(String token, String placementId);

    //查询当日委托
    public Map<String, Object> queryPlacementCurrentDayList(String token, String tradeType);

    //查询历史委托
    Map<String, Object> queryPlacementHistoryByPage(String token,
                                                    String pStatusList, String stockCode, String tradeType,
                                                    String startDate, String endDate, Integer page, Integer pageSize);

    //查询当日成交
    public Map<String, Object> queryFillCurrentDayList(String token);

    //查询历史成交记录
    public Map<String, Object> queryFillHistoryByPage(String token, String stockCode, String tradeType,
                                                      String startDate, String endDate, Integer page, Integer pageSize);

    //查询持仓
    public Map<String, Object> queryHoldStockList(String token);

    //查询持仓分页
    Map<String, Object> queryHoldStockListByPage(String token, int page,
                                                 int pageSize);

    //查询单支股票持仓
    Map<String, Object> queryHoldStockByStockCode(String token, String stockCode);

    //提出建议
    public Map<String, Object> makeSuggestions(String token, String title, String content);

    //获取自选股列表
    public Map<String, Object> getOptionalUnit(String token);

    //添加自选股
    public Map<String, Object> saveOptionalUnit(String token, String stockCodes);

    //删除自选股
    public Map<String, Object> delOptionalUnit(String token, String stockCodes);

    //置顶自选股
    public Map<String, Object> topOptionalUnit(String token, String stockCodes);

    //保存自选股顺序
    public Map<String, Object> saveOptionalUnitOrder(String token, String stockCodes);

    //保存系统信息
    public Map<String, Object> saveDeviceInfo(String token, String chanelId, String deviceType, String deviceVersion,
                                              String deviceModel, String resolution, String applicationVersion);

    //当日成交分页查询
    Map<String, Object> queryFillByPage(String token, Integer page,
                                        Integer pageSize);

    Map<String, Object> queryCurrentDayPlacementNotEnd(String token);


}
