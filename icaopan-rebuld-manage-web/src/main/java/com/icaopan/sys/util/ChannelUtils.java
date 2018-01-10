package com.icaopan.sys.util;

import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.realm.LoginRealm;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.admin.util.SpringContextHolder;
import com.icaopan.customer.bean.ChannelParams;
import com.icaopan.customer.dao.ChannelMapper;
import com.icaopan.customer.model.Channel;

import java.util.List;

/**
 * 交易通道获取类，方便调用
 * Created by RoyLeong @royleo.xyz on 2016/12/15.
 */


public class ChannelUtils {

    public static final AdminUserService adminUserService = SpringContextHolder.getBean(AdminUserService.class);
    public static ChannelMapper channelDao = SpringContextHolder.getBean(ChannelMapper.class);

    public static String getChanelOpt(){
        ChannelParams params = new ChannelParams();
        AdminUser adminUser = LoginRealm.getCurrentUser();
        if (!adminUserService.isSuperAdmin(adminUser)){
            params.setCustomerId(adminUser.getCustomerId());
        }
        StringBuffer sb = new StringBuffer();
        List<Channel> list = channelDao.selectChannelByPage(null, params);
        for (Channel channel : list){
            sb.append(String.format("<option value='%s'>%s</option>", channel.getId(),channel.getName()));
        }
        return sb.toString();
    }

    public static String getAvailableChannelsOpt(){
        ChannelParams params = new ChannelParams();
        AdminUser adminUser = LoginRealm.getCurrentUser();
        if (!adminUserService.isSuperAdmin(adminUser)){
            params.setCustomerId(adminUser.getCustomerId());
        }
        params.setIsAvailable(true);
        StringBuffer sb = new StringBuffer();
        List<Channel> list = channelDao.selectChannelByPage(null, params);
        for (Channel channel : list){
            sb.append(String.format("<option value='%s'>%s</option>", channel.getId(),channel.getName()));
        }
        return sb.toString();
    }


}
