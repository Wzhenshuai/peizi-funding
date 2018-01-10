package com.icaopan.sys.util;

import com.icaopan.admin.bean.AdminUserParams;
import com.icaopan.admin.dao.AdminUserMapper;
import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.realm.LoginRealm;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.admin.util.SpringContextHolder;
import com.icaopan.user.bean.UserPageParams;
import com.icaopan.user.dao.UserMapper;
import com.icaopan.user.model.User;

import java.util.List;

/**
 * 查询所有用户信息的公共类
 * Created by RoyLeong @royleo.xyz on 2016/12/23.
 */
public class UserUtils {

    public static final AdminUserService adminUserService = SpringContextHolder.getBean(AdminUserService.class);
    private static UserMapper userDao = SpringContextHolder.getBean(UserMapper.class);
    private static AdminUserMapper adminUserDao = SpringContextHolder.getBean(AdminUserMapper.class);

    public static String getUsers(){
        StringBuffer sbf = new StringBuffer();
        AdminUser adminUser = LoginRealm.getCurrentUser();
        UserPageParams params = new UserPageParams();
        if (!adminUserService.isSuperAdmin(adminUser)){
            params.setCustomerId(adminUser.getCustomerId());
        }
        List<User> list = userDao.findAllUsersByPage(null,params);
        for (User user : list){
            sbf.append(String.format("<option value='%s'>%s</option>", user.getId(),user.getUserName()));
        }
        return sbf.toString();
    }

    public static String getAdminUsers(){
        StringBuffer sbf = new StringBuffer();
        List<AdminUser> list = adminUserDao.selectUserByPage(null,new AdminUserParams());
        for (AdminUser user : list){
            sbf.append(String.format("<option value='%s'>%s</option>", user.getId(),user.getUserName()));
        }
        return sbf.toString();    }

}
