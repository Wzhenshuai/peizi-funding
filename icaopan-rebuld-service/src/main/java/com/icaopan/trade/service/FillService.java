package com.icaopan.trade.service;

import com.icaopan.trade.bean.FillDock;
import com.icaopan.trade.bean.FillHistoryParams;
import com.icaopan.trade.bean.FillParams;
import com.icaopan.trade.bean.FillSummaryParams;
import com.icaopan.trade.model.ChannelPlacement;
import com.icaopan.trade.model.Fill;
import com.icaopan.trade.model.FillHistory;
import com.icaopan.trade.model.FillSummary;
import com.icaopan.user.model.User;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.FillSummaryVO;
import com.icaopan.web.vo.FillVO;
import com.icaopan.web.vo.PageBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName FillService
 * @Description (成交业务)
 * @Date 2016年12月7日 上午10:41:26
 */
public interface FillService {
    /**
     * @param record
     * @return
     * @Description (生成当日成交)
     */
    public void saveFill(ChannelPlacement placement, FillDock fillDock);

    public void saveFill(ChannelPlacement placement, String reportNo,
                         BigDecimal quantity, BigDecimal price);

    public Fill getFillById(Integer id);

    /**
     * @param page
     * @param fillParams
     * @return
     * @Description (查询当日成交)
     */
    public Page<Fill> getFillByPage(Page<Fill> page, FillParams fillParams);

    /**
     * @param record
     * @return
     * @Description (生成历史成交)
     */
    public boolean saveFillHistory(FillHistory record);

    /**
     * @param page
     * @param fillHistoryParams
     * @return
     * @Description (查询历史成交)
     */

    public Page<FillHistory> selectFillHistoryByPage(Page<FillHistory> page, FillHistoryParams fillHistoryParams);


    List<Fill> selectFill(FillDock fillDock);

    PageBean<FillVO> queryFillByPage(User user, Integer page, Integer pageSize);

    Page<Fill> selectFillByPage(Page<Fill> page, FillParams fillParams);

    PageBean<FillVO> queryFillHistoryByPage(FillHistoryParams fillHistoryParams, Integer pageNo,
                                            Integer pageSize);

    Page<FillSummary> selectFillSummaryByPage(Page<FillSummary> page, FillSummaryParams fillParams);

    PageBean<FillSummaryVO> queryFillSummaryByPage(FillSummaryParams fillParams, Integer pageNo,
                                                   Integer pageSize);

    /**
     * (删除当日成交)
     *
     * @Description (删除当日成交)
     */
    public boolean delete();

    /**
     * 当日成交生成历史成交
     *
     * @return
     */
    public boolean generateHistory();

    boolean isFillExist(String account, String placementCode, String fillCode);


    //通道当日成交额的汇总
    public List<Fill> selectFillCollection();

	Double selectFillSummaryAmount(FillSummaryParams fillParams);

}
