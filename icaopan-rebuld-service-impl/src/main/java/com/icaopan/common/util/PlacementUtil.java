package com.icaopan.common.util;

import com.icaopan.enums.enumBean.TradeStatus;

/**
 * Created by RoyLeong @royleo.xyz on 2017/3/28.
 */
public class PlacementUtil {

    /**
     * 判断当前委托是否为终态：如果是终态，返回true，否则返回false
     * @param status
     * @return boolean
     * */
    public static boolean isFinished(TradeStatus status) {
        switch (status){
            case CANCELLED:
            case INVALID:
            case FILLED:
                return true;
            default:
                return false;
        }
    }


}
