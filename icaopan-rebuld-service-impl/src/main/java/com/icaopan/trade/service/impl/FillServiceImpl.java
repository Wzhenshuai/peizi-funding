package com.icaopan.trade.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icaopan.common.util.DataUtils;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.log.LogUtil;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.trade.bean.FillDock;
import com.icaopan.trade.bean.FillHistoryParams;
import com.icaopan.trade.bean.FillParams;
import com.icaopan.trade.bean.FillSummaryParams;
import com.icaopan.trade.dao.FillHistoryMapper;
import com.icaopan.trade.dao.FillMapper;
import com.icaopan.trade.dao.FillSummaryMapper;
import com.icaopan.trade.model.ChannelPlacement;
import com.icaopan.trade.model.Fill;
import com.icaopan.trade.model.FillHistory;
import com.icaopan.trade.model.FillSummary;
import com.icaopan.trade.service.FillService;
import com.icaopan.user.model.User;
import com.icaopan.util.DateUtils;
import com.icaopan.util.page.Page;
import com.icaopan.web.vo.FillSummaryVO;
import com.icaopan.web.vo.FillVO;
import com.icaopan.web.vo.PageBean;

@Service("fillService")
public class FillServiceImpl implements FillService {

	private Logger logger=LogUtil.getLogger(getClass());
    @Autowired 
    private FillMapper fillMapper;
    
    @Autowired 
    private FillHistoryMapper fillHistoryMapper;
    @Autowired
    private FillSummaryMapper fillSummaryMapper;
    
    @Autowired
    private SecurityService securityService;
    
    @Override
    public void saveFill(ChannelPlacement placement,FillDock fillDock) {
    	Fill record=new Fill();
    	record.setAccount(placement.getAccount());
    	record.setFillCode(fillDock.getReportSno());
    	record.setFillTime(new Date());
    	record.setPlacementCode(fillDock.getExecutionSno());
    	record.setPrice(fillDock.getPrice());
    	record.setQuantity(fillDock.getQuantity());
    	record.setSecurityCode(placement.getSecurityCode());
    	record.setSide(placement.getSide());
    	record.setAmount(fillDock.getQuantity().multiply(fillDock.getPrice()));
    	record.setChannelId(placement.getChannelId());
    	record.setChannelPlacementId(placement.getId());
    	record.setCustomerId(placement.getCustomerId());
    	record.setPlacementId(placement.getPlacementId());
    	record.setUserId(placement.getUserId());
        fillMapper.insert(record);
    }

    @Override
    public void saveFill(ChannelPlacement placement,String reportNo,BigDecimal quantity,BigDecimal price) {
    	//插入新成交记录
    	Fill record=new Fill();
    	record.setAccount(placement.getAccount());
    	record.setFillCode(reportNo);
    	record.setFillTime(new Date());
    	record.setPlacementCode(placement.getPlacementCode());
    	record.setPrice(price);
    	record.setQuantity(quantity);
    	record.setSecurityCode(placement.getSecurityCode());
    	record.setSide(placement.getSide());
    	record.setAmount(quantity.multiply(price));
    	record.setChannelId(placement.getChannelId());
    	record.setChannelPlacementId(placement.getId());
    	record.setCustomerId(placement.getCustomerId());
    	record.setPlacementId(placement.getPlacementId());
    	record.setUserId(placement.getUserId());
        fillMapper.insert(record);
    }
    
    @Override
    public Fill getFillById(Integer id) {
        return null;
    }

    @Override
    public Page<Fill> getFillByPage(Page<Fill> page, FillParams fillParams) {
		if (page==null){
			Page page1 = new Page();
			page1.setAaData(fillMapper.selectFillByPage(null,fillParams));
			page = page1;
		}else {
			page.setAaData(fillMapper.selectFillByPage(page, fillParams));
		}
        return page;
    }

    @Override
    public boolean saveFillHistory(FillHistory record) {
        return fillHistoryMapper.insert(record);
    }

    @Override
    public Page<FillHistory> selectFillHistoryByPage(Page<FillHistory> page, FillHistoryParams fillHistoryParams) {
		if (page==null){
			page = new Page<FillHistory>();
			page.setAaData(fillHistoryMapper.selectByPage(null,fillHistoryParams));
		}else {
			page.setAaData(fillHistoryMapper.selectByPage(page, fillHistoryParams));
		}
        return page;
    }

    @Override
    public Page<FillSummary> selectFillSummaryByPage(Page<FillSummary> page,
    		FillSummaryParams fillParams) {
    	if(StringUtils.isNotEmpty(fillParams.getEndTime())){
			try {
				Date _endDate=new SimpleDateFormat("yyyy-MM-dd").parse(fillParams.getEndTime());
				Date __endDate=DateUtils.addDays(_endDate, 1);
				String endDateStr=new SimpleDateFormat("yyyy-MM-dd").format(__endDate);
				fillParams.setEndTime(endDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
    	if (page==null){
			page = new Page<FillSummary>();
			page.setAaData(fillSummaryMapper.selectFillSummaryByPage(page, fillParams));
		}else {
			page.setAaData(fillSummaryMapper.selectFillSummaryByPage(page, fillParams));
		}
        return page;
    }
    
    @Override
    public Page<Fill> selectFillByPage(Page<Fill> page, FillParams fillParams){
    	page.setAaData(fillMapper.selectFillByPage(page, fillParams));
        return page;
    }

	@Override
	public List<Fill> selectFill(FillDock fillDock) {
		return fillMapper.selectFill(fillDock);
	}
	
	/**
	 * 网站前台分页查询用户当日成交
	 */
	@Override
	public PageBean<FillVO> queryFillByPage(User user,Integer pageNo,Integer pageSize){
		Page<Fill> page=new Page<Fill>(pageNo, pageSize);
    	//查询参数
    	FillParams params=new FillParams();
    	params.setUserId(user.getId());
    	page= selectFillByPage(page, params);
    	//转换page->pageBean
    	List<FillVO> voList=convertToFillVO(page.getAaData());
    	PageBean<FillVO> pageBean=new PageBean<FillVO>(pageNo, pageSize, page.getiTotalRecords(),voList);
    	return pageBean;
	}
	
	/**
	 * 网站前台分页查询用户当日成交
	 */
	@Override
	public PageBean<FillVO> queryFillHistoryByPage(FillHistoryParams fillHistoryParams,Integer pageNo,Integer pageSize){
		Page<FillHistory> page=new Page<FillHistory>(pageNo, pageSize);
    	page= selectFillHistoryByPage(page, fillHistoryParams);
    	//转换page->pageBean
    	List<FillVO> voList=convertToFillHistoryVO(page.getAaData());
    	PageBean<FillVO> pageBean=new PageBean<FillVO>(pageNo, pageSize, page.getiTotalRecords(),voList);
    	return pageBean;
	}

	/**
	 * 成交汇总查询
	 */
	@Override
	public PageBean<FillSummaryVO> queryFillSummaryByPage(
			FillSummaryParams fillParams, Integer pageNo, Integer pageSize) {
		Page<FillSummary> page=new Page<FillSummary>(pageNo, pageSize);
    	page= selectFillSummaryByPage(page, fillParams);
    	//转换page->pageBean
    	List<FillSummaryVO> voList=convertToFillSummaryVO(page.getAaData());
    	PageBean<FillSummaryVO> pageBean=new PageBean<FillSummaryVO>(pageNo, pageSize, page.getiTotalRecords(),voList);
    	return pageBean;
	}
	
	private List<FillVO> convertToFillVO(List<Fill> fillList){
		List<FillVO> voList=new  ArrayList<FillVO>();
		for (Fill fill : fillList) {
			FillVO vo=new FillVO();
			vo.setSecurityCode(fill.getSecurityCode());
			vo.setSecurityName(securityService.queryNameByCode(fill.getSecurityCode()));
			vo.setFilledPrice(fill.getPrice());
			vo.setFilledQty(fill.getQuantity());
			vo.setFilledAmount(fill.getAmount());
			vo.setFilledTime(fill.getFillTime());
			if(fill.getSide()!=null){
				vo.setTradeTypeDisplay(fill.getSide().getDisplay());
			}else{
				vo.setTradeTypeDisplay("");
			}
			voList.add(vo);
		}
		return voList;
	}
	
	@SuppressWarnings("unused")
	private List<FillVO> convertToFillHistoryVO(List<FillHistory> fillList){
		List<FillVO> voList=new  ArrayList<FillVO>();
		for (FillHistory fill : fillList) {
			FillVO vo=new FillVO();
			vo.setSecurityCode(fill.getSecurityCode());
			vo.setSecurityName(securityService.queryNameByCode(fill.getSecurityCode()));
			vo.setFilledPrice(fill.getPrice());
			vo.setFilledQty(fill.getQuantity());
			vo.setFilledAmount(fill.getAmount());
			vo.setFilledTime(fill.getFillTime());
			if(fill.getSide()!=null){
				vo.setTradeTypeDisplay(fill.getSide().getDisplay());
			}else{
				vo.setTradeTypeDisplay("");
			}
			voList.add(vo);
		}
		return voList;
	}


	private List<FillSummaryVO> convertToFillSummaryVO(List<FillSummary> fillList){
		List<FillSummaryVO> voList=new  ArrayList<FillSummaryVO>();
		for (FillSummary fill : fillList) {
			FillSummaryVO vo=new FillSummaryVO();
			vo.setSecurityCode(fill.getSecurityCode());
			vo.setSecurityName(securityService.queryNameByCode(fill.getSecurityCode()));
			vo.setFilledPrice(fill.getPrice());
			vo.setFilledQty(fill.getQuantity());
			vo.setFilledAmount(fill.getAmount());
			vo.setFilledTimes(fill.getFillTimes());
			if(fill.getSide()!=null){
				vo.setTradeTypeDisplay(fill.getSide());
			}else{
				vo.setTradeTypeDisplay("");
			}
			voList.add(vo);
		}
		return voList;
	}
	
	@Override
	public boolean delete() {
		return fillMapper.delete();
	}

	@Override
	public boolean generateHistory() {
		return fillHistoryMapper.generateHistory();
	}

	/**
	 * 判断成交是否对接过
	 */
	@Override
	public boolean isFillExist(String account,String placementCode,String fillCode){
		List<Fill> list=fillMapper.selectFillByPlacementAndFillCode(account, placementCode, fillCode);
		if(list!=null&&list.size()>0){
			return true;
		}
		return false;
	}

	@Override
	public List<Fill> selectFillCollection() {
		return fillMapper.selectFillCollection();
	}
	
	@Override
	public Double selectFillSummaryAmount(FillSummaryParams fillParams){
		if(StringUtils.isNotEmpty(fillParams.getEndTime())){
			try {
				Date _endDate=new SimpleDateFormat("yyyy-MM-dd").parse(fillParams.getEndTime());
				Date __endDate=DateUtils.addDays(_endDate, 1);
				String endDateStr=new SimpleDateFormat("yyyy-MM-dd").format(__endDate);
				fillParams.setEndTime(endDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Double b= fillSummaryMapper.selectFillSummaryAmount(fillParams);
		if(b==null){
			return 0d;
		}
		return b;
	}

}
