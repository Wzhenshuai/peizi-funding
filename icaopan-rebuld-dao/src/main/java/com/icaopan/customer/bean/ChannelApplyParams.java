package com.icaopan.customer.bean;

/**
 * Created by Administrator on 2017/6/30 0030.
 */
public class ChannelApplyParams {

    private Integer customerId;

    private Integer id;

    private Integer isHandle;

    private String adminNote;

    public String getAdminNote() {
        return adminNote;
    }

    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsHandle() {
        return isHandle;
    }

    public void setIsHandle(Integer isHandle) {
        this.isHandle = isHandle;
    }
}
