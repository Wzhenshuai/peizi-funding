package com.icaopan.sys.util;

import com.icaopan.admin.model.AdminUser;
import com.icaopan.admin.realm.LoginRealm;
import com.icaopan.admin.service.AdminUserService;
import com.icaopan.admin.util.SpringContextHolder;
import com.icaopan.customer.bean.CustomerParams;
import com.icaopan.customer.dao.CustomerMapper;
import com.icaopan.customer.model.Customer;

import java.util.List;

/**
 * 获取客户信息的公用类
 * Created by RoyLeong @royleo.xyz on 2016/12/15.
 */
public class CustomerUtils {

    public static final CustomerMapper customerDAO = SpringContextHolder.getBean(CustomerMapper.class);

    public static final AdminUserService adminUserService = SpringContextHolder.getBean(AdminUserService.class);

    public static String getCustomers(){
        CustomerParams params = new CustomerParams();
        AdminUser adminUser = LoginRealm.getCurrentUser();
        if (!adminUserService.isSuperAdmin(adminUser)){
            params.setId(adminUser.getCustomerId());
        }
        StringBuffer sb = new StringBuffer();
        List<Customer> list = customerDAO.selectCustomerByPage(null,params);
        for (Customer customer :list){
            sb.append(String.format("<option value='%s'>%s</option>", customer.getId(),customer.getName()));
        }
        return sb.toString();
    }

}
