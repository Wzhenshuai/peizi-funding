package com.icaopan.admin.model;

public class AdminMenu {
    private Integer id;

    private String menuParent;

    private String menuCode;

    private String menuName;

    private String menuUrl;

    private String menuClazz;

    private String menuOrder;

    private Integer menuHidden;

    private String menuStyle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuParent() {
        return menuParent;
    }

    public void setMenuParent(String menuParent) {
        this.menuParent = menuParent == null ? null : menuParent.trim();
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode == null ? null : menuCode.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public String getMenuClazz() {
        return menuClazz;
    }

    public void setMenuClazz(String menuClazz) {
        this.menuClazz = menuClazz == null ? null : menuClazz.trim();
    }

    public String getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(String menuOrder) {
        this.menuOrder = menuOrder == null ? null : menuOrder.trim();
    }

    public Integer getMenuHidden() {
        return menuHidden;
    }

    public void setMenuHidden(Integer menuHidden) {
        this.menuHidden = menuHidden;
    }

    public String getMenuStyle() {
        return menuStyle;
    }

    public void setMenuStyle(String menuStyle) {
        this.menuStyle = menuStyle == null ? null : menuStyle.trim();
    }
}