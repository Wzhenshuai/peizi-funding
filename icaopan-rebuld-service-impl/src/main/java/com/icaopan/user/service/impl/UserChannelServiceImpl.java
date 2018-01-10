package com.icaopan.user.service.impl;

import com.icaopan.enums.enumBean.UserChannelOperateType;
import com.icaopan.enums.enumBean.UserChannelType;
import com.icaopan.trade.bean.ChannelQuotaLog;
import com.icaopan.trade.service.ChannelQuotaLogService;
import com.icaopan.user.dao.UserChannelMapper;
import com.icaopan.user.model.UserChannel;
import com.icaopan.user.service.UserChannelService;
import com.icaopan.util.BigDecimalUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by RoyLeong@royleo.xyz on 2016/11/15.
 */
@Service("userChannelService")
public class UserChannelServiceImpl implements UserChannelService {

    @Autowired
    private UserChannelMapper userChannelMapper;

    @Autowired
    private ChannelQuotaLogService quotaLogService;

    /**
     * 更新用户和通道关联关系
     * @return
     * */
    @Override
    public Boolean updateUserChannel(List<UserChannel> userChannelList) {
        if (userChannelList == null || userChannelList.isEmpty()){
            return false;
        }else {
            for (UserChannel uc : userChannelList){
                UserChannel userChannel = findByUserIdAndChannelId(uc.getUserId(),uc.getChannelId());
                if (userChannel==null){
                    ChannelQuotaLog log = new ChannelQuotaLog(uc.getUserId(),uc.getChannelId(), UserChannelOperateType.CREATE.getCode(),String.valueOf(uc.getUserChannelTypeVal()),uc.getQuota(),null);
                    quotaLogService.saveLog(log);
                }else {
                    if (BigDecimalUtil.compareTo(userChannel.getQuota(),uc.getQuota())==0 &&
                            StringUtils.equals(String.valueOf(userChannel.getUserChannelTypeVal()),String.valueOf(uc.getUserChannelTypeVal())))continue;    //限额数量不改变，不记录日志
                    ChannelQuotaLog log = new ChannelQuotaLog(uc.getUserId(),uc.getChannelId(),UserChannelOperateType.UPDATE.getCode(),String.valueOf(uc.getUserChannelTypeVal()),uc.getQuota(),null);
                    quotaLogService.saveLog(log);
                }
            }
            userChannelMapper.deleteByUserId(userChannelList.get(0).getUserId());
            userChannelMapper.insert(userChannelList);
            return true;
        }
    }


    /**
     * 插入用户和通道关系
     * @param userChannelList
     * @return
     * */
    @Override
    public int saveUserChannel(List<UserChannel> userChannelList) {
        int i = 0;
        i = userChannelMapper.insert(userChannelList);
        if (i>0){   //新增限额记录(创建)
            for (UserChannel uc : userChannelList){
                if (BigDecimal.ZERO.compareTo(uc.getQuota())==0) continue;
                ChannelQuotaLog log = new ChannelQuotaLog(uc.getUserId(),uc.getChannelId(), UserChannelOperateType.CREATE.getCode(),String.valueOf(uc.getUserChannelTypeVal()),uc.getQuota(),null);
                quotaLogService.saveLog(log);
            }
        }
        return i;
    }

    /**
     * 根据用户id 删除用户与通道的关系
     */
    @Override
    public boolean deleteByUserId(Integer userId) {
        return userChannelMapper.deleteByUserId(userId);
    }

    @Override
    public boolean deleteByUserIdAndChannelId(Integer userId, Integer channelId) {
        return userChannelMapper.deleteByUserIdAndChannelId(userId,channelId);
    }

    @Override
    public List<UserChannel> findByUserId(Integer userId) {
        return userChannelMapper.findByUserId(userId);
    }

    @Override
    public UserChannel findByUserIdAndChannelId(Integer userId, Integer channelId) {
        return userChannelMapper.selectByUserIdAndChannelId(userId,channelId);
    }


    @Override
    public void addUserChannelQuota(Integer userId, Integer channelId, BigDecimal variable) {
        //只更新限额的额度
        userChannelMapper.addUserChannelQuota(userId, channelId, variable, UserChannelType.LIMITED.getNum());
    }

    @Override
    public void updateUserChannelQuota(List<UserChannel> userChannelList) {
        for(UserChannel userChannel : userChannelList) {
            //只更新限额的额度
            userChannelMapper.updateUserChannelQuota(userChannel.getUserId(), userChannel.getChannelId(), userChannel.getQuota());
        }
    }

}
