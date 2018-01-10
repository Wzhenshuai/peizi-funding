package com.icaopan.test.sys.dao;

import com.icaopan.sys.dao.DictMapper;
import com.icaopan.sys.model.Dict;
import com.icaopan.test.common.dao.BaseTestDao;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.testng.annotations.Test;

import java.util.List;

public class DictDaoTest extends BaseTestDao {

    @SpringBeanByType
    private DictMapper dictMapper;

    @Test
    @DbFit(when = { "wiki/sys/testcase.dict.when.wiki" }, then = { "wiki/sys/testcase.dict.insert.then.wiki" })
    public void testInsert() {
        Dict dict = new Dict();
        dict.setId(2);
        dict.setValue("已成");
        dict.setLabel("委托状态");
        dict.setType("placement_type");
        dict.setDescription("委托状态");
        dict.setDelFlag(true);
        dict.setSort(0);
        dictMapper.insert(dict);
    }

    @Test
    @DbFit(when = { "wiki/sys/testcase.dict.when.wiki" }, then = { "wiki/sys/testcase.dict.update.then.wiki" })
    public void testUpdate() {
        Dict dict = new Dict();
        dict.setId(1);
        dict.setValue("已成");
        dictMapper.update(dict);
    }

    @Test
    @DbFit(when = { "wiki/sys/testcase.dict.when.wiki" })
    public void testSelect() {
        Dict dict = new Dict();
        dict.setType("placement_type");
        List<Dict> list = dictMapper.findList(dict);
        org.testng.Assert.assertTrue(list.size() > 0);
    }

    @Test
    @DbFit(when = { "wiki/sys/testcase.dict.when.wiki" }, then = { "wiki/sys/testcase.dict.delete.then.wiki" })
    public void testDelete() {
        dictMapper.delete(1);
    }
}
