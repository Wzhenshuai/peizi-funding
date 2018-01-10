package com.icaopan.trade.dao;


import com.icaopan.enums.enumBean.TradeStatus;
import com.icaopan.trade.bean.PlacementParams;
import com.icaopan.trade.model.Placement;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName PlacementMapper
 * @Description (用户当日委托)
 * @Date 2016年11月29日 下午3:05:17
 */
@Repository
public interface PlacementMapper {
    /**
     * @return
     * @Description (删除当日委托)
     */
    boolean deletePlacementToday();

    /**
     * @param record
     * @return
     * @Description (插入当日委托的信息)
     */
    int insert(Placement record);

    /**
     * @param id
     * @return
     * @Description (根据id 查询当日委托)
     */
    Placement selectById(Integer id);

    /**
     * @param page
     * @return
     * @Description (查询当日委托分页)
     */
    List<Placement> placementSelectByPage(@Param("page") Page page, @Param("params") PlacementParams placementParams);


    /**
     * 查询当日未结束的委托
     *
     * @param userId
     * @return
     */
    List<Placement> selectPlacementNotEnd(Integer userId);


    /**
     * 查询当日买入未结束的委托
     *
     * @param userId
     * @return
     */
    List<Placement> selectBuyPlacementNotEnd(Integer userId);

    /**
     * 根据中小板、创业板情况查询非终态委托
     *
     * @param userId
     * @param stockPoolType
     * @return
     */
    List<Placement> selectPlacementNotEndByStockType(@Param("userId") Integer userId, @Param("stockPoolType") Integer stockPoolType);

    List<Placement> selectPlacement(Integer userId);

    /**
     * @param tradeStatus
     * @param placementId
     * @return
     * @Description (更新当日委托的状态)
     */
    boolean updatePlacementStatus(@Param("tradeStatus") TradeStatus tradeStatus, @Param("placementId") Integer placementId);

    /**
     * @param changeQuantity
     * @param changeAmount
     * @param placementId
     * @return
     * @Description (更新当日委托的 成交数量 成交金额 成交价格)
     */
    boolean updatePlaceQuantityAndAmount(@Param("changeQuantity") BigDecimal changeQuantity, @Param("changeAmount") BigDecimal changeAmount, @Param("placementId") Integer placementId);

    /**
     * 更新废单数量
     */
    boolean updateInvalidQuantity(@Param("invalidQuantity") BigDecimal invalidQuantity, @Param("placementId") Integer placementId);

    /**
     * 更新撤单数量
     */
    boolean updateCancelQuantity(@Param("cancelQuantity") BigDecimal cancelQuantity, @Param("placementId") Integer placementId);

    /**
     * 当日不是终态的委托
     */
    List<Placement> selectCurrentPlacementNotEnd();

    /**
     * 查询所有的当日委托
     */

    List<Placement> selectAllPlacement();
}
