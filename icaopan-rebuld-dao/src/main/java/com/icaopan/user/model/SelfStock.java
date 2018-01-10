package com.icaopan.user.model;

public class SelfStock {

    private String id;
    private String userid;                //用户Id
    private String stockcode;            //股票代码
    private String stockname;            //股票名称
    private String stickflag;            //置顶标识
    private String lastmodifytime;        //最后修改时间
    private String groupid;                //组Id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode;
    }

    public String getStockname() {
        return stockname;
    }

    public void setStockname(String stockname) {
        this.stockname = stockname;
    }

    public String getStickflag() {
        return stickflag;
    }

    public void setStickflag(String stickflag) {
        this.stickflag = stickflag;
    }

    public String getLastmodifytime() {
        return lastmodifytime;
    }

    public void setLastmodifytime(String lastmodifytime) {
        this.lastmodifytime = lastmodifytime;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }
}
