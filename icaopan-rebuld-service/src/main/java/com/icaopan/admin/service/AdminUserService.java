package com.icaopan.admin.service;

import com.icaopan.admin.bean.AdminUserParams;
import com.icaopan.admin.model.AdminUser;
import com.icaopan.util.page.Page;

public interface AdminUserService {

    public AdminUser selectUserByUsernameAndPassword(AdminUser adminUser);

    public Page findUserByPage(Page page, AdminUserParams params);

    public int saveAdminUser(AdminUser adminUser);

    public AdminUser getAdminUserById(int id);

    public void updateAdminUserById(AdminUser adminUser);

    public void deleteAdminUserById(int id);

    public int saveRoleRelatePermission(int id, int[] role_ids);

    public boolean isSuperAdmin(AdminUser adminUser);

    public void updatePassword(AdminUser record);

    public boolean isUserNameExist(String userName);

}
