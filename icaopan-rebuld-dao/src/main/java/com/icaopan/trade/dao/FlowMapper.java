package com.icaopan.trade.dao;

import com.icaopan.trade.bean.FlowParams;
import com.icaopan.trade.model.Flow;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName FlowMapper
 * @Description (用户 资金流水)
 * @Date 2016年11月29日 下午3:02:30
 */
public interface FlowMapper {
    /**
     * @param record
     * @return
     * @Description ()插入流水信息
     */
    boolean insert(Flow record);

    /**
     * @param page
     * @param flowParams
     * @return
     * @Description (查询资金流水信息 记录)
     */
    List<Flow> selectFundFlowByPage(@Param("page") Page page, @Param("params") FlowParams flowParams);

    /**
     * @param page
     * @param flowParams
     * @return
     * @Description (查询交易流水信息 记录)
     */
    List<Flow> selectTradeFlowByPage(@Param("page") Page page, @Param("params") FlowParams flowParams);

    boolean insertList(@Param("list") List<Flow> list);

}

