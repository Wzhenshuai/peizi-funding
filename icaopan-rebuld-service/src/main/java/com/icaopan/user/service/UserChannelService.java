package com.icaopan.user.service;

import com.icaopan.user.model.UserChannel;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by RoyLeong@royleo.xyz on 2017/04/05.
 */
public interface UserChannelService {

    public int saveUserChannel(List<UserChannel> userChannelList);

    public Boolean updateUserChannel(List<UserChannel> userChannelList);

    public boolean deleteByUserId(Integer userId);

    boolean deleteByUserIdAndChannelId(Integer userId,Integer channelId);

    List<UserChannel> findByUserId(Integer userId);

    UserChannel findByUserIdAndChannelId(Integer userId,Integer channelId);

    public void addUserChannelQuota(Integer userId, Integer channelId, BigDecimal variable);

    public void updateUserChannelQuota(List<UserChannel> userChannelList);

}
