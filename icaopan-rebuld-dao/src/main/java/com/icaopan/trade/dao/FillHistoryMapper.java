package com.icaopan.trade.dao;


import com.icaopan.trade.bean.FillHistoryParams;
import com.icaopan.trade.model.FillHistory;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName FillHistoryMapper
 * @Description (历史成交)
 * @Date 2016年11月29日 下午2:56:40
 */
@Repository
public interface FillHistoryMapper {
    /**
     * @param record
     * @return
     * @Description (插入历史成交 记录)
     */
    boolean insert(FillHistory record);

    /**
     * @param page
     * @return
     * @Description (查询历史成交 分页)
     */
    List<FillHistory> selectByPage(@Param("page") Page page, @Param("params") FillHistoryParams fillHistoryParams);

    //当日成交 转移到历史成交

    /**
     * 当日成交 转移到历史成交
     *
     * @return
     */
    boolean generateHistory();
}