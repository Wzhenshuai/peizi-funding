package com.icaopan.customer.service;

import com.icaopan.customer.bean.ChannelApplyParams;
import com.icaopan.customer.bean.ChannelParams;
import com.icaopan.customer.model.BuyLimitChannel;
import com.icaopan.customer.model.Channel;
import com.icaopan.customer.model.ChannelApply;
import com.icaopan.customer.model.ChannelLimit;
import com.icaopan.util.page.Page;

import java.math.BigDecimal;
import java.util.List;


public interface ChannelService {

    public void saveChannel(Channel record);

    public Page getChannelByPage(Page page, ChannelParams channelParams);

    public Channel getChannelById(Integer id);

    public List<Channel> selectAll();

    public void updateCashAvailable(BigDecimal cashAvailable, String code, BigDecimal totalAssets);

    public List<Channel> selectChannelByUserId(Integer userId);

    public List<Channel> selectChannelsBuCustomerId(Integer customerId);

    public List<Channel> selectChanelsByCustomerIds(Integer[] customerIds);

    public List<Channel> findByCustomerIdNotInUserId(Integer customerId, Integer userId);

    Page findUnchecked_AM(Page page);

    public String addMQ(String code, String channelType);

    public boolean verifyCode(String code);

    public List<BuyLimitChannel> selectBuyLimitChannels(Integer userId, String securityCode);

    public List<ChannelLimit> findChannelLimitByChannelId(Integer channelId);

    List<ChannelLimit> findChannelLimit(Integer channelId,Integer customerId);

    public boolean updateChannelLimit(ChannelLimit channelLimit);

    public boolean deleteChannelLimit(Integer id);

    boolean addChannelLimit(ChannelLimit channelLimit);
    // 添加提交的通道信息
    boolean addChannelApply(ChannelApply channelApply);

    boolean updateApplyStatus(String status,String adminNotes, Integer applyId);

    Page findChannelApplyByPage(Page page,String status,Integer customerId);

    public Channel getChannelByAccount(String account);
}
