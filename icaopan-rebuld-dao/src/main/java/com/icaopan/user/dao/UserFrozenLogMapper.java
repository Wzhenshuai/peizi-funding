package com.icaopan.user.dao;


import com.icaopan.user.model.UserFrozenLog;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFrozenLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserFrozenLog record);

    UserFrozenLog selectByPrimaryKey(Integer id);

    List<UserFrozenLog> selectByPage(@Param("params") UserFrozenLog log,@Param("page")Page page);

}