package com.icaopan.enums.enumBean;

/**
 * Created by dongyuan on 17/3/28.
 */
public enum RiskResult {

    Success(0, "0", "Success", "所有的风控检查均已通过"),
    WarningRiskFail(1, "1", "Fail", "用户总资产已达警戒线"),
    OpenRiskFail(2, "2", "Fail", "用户总资产已达平仓线"),
    BannedStockRiskFail(3, "3", "Fail", "所购股票为禁买股"),
    SingleStockRiskFail(4, "4", "Fail", "单支股票持仓比例已达风控比例"),
    SmallStockRiskFail(5, "5", "Fail", "中小板持仓比例已达风控比例"),
    SmallSingleStockRiskFail(6, "6", "Fail", "中小板单支股票持仓比例已达风控比例"),
    CreateStockRiskFail(7, "7", "Fail", "创业板持仓比例已达风控比例"),
    CreateSingleStockRiskFail(8, "8", "Fail", "创业板单支股票持仓比例已达风控比例"),
    StockBlacklistRiskFail(9, "9", "Fail", "所购股票被禁止买入"),
    StockWhitelistRiskFail(10, "10", "Fail", "所购股票未在白名单中"),
    AmountQuotaRiskFail(11, "11", "Fail", "单笔所购股票金额已达上限"),
    QuantityQuotaRiskFail(12, "12", "Fail", "单笔所购股票数量已达上限"),
    UpAmplitudeRiskFail(13, "13", "Fail", "所购股票为昨收涨停股票,振幅已达上限"),
    DownAmplitudeRiskFail(14, "14", "Fail", "所购股票为昨收跌停股票,振幅已达上限");

    private int num;

    private String code;

    private String name;

    private String display;

    RiskResult(int num, String code, String name, String display) {
        this.num = num;
        this.code = code;
        this.name = name;
        this.display = display;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
