package com.icaopan.trade.model;

import com.icaopan.util.DateUtils;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

public class CheckLog {

    private Integer id;

    private Date updateTime;

    private String operationName;

    private String result;

    private List<String> results;

    private String updateTimeStr;

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public String getUpdateTimeStr() {
        return DateUtils.formatDateTime(updateTime);
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    public CheckLog() {
    }

    public CheckLog(Date updateTime, String operationName, String result) {
        this.updateTime = updateTime;
        this.operationName = operationName;
        this.result = result;
    }
}
