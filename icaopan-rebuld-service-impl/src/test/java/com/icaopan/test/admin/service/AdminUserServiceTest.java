package com.icaopan.test.admin.service;

import org.jtester.annotations.SpringBeanByType;
import org.testng.annotations.Test;

import com.icaopan.admin.dao.AdminUserMapper;
import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.admin.service.impl.AdminUserServiceImpl;
import com.icaopan.test.common.service.BaseTestService;

public class AdminUserServiceTest extends BaseTestService {
	@SpringBeanByType
	private AdminUserService adminUserService;
	@Test
	public void testSave(){
//		AdminUser user=new AdminUser();
//		user.setUserName("zzz");
//		user.setPassWord("zzz");
//		adminUserService.saveAdminUser(user);
	}
}
