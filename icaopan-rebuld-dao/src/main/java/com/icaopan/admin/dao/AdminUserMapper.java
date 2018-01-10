package com.icaopan.admin.dao;

import com.icaopan.admin.bean.AdminUserParams;
import com.icaopan.admin.model.AdminUser;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(AdminUser record);

    void updatePassword(AdminUser record);

    AdminUser selectUserByUsernameAndPassword(AdminUser record);

    List<AdminUser> selectUserByPage(@Param("page") Page page, @Param("params") AdminUserParams params);

    List<AdminUser> selectUserByUserName(String userName);

}