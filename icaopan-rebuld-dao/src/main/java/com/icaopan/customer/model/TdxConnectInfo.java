package com.icaopan.customer.model;

import java.util.Date;

public class TdxConnectInfo {
    private Integer id;

    private String accountNo;

    private String tradeAccount;

    private String gddmSz;

    private String gddmSh;

    private String jyPassword;

    private String txPassword;

    private String ip;

    private String port;

    private String version;

    private String yybId;

    private String exchangeIdSh;

    private String exchangeIdSz;

    private String oName;

    private String description;

    private String dllName;

    private Date upDateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public String getTradeAccount() {
        return tradeAccount;
    }

    public void setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount == null ? null : tradeAccount.trim();
    }

    public String getGddmSz() {
        return gddmSz;
    }

    public void setGddmSz(String gddmSz) {
        this.gddmSz = gddmSz == null ? null : gddmSz.trim();
    }

    public String getGddmSh() {
        return gddmSh;
    }

    public void setGddmSh(String gddmSh) {
        this.gddmSh = gddmSh == null ? null : gddmSh.trim();
    }

    public String getJyPassword() {
        return jyPassword;
    }

    public void setJyPassword(String jyPassword) {
        this.jyPassword = jyPassword == null ? null : jyPassword.trim();
    }

    public String getTxPassword() {
        return txPassword;
    }

    public void setTxPassword(String txPassword) {
        this.txPassword = txPassword == null ? null : txPassword.trim();
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getYybId() {
        return yybId;
    }

    public void setYybId(String yybId) {
        this.yybId = yybId == null ? null : yybId.trim();
    }

    public String getExchangeIdSh() {
        return exchangeIdSh;
    }

    public void setExchangeIdSh(String exchangeIdSh) {
        this.exchangeIdSh = exchangeIdSh == null ? null : exchangeIdSh.trim();
    }

    public String getExchangeIdSz() {
        return exchangeIdSz;
    }

    public void setExchangeIdSz(String exchangeIdSz) {
        this.exchangeIdSz = exchangeIdSz == null ? null : exchangeIdSz.trim();
    }

    public String getoName() {
        return oName;
    }

    public void setoName(String oName) {
        this.oName = oName == null ? null : oName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getDllName() {
        return dllName;
    }

    public void setDllName(String dllName) {
        this.dllName = dllName == null ? null : dllName.trim();
    }

    public Date getUpDateTime() {
        return upDateTime;
    }

    public void setUpDateTime(Date upDateTime) {
        this.upDateTime = upDateTime;
    }


}