package com.icaopan.risk.util;

import com.icaopan.risk.bean.RiskCtrlParam;
import com.icaopan.risk.bean.RiskCtrlVO;
import com.icaopan.risk.consts.RiskConsts;
import com.icaopan.util.page.Page;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * desc 风控大盘数据容器类
 * Created by kanglj on 17/3/31.
 */
public class Container {
    // 风控大盘容器
    public static final List<RiskCtrlVO> container = Collections.synchronizedList(new LinkedList<RiskCtrlVO>());
    private static Logger log = LoggerFactory.getLogger(Container.class);

    public static Page getPageByParams(Page page, RiskCtrlParam params){
        List<RiskCtrlVO> data = new ArrayList<>();
        List<RiskCtrlVO> totalList = new ArrayList<>();
        if(container.size() == 0 || "0".equals(params.getMarketValues())){
            page.setAaData(data);
            return page;
        }

        if(StringUtils.isEmpty(params.getUserName()) && params.getCustomerId() == null){
            if(container.size() == 0 || RiskConsts.MARKETTYPE_NONE.equals(params.getMarketValues())){
                page.setiTotalRecords(0);
                page.setiTotalDisplayRecords(0);
                page.setAaData(data);
                return page;
            }
            synchronized (container){
                for(int i = 0; i < container.size(); i++){
                    marketValueFilterSnippet(params.getMarketValues(), container.get(i), data, totalList);
                }
            }
        }
        else if(StringUtils.isNotEmpty(params.getUserName()) && params.getCustomerId() == null){
            synchronized (container){
                for(int i = 0; i < container.size(); i++){
                    RiskCtrlVO vo = container.get(i);
                    if(StringUtils.equals(params.getUserName(), vo.getUserName())){
                        marketValueFilterSnippet(params.getMarketValues(), vo, data, totalList);
                    }
                }
            }
        }
        else if(StringUtils.isEmpty(params.getUserName()) && params.getCustomerId() != null){
            synchronized (container){
                for(int i = 0; i < container.size(); i++){
                    RiskCtrlVO vo = container.get(i);
                    if(params.getCustomerId().intValue() == vo.getCustomerId().intValue()){
                        marketValueFilterSnippet(params.getMarketValues(), vo, data, totalList);
                    }
                }
            }
        }else{
            synchronized (container){
                for(int i = 0; i < container.size(); i++){
                    RiskCtrlVO vo = container.get(i);
                    if(StringUtils.equals(params.getUserName(), vo.getUserName()) && params.getCustomerId().intValue() == vo.getCustomerId().intValue()){
                        marketValueFilterSnippet(params.getMarketValues(), vo, data, totalList);
                    }
                }
            }
        }
        page.setiTotalRecords(totalList.size());
        page.setiTotalDisplayRecords(totalList.size());
        int total = page.getiDisplayStart() + page.getiDisplayLength();
        int maxSize = total > totalList.size() ? totalList.size() : total;
        for(int i = page.getiDisplayStart(); i<maxSize; i++){
            data.add(totalList.get(i));
        }
        page.setAaData(data);
        return page;
    }

    public static void remove(Integer userId){
        for(RiskCtrlVO vo : container){
            if(userId.intValue() == vo.getUserId().intValue()){
                container.remove(vo);
                break;
            }
        }
    }

    private static void marketValueFilterSnippet(String marketValues, RiskCtrlVO vo, List<RiskCtrlVO> data, List<RiskCtrlVO> totalList){
        switch (marketValues){
            case RiskConsts.MARKETTYPE_MoreThanZero:
                if(vo.getMarketValue().compareTo(BigDecimal.ZERO) > 0){
                    totalList.add(vo);
                }
                break;
            case RiskConsts.MARKETTYPE_EqualsZero:
                if(vo.getMarketValue().compareTo(BigDecimal.ZERO) == 0){
                    totalList.add(vo);
                }
                break;
            case RiskConsts.MARKETTYPE_ALL:
                totalList.add(vo);
                break;
            default:
                break;
        }
    }
}
