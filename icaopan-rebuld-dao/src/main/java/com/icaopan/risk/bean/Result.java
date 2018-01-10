package com.icaopan.risk.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2017/3/9.
 * 用于返回json反序列化对象
 */
public class Result implements Serializable {

    private List<String> header;

    private List<List<String>> body;

    private String message;

    public List<String> getHeader() {
        return header;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }

    public List<List<String>> getBody() {
        return body;
    }

    public void setBody(List<List<String>> body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
