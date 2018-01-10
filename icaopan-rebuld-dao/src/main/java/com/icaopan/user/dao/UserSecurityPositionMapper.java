package com.icaopan.user.dao;

import com.icaopan.user.bean.PositionParams;
import com.icaopan.user.bean.UserPositionParams;
import com.icaopan.user.model.UserSecurityPosition;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserSecurityPositionMapper {

    /**
     * 保存证券头寸信息
     *
     * @return boolean
     * @Parameter SecurityPosition
     */
    boolean insert(UserSecurityPosition record);

    /**
     * 根据前端用户编号，股票编号查询证券头寸信息
     *
     * @param userId
     * @param internalSecurityId
     * @return SecurityPosition
     */
    UserSecurityPosition findByUserIdAndInternalSecurityId(@Param("userId") Integer userId, @Param("internalSecurityId") String internalSecurityId);

    /**
     * 清除空持仓，这是一个计划任务,sql判断amount available字段都为0的时候，删除持仓信息
     *
     * @return boolean
     * @Parameter
     */
    boolean deleteEmptyPosition();

    /**
     * 根据头寸编号查询证券头寸，主键查询
     *
     * @Parameter id
     * @returns SecurityPosition
     */
    UserSecurityPosition selectByPrimaryKey(Integer id);

    /**
     * 查询证券头寸，并作分页处理
     *
     * @param page
     * @param params
     * @return
     */
    List<UserSecurityPosition> findByPage(@Param("page") Page page, @Param("params") UserPositionParams params);


    /**
     * 更新可用头寸
     *
     * @param userPositionParams
     * @return
     */
    int updateAvailable(@Param("userPositionParams") UserPositionParams userPositionParams);


    /**
     * 更新成本价增量
     *
     * @param userPositionParams
     * @return
     */
    boolean updateCostPriceDelta(@Param("userPositionParams") UserPositionParams userPositionParams);
    
    /**
     * 更新总头寸和成本价
     *
     * @param userPositionParams
     * @return
     */
    int updateAmountAndCostPrice(@Param("userPositionParams") UserPositionParams userPositionParams);


    /**
     * 根据用户ID，股票代码，查询证券头寸信息
     *
     * @param Integer userId, Integer customerId, String internalSecurityId
     * @return UserSecurityPosition
     */
    UserSecurityPosition findByUserIdSecurityId(@Param("userId") Integer userId, @Param("customerId") Integer customerId, @Param("internalSecurityId") String internalSecurityId);

    List<UserSecurityPosition> findByUserId(@Param("userId") Integer userId);

    BigDecimal findPostionAvailable(@Param("userId") Integer userId, @Param("internalSecurityId") String internalSecurityId);

    /**
     * 日切调平证券头寸
     */
    boolean adjustPosition();

    /**
     * 根据用户ID，股票code查询单支股票持仓
     *
     * @param userId
     * @param stockCode
     * @return List<UserSecurityPosition>
     */
    List<UserSecurityPosition> queryPositionByUserIdStockCode(@Param("userId") Integer userId, @Param("stockCode") String stockCode);

    /**
     * 根据用户ID，股票code查询单支股票持仓
     *
     * @param userId
     * @param stockType
     * @return List<UserSecurityPosition>
     */
    List<UserSecurityPosition> queryPositionByUserIdStockType(@Param("userId") Integer userId, @Param("stockType") String stockType);

    /**
     * 根据资金方编号查询用户持仓汇总，并将汇总按照股票代码区分区分汇总
     * @param page
     * @param params
     * @return
     * */
    List<UserSecurityPosition> queryPositionByCustomerId(@Param("page") Page page, @Param("params")PositionParams params);

    /**
     * 根据资金方编号，股票编号查询用户对应股票下面的持仓明细
     * @param customerId
     * @param internalSecurityId
     * @return
     * */
    List<UserSecurityPosition> queryByCustomerIdAndInternalSecurityId(@Param("page") Page page,@Param("params")PositionParams params);
}