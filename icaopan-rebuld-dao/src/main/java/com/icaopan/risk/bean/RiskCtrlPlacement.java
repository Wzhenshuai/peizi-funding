package com.icaopan.risk.bean;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2017/3/13.
 */
public class RiskCtrlPlacement {

    private Integer userId;

    private List<Content> contents;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
