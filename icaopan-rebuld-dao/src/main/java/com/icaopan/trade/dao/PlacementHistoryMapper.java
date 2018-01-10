package com.icaopan.trade.dao;


import com.icaopan.trade.bean.PlacementHistoryParams;
import com.icaopan.trade.model.PlacementHistory;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName PlacementHistoryMapper
 * @Description (用户历史委托)
 * @Date 2016年11月29日 下午3:04:31
 */
@Repository
public interface PlacementHistoryMapper {
    /**
     * @param record
     * @return
     * @Description (插入历史委托信息)
     */
    boolean insert(PlacementHistory record);

    /**
     * @param page
     * @return
     * @Description (查询历史委托 分页)
     */
    List<PlacementHistory> selectPlacementHistoryByPage(@Param("page") Page page, @Param("params") PlacementHistoryParams placementHistoryParams);

    boolean insertList(@Param("placementHistoryList") List<PlacementHistory> placementHistoryList);

}
