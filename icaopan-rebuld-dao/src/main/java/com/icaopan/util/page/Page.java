package com.icaopan.util.page;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lifaxin
 * @version V1.0
 * @Title: page.java
 * @Package com.icaopan.page
 * @Description: 分页参数
 * @date 2015年6月13日 下午2:24:52
 */
public class Page<E> {

    private int sEcho          = 0;
    private int iDisplayStart  = 0; // 开始下标
    private int iDisplayLength = 10;// 页面大小
    private List<E> aaData; // 查询出来的数据
    private int     iTotalRecords; // 总条数
    private int     iTotalDisplayRecords;// 总条数
    private Map<String, Object> params = new HashMap<String, Object>(); // 参数

    public Page() {
    }

    public Page(Integer pageNo, Integer pageSize) {
        iDisplayStart = (pageNo - 1) * pageSize;
        iDisplayLength = pageSize;
    }

    public Page(String pageJson) {
        JSONArray pageArray = JSONObject.parseArray(pageJson);
        if(pageArray!=null){
        	this.sEcho = Integer.parseInt(String.valueOf(getValueFromArray(pageArray, "sEcho")));
            this.iDisplayStart = Integer.parseInt(String.valueOf(getValueFromArray(pageArray, "iDisplayStart")));
            this.iDisplayLength = Integer.parseInt(String.valueOf(getValueFromArray(pageArray, "iDisplayLength")));
        }
    }


    //	从json串中 获取指定 的参数
    private Object getValueFromArray(JSONArray pageArray, String key) {
        Map<String, Object> searchBoxForPage = new HashMap<String, Object>();
        // 将list中所有值 转化到map 中去，在进行搜索
        for (Object pageObject : pageArray) {
            searchBoxForPage.put(((JSONObject) pageObject).getString("name"), ((JSONObject) pageObject).getString("value"));
        }
        return searchBoxForPage.get(key);
    }


    public int getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public int getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public List<E> getAaData() {
        return aaData;
    }

    public void setAaData(List<E> aaData) {
        this.aaData = aaData;
    }

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public int getLimitStart() {
        return iDisplayStart;
    }

    public int getLimitend() {
        if (iDisplayLength < 0) {
            return iTotalRecords;
        }
        return iDisplayLength;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public int getsEcho() {
        return sEcho;
    }

    public void setsEcho(int sEcho) {
        this.sEcho = sEcho;
    }

}
