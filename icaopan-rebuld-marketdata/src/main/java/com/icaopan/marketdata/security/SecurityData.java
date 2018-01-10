package com.icaopan.marketdata.security;

import java.io.Serializable;
import java.util.Date;

public class SecurityData implements Serializable {

    private static final long serialVersionUID = -7545119679065644298L;

    /** 公共字段： **/

    /**
     * 证券代码（LOCAL_CODE） *
     */
    private String symbol;

    /**
     * 上市交易所CODE（LIST_EXCHANGE_CODE） *
     */
    private String exchange;

    /**
     * 证券类型（SECURITY_TYPE_CODE） *
     */
    private String type;

    /**
     * 中文简称（SHORT_NAME_CN） *
     */
    private String name;

    /**
     * 当天停牌标识 *
     */
    private boolean suspensionFlag;

    /**
     * 涨停价
     */
    private double limitUp;
    /**
     * 跌停价
     */
    private double limitDown;

    /**
     * 上市日期 *
     */
    private Date issueDate;

    /**
     * 集合竞价标识*
     */
    private String competitionFlag;

    public String getCompetitionFlag() {
        return competitionFlag;
    }

    public void setCompetitionFlag(String competitionFlag) {
        this.competitionFlag = competitionFlag;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date listedDate) {
        issueDate = listedDate;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getSuspensionFlag() {
        return suspensionFlag;
    }

    public void setSuspensionFlag(boolean isTrading) {
        suspensionFlag = isTrading;
    }

    public double getLimitUp() {
        return limitUp;
    }

    public void setLimitUp(double limitUp) {
        this.limitUp = limitUp;
    }

    public double getLimitDown() {
        return limitDown;
    }

    public void setLimitDown(double limitDown) {
        this.limitDown = limitDown;
    }

}
