package com.icaopan.customer.dao;


import com.icaopan.customer.bean.ChannelParams;
import com.icaopan.customer.model.BuyLimitChannel;
import com.icaopan.customer.model.Channel;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName ChannelMapper
 * @Description(通道信息)
 * @Date 2016年11月29日 下午2:57:08
 */
@Repository
public interface ChannelMapper {
    /**
     * @param record
     * @return
     * @Description (插入通道管理信息)
     */
    boolean insert(Channel record);

    /**
     * @param page
     * @return
     * @Description (查询通道信息分页)
     */
    List<Channel> selectChannelByPage(@Param("page") Page page, @Param("params") ChannelParams channelParams);

    List<Channel> selectAll();

    /**
     * @param record
     * @return
     * @Description (更新通道信息)
     */
    boolean updateBySelective(Channel record);

    /**
     * @param id
     * @return
     * @Description (根据id 查询通道信息)
     */
    Channel seleChannelById(Integer id);

    List<Channel> selectChannelByUserId(@Param("userId") Integer userId);

    List<Channel> selectChannelsByCustomerId(@Param("customerId") Integer customerId);

    List<Channel> selectChanelsByCustomerIds(@Param("customerIds")Integer[] customerIds);

    List<Channel> selectChannelsByCustomerIdNotInUserId(@Param("customerId") Integer customerId, @Param("userId") Integer userId);

    /**
     * 查询盘前未测试过的通道账户
     * */
    List<Channel> selectUnchecked_AM(@Param("page") Page page);

    /**
     * 更新 可用资金
     *
     * @param updateCashAvailable
     * @param code
     * @return
     */
    boolean updateCashAvailable(@Param("cashAvailable") BigDecimal updateCashAvailable, @Param("code") String code, @Param("totalAssets") BigDecimal totalAssets);

    boolean verifyCode(@Param("code") String code);

    /**
     * 查询买入限制的通道列表
     *
     * @param userId
     * @param securityCode
     * @return
     */
    List<BuyLimitChannel> selectBuyLimitChannels(@Param("userId") Integer userId, @Param("securityCode") String securityCode, @Param("internalSecurityId") String internalSecurityId);
    
    Channel selectChannelByAccount(String account);
}