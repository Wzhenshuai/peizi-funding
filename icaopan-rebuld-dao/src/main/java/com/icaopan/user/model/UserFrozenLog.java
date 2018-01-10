package com.icaopan.user.model;

import com.icaopan.util.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

public class UserFrozenLog {
    private Integer id;

    private Integer userId;

    private Integer customerId;

    private String userName;

    private BigDecimal frozen;

    private String type;

    private String typeStr;

    private Date createTime;

    private String createTimeStr;

    private String remark;

    public UserFrozenLog() {
    }

    public UserFrozenLog(Integer userId, String userName, BigDecimal frozen, String type,String remark) {
        this.userId = userId;
        this.userName = userName;
        this.frozen = frozen;
        this.type = type;
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getFrozen() {
        return frozen;
    }

    public void setFrozen(BigDecimal frozen) {
        this.frozen = frozen;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTypeStr() {
        if (type==null) return null;
        if (StringUtils.equals("froze",type)){
            return "冻结";
        }else {
            return "解冻";
        }
    }

    public Date getcreateTime() {
        return createTime;
    }

    public void setcreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        return DateUtils.formatDateTime(createTime);
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}