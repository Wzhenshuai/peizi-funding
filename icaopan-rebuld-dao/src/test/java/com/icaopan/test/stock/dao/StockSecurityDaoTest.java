package com.icaopan.test.stock.dao;

import com.icaopan.stock.bean.StockSecurityParams;
import com.icaopan.stock.dao.StockSecurityMapper;
import com.icaopan.stock.model.StockSecurity;
import com.icaopan.test.common.dao.BaseTestDao;
import com.icaopan.util.page.Page;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2016/11/21.
 */
public class StockSecurityDaoTest extends BaseTestDao {

    @SpringBeanByType
    private StockSecurityMapper stockSecurityMapper;

    /**
     * 保存信息
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_security.when.wiki",
            then = "wiki/stock/testcase.stock_security.insert.then.wiki")
    public void TestInsert() {
        StockSecurity stockSecurity = new StockSecurity();
        stockSecurity.setInternalSecurityId("612309");
        stockSecurity.setExchangeCode("601010");
        stockSecurity.setCode("600767");
        stockSecurity.setName("运盛实业");
        stockSecurity.setModifyTime(new Date());
        stockSecurity.setIssueDate((new Date()));
        stockSecurity.setSuspensionFlag(Boolean.FALSE);
        stockSecurity.setFirstLetter("YSSY");
        stockSecurityMapper.insert(stockSecurity);
    }


    /**
     * 批量保存证券
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_security.when.wiki",
            then = "wiki/stock/testcase.stock_security.insertBatch.then.wiki")
    public void TestInsertBatch() {
        List<StockSecurity> records = new ArrayList<StockSecurity>();
        for (int i = 0; i < 5; i++) {
            StockSecurity ss = new StockSecurity();
            ss.setInternalSecurityId("000922" + i);
            ss.setName("ta苹果^&" + String.valueOf((char) (70 + i * 3)));
            ss.setExchangeCode("XSHE");
            ss.setIssueDate(new Date());
            ss.setFirstLetter("PG");
            ss.setSuspensionFlag(Boolean.FALSE);
            records.add(ss);
        }
        stockSecurityMapper.insertBatch(records);
    }

    /**
     * 根据证券标号查找
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_security.when.wiki")
    public void TestSelectByPrimaryKey() {
        StockSecurity stockSecurity = stockSecurityMapper.selectByPrimaryKey("603308");
        Assert.assertTrue(stockSecurity != null);
    }

    /**
     * 更新证券拼音缩写
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_security.when.wiki",
            then = "wiki/stock/testcase.stock_security.update.then.wiki")
    public void TestUpdateFirstLetter() {
        stockSecurityMapper.update("603308", "灭绝师太", "MJST", null);
    }

    /**
     * 查询所有证券信息
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_security.when.wiki")
    public void TestFindAllByPage() {
        Page page = new Page();
        StockSecurityParams params = new StockSecurityParams();
        List<StockSecurity> list = stockSecurityMapper.findAllByPage(page, params);
        Assert.assertTrue(list.size() == 6);
    }

    /**
     * 根据股票代码首字符查询证券信息
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_security.when.wiki")
    public void TestFindByCodeStartIndex() {
        List<StockSecurity> list = stockSecurityMapper.findByCodeStartIndex("6");
        Assert.assertTrue(list.size() == 5);
    }

    /**
     * 查询所有证券信息,筛选条件: 证券状态
     * 查找停牌的股票：一条搜索结果
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_security.page.when.wiki")
    public void TestFindAllByStatus() {
        Page page = new Page();
        StockSecurityParams params = new StockSecurityParams();
        params.setSuspensionFlag(Boolean.TRUE);
        List<StockSecurity> list = stockSecurityMapper.findAllByPage(page, params);
        Assert.assertTrue(list.size() == 2);
    }

    /**
     * 根据指定条件查询证券信息
     * 采用模糊查询模式
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_security.when.wiki")
    public void TestFindAllByParamsPage() {
        Page page = new Page();
        StockSecurityParams params = new StockSecurityParams();
        params.setName("港");
        List<StockSecurity> list = stockSecurityMapper.findAllByPage(page, params);
        Assert.assertTrue(list.size() == 2);
    }

    /**
     * 查询所有证券参数，并做分页处理
     * 设置分页起始位置为2 ，每页显示数据两条
     * 验证条件：获取到第二条记录的证券代码
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_security.when.wiki")
    public void TestFindStockSecurityInSecondPage() {
        Page page = new Page();
        page.setiDisplayStart(2);
        page.setiDisplayLength(2);
        StockSecurityParams params = new StockSecurityParams();
        List<StockSecurity> list = stockSecurityMapper.findAllByPage(page, params);
        Assert.assertTrue(list.get(1).getInternalSecurityId().equals("603309"));
        Assert.assertTrue(page.getiTotalRecords() == 6);
        Assert.assertTrue(page.getiTotalDisplayRecords() == 6);
    }

    /**
     * 根据股票编号和股票名查找股票信息
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_security.when.wiki")
    public void TestFindByNameAndCode() {
        StockSecurity stockSecurity = stockSecurityMapper.findByNameAndCode("大新闻", null);
        Assert.assertTrue(stockSecurity != null);
    }


}
