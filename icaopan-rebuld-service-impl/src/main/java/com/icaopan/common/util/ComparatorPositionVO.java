package com.icaopan.common.util;

import com.icaopan.web.vo.SecurityPositionVO;

import java.util.Comparator;

/**
 * Created by RFC on 2017/12/15.
 * 比较持仓市值
 */
public class ComparatorPositionVO implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        SecurityPositionVO positionVO1 = (SecurityPositionVO) o1;
        SecurityPositionVO positionVO2 = (SecurityPositionVO) o2;
        int flag = positionVO2.getMarketValue().compareTo(positionVO1.getMarketValue());
        return flag;
    }
}
