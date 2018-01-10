package com.icaopan.trade.service;

import com.icaopan.enums.enumBean.TradeStatus;
import com.icaopan.enums.enumBean.TransactionType;
import com.icaopan.trade.bean.PlacementHistoryParams;
import com.icaopan.trade.bean.PlacementParams;
import com.icaopan.trade.model.Placement;
import com.icaopan.trade.model.PlacementHistory;
import com.icaopan.user.model.User;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.PageBean;
import com.icaopan.web.vo.PlacementVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName PlacementService
 * @Description (用户委托业务)
 * @Date 2016年12月7日 上午10:36:59
 */
public interface PlacementService {
    /**
     * @param placement
     * @return
     * @Description (插入用户委托)
     * 1、校验
     * 1.1参数信息的校验（价格、数量  格式校验，证券代码的校验-是否存在）
     * 1.2 业务逻辑校验
     * 1.2.1 买入 ：资金头寸的可用是否大于当前委托的金额+佣金（需要计算）
     * 1.2.2 卖出 ：通道证券头寸的可用之和 是否大于当前委托数量
     * 1.3 调用风控接口校验
     * 2、 校验失败 抛异常
     * 3、 校验成功，保存用户委托
     * 4、 更改用户资金头寸
     * 买入 ：减用户的资金可用头寸（当前委托的金额+佣金）
     * 卖出 ：无操作
     * 5、 调用通道委托下单接口
     * 5.1 买入：直接调用通道委托下单接口
     * 5.2 卖出：
     * 5.2.1 （定义卖出 通道选择规则[通道分配数量]）{1、考虑有碎股的情况}
     * 5.2.2 调用5.2.1的规则结果调用通道委托下单接口
     */
    public void placement(Placement placement);

    /**
     * cancelPlacement 撤单操作
     * <p>
     * 1. 校验：判断当前委托状态，如果是已报/部成/正撤状态，则校验通过
     * 2. 校验通过的情况下，
     * 2.1: 修改委托信息状态，改为正撤
     * 2.2: 查找对应的通道委托，调用通道委托撤单接口
     * 3. 校验失败 ： 抛异常
     */
    public void cancelPlacement(Integer placementId);


    /**
     * @return
     * @Description (删除当日的委托)
     */
    public boolean deletePlacementToday();

    public Placement getPlacementById(Integer id);

    /**
     * @param page
     * @param placementParams
     * @return
     * @Description (查询当日委托)
     */
    public Page selectPlacementByPage(Page page, PlacementParams placementParams);

    /**
     * @param tradeStatus
     * @param placementId
     * @return
     * @Description (跟新委托状态)
     */
    public boolean updatePlacementStatus(TradeStatus tradeStatus, Integer placementId);

    /**
     * @param changeQuantity
     * @param changeAmount
     * @param placementId
     * @return
     * @Description (跟新成交数量，成交金额，成交价格)
     */
    public boolean updatePlaceQuantityAndAmount(BigDecimal changeQuantity, BigDecimal changeAmount, Integer placementId);

    /**
     * @param record
     * @return
     * @Description (插入历史委托)
     */
    public boolean insert(PlacementHistory record);

    /**
     * @param page
     * @param placementHistoryParams
     * @return
     * @Description (查询历史委托)
     */
    public Page selectPlacementHistoryByPage(Page page, PlacementHistoryParams placementHistoryParams);

    /**
     * 判断用户委托状态是否是终态
     *
     * @return
     */
    public boolean isPlacementEnd(Placement placement);

    public void fill(Integer placementId, TransactionType transactionType, BigDecimal quantity, BigDecimal price,boolean isForceFill);

    /**
     * 更新废单数量
     */
    public boolean updateInvalidQuantity(BigDecimal invalidQuantity, Integer placementId);

    /**
     * 更新撤单数量
     */
    public boolean updateCancelQuantity(BigDecimal cancelQuantity, Integer placementId);


    public List<PlacementVO> queryCurrentDayPlacementNotEnd(User user);

    PageBean<PlacementVO> queryCurrentDayPlacementByPage(User user, Integer pageNo,
                                                         Integer pageSize);

    PageBean<PlacementVO> queryPlacementHistoryByPage(PlacementHistoryParams params, Integer pageNo,
                                                      Integer pageSize);

    public boolean insertList(List<PlacementHistory> placementHistoryList);

    List<PlacementVO> queryCurrentDayPlacement(User user);

    Page selectCurrentPlacementByPage(Page page, PlacementParams placementParams);

    //下单测试
    public Map<String, String> autoPlacement(String stockCode, BigDecimal quantity, BigDecimal price, Integer[] userIds);

    void doPersonFill(Integer id,BigDecimal fillPrice);
}
