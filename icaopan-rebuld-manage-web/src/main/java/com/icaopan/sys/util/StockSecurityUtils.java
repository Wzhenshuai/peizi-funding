package com.icaopan.sys.util;

import com.icaopan.admin.util.SpringContextHolder;
import com.icaopan.stock.dao.StockSecurityMapper;

/**
 * Created by RoyLeong @royleo.xyz on 2016/12/16.
 */
public class StockSecurityUtils {

    public static StockSecurityMapper stockSecurityDAO = SpringContextHolder.getBean(StockSecurityMapper.class);


}
