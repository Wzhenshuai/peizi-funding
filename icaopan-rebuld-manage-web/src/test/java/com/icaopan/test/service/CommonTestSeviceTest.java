package com.icaopan.test.service;


import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.user.service.UserService;


public class CommonTestSeviceTest {

	AdminUserService userService=null;
	@Before
	public void before(){
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring-mybatis.xml","spring-shrio.xml","spring-mvc.xml"});
		userService=(AdminUserService) context.getBean("adminUserService");
	}
	
	@Test
	public void test() {
		AdminUser user=userService.getAdminUserById(1);
		Assert.assertNotNull(user);
	}

}
