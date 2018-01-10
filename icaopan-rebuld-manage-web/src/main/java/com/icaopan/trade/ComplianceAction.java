package com.icaopan.trade;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.realm.LoginRealm;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.trade.bean.ComplianceQueryParams;
import com.icaopan.trade.model.ComplianceResult;
import com.icaopan.trade.service.ComplianceService;
import com.icaopan.trade.service.impl.ComplianceServiceImpl;
import com.icaopan.util.page.Page;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("compliance")
public class ComplianceAction {

	@Autowired
	private  ComplianceService complianceService;
	@Autowired
	private AdminUserService adminUserService;
	
	@RequestMapping("/complianceJsp")
	public String complianceJsp(HttpServletRequest request){
        AdminUser adminUser=LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(adminUser)){
            request.setAttribute("admin", "true");
        }
		return "trade/compliance/complianceResult";
	}
	
	
	@RequestMapping("/findAll")
    @ResponseBody
    @RequiresUser
    public Page<ComplianceResult> findAll(@Param("aoData") String aoData,ComplianceQueryParams params){
        Page<ComplianceResult> page = new Page<ComplianceResult>(aoData);
        AdminUser user = LoginRealm.getCurrentUser();
        if(!adminUserService.isSuperAdmin(user)){
        	params.setCustomerId(user.getCustomerId());
        }
        page = complianceService.getComplianceResultByPage(page,params);
        return page;
    } 
}
