package com.icaopan.sys.util;

import com.icaopan.admin.util.SpringContextHolder;
import com.icaopan.risk.bean.TdxBroker;
import com.icaopan.risk.bean.TdxBrokerBase;
import com.icaopan.risk.dao.PrivateRiskCtrlMapper;

import java.util.List;

/**
 * 个人风控公用类
 * Created by RoyLeong @royleo.xyz on 2017/7/14.
 */
public class RiskCtrlUtils {

    public static final PrivateRiskCtrlMapper riskCtrlMapper = SpringContextHolder.getBean(PrivateRiskCtrlMapper.class);

    /**
     * 获取所有券商
     * */
    public static String getBrokers(){
        TdxBroker broker = new TdxBroker();
        List<TdxBrokerBase> brokerList = riskCtrlMapper.getAllBrokerBaseInfo();
        StringBuffer stringBuffer = new StringBuffer();
        for (TdxBrokerBase brb: brokerList) {
            stringBuffer.append(String.format("<option value='%s'>%s</option>", brb.getId(),brb.getBrokerName()));
        }
        return stringBuffer.toString();
    }



}
