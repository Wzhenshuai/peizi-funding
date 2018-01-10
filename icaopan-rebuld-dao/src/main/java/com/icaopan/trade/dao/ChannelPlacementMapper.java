package com.icaopan.trade.dao;


import com.icaopan.enums.enumBean.TradeStatus;
import com.icaopan.trade.bean.ChannelForEmsParameter;
import com.icaopan.trade.bean.ChannelPlacementParams;
import com.icaopan.trade.model.ChannelPlacement;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName ChannelPlacementMapper
 * @Description (通道当日委托)
 * @Date 2016年11月29日 下午3:33:40
 */
public interface ChannelPlacementMapper {

    /**
     * @param record
     * @return
     * @Description (插入通道委托数据)
     */
    boolean insert(ChannelPlacement record);

    /**
     * @param Page
     * @return
     * @Description (分页查询通道委托)
     */
    List<ChannelPlacement> selectChannelPlacementByPage(@Param("page") Page Page, @Param("params") ChannelPlacementParams channelPlacementParams);

    /**
     * 查找通道委托记录
     *
     * @param channelPlacementParams
     * @return
     */
    List<ChannelPlacement> selectChannelPlacement(ChannelPlacementParams channelPlacementParams);
    
    List<ChannelPlacement> selectNotDockChannelPlacement(ChannelPlacementParams channelPlacementParams);

    List<ChannelPlacement> selectByPlacementNo(@Param("account") String account, @Param("placementNo") String placementNo);

    ChannelPlacement selectById(@Param("id") Integer id);

    /**
     * @param channelForEmsParameter
     * @return
     * @Description (查询说有的通道委托 提供给EMS)
     */
    List<ChannelPlacement> selectAllForEms(ChannelForEmsParameter channelForEmsParameter);


    /**
     * @param id
     * @param status
     * @return
     * @Description (更新通道委托的状态)
     */
    boolean updateStatus(@Param("id") Integer id, @Param("status") TradeStatus status, @Param("errorMessage") String errorMessage);

    /**
     * @param id
     * @param changeQuantity
     * @param changeAmount
     * @return
     * @Description (更新通道委托的 成交数量 成交金额 成交价格)
     */
    boolean updateQuantityAndAmount(@Param("id") Integer id, @Param("changeQuantity") BigDecimal changeQuantity, @Param("changeAmount") BigDecimal changeAmount);

    /**
     * @return
     * @Description (删除通道当日委托)
     */
    boolean deletePlacementToday();

    /**
     * @param id
     * @param rejectMessage
     * @return
     * @Description 更新废单原因
     */
    boolean fillRejectMessage(@Param("id") String id, @Param("rejectMessage") String rejectMessage);


    /**
     * 更新委托编号
     *
     * @param id
     * @param placementCode
     * @return
     */
    boolean fillPlacementCode(@Param("id") Integer id,
                              @Param("placementCode") String placementCode);

    /**
     * 根据状态查询通道委托
     *
     * @param List<String>
     * @return
     */
    List<ChannelPlacement> selectPlacementByStatus(@Param("statusList") List<String> statusList);

}
