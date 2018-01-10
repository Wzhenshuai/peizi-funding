package com.icaopan.user.dao;

import com.icaopan.user.model.SelfStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SelfStockMapper {

    public List<SelfStock> selectByUserid(Integer userid);

    public void save(SelfStock selfStock);

    public void deleteByStockCode(@Param("userid") Integer userid, @Param("stockCode") String stockCode);

    public void topByStockCode(@Param("userid") Integer userid, @Param("stockCode") String stockCode);

    public SelfStock selectByStockCode(@Param("userid") Integer userid, @Param("stockCode") String stockCode);

    public void deleteByUserId(Integer userId);

}
