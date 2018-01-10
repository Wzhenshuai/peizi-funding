package com.icaopan.user.dao;

import com.icaopan.user.bean.PositionParams;
import com.icaopan.user.model.ChannelSecurityPosition;
import com.icaopan.user.model.UserChannelPosition;
import com.icaopan.util.page.Page;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ChannelSecurityPositionMapper {

    /**
     * 保存证券头寸信息
     *
     * @return boolean
     * @Parameter SecurityPosition
     */
    boolean insert(ChannelSecurityPosition record);

    /**
     * 根据前端用户编号，股票编号查询证券头寸信息
     *
     * @param userId
     * @param internalSecurityId
     * @return SecurityPosition
     */
    ChannelSecurityPosition findByUserIdAndInternalSecurityId(@Param("userId") Integer userId, @Param("internalSecurityId") String internalSecurityId);

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
    ChannelSecurityPosition selectByPrimaryKey(Integer id);

    /**
     * 查询证券头寸，并作分页处理
     *
     * @param page
     * @param params
     * @return
     */
    List<ChannelSecurityPosition> findByPage(@Param("page") Page page, @Param("params") PositionParams params);

    List<ChannelSecurityPosition> findAllPostionBySecurityId(String securityId);


    /**
     * 根据用户编号，股票代码，交易通道查询证券头寸信息
     *
     * @param userName,internalSecurityId,channelId
     * @return List<SecurityPosition>
     */
    List<ChannelSecurityPosition> find(@Param("userName") String userName,
                                       @Param("internalSecurityId") String internalSecurityId,
                                       @Param("channelId") String channelId,
                                       @Param("customerId") Integer customerId);

    /**
     * 查找证券头寸
     * 查询条件：证券ID，资金方ID，用户ID
     *
     * @param userId
     * @param channelId
     * @param internalSecurityId
     * @return securityPosition
     */
    ChannelSecurityPosition findByUserIdAndChannelIdAndInternalSecurityId(@Param("userId") Integer userId,
                                                                          @Param("channelId") Integer channelId,
                                                                          @Param("internalSecurityId") String internalSecurityId);

    /**
     * 根据资金方编号，股票ID查询持仓明细
     * */
    List<ChannelSecurityPosition> findByCustomerIdAndInternalSecurityId(@Param("page")Page page,@Param("params")PositionParams params);


    /**
     * 更新可用头寸
     *
     * @param id
     * @param availableChanged
     * @return
     */
    int updateAvailable(@Param("id") Integer id,
                            @Param("availableChanged") BigDecimal availableChanged);

    /**
     * 更新总头寸和成本价
     *
     * @param id
     * @param amountChanged
     * @param costPrice
     * @return
     */
    int updateAmountAndCostPrice(@Param("id") Integer id, @Param("amountChanged") BigDecimal amountChanged, @Param("costPrice") BigDecimal costPrice);

    /**
     * 更新成本价
     *
     * @param id
     * @param amountChanged
     * @return
     */
    int updateTotalCost(@Param("id") Integer id, @Param("totalCostChanged") BigDecimal amountChanged);


    /**
     * 根据用户ID，股票代码，查询证券头寸信息
     *
     * @param userId,internalSecurityId,customerId
     * @return List<SecurityPosition>
     */
    List<ChannelSecurityPosition> findByUserIdSecurityId(@Param("userId") Integer userId,
                                                         @Param("internalSecurityId") String internalSecurityId,
                                                         @Param("customerId") Integer customerId);

    List<ChannelSecurityPosition> findByUserId(@Param("userId") Integer userId);

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
     * @return BigDecimal
     */
    BigDecimal queryAmountByUserIdStockCode(@Param("userId") Integer userId, @Param("stockCode") String stockCode);


    /**
     * 根据用户ID，股票code查询单支股票持仓
     *
     * @param userId
     * @param stockType
     * @return BigDecimal
     */
    BigDecimal queryAmountByUserIdStockType(@Param("userId") Integer userId, @Param("stockType") String stockType);

    ChannelSecurityPosition verifyChannel(@Param("userId") Integer userId,@Param("channelId") Integer channelId, @Param("internalSecurityId") String internalSecurityId);

    List<UserChannelPosition> queryUserChannelPositionByIdStock(@Param("userId") Integer userId, @Param("internalSecurityId") String internalSecurityId);

    Double querySummarySecurityPostionAmount(@Param("account") String account,@Param("securityId") String securityId);
    
    List<ChannelSecurityPosition> queryAllByAccount(@Param("account")String account,@Param("securityId") String securityId);
    
    List<String> queryAllStockCode();
}