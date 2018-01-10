package com.icaopan.user.dao;

import com.icaopan.user.model.UserChannel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/4/6 0006.
 */
@Repository
public interface UserChannelMapper {

    int insert(@Param("userChannelList") List<UserChannel> userChannelList);

    boolean update(@Param("userChannelList") List<UserChannel> userChannelList);

    boolean deleteByUserId(@Param("userId") Integer userId);

    boolean deleteByUserIdAndChannelId(@Param("userId") Integer userId,@Param("channelId") Integer channelId);

    List<UserChannel> findByUserId(@Param("userId") Integer userId);

    UserChannel selectByUserIdAndChannelId(@Param("userId") Integer userId,@Param("channelId") Integer channelId);

    void addUserChannelQuota(@Param("userId") Integer userId, @Param("channelId") Integer channelId, @Param("variable") BigDecimal variable, @Param("userChannelType") Integer userChannelType);

    void updateUserChannelQuota(@Param("userId") Integer userId, @Param("channelId") Integer channelId, @Param("variable") BigDecimal variable);
}
