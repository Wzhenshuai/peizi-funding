package com.icaopan.marketdata.provider.tonglian;

import java.util.List;

/**
 * Created by ffff on 2016-09-22.
 */
public class Entity<T> {
    private static final long serialVersionUID = 13312L;
    private Integer retCode;
    private String  retMsg;
    private List<T> data;

    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
