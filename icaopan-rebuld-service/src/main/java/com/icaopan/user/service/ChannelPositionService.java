package com.icaopan.user.service;

import com.icaopan.trade.model.ChannelPlacement;
import com.icaopan.user.bean.PositionParams;
import com.icaopan.user.model.ChannelSecurityPosition;
import com.icaopan.user.model.User;
import com.icaopan.user.model.UserChannelPosition;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.PageBean;
import com.icaopan.web.vo.SecurityPositionVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2016/12/19.
 */
public interface ChannelPositionService {

    public boolean saveSecurityPosition(ChannelSecurityPosition securityPosition);

    public ChannelSecurityPosition initialPosition(ChannelPlacement placement);

    boolean verify(Integer userId, String InternalSecurityId);

    public boolean deleteSecurityPosition();            //计划任务方法，删除总金额，可用金额都为0的证券头寸信息

    public ChannelSecurityPosition findSecurityPositionById(Integer id);

    public ChannelSecurityPosition find(Integer userId, Integer channelId, String internalSecurityId);

    public Page<SecurityPositionVO> findByCustomerIdAndInternalId(Page<SecurityPositionVO> page,PositionParams params);

    public void changeAvailable(Integer id, BigDecimal availableChanged);

    public void changeAmountAndCostPrice(Integer id, BigDecimal amountChenged, BigDecimal costPrice);

    public boolean movePosition(ChannelSecurityPosition currentPosition,Integer targetId,BigDecimal moveAmount,String isHiddenRecord);

    public void updatePosition(Integer channelPositionId, BigDecimal availableChanged, BigDecimal amountChanged, BigDecimal costPrice, String createTime,String isHiddenRecord);

    // public boolean updatePosition(Integer channelPositionId, BigDecimal availableChanged, BigDecimal amountChanged, BigDecimal costPrice, String createTime);

    public void updatePositionPrice(Integer channelPositionId, BigDecimal costPrice);

    PageBean<SecurityPositionVO> findPositionByPage(User user, Integer pageNo, Integer pageSize);

    List<SecurityPositionVO> findAllPosition(User user);

    BigDecimal findPositionAvailable(User user, String stockCode);

    //清算用 调整证券头寸
    public void adjustPosition();

    Page<SecurityPositionVO> findPositionByPage(Page<SecurityPositionVO> page,
                                                PositionParams params);

    BigDecimal getMaketValueByUserId(Integer userId);

    // 根据用户ID，股票code查询单支股票持仓
    BigDecimal queryAmountByUserIdStockCode(Integer userId, String stockCode);

    // 根据用户ID，股票类型查询某类股票持仓
    BigDecimal queryAmountByUserIdStockType(Integer userId, String stockType);

    List<ChannelSecurityPosition> findAllPositionBySecurityId(String securityId);

    boolean verifyChannel(Integer userId,Integer channelId, String internalSecurityId);

    /**
     * 根据用户ID，股票代码，查询证券头寸信息
     *
     * @param userId,internalSecurityId,customerId
     * @return List<SecurityPosition>
     */
    public List<ChannelSecurityPosition> findByUserIdSecurityId(Integer userId,
                                                         String internalSecurityId,
                                                         Integer customerId);

    public List<SecurityPositionVO> findPositionByParams(PositionParams params);

    public List<UserChannelPosition> queryUserChannelPositionByIdStock(Integer userId, String stockCode);

    public Double querySummarySecurityPostionAmount(String account, String stockCode);

	public List<ChannelSecurityPosition> queryAllByAccount(String account,String securityId);

	public List<ChannelSecurityPosition> findByChannelId(Integer channelId);

    public boolean clearPositionByChannelId(Integer channelId);

    public List<String> queryAllStockCode();

}
