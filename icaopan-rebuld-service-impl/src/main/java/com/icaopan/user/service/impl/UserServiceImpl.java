package com.icaopan.user.service.impl;

import com.icaopan.common.util.MD5;
import com.icaopan.enums.enumBean.TradeFlowNote;
import com.icaopan.enums.enumBean.TradeFowType;
import com.icaopan.enums.enumBean.UserStatus;
import com.icaopan.log.LogUtil;
import com.icaopan.log.annocation.MoneyAdjustLogAnnocation;
import com.icaopan.risk.service.RiskTaskService;
import com.icaopan.trade.model.Flow;
import com.icaopan.trade.service.FlowService;
import com.icaopan.user.bean.UserPageParams;
import com.icaopan.user.dao.UserFrozenLogMapper;
import com.icaopan.user.dao.UserMapper;
import com.icaopan.user.model.User;
import com.icaopan.user.model.UserFrozenLog;
import com.icaopan.user.service.ChannelPositionService;
import com.icaopan.user.service.UserPositionService;
import com.icaopan.user.service.UserService;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.AccountVO;
import com.icaopan.web.vo.SecurityPositionVO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by RoyLeong@royleo.xyz on 2016/11/15.
 */
@Service("userService")
public class UserServiceImpl implements UserService{

    private Logger logger = LogUtil.getLogger(getClass());
    @Autowired
    FlowService flowService;
    @Autowired
    RiskTaskService riskTaskService;
    @Autowired
    private UserMapper             userMapper;
    @Autowired
    private ChannelPositionService positionService;
    @Autowired
    private UserPositionService userPositionService;
    @Autowired
    private UserFrozenLogMapper frozenLogMapper;

    /**
     * 检查用户状态是否为正常状态，如果是正常状态返回true，否则返回false
     * @param user
     * @return
     * */
    @Override
    public boolean isNormal(User user) {
        if (user==null||user.getStatus()==null){
            return false;
        }
        return "0".equals(user.getStatus());
    }

    /**
     * 检查用户状态是否为停用状态，如果是停用状态返回true，否则返回false
     * @param user
     * @return
     * */
    @Override
    public boolean isLogOut(User user) {
        if (user==null||user.getStatus()==null){
            return false;
        }
        return "2".equals(user.getStatus());
    }

    /**
     * 检查用户状态是否为 锁买卖 状态
     * @param user
     * @return
     * */
    @Override
    public boolean isBuySellLocked(User user) {
        if (user==null||user.getStatus()==null){
            return false;
        }
        return "1".equals(user.getStatus());
    }

    /**
     * 检查用户状态是否为 锁定买入 状态
     * @param user
     * @return
     * */
    @Override
    public boolean isBuyLocked(User user) {
        if (user==null||user.getStatus()==null){
            return false;
        }
        return "3".equals(user.getStatus());
    }

    /**
     * 保存用户
     * @param user
     * @return
     * */
    @Override
    public int saveUser(User user) {
        if (StringUtils.isNoneEmpty(user.getPassword())){
            user.setPassword(MD5.MD5Encrypt(user.getPassword()));
        }
        int row = userMapper.insert(user);

        try {
            //添加交割单流水信息
            Flow flow = new Flow();
            flow.setUserId(user.getId());
            flow.setType(TradeFowType.CASH_ADD);
            flow.setNotes(TradeFlowNote.CASH_ADDCREATEHAND);

            flow.setCustomerId(user.getCustomerId());
            flow.setAdjustQuantity(BigDecimal.ZERO);
            flow.setAdjustAmount(user.getAmount());
            flow.setSecurityCode("CNY");
            flowService.saveFlow(flow);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("创建用户生成资金流水失败：【用户名:" + user.getUserName() + ",总金额：" + user.getAmount() + "】");

        }


        return row;
    }

    /**
     * 修改密码
     * @param userId
     * @param password
     * @return
     * */
    @Override
    public void updatePwd(Integer userId, String password) {
         userMapper.updatePwd(userId,com.icaopan.common.util.MD5.MD5Encrypt(password));
    }

    @Override
    public int updateUserFrozen(Integer userId, BigDecimal frozen) {
        int result =userMapper.updateFrozen(userId,frozen);
        if(result>0){   //如果更新成功,重新给风控大盘刷新缓存
            User user = findUserById(userId);
            if (user!=null && !isLogOut(user)){       //如果用户不是停用，执行刷新缓存
                riskTaskService.singleUserFlush(userId);
            }
        }
        return result;
    }

    @Override
    public Page<UserFrozenLog> findUserFrozenLogByPage(UserFrozenLog log,Page page) {
        page.setAaData(frozenLogMapper.selectByPage(log,page));
        return page;
    }

    @Override
    public int saveUserFrozenLog(UserFrozenLog log) {
        return frozenLogMapper.insert(log);
    }

    /**
     * 根据用户编号查询用户信息
     * @param userId
     * @return user
     * */
    @Override
    public User findUserById(Integer userId) {
        return userMapper.findByUserId(userId);
    }

    @Override
    public List<User> findByCustomerId(Integer customerId) {
        return userMapper.findByCustomerId(customerId);
    }

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return user
     * */
    @Override
    public User findUserByUserName(String userName) {
        return userMapper.verify(userName);
    }

    /**
     * @param idOrigin 主动发起关联行为的账号ID
     * @param passiveId 被动ID
     * @return
     * */
    @Override
    public boolean updateRelatedId(Integer idOrigin,Integer passiveId) {
        List<User> list = userMapper.findRelatedUser(idOrigin);
        String uuidStr = UUID.randomUUID().toString();
        boolean bool =false;
        if (list.size()==0){
            bool = userMapper.updateUser(new User(idOrigin,uuidStr))&&userMapper.updateUser(new User(passiveId,uuidStr));
        }else {
            String uuid = list.get(0).getRelatedUuid();
            bool = userMapper.updateUser(new User(passiveId,uuid));
        }
        return bool;
    }

    /**
     * 根据用户编号查询关联用户集合
     * @param userId
     * @return List<User>
     * */
    @Override
    public List<User> findRelatedUsers(Integer userId) {
        return userMapper.findRelatedUser(userId);
    }

    /**
     * 根据用户状态筛选用户
     * @param users 待筛选的List集合
     * @param userStatus 需要除掉的状态
     * */
    @Override
    public List<User> removeUserByStatus(List<User> users, UserStatus userStatus) {
        if (users.size()<=0||userStatus==null) return null;
        Iterator<User> userIterator = users.iterator();
        while (userIterator.hasNext()){
            User user = userIterator.next();
            if (user!=null && user.getStatus()!=null && StringUtils.equals(userStatus.getCode(),user.getStatus())){
                userIterator.remove();
            }
        }
        return users;
    }

    /**
     * 取消关联用户
     * @param userId
     * @return
     * */
    @Override
    public boolean unLink(Integer userId) {
        return userMapper.unLinkUser(userId);
    }

    /**
     * 分页查询用户信息
     * @param page
     * @param params
     * @return page
     * */
    @Override
    public Page findUserByPage(Page page,UserPageParams params) {
        page.setAaData(userMapper.findAllUsersByPage(page,params));
        return page;
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     * */
    @Override
    public boolean updateUser(User user) {
        if (StringUtils.isNoneEmpty(user.getPassword())){
            user.setPassword(MD5.MD5Encrypt(user.getPassword()));
        }
        boolean flag = userMapper.updateUser(user);
        // 风控大盘信息更新(如果用户已经停用,则删除该用户在风控容器的数据)
        if(flag){
            if(!UserStatus.Logout.getCode().equals(user.getStatus())){
                riskTaskService.singleUserFlush(user.getId());
            }else{
                riskTaskService.delUserFromContainer(user.getId());
            }
        }
        return flag;
    }

    /**
     * 更新用户总金额和可用头寸
     * @param userId
     * @param amount
     * @param available
     * @return
     * */
    @Override
    @MoneyAdjustLogAnnocation(tag="用户余额调整")
    public boolean updateAmountAndAvailable(Integer userId, BigDecimal amount, BigDecimal available, String changeCause) {
        Flow flow = new Flow();
        flow.setUserId(userId);
        if (amount.compareTo(BigDecimal.ZERO)==1){
            flow.setType(TradeFowType.CASH_ADD);
            flow.setNotes(TradeFlowNote.CASH_ADDCREATEHAND);
        }else {
            flow.setType(TradeFowType.CASH_REDUCE);
            flow.setNotes(TradeFlowNote.CASH_REDUCEBYHAND);
        }
        User user = findUserById(userId);
        flow.setCustomerId(user.getCustomer().getId());
        flow.setAdjustAmount(amount);
        flow.setAdjustQuantity(BigDecimal.ZERO);
        flow.setSecurityCode("CNY");
        flow.setChangeCause(changeCause);
        flowService.saveFlow(flow);
        boolean flag = userMapper.updateAvailableAndTotalAmount(userId, available, amount);
        // 风控大盘信息更新
        if(flag && !"2".equals(user.getStatus())){
            riskTaskService.singleUserFlush(userId);
        }
        return flag;
    }

    /**
     * 更新用户状态
     * @param userId
     * @param status
     * @return
     * */
    @Override
    public boolean updateUserStatus(Integer userId, String status) {
        return userMapper.updateUserStatus(userId,status);
    }

    /**
     * 更新用户登陆状态
     * @param userId
     * @return
     * */
    @Override
    public boolean updateLoginCount(Integer userId) {
        return userMapper.updateLoginCount(userId);
    }

    /**
     * 验证增加的用户名是否重复
     * @param userName
     * @return
     * */
    @Override
    public boolean verifyUserName(String userName) {
        return userMapper.verify(userName) == null;
    }

    /**
     * 根据用户名和用户密码查询用户信息
     * @param userName
     * @param passWord
     * @return user
     * */
	@Override
	public User findUser(String userName, String passWord) {
		return userMapper.findUser(userName, passWord);
	}

	/**
	 * 查询账户信息
     * @param user
     * @return
	 */
	@Override
	public AccountVO queryUserAccount(User user) {
		//查询持仓信息
		//List<SecurityPositionVO> voList=positionService.findAllPosition(user);
		List<SecurityPositionVO> voList=userPositionService.findAllPosition(user);
		//获取最新的User
		user=findUserById(user.getId());
		
		return convertToAccountVO(user, voList);
	}

    @Override
    public void adjustPosition() {
        userMapper.adjustPosition();
    }

    /**
	 * 转换账户信息对象
	 * @param user
	 * @param voList
	 * @return
	 */
	private AccountVO convertToAccountVO(User user,List<SecurityPositionVO> voList){
		user=findUserById(user.getId());
		//计算总市值，总盈亏
		BigDecimal marketValue=BigDecimal.ZERO;
		BigDecimal profitValue=BigDecimal.ZERO;
		for (SecurityPositionVO securityPositionVO : voList) {
			marketValue=marketValue.add(securityPositionVO.getMarketValue());
			profitValue=profitValue.add(securityPositionVO.getMarketProfit());
		}
		AccountVO vo=new AccountVO();
		vo.setCashAmount(user.getAmount());
		vo.setCashAvailableAmount(user.getAvailable());
		vo.setCashFrozenAmount(user.getFrozen());
		vo.setFinanceAmount(user.getFinanceAmount());
		vo.setMarketValue(marketValue);
		vo.setOpenLine(user.getOpenLine());
		vo.setWarnLine(user.getWarnLine());
		vo.setProfitValue(profitValue);
		return vo;
	}
	
	/**
	 * 修改登录次数和最后登录时间
	 * @param user
	 */
	@Override
	public void updateLoginCountAndLastTime(User user){
		userMapper.updateLoginCountAndLastTime(user.getId());
	}

    @Override
    public Page queryTestTrader(Page page) {
        page.setAaData(userMapper.queryTestTrader());
        return page;
    }

    @Override
    @MoneyAdjustLogAnnocation(tag="用户资金调整")
    public boolean updateAvailableAndTotalAmount(Integer id, BigDecimal availableChanged, BigDecimal amountChanged) {
        return userMapper.updateAvailableAndTotalAmount(id, availableChanged, amountChanged);
    }

    @Override
    public List<User> findAllUser(UserPageParams params){
        return userMapper.findAllUsersByPage(null,params);
    }


}
