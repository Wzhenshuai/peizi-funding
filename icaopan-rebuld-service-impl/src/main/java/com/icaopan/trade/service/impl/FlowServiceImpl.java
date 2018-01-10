package com.icaopan.trade.service.impl;

import com.icaopan.enums.enumBean.TradeFowType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icaopan.log.LogUtil;
import com.icaopan.stock.model.StockSecurity;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.trade.bean.FlowParams;
import com.icaopan.trade.dao.FlowMapper;
import com.icaopan.trade.model.Flow;
import com.icaopan.trade.service.FlowService;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.FlowVO;
import com.icaopan.web.vo.PageBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName FlowServiceImpl
 * @Description (这里用一句话描述这个类的作用)
 * @author Wangzs
 * @Date 2016年12月7日 上午11:08:21
 * @version 1.0.0
 */
@Service("flowService")
public class FlowServiceImpl  implements FlowService {

    private Logger logger=LogUtil.getLogger(getClass());

	@Autowired
	private SecurityService securityService;
	
    @Autowired FlowMapper flowMapper;
    @Override
    public boolean saveFlow(Flow flow) {
        if (flow.getCreateTime() == null) {
            flow.setCreateTime(new Date());
        }
        if(StringUtils.isEmpty(flow.getIsHidden())){
        	flow.setIsHidden("0");
        }
        return flowMapper.insert(flow);
    }

    @Override
    public Page getFundFlowByPage(Page page, FlowParams flowParams) {
        page.setAaData(flowMapper.selectFundFlowByPage(page, flowParams));
        return page;
    }

    @Override
    public boolean saveFlowList(List<Flow> flowList) {
        return flowMapper.insertList(flowList);
    }

    @Override
    public Page getTradeFlowByPage(Page page, FlowParams flowParams) {
        if (page==null){
            Page page1 = new Page();
            page1.setAaData(flowMapper.selectTradeFlowByPage(null, flowParams));
            page=page1;
        }else {
            page.setAaData(flowMapper.selectTradeFlowByPage(page, flowParams));
        }
        return page;
    }

    /**
     * 前台获取交割单需要区分是否可见字段
     */
    @Override
    public PageBean<FlowVO> queryTradeFlowByPage(
    		FlowParams flowParams, Integer pageNo, Integer pageSize) {
        Page page=new Page(pageNo, pageSize);
        //查询参数
        flowParams.setIsHidden("0");
        page= getTradeFlowByPage(page, flowParams);
        //转换page->pageBean
        List<FlowVO> voList=convertToFlowVO(page.getAaData());
        PageBean<FlowVO> pageBean=new PageBean<FlowVO>(pageNo, pageSize, page.getiTotalRecords(),voList);
        return pageBean;
    }
    
    private List<FlowVO> convertToFlowVO(List<Flow> list){
        List<FlowVO> voList=new ArrayList<FlowVO>();
        for (Flow flow : list) {
            if (StringUtils.equals(flow.getType().getCode(), TradeFowType.COST_PRICE_ADJUST.getCode())) continue;   //前台过滤 成本价调整 的记录 @Royx
            String securityName="";
            String securityCode=flow.getSecurityCode();
            if(StringUtils.isNotBlank(securityCode)) {
                StockSecurity stock = securityService.findByNameAndCode(null, securityCode);
                if (stock == null) {
                    logger.error("证券信息不存在");
                } else {
                    securityName = stock.getName();
                }
            }
            //组装VO对象
            FlowVO vo=new FlowVO();
            vo.setFlowId(flow.getId());
            vo.setAmount(flow.getAdjustAmount());
            vo.setBusinessTypeName(flow.getType().getDisplay());
            vo.setCommission(flow.getCommissionFee());
            vo.setExecutionTime(flow.getCreateTime());
            vo.setQuantity(flow.getAdjustQuantity());
            vo.setSecurityShortName(securityName);
            vo.setLocalCode(securityCode);
            vo.setStampDuty(flow.getStampDutyFee());
            vo.setTransferFee(flow.getTransferFee());
            voList.add(vo);
        }
        return voList;
    }
    
    
}
