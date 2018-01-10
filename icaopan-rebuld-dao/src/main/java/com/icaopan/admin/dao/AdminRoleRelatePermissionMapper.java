package com.icaopan.admin.dao;

import com.icaopan.admin.model.AdminRoleRelatePermission;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface AdminRoleRelatePermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminRoleRelatePermission record);

    int insertSelective(AdminRoleRelatePermission record);

    AdminRoleRelatePermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminRoleRelatePermission record);

    int updateByPrimaryKey(AdminRoleRelatePermission record);

    void deleteByRoleId(int id);

    List<HashMap<String, Object>> selectAllPermissionForDistribute(@Param("id") int id, @Param("permissionName") String permissionName);

    void deleteByMenuId(Integer menuId);
}