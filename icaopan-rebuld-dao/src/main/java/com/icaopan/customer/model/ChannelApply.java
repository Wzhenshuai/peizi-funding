package com.icaopan.customer.model;

import com.icaopan.enums.enumBean.ChannelApplyStatus;
import com.icaopan.util.DateUtils;

import java.util.Date;

/**
 * Created by Administrator on 2017/6/30 0030.
 */
public class ChannelApply{

    private Integer id;
    //券商名称
    private String securityName;
    //资金账号
    private String cashAccount;
    //交易账号
    private String tradeAccount;
    //交易密码
    private String jyPass;
    //通信密码
    private String txPass;
    // 备注
    private String notes;
    //营业部名称
    private String yybName;
    // 资金方ID
    private Integer customerId;
    // 是否处理
    private String status;

    private String createTimeStr;

    private Date createTime;

    private  Date updateTime;

    private String updateTimeStr;

    private String adminNotes;

    private String statusStr;

    public String getStatusStr() {
        return ChannelApplyStatus.getByCode(status).getDisplay();
    }

    public String getAdminNotes() {
        return adminNotes;
    }

    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getCashAccount() {
        return cashAccount;
    }

    public void setCashAccount(String cashAccount) {
        this.cashAccount = cashAccount;
    }

    public String getTradeAccount() {
        return tradeAccount;
    }

    public void setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount;
    }

    public String getJyPass() {
        return jyPass;
    }

    public void setJyPass(String jyPass) {
        this.jyPass = jyPass;
    }

    public String getTxPass() {
        return txPass;
    }

    public void setTxPass(String txPass) {
        this.txPass = txPass;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getYybName() {
        return yybName;
    }

    public void setYybName(String yybName) {
        this.yybName = yybName;
    }

   /* public String getStatus() {
        return ChannelApplyStatus.getByCode(status).getDisplay();
    }

    public void setStatus(String status) {
        this.status = status;
    }*/

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTimeStr(){
        return DateUtils.formatDateTime(createTime);
    }

    public String getUpdateTimeStr(){
        return DateUtils.formatDateTime(updateTime);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
