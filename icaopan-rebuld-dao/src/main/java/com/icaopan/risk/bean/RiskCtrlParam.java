package com.icaopan.risk.bean;

/**
 * Created by kanglj on 17/3/31.
 */
public class RiskCtrlParam {

    private String  userName;
    private Integer customerId;
    private String  marketValues;

    public RiskCtrlParam(String userName, Integer customerId, String marketValues) {
        this.userName = userName;
        this.customerId = customerId;
        this.marketValues = marketValues;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMarketValues() {
        return marketValues;
    }

    public void setMarketValues(String marketValues) {
        this.marketValues = marketValues;
    }

}
