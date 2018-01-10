package com.icaopan.user.service;

import com.icaopan.user.model.SelfStock;

import java.util.List;

public interface SelfStockService {

    public List<SelfStock> selectByUserid(Integer userid);

    public void save(SelfStock selfStock);

    public void deleteByStockCode(Integer userid, String stockCode);

    public void topByStockCode(Integer userid, String stockCode);

    public SelfStock selectByStockCode(Integer userid, String stockCode);

    public void deleteByUserId(Integer userId);
}
