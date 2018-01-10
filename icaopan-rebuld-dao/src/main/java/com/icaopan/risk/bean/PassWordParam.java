package com.icaopan.risk.bean;

/**
 * Created by RoyLeong @royleo.xyz on 2017/3/27.
 */
public class PassWordParam {

    private Integer id;

    private String txPassWord;

    private String jyPassWord;

    public PassWordParam() {
    }

    public PassWordParam(Integer id, String txPassWord, String jyPassWord) {
        this.id = id;
        this.txPassWord = txPassWord;
        this.jyPassWord = jyPassWord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxPassWord() {
        return txPassWord;
    }

    public void setTxPassWord(String txPassWord) {
        this.txPassWord = txPassWord;
    }

    public String getJyPassWord() {
        return jyPassWord;
    }

    public void setJyPassWord(String jyPassWord) {
        this.jyPassWord = jyPassWord;
    }


}
