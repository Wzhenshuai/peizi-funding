package com.icaopan.trade.dao;


import com.icaopan.trade.bean.FillSummaryParams;
import com.icaopan.trade.model.FillSummary;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName FillMapper
 * @Description (当日成交)
 * @Date 2016年11月29日 下午3:01:05
 */
public interface FillSummaryMapper {

    /**
     * @param page
     * @return
     * @Description (成交汇总查询)
     */
    List<FillSummary> selectFillSummaryByPage(@Param("page") Page page, @Param("params") FillSummaryParams fillParams);

    Double selectFillSummaryAmount(FillSummaryParams fillParams);

}
