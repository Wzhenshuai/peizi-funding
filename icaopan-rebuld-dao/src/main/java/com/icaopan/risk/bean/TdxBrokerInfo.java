package com.icaopan.risk.bean;


import java.util.Date;

public class TdxBrokerInfo {
    private Integer id;

    private String brokerName;

    private String ip;

    private String port;

    private String serverName;

    private String yybCode;

    private String yybName;

    private String remark;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName == null ? null : brokerName.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port == null ? null : port.trim();
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public String getYybCode() {
        return yybCode;
    }

    public void setYybCode(String yybCode) {
        this.yybCode = yybCode == null ? null : yybCode.trim();
    }

    public String getYybName() {
        return yybName;
    }

    public void setYybName(String yybName) {
        this.yybName = yybName == null ? null : yybName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}