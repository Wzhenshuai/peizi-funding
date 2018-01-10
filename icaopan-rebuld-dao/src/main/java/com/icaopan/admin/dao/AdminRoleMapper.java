package com.icaopan.admin.dao;

import com.icaopan.admin.bean.AdminRoleParams;
import com.icaopan.admin.model.AdminRole;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface AdminRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminRole record);

    int insertSelective(AdminRole record);

    AdminRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminRole record);

    int updateByPrimaryKey(AdminRole record);

    List<AdminRole> findAdminRoles(@Param("page") Page page, @Param("params") AdminRoleParams params);

    List<HashMap<String, Object>> findAdminRolesForUser(int id);

    AdminRole findRolesByUserId(Integer userId);

    List<AdminRole> findAllRolesByUserId(Integer userId);
}
