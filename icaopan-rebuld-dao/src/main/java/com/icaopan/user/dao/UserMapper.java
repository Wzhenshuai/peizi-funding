package com.icaopan.user.dao;

import com.icaopan.user.bean.TestTrader;
import com.icaopan.user.bean.UserPageParams;
import com.icaopan.user.model.User;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserMapper {

    /**
     * 保存用户信息
     *
     * @param user
     * @return boolean
     */
    int insert(User user);

    /**
     * 验证用户名是否存在
     *
     * @param username
     * @return user
     */
    User verify(String username);

    void updateLoginCountAndLastTime(@Param("userId") Integer userId);

    User findUser(@Param("userName") String userName, @Param("passWord") String passWord);

    /**
     * 查找所有用户，并分页处理
     *
     * @param page
     * @return List<User>
     */
    List<User> findAllUsersByPage(@Param("page") Page page, @Param("params") UserPageParams params);

    /**
     * 根据用户编号,用户名，用户状态查找用户，并根据关联编号找出该用户的所有关联账户
     *
     * @param userId
     * @return List<User>
     */
    List<User> findRelatedUser(@Param("userId") Integer userId);

    /**
     * 取消关联用户
     *
     * @param userId
     */
    public boolean unLinkUser(@Param("userId") Integer userId);

    /**
     * 根据用户编号查询用户信息(主键查找功能)
     *
     * @param userId
     * @return
     */
    User findByUserId(Integer userId);

    /**
     * 根据资金方编号查询用户信息
     */
    List<User> findByCustomerId(Integer customerId);

    /**
     * 更新用户状态，用户状态参数为枚举类型
     * 用户状态：0:正常,1:已锁定,2:已注销
     *
     * @param userId,UserState
     * @return boolean
     */
    boolean updateUserStatus(@Param("userId") Integer userId,
                             @Param("userStatus") String userStatus);

    /**
     * 更新用户登陆次数,最后一次登陆状态，在登陆成功的时候调用
     *
     * @param userId
     */
    boolean updateLoginCount(Integer userId);

    /**
     * 更改密码
     */
    boolean updatePwd(@Param("userId") Integer userId, @Param("pwd") String password);

    /**
     * 更新冻结金额
     * */
    int updateFrozen(@Param("id") Integer id,@Param("frozen")BigDecimal frozen);

    /**
     * 更新用户信息
     *
     * @param user
     */
    boolean updateUser(User user);

    /**
     * 删除收费方案，在数据表中标注使用默认费率
     *
     * @param userId
     */
    boolean updateRatioFeeToDefault(Integer userId);

    /**
     * 更新警戒线，平仓线
     *
     * @param id,warnLine,openLine
     */
    boolean updateRiskControl(@Param("userId") Integer id,
                              @Param("warnLine") BigDecimal warnLine,
                              @Param("openLine") BigDecimal openLine);

    /**
     * 设置佣金方案，最低费用，更新佣金费用的时候，还需要设置 不再使用默认费率
     *
     * @param id,costmin,ratiofee
     * @return
     */
    boolean updateCostFee(@Param("userId") Integer id,
                          @Param("minCost") BigDecimal minCost,
                          @Param("ratioFee") BigDecimal ratioFee);

    /**
     * 比例风控设置
     *
     * @param userId,singleStockScal,smallStockScale,createStockScale,smallSingleStockScale,createSingleStockScale
     * @return
     */
    boolean updateRatioRiskControl(@Param("userId") Integer userId,
                                   @Param("singleStockScale") BigDecimal singleStockScale,
                                   @Param("smallStockScale") BigDecimal smallStockScale,
                                   @Param("createStockScale") BigDecimal createStockScale,
                                   @Param("smallSingleStockScale") BigDecimal smallSingleStockScale,
                                   @Param("createSingleStockScale") BigDecimal createSingleStockScale);

    /**
     * 增配减配功能
     * 保存更新之后的本金金额，融资金额
     *
     * @param id,cash_amount,finance_amount
     */
    boolean updateCapitalBySave(@Param("userId") Integer id,
                                @Param("cashAmount") BigDecimal cashAmount,
                                @Param("financeAmount") BigDecimal financeAmount);

    /**
     * 增配减配功能
     * 更新本金金额变量，融资金额变量
     *
     * @param id cash_amount_changed finance_amount_changed
     */
    boolean updateCapitalByChanged(@Param("userId") Integer id,
                                   @Param("cashChanged") BigDecimal cashChanged,
                                   @Param("financeChanged") BigDecimal fiannceChanged);

    /**
     * 更新可用金额，账户总金额
     *
     * @param id amount available
     **/
    boolean updateAvailableAndTotalAmount(@Param("userId") Integer id,
                                          @Param("availableChanged") BigDecimal availableChanged,
                                          @Param("amountChanged") BigDecimal amountChanged);


    /**
     * 调整资金头寸和可用，清算用
     */
    void adjustPosition();

    List<TestTrader> queryTestTrader();

}
