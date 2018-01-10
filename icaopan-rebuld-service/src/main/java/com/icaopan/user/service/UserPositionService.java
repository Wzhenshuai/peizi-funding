package com.icaopan.user.service;

import com.icaopan.user.bean.PositionParams;
import com.icaopan.user.bean.UserPositionParams;
import com.icaopan.user.model.User;
import com.icaopan.user.model.UserSecurityPosition;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.PageBean;
import com.icaopan.web.vo.SecurityPositionVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2016/12/19.
 */
public interface UserPositionService {

    public boolean saveUserSecurityPosition(UserSecurityPosition userSecurityPosition);

    public boolean deleteUserSecurityPosition();            //计划任务方法，删除总金额，可用金额都为0的证券头寸信息

    public UserSecurityPosition findUserSecurityPositionById(Integer userId, Integer customerId, String internalSecurityId);

    UserSecurityPosition findById(Integer id);

    public void changeAvailable(UserPositionParams params);

    public void changeAmountAndCostPrice(UserPositionParams params);

    void updatePositionPrice(Integer channelPositionId, BigDecimal costPrice);

    PageBean<SecurityPositionVO> findUserPositionByPage(User user, Integer pageNo, Integer pageSize);

    List<SecurityPositionVO> findAllPosition(User user);

    BigDecimal findPositionAvailable(User user, String stockCode);

    //清算用 调整证券头寸
    public void adjustPosition();

    Page<SecurityPositionVO> findUserPositionByPage(
            Page<SecurityPositionVO> page, UserPositionParams params);

    // 根据用户和证券代码查询用户该股票持仓
    List<UserSecurityPosition> queryPositionByUserIdStockCode(Integer userId, String securityCode);

    // 根据用户和证券类型查询用户改类型的股票持仓
    List<UserSecurityPosition> queryPositionByUserIdStockType(Integer userId, String stockType);

    public List<SecurityPositionVO> queryUserPositionByParams(UserPositionParams params);

    public Page<SecurityPositionVO> querySummaryPositionByCustomerId(Page<SecurityPositionVO> page, PositionParams params);

    public Page<SecurityPositionVO> queryByCustomerIdAndInternalSecurityId(Page<SecurityPositionVO> page,PositionParams params);

	BigDecimal findPositionAvailable(User user, String stockCode,
			Integer channelId);

}
