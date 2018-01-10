package com.icaopan.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icaopan.trade.bean.ComplianceQueryParams;
import com.icaopan.trade.model.ComplianceResult;
import com.icaopan.util.page.Page;

public interface ComplianceResultMapper {

    int insert(ComplianceResult record);

    ComplianceResult selectByPrimaryKey(Integer id);
    
    List<ComplianceResult> selectByPage(@Param("page") Page<ComplianceResult> page,@Param("params") ComplianceQueryParams params);
}