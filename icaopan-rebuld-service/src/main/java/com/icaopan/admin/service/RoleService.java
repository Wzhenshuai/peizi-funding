package com.icaopan.admin.service;

import com.icaopan.admin.bean.AdminRoleParams;
import com.icaopan.admin.model.AdminRole;
import com.icaopan.util.page.Page;

import java.util.HashMap;
import java.util.List;

public interface RoleService {

    public int saveAdminRole(AdminRole adminRole);

    public int deleteAdminRoleById(int id);

    public int updateAdminRole(AdminRole adminRole);

    public AdminRole getAdminRoleById(int id);

    public Page findAdminRoles(Page page, AdminRoleParams params);

    public List<HashMap<String, Object>> selectAllPermissionForDistribute(int id, String permissionName);

    public int saveRoleRelatePermission(int id, int[] permission_ids);

    public List<HashMap<String, Object>> findAdminRolesForUser(int id);

    AdminRole findRolesByUserId(Integer userId);

	List<AdminRole> findAllRolesByUserId(Integer userId);

}
