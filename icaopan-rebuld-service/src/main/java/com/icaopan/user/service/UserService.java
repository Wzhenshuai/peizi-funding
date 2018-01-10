package com.icaopan.user.service;

import com.icaopan.enums.enumBean.UserStatus;
import com.icaopan.user.bean.UserPageParams;
import com.icaopan.user.model.User;
import com.icaopan.user.model.UserFrozenLog;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.AccountVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by RoyLeong@royleo.xyz on 2016/11/15.
 */
public interface UserService {

    boolean isNormal(User user);

    public boolean isLogOut(User user);

    boolean isBuySellLocked(User user);

    boolean isBuyLocked(User user);

    public int saveUser(User user);

    public void updatePwd(Integer userId, String password);

    int updateUserFrozen(Integer userId,BigDecimal frozen);

    Page<UserFrozenLog> findUserFrozenLogByPage(UserFrozenLog log,Page page);

    int saveUserFrozenLog(UserFrozenLog log);

    public User findUserById(Integer userId);   //根据用户ID查询用户信息

    public List<User> findByCustomerId(Integer customerId);

    public User findUserByUserName(String userName);    //根据用户名查找用户信息

    public boolean updateRelatedId(Integer idOrigin, Integer passiveId);     //设置关联账户

    public List<User> findRelatedUsers(Integer userId);    //根据ID查找关联用户

    public List<User> removeUserByStatus(List<User> users, UserStatus userStatus);

    public boolean unLink(Integer userId);  //取消关联用户

    public Page findUserByPage(Page page, UserPageParams params);    //分页查找用户

    public boolean updateUser(User user);               //更新用户信息,对应前端部分的编辑功能

    public boolean updateAmountAndAvailable(Integer userId, BigDecimal amount, BigDecimal available, String changeCause);

    public boolean updateUserStatus(Integer userId, String status);   //更新用户状态

    public boolean updateLoginCount(Integer userId);       //更新登陆次数

    public boolean verifyUserName(String userName);     //验证用户名是否重复

    public User findUser(String userName, String passWord);

    public AccountVO queryUserAccount(User user);//查看账户信息

    /**
     * 清算用，调整用户资金可用
     */
    public void adjustPosition();

    public void updateLoginCountAndLastTime(User user);

    public Page queryTestTrader(Page page);

    /**
     * 更新可用金额，账户总金额
     *
     * @param id amount available
     **/
    boolean updateAvailableAndTotalAmount(Integer id,
                                          BigDecimal availableChanged,
                                          BigDecimal amountChanged);

    public List<User> findAllUser(UserPageParams params);
}
