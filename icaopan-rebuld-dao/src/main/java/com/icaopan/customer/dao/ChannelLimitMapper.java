package com.icaopan.customer.dao;


import com.icaopan.customer.model.ChannelLimit;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.util.Internal;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName TradeChannelLimitMapper
 * @Description(通道单票信息)
 * @Date 2017年06月22日 下午9:57:08
 */
@Repository
public interface ChannelLimitMapper {

    boolean insert(ChannelLimit channelLimit);

    boolean update(@Param("channelLimit") ChannelLimit channelLimit);

    List<ChannelLimit> selectChannelLimit();

    List<ChannelLimit> selectByChannelId(@Param("channelId") Integer channelId);

    boolean deleteChannelLimit(@Param("id")Integer id);

}