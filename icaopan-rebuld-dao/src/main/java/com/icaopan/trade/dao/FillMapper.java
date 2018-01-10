package com.icaopan.trade.dao;


import com.icaopan.trade.bean.FillDock;
import com.icaopan.trade.bean.FillParams;
import com.icaopan.trade.model.Fill;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName FillMapper
 * @Description (当日成交)
 * @Date 2016年11月29日 下午3:01:05
 */
public interface FillMapper {
    /**
     * @param record
     * @return
     * @Description (插入当日成交信息)
     */
    boolean insert(Fill record);

    /**
     * @param id
     * @return
     * @Description (根据id查询当日成交)
     */
    Fill selectById(Integer id);

    /**
     * @param page
     * @return
     * @Description (查询当日成交分页)
     */
    List<Fill> selectFillByPage(@Param("page") Page page, @Param("params") FillParams fillParams);

    /**
     * 查询当日成交
     *
     * @param fillDock
     * @return
     */
    List<Fill> selectFill(FillDock fillDock);

    List<Fill> selectFillByPlacementAndFillCode(@Param("account") String account, @Param("placementCode") String placementCode, @Param("fillCode") String fillCode);

    /**
     * 清空当日成交
     */
    public boolean delete();

    /**
     * 根据chanelId 汇总通道的成交额
     */
    public List<Fill> selectFillCollection();

}
