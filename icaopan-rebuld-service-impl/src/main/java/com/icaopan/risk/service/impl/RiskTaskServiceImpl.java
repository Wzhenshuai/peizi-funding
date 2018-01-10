package com.icaopan.risk.service.impl;

import com.icaopan.common.util.CollectionGroupUtil;
import com.icaopan.common.util.SecurityUtil;
import com.icaopan.marketdata.market.MarketdataService;
import com.icaopan.risk.bean.RiskCtrlVO;
import com.icaopan.risk.bean.RiskMarketVO;
import com.icaopan.risk.dao.RiskTaskMapper;
import com.icaopan.risk.service.RiskTaskService;
import com.icaopan.risk.util.Container;

import elf.api.marketdata.marketdata.MarketDataSnapshot;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * desc 用户分控大盘数据定时刷入内存任务类
 * <p>
 * Created by kanglj on 17/3/7.
 */
@Service("riskTaskService")
@EnableScheduling
public class RiskTaskServiceImpl implements RiskTaskService {

    private static Logger log = com.icaopan.log.LogUtil.getTaskLogger();
    @Autowired
    private RiskTaskMapper    riskTaskMapper;
    @Autowired
    private MarketdataService marketdataService;

    /**
     * 运行频率:周一至周五 9点至16点 每隔30秒运行一次
     * 用户分控大盘数据定时刷入内存
     * 0/30 * 9-15 ? * MON-FRI
     */
    @Override
    public void scheduledFlush() {
//        log.info("scheduledFlush start...");
        List<RiskMarketVO> data = riskTaskMapper.selectRiskMarketInfo();
        List<RiskMarketVO> result = new ArrayList<>();
        // 现根据股票代码分组(将相同的股票代码合并成一次行情查询),至于根据股票代码分组的损耗和
        // 不分组直接去行情服务器查询损耗,可能会因所有用户购买同一支股票的数据量、
        // 行情服务器获取现价损耗大小等不同而性能不同,待根据数据量以及性能,选择最优情况
        Map<String, List<RiskMarketVO>> securityGroupMapper = CollectionGroupUtil.groupByProperty(data, "securityId", RiskMarketVO.class);
        for (Map.Entry<String, List<RiskMarketVO>> entry : securityGroupMapper.entrySet()) {
            // 将股票ID转换为股票代码
            String securityCode = SecurityUtil.getSecurityCodeById(entry.getKey());
            // 查询股票的现价
            BigDecimal latestPrice = BigDecimal.ZERO;
            MarketDataSnapshot shot = marketdataService.getBySymbol(securityCode);
            if (shot != null) {
                latestPrice = new BigDecimal(String.valueOf(shot.getPrice()));
            }
            // 遍历
            for (RiskMarketVO vo : entry.getValue()) {
                vo.setLatestPrice(latestPrice);
                if (shot != null) {
                    vo.setSecurityName(shot.getStockName());
                    if (shot.isSuspensionFlag()) {
                        vo.setSuspensionFlag(true);
                    }
                }
                // 重新放入容器
                result.add(vo);
            }
        }
        // 合并用户
        Map<Integer, List<RiskMarketVO>> userGroupMapper = CollectionGroupUtil.groupByProperty(result, "userId", RiskMarketVO.class);
        for (Map.Entry<Integer, List<RiskMarketVO>> entry : userGroupMapper.entrySet()) {
            synchronized (Container.container) {
                // 将该用户数据删除掉
                Container.remove(entry.getKey());
                // 合并用户后,放入容器
                Container.container.add(convertToRiskCtrlVO(entry.getValue()));
            }
        }
        synchronized (Container.container) {
            Collections.sort(Container.container);
        }
//        log.info("scheduledFlush end...");
    }

    /**
     * 将用户表和用户头寸表的补集放入内存
     * 运行频率:周一至周五 9点至16点 每隔10分钟运行一次
     * 用户分控大盘数据定时刷入内存
     * 0 0/10 9-15 ? * MON-FRI
     */
    @Override
    public void positionComplementaryFlush() {
        List<RiskMarketVO> data = riskTaskMapper.selectPositionComplementary();
        for (RiskMarketVO vo : data) {
            synchronized (Container.container) {
                // 将该用户数据删除掉
                Container.remove(vo.getUserId());
                // 合并用户后,放入容器
                Container.container.add(convertToRiskCtrlVO(vo));
            }
        }
        synchronized (Container.container) {
            Collections.sort(Container.container);
        }
    }

    /**
     * 单个用户大盘数据更新
     */
    public void singleUserFlush(Integer userId) {
        List<RiskMarketVO> data = riskTaskMapper.selectRiskMarketInfoByUserId(userId);
        RiskCtrlVO result = null;
        // 如果用户头寸表中存在该用户
        if (data.size() > 0) {
            for (RiskMarketVO item : data) {
                // 将股票ID转换为股票代码
                String securityCode = SecurityUtil.getSecurityCodeById(item.getSecurityId());
                // 查询股票的现价
                BigDecimal latestPrice = BigDecimal.ZERO;
                MarketDataSnapshot shot = marketdataService.getBySymbol(securityCode);
                if (shot != null) {
                    latestPrice = new BigDecimal(String.valueOf(shot.getPrice()));
                }
                item.setLatestPrice(latestPrice);
                if (shot != null) {
                    item.setSecurityName(shot.getStockName());
                    if (shot.isSuspensionFlag()) {
                        item.setSuspensionFlag(true);
                    }
                }
            }
            result = convertToRiskCtrlVO(data);
        }
        // 从用户表中查询
        else {
            RiskMarketVO rmVO = riskTaskMapper.selectSingleComplementary(userId);
            if(rmVO != null){
                result = convertToRiskCtrlVO(rmVO);
            }
        }
        if(result != null){
            synchronized (Container.container) {
                // 将该用户数据删除掉
                Container.remove(userId);
                // 合并用户后,放入容器
                Container.container.add(result);
                Collections.sort(Container.container);
            }
        }
        
    }

    public void delUserFromContainer(Integer userId){
        synchronized (Container.container) {
            // 将该用户数据删除掉
            Container.remove(userId);
        }
    }

    private RiskCtrlVO convertToRiskCtrlVO(List<RiskMarketVO> voList) {
        //计算总市值，总盈亏
        BigDecimal marketValue = BigDecimal.ZERO;
        BigDecimal profitValue = BigDecimal.ZERO;
        RiskCtrlVO riskCtrlVO = new RiskCtrlVO();
        for (int i = 0; i < voList.size(); i++) {
            RiskMarketVO riskMarketVO = voList.get(i);
            marketValue = marketValue.add(riskMarketVO.getMarketValue());
            profitValue = profitValue.add(riskMarketVO.getMarketProfit());
            if (!riskCtrlVO.isSuspensionFlag() && riskMarketVO.isSuspensionFlag()) {
                riskCtrlVO.setSuspensionFlag(true);
            }
            if (i == 0) {
                convertVOCommonSegmentA(riskCtrlVO, riskMarketVO);
            }
        }
        convertVOCommonSegmentB(riskCtrlVO, marketValue, profitValue);
        return riskCtrlVO;
    }

    private RiskCtrlVO convertToRiskCtrlVO(RiskMarketVO riskMarketVO) {
        RiskCtrlVO riskCtrlVO = new RiskCtrlVO();
        //总市值，总盈亏
        BigDecimal marketValue = BigDecimal.ZERO;
        BigDecimal profitValue = BigDecimal.ZERO;
        convertVOCommonSegmentA(riskCtrlVO, riskMarketVO);
        convertVOCommonSegmentB(riskCtrlVO, marketValue, profitValue);
        return riskCtrlVO;
    }

    public void convertVOCommonSegmentA(RiskCtrlVO riskCtrlVO, RiskMarketVO riskMarketVO) {
        riskCtrlVO.setUserId(riskMarketVO.getUserId());
        //姓名
        riskCtrlVO.setRealName(riskMarketVO.getRealName());
        //用户名
        riskCtrlVO.setUserName(riskMarketVO.getUserName());
        //现金账户余额
        riskCtrlVO.setCashAmount(riskMarketVO.getCashAmount());
        //股票账户余额
        riskCtrlVO.setAmount(riskMarketVO.getAmount());
        //股票市值
        riskCtrlVO.setMarketValue(riskMarketVO.getMarketValue());
        //冻结金额
        riskCtrlVO.setFrozenAmount(riskMarketVO.getFrozenAmount());
        //警戒线
        riskCtrlVO.setWarnLine(riskMarketVO.getWarnLine());
        //平仓线
        riskCtrlVO.setOpenLine(riskMarketVO.getOpenLine());
        //融资金额
        riskCtrlVO.setFinanceAmount(riskMarketVO.getFinanceAmount());
        //公司ID
        riskCtrlVO.setCustomerId(riskMarketVO.getCustomerId());
        //资金方
        riskCtrlVO.setCustomerName(riskMarketVO.getCustomerName());
    }

    public void convertVOCommonSegmentB(RiskCtrlVO riskCtrlVO, BigDecimal marketValue, BigDecimal profitValue) {
        riskCtrlVO.setMarketValue(marketValue);
        riskCtrlVO.setProfitValue(profitValue);
        //剩余本金
        if (riskCtrlVO.getTotalAmount() != null) {
            if (riskCtrlVO.getWarnLine() != null) {
                //距离警戒线金额
                riskCtrlVO.setToWarnLine(riskCtrlVO.getTotalAmount().subtract(riskCtrlVO.getWarnLine()));
            }
            if (riskCtrlVO.getOpenLine() != null) {
                //距离平仓线金额
                riskCtrlVO.setToOpenLine(riskCtrlVO.getTotalAmount().subtract(riskCtrlVO.getOpenLine()));
            }
        }
//        riskCtrlVO.setLeftAmount(riskCtrlVO.getTotalAmount().subtract(riskCtrlVO.getFinanceAmount()));
    }


}
