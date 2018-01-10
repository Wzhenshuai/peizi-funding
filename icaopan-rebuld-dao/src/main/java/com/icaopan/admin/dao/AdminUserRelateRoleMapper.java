package com.icaopan.admin.dao;

import com.icaopan.admin.model.AdminUserRelateRole;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserRelateRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminUserRelateRole record);

    int insertSelective(AdminUserRelateRole record);

    AdminUserRelateRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminUserRelateRole record);

    int updateByPrimaryKey(AdminUserRelateRole record);

    void deleteByUserId(int id);
}