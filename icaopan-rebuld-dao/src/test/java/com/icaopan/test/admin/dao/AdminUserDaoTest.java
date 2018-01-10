package com.icaopan.test.admin.dao;

import com.icaopan.admin.bean.AdminUserParams;
import com.icaopan.admin.dao.AdminUserMapper;
import com.icaopan.admin.model.AdminUser;
import com.icaopan.test.common.dao.BaseTestDao;
import com.icaopan.util.page.Page;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.testng.annotations.Test;

import java.util.List;

public class AdminUserDaoTest extends BaseTestDao {

    @SpringBeanByType
    private AdminUserMapper adminUserMapper;

    @Test
    @DbFit(when = { "wiki/admin/testcase.admin_user.when.wiki" }, then = { "wiki/admin/testcase.admin_user.then.wiki" })
    public void testInsert() {
        AdminUser user = new AdminUser();
        user.setId(1);
        user.setEmail("lisi@email.com");
        user.setPassWord("lisi");
        user.setUserName("lisi");
        adminUserMapper.insert(user);
    }

    @Test
    @DbFit(when = { "wiki/admin/testcase.admin_user.when.wiki" }, then = { "wiki/admin/testcase.admin_user.update.then.wiki" })
    public void testUpdate() {
        AdminUser user = new AdminUser();
        user.setId(999);
        user.setQq("254983885");
        adminUserMapper.updateByPrimaryKey(user);
    }

    @Test
    @DbFit(when = { "wiki/admin/testcase.admin_user.when.wiki" })
    public void testSelect() {
        List<AdminUser> list = adminUserMapper.selectUserByPage(new Page(), new AdminUserParams());
        org.testng.Assert.assertTrue(list.size() > 0);
    }

    @Test
    @DbFit(when = { "wiki/admin/testcase.admin_user.when.wiki" }, then = { "wiki/admin/testcase.admin_user.delete.then.wiki" })
    public void testDelete() {
        adminUserMapper.deleteByPrimaryKey(999);
    }
}

