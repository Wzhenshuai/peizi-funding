package com.icaopan.test.user.service;

import com.icaopan.test.common.service.BaseTestService;
import com.icaopan.user.bean.UserPageParams;
import com.icaopan.user.model.User;
import com.icaopan.user.service.UserService;
import com.icaopan.util.page.Page;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;


/**
 * Created by RoyLeong @royleo.xyz on 2016/12/27.
 */
public class UserServiceTest extends BaseTestService {

    @SpringBeanByType
    private UserService userService;


}
