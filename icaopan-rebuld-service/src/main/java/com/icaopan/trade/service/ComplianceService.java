package com.icaopan.trade.service;

import com.icaopan.trade.bean.ComplianceQueryParams;
import com.icaopan.trade.model.ComplianceResult;
import com.icaopan.trade.model.Placement;
import com.icaopan.user.model.User;
import com.icaopan.util.page.Page;

public interface ComplianceService {

	public void saveCompliance(ComplianceResult result);
	
	public Page<ComplianceResult> getComplianceResultByPage(Page<ComplianceResult> page,ComplianceQueryParams params);

	void saveLog(User user, Placement placement, String opType, String reason);
}
