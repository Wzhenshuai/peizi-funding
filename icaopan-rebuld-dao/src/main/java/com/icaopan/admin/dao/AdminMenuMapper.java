package com.icaopan.admin.dao;


import com.icaopan.admin.model.AdminMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AdminMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminMenu record);

    int insertSelective(AdminMenu record);

    AdminMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminMenu record);

    int updateByPrimaryKey(AdminMenu record);

    List<AdminMenu> selectAllParentMenu(int userId);

    List<AdminMenu> selectAllSubMenu(@Param("userId") int userId, @Param("menuId") int menuId);

    List<AdminMenu> selectAllMenu();

    List<AdminMenu> selectAllParentMenuForShow(int userId);

    List<AdminMenu> selectAllSubMenuForShow(@Param("userId") int userId, @Param("menuId") int menuId);
}