package com.icaopan.customer.dao;


import com.icaopan.customer.model.TdxConnectInfo;

import java.util.List;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName TdxConnectInfoMapper
 * @Description (通达信走接口 的初始化信息)
 * @Date 2016年11月29日 下午3:32:13
 */
public interface TdxConnectInfoMapper {
    /**
     * @param record
     * @return
     * @Description (插入接口的基本信息)
     */
    boolean insert(TdxConnectInfo record);

    /**
     * @return
     * @Description ()
     */
    List<TdxConnectInfo> selectAll();

    /**
     * @param record
     * @return
     * @Description (更新接口的信息)
     */
    boolean updateBySelectiveId(TdxConnectInfo record);

}
