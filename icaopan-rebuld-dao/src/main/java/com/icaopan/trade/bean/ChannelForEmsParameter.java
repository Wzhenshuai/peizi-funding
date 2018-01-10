package com.icaopan.trade.bean;

import com.icaopan.enums.enumBean.TradeStatus;

import java.util.List;

public class ChannelForEmsParameter {

    private List<Integer> channelList;

    private List<TradeStatus> statusList;

    private String securityCode;

    public List<Integer> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<Integer> channelList) {
        this.channelList = channelList;
    }


    public List<TradeStatus> getStatusList() {
        return statusList;
    }


    public void setStatusList(List<TradeStatus> statusList) {
        this.statusList = statusList;
    }

    public String getSecurityCode() {
        return securityCode;
    }


    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

}

