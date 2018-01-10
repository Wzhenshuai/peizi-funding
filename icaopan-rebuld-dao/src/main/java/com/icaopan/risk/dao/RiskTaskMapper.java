package com.icaopan.risk.dao;


import com.icaopan.risk.bean.RiskMarketVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author kanglj
 * @Description(风控大盘信息查询Mapper类)
 */
@Repository
public interface RiskTaskMapper {

    /**
     * @param
     * @return
     * @Description (风控大盘信息查询)
     */
    List<RiskMarketVO> selectRiskMarketInfo();

    /**
     * @param
     * @return
     * @Description (查询用户头寸表中不存在的用户(补集))
     */
    List<RiskMarketVO> selectPositionComplementary();

    /**
     * @param
     * @return
     * @Description (风控大盘信息单个用户查询查询)
     */
    List<RiskMarketVO> selectRiskMarketInfoByUserId(@Param("userId") Integer userId);

    /**
     * @param
     * @return
     * @Description (查询用户头寸表中不存在的用户(补集))
     */
    RiskMarketVO selectSingleComplementary(@Param("userId") Integer userId);
}