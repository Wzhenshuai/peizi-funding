package com.icaopan.risk.bean;

/**
 * Created by RoyLeong @royleo.xyz on 2017/3/28.
 */
public class PrivateCustomer {

    private String id;

    private String userName;

    public PrivateCustomer() {
    }

    public PrivateCustomer(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
