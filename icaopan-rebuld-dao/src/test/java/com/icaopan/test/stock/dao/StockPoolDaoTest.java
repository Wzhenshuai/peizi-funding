package com.icaopan.test.stock.dao;

import com.icaopan.enums.enumBean.StockPoolType;
import com.icaopan.stock.bean.StockPoolParams;
import com.icaopan.stock.dao.StockPoolMapper;
import com.icaopan.stock.model.StockPool;
import com.icaopan.test.common.dao.BaseTestDao;
import com.icaopan.util.page.Page;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2016/11/18.
 */
public class StockPoolDaoTest extends BaseTestDao {

    @SpringBeanByType
    private StockPoolMapper stockPoolMapper;

    /**
     * 保存股票池信息
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_pool.when.wiki", then = "wiki/stock/testcase.stock_pool.then.wiki")
    public void TestInsert() {
        StockPool stockPool = new StockPool();
        stockPool.setStockCode("061011");
        stockPool.setStockName("bourgeois");
        stockPool.setModifyTime(new Date());
        stockPool.setCustomerId(3);
        stockPool.setType(StockPoolType.getByName("BannedShares").getCode());
        stockPoolMapper.insert(stockPool);
    }

    /**
     * 根据主键查找股票池信息
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_pool.when.wiki")
    public void TestFindByPrimaryId() {
        Assert.assertTrue(stockPoolMapper.findByPrimaryId(3) != null);
    }

    /**
     * 查找用户并作分页处理
     * 验证分页查询出来的总记录数
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_pool.when.wiki")
    public void TestSelectByPage() {
        Page page = new Page();
        StockPoolParams stockPoolParams = new StockPoolParams();
        List<StockPool> list = stockPoolMapper.findByPage(page, stockPoolParams);
        Assert.assertTrue(list.size() > 0);
        Assert.assertTrue(page.getiTotalRecords() == 5);
    }

    /**
     * 根据指定条件查询股票池信息
     * 查询股票名字里面有 “p”字母的记录
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_pool.when.wiki")
    public void TestSelectStoclPoolByNameOfAlphabet() {
        StockPoolParams params = new StockPoolParams();
        params.setStockName("p");
        List<StockPool> list = stockPoolMapper.findByPage(new Page(), params);
        Assert.assertTrue(list.size() == 3);
    }

    /**
     * 查找用户并作分页处理:获取分页的第二页记录
     * 设置分页起始位置为2 ，每页显示数据两条
     * 验证条件：获取到第二条记录
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_pool.when.wiki")
    public void TestSelectByPageSecond() {
        Page page = new Page();
        page.setiDisplayStart(2);
        page.setiDisplayLength(2);
        StockPoolParams params = new StockPoolParams();
        List<StockPool> list = stockPoolMapper.findByPage(page, params);
        Assert.assertTrue(list.size() == 2);
        Assert.assertTrue(list.get(1).getStockCode().equals("065000"));
        Assert.assertTrue(page.getiTotalDisplayRecords() == 5);
    }


    /**
     * 批量导入
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_pool.when.wiki", then = "wiki/stock/testcase.stock_pool.insertBatch.then.wiki")
    public void TestInsertBatch() {
        List<StockPool> record = new ArrayList<StockPool>();
        for (int i = 0; i < 10; i++) {
            StockPool stockpool = new StockPool();
            stockpool.setStockName("tad^&" + String.valueOf((char) (70 + i * 3)));
            stockpool.setStockCode("1008" + i);
            stockpool.setCustomerId(i % 3);
            stockpool.setModifyTime(new Date());
            if (i % 2 == 0) {
                stockpool.setType(StockPoolType.getByName("BannedShares").getCode());
            } else {
                stockpool.setType(StockPoolType.getByName("SmallPlates").getCode());
            }
            record.add(stockpool);
            stockpool = null;
        }
        stockPoolMapper.inertBatch(record);
    }

    /**
     * 批量删除
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_pool.when.wiki",
            then = "wiki/stock/testcase.stock_pool.delBatch.then.wiki")
    public void TestDelBatch() {
        List<Integer> record = new ArrayList<Integer>();
        Integer[] ids = { 2, 5, 3 };
        record = Arrays.asList(ids);
        stockPoolMapper.deleteBatch(record);
    }

    /**
     * 根据资金方顾客ID删除股票池信息
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_pool.when.wiki",
            then = "wiki/stock/testcase.stock_pool.delete.then.wiki")
    public void TestDelete() {
        stockPoolMapper.deleteByIdAndStockType(1, StockPoolType.getByName("Gem").getCode());
    }

    /**
     * 根据资金方顾客ID查找股票池信息
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_pool.when.wiki")
    public void TestFindUserByCustomerId() {
        List<StockPool> list = stockPoolMapper.findByIdAndStockType(2, StockPoolType.getByName("SmallPlates").getCode());
        Assert.assertTrue(list.size() > 0);
    }

    /**
     * 根据股票名称，股票编号查询股票信息，主要在添加和编辑股票信息的时候，验证股票名和股票编号的唯一性
     *
     * @param
     * @return
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_pool.when.wiki")
    public void TestFindByNameAndCode() {
        List<StockPool> stockPools = stockPoolMapper.findByNameAndCode(null, "061000");
        Assert.assertTrue(stockPools.size() >= 0);
    }

    /**
     * 更新股票池信息
     */
    @Test
    @DbFit(when = "wiki/stock/testcase.stock_pool.when.wiki", then = "wiki/stock/testcase.stock_pool.update.then.wiki")
    public void TestUpdate() {
        stockPoolMapper.update(1, null, "65535", null, null);
    }
}
