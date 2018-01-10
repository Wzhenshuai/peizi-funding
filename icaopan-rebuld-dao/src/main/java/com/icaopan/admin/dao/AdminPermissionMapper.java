package com.icaopan.admin.dao;

import com.icaopan.admin.bean.AdminPermissionParams;
import com.icaopan.admin.model.AdminPermission;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AdminPermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminPermission record);

    int insertSelective(AdminPermission record);

    AdminPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminPermission record);

    int updateByPrimaryKey(AdminPermission record);

    List<AdminPermission> findAdminPermissionByPage(@Param("page") Page page, @Param("params") AdminPermissionParams params);

    List<AdminPermission> selectPermissionsByUser(Integer id);

    void deleteByMenuId(Integer menuId);
}