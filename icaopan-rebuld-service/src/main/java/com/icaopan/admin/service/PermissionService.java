package com.icaopan.admin.service;

import com.icaopan.admin.bean.AdminPermissionParams;
import com.icaopan.admin.model.AdminMenu;
import com.icaopan.admin.model.AdminPermission;
import com.icaopan.util.page.Page;

import java.util.List;

public interface PermissionService {

    public List<AdminMenu> selectAllParentMenu(int userId);

    public List<AdminMenu> selectAllSubMenu(int userId, int menuId);

    public List<AdminMenu> selectAllParentMenuForShow(int userId);

    public List<AdminMenu> selectAllSubMenuForShow(int userId, int menuId);

    public Page findAdminPermissionByPage(Page page, AdminPermissionParams params);

    public int deleteAdminPermissionById(int id);

    public List<AdminMenu> selectAllMenu();

    public int saveAdminPermission(AdminPermission adminPermission);

    public void saveAdminMenu(AdminMenu adminMenu);

    public AdminMenu getAdminMenu(int menuId);

    public void deleteAdminMenu(int userId, int menuId);

    public List<AdminPermission> selectPermissionsByUser(Integer id);
}
