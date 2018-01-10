package com.icaopan.admin.model;

public class AdminPermissionForShow {

    private Integer id;

    private String permissionName;

    private Boolean canSave;

    private Boolean canQuery;

    private Boolean canUpdate;

    private Boolean canDelete;

    private String permissionDesc;

    private String menuName;

    private String menuUrl;

    private String menuClazz;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Boolean getCanSave() {
        return canSave;
    }

    public void setCanSave(Boolean canSave) {
        this.canSave = canSave;
    }

    public Boolean getCanQuery() {
        return canQuery;
    }

    public void setCanQuery(Boolean canQuery) {
        this.canQuery = canQuery;
    }

    public Boolean getCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(Boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }

    public String getPermissionDesc() {
        return permissionDesc;
    }

    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuClazz() {
        return menuClazz;
    }

    public void setMenuClazz(String menuClazz) {
        this.menuClazz = menuClazz;
    }

}
