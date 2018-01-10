package com.icaopan.trade.service.impl;

import com.icaopan.trade.model.Placement;
import com.icaopan.user.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icaopan.trade.bean.ComplianceQueryParams;
import com.icaopan.trade.dao.ComplianceResultMapper;
import com.icaopan.trade.model.ComplianceResult;
import com.icaopan.trade.service.ComplianceService;
import com.icaopan.util.page.Page;
@Service("complianceService")
public class ComplianceServiceImpl implements ComplianceService {

	@Autowired
	private ComplianceResultMapper complianceResultMapper;
	
	
	@Override
	public void saveCompliance(ComplianceResult result) {
		complianceResultMapper.insert(result);
	}

	@Override
	public Page<ComplianceResult> getComplianceResultByPage(
			Page<ComplianceResult> page,ComplianceQueryParams params) {
		 page.setAaData(complianceResultMapper.selectByPage(page,params));
		 return page;
	}

	@Override
	public void saveLog(User user, Placement placement, String opType, String reason){
		try {
			ComplianceResult record=new ComplianceResult();
			record.setOpType(opType);
			record.setStockCode(placement.getSecurityCode());
			record.setQuantity(placement.getQuantity().toString());
			record.setUserName(user.getUserName());
			record.setReason(reason);
			saveCompliance(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
