package com.icaopan.customer.service;

import com.icaopan.customer.model.TdxConnectInfo;

import java.util.List;

public interface TdxConnectService {


    public boolean saveTdxConnectInfo(TdxConnectInfo record);

    public List<TdxConnectInfo> getAllTdxConnectInfo();

    public boolean updateBySelectiveId(TdxConnectInfo record);

}
