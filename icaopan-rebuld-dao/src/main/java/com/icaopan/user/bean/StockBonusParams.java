package com.icaopan.user.bean;

import java.util.List;

/**
 * 红股分配方案
 *
 * @author yong
 * @Description
 * @date 2017年4月20日
 */
public class StockBonusParams {

    private String securityCode;

    private List<Integer> statusList;

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }


}
