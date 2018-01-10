package com.icaopan.trade.dao;


import com.icaopan.trade.bean.ChannelPlacemenHistoryParams;
import com.icaopan.trade.model.ChannelPlacementHistory;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName ChannelPlacementHistoryMapper
 * @Description (通道历史委托)
 * @Date 2016年11月29日 下午3:33:00
 */
public interface ChannelPlacementHistoryMapper {
    /**
     * @param record
     * @return
     * @Description (插入通道委托的历史记录)
     */
    boolean insert(ChannelPlacementHistory record);

    /**
     * @param page
     * @return
     * @Description (查询 通道委托历史委托 分页)
     */
    List<ChannelPlacementHistory> selectByPage(@Param("page") Page page, @Param("params") ChannelPlacemenHistoryParams channelPlacemenParams);

    /**
     * 生成通道历史委托
     */
    boolean generateHistoryPlacement();

    boolean insertList(@Param("ChannelPlacementHistoryList") List<ChannelPlacementHistory> channelPlacementHistory);

    /**
     * @param page
     * @return
     * @Description (佣金差额汇总 分页)
     */
    List<ChannelPlacementHistory> selectCommissionCollectBypage(@Param("page") Page page, @Param("params") ChannelPlacemenHistoryParams channelParams);
}
