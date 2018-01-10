package com.icaopan.sys.util;

import com.icaopan.enums.enumBean.TradeSide;

/**
 * Created by RoyLeong @royleo.xyz on 2017/8/21.
 * @des 成交工具类
 */
public class FillUtils {

    /**
     * @des (交易类型, 买入、卖出)
     * */
    public static String getTradeType(){
        StringBuffer sb = new StringBuffer();
        for (TradeSide side : TradeSide.values()){
            sb.append(String.format("<option value='%s'>%s</option>", side.getName(),side.getDisplay()));
        }
        return sb.toString();
    }

}
