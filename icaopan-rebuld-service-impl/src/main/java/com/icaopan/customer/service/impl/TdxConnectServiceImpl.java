package com.icaopan.customer.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.icaopan.customer.model.TdxConnectInfo;
import com.icaopan.customer.service.TdxConnectService;
/**
 * 
 * @ClassName TdxConnectServiceImpl
 * @Description (这里用一句话描述这个类的作用)
 * @author Wangzs
 * @Date 2016年12月7日 上午11:05:57
 * @version 1.0.0
 */
@Service("tdxConnectService")
public class TdxConnectServiceImpl implements TdxConnectService {

    @Override
    public boolean saveTdxConnectInfo(TdxConnectInfo record) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<TdxConnectInfo> getAllTdxConnectInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean updateBySelectiveId(TdxConnectInfo record) {
        // TODO Auto-generated method stub
        return false;
    }

   
}
