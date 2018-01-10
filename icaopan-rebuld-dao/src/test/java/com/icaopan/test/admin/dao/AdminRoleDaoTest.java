package com.icaopan.test.admin.dao;

import com.icaopan.admin.bean.AdminRoleParams;
import com.icaopan.admin.dao.AdminRoleMapper;
import com.icaopan.admin.model.AdminRole;
import com.icaopan.test.common.dao.BaseTestDao;
import com.icaopan.util.page.Page;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.testng.annotations.Test;

import java.util.List;

public class AdminRoleDaoTest extends BaseTestDao {

    @SpringBeanByType
    private AdminRoleMapper adminRoleMapper;

    @Test
    @DbFit(when = { "wiki/admin/testcase.admin_role.when.wiki" }, then = { "wiki/admin/testcase.admin_role.insert.then.wiki" })
    public void testInsert() {
        AdminRole role = new AdminRole();
        role.setId(2);
        role.setRolename("zhangsan");
        role.setRoledesc("zhangsan");
        adminRoleMapper.insert(role);
    }

    @Test
    @DbFit(when = { "wiki/admin/testcase.admin_role.when.wiki" }, then = { "wiki/admin/testcase.admin_role.update.then.wiki" })
    public void testUpdate() {
        AdminRole role = new AdminRole();
        role.setId(1);
        role.setRolename("lisi");
        role.setRoledesc("lisi");
        adminRoleMapper.updateByPrimaryKey(role);
    }

    @Test
    @DbFit(when = { "wiki/admin/testcase.admin_role.when.wiki" })
    public void testSelect() {
        AdminRoleParams params = new AdminRoleParams("");
        params.setRoleName("%员%");
        List<AdminRole> list = adminRoleMapper.findAdminRoles(new Page(), params);
        org.testng.Assert.assertTrue(list.size() > 0);
    }

    @Test
    @DbFit(when = { "wiki/admin/testcase.admin_role.when.wiki" }, then = { "wiki/admin/testcase.admin_role.delete.then.wiki" })
    public void testDelete() {
        adminRoleMapper.deleteByPrimaryKey(1);
    }
}
