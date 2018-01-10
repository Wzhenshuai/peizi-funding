package com.icaopan.sys.dao;

import com.icaopan.sys.model.Log;
import com.icaopan.sys.model.LogParams;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangzs on 2017/12/21 0021.
 */
@Repository
public interface LogMapper {

    public List<Log> findByUser(@Param("userId") Integer userId);

    public List<Log> findByCustomer(@Param("customerId") Integer customerId);

    public List<Log> findByLogParams(@Param("page") Page page, @Param("params") LogParams params);

    public int insert(Log log);
}
