package com.icaopan.test.admin.dao;

import com.icaopan.admin.dao.AdminMenuMapper;
import com.icaopan.admin.model.AdminMenu;
import com.icaopan.test.common.dao.BaseTestDao;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.testng.annotations.Test;

import java.util.List;

public class AdminMenuDaoTest extends BaseTestDao {

    @SpringBeanByType
    private AdminMenuMapper adminMenuMapper;

    @Test
    @DbFit(when = { "wiki/admin/testcase.admin_menu.when.wiki" }, then = { "wiki/admin/testcase.admin_menu.insert.then.wiki" })
    public void testInsert() {
        AdminMenu menu = new AdminMenu();
        menu.setId(2);
        menu.setMenuParent("0");
        menu.setMenuCode("user_manage2");
        menu.setMenuName("用户管理2");
        menu.setMenuUrl("/userFind2");
        menu.setMenuClazz("0");
        menu.setMenuOrder("0");
        menu.setMenuHidden(1);
        adminMenuMapper.insert(menu);
    }

    @Test
    @DbFit(when = { "wiki/admin/testcase.admin_menu.when.wiki" }, then = { "wiki/admin/testcase.admin_menu.update.then.wiki" })
    public void testUpdate() {
        AdminMenu menu = new AdminMenu();
        menu.setId(1);
        menu.setMenuParent("0");
        menu.setMenuCode("user_manage2");
        menu.setMenuName("用户管理2");
        menu.setMenuUrl("/userFind2");
        menu.setMenuClazz("0");
        menu.setMenuOrder("0");
        menu.setMenuHidden(1);
        adminMenuMapper.updateByPrimaryKey(menu);
    }

    @Test
    @DbFit(when = { "wiki/admin/testcase.admin_menu.when.wiki" })
    public void testSelect() {
        List<AdminMenu> list = adminMenuMapper.selectAllMenu();
        org.testng.Assert.assertTrue(list.size() > 0);
    }

    @Test
    @DbFit(when = { "wiki/admin/testcase.admin_menu.when.wiki" }, then = { "wiki/admin/testcase.admin_menu.delete.then.wiki" })
    public void testDelete() {
        adminMenuMapper.deleteByPrimaryKey(1);
    }
}
