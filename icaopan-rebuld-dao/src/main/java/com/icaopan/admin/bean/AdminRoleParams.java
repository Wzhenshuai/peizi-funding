package com.icaopan.admin.bean;

import com.icaopan.common.BaseParams;

public class AdminRoleParams extends BaseParams {

    private String roleName;

    public AdminRoleParams() {
        super();
    }

    public AdminRoleParams(String roleName) {
        super();
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
