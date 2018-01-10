package com.icaopan.admin.bean;

import com.icaopan.common.BaseParams;

public class AdminPermissionParams extends BaseParams {

    private String menuId;


    public AdminPermissionParams() {
        super();
    }

    public AdminPermissionParams(String menuId) {
        super();
        this.menuId = menuId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

}
