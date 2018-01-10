package com.icaopan.trade.service;

import com.icaopan.trade.bean.FlowParams;
import com.icaopan.trade.model.Flow;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.FlowVO;
import com.icaopan.web.vo.PageBean;

import java.util.List;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName FlowService
 * @Description (用户流水)
 * @Date 2016年12月7日 上午10:40:08
 */
public interface FlowService {
    /**
     * @param flow
     * @return
     * @Description (插入用户流水)
     */
    public boolean saveFlow(Flow flow);

    /**
     * @param page
     * @param flowParams
     * @return
     * @Description (查询交易用户流水)
     */
    public Page getTradeFlowByPage(Page page, FlowParams flowParams);

    /**
     * @param page
     * @param flowParams
     * @return
     * @Description (查询用户资金流水)
     */
    public Page getFundFlowByPage(Page page, FlowParams flowParams);

    public boolean saveFlowList(List<Flow> flowList);

    PageBean<FlowVO> queryTradeFlowByPage(FlowParams flowParams,
                                          Integer pageNo, Integer pageSize);

}
