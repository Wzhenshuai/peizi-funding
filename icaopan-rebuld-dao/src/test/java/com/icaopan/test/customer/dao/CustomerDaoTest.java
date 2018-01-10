package com.icaopan.test.customer.dao;

import com.icaopan.customer.bean.CustomerParams;
import com.icaopan.customer.dao.CustomerMapper;
import com.icaopan.customer.model.Customer;
import com.icaopan.enums.enumBean.CustomerCostPattern;
import com.icaopan.enums.enumBean.CustomerStatus;
import com.icaopan.test.common.dao.BaseTestDao;
import com.icaopan.util.page.Page;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Wangzs
 * @ClassName: CustomerDaoTest
 * @Description: (客户管理)
 * @date 2016年11月28日 下午6:00:55
 */
public class CustomerDaoTest extends BaseTestDao {

    @SpringBeanByType
    private CustomerMapper customerMapper;

    @Test
    @DbFit(when = { "wiki/customer/testcase.clean_customer.when.wiki" }, then = { "wiki/customer/testcase.customer.insert.then.wiki" })
    public void testCustomerInsert() {
        Customer customer = new Customer();
        //customer.setId(102);
        customer.setName("wahaha");
        customer.setNotes("客户管理");
        customer.setStatus(CustomerStatus.getByName("NORMAL").getCode());
        customer.setBalance(new BigDecimal(0.00));
        customer.setCostPattern(CustomerCostPattern.getByName("CHANNL").getCode());
        //customer.setCreateTime(null);
        customer.setMinCost(new BigDecimal(200));
        //customer.setModifyTime(null);
        customer.setDefaultTatio(new BigDecimal(0.0003));
        customer.setDefaultMinCost(new BigDecimal(1000000));
        customerMapper.insert(customer);
    }

    @Test
    @DbFit(when = { "wiki/customer/testcase.customer.when.wiki" }, then = { "wiki/customer/testcase.customer.update.then.wiki" })
    public void testCustomerUpdate() {
        Customer customer = new Customer();
        customer.setId(101);
        customer.setName("zezeze");
        customer.setNotes("客户管理更新");
        customer.setStatus(CustomerStatus.getByName("LOCKED").getCode());
        customer.setCostPattern(CustomerCostPattern.getByName("CHANNL").getCode());
        customer.setMinCost(new BigDecimal(300));
        customer.setDefaultTatio(new BigDecimal(0.0004));
        customer.setDefaultMinCost(new BigDecimal(2000000));
        customerMapper.updateCustomer(customer);
    }

    @Test
    @DbFit(when = { "wiki/customer/testcase.customer.when.wiki" }, then = { "wiki/customer/testcase.customer.update_balance.then.wiki" })
    public void updateBalance() {
        customerMapper.updateBalance(101, new BigDecimal(200000));
    }

    @Test
    @DbFit(when = { "wiki/customer/testcase.customer.when.wiki" }, then = { "wiki/customer/testcase.customer.then.wiki" })
    public void testCustomerSelectById() {
        Customer customer = customerMapper.selectCustomerById(101);
        Assert.assertTrue(customer != null);

    }

    @Test
    @DbFit(when = { "wiki/customer/testcase.customer.when.wiki" })
    public void testCustomerSelectByPage() {
        Page page = new Page();

        CustomerParams customerParams = new CustomerParams();
        customerParams.setName("wahaha");
        customerParams.setStatus("0");

        List<Customer> list = customerMapper.selectCustomerByPage(page, customerParams);
        org.testng.Assert.assertTrue(list.size() > 0);
    }

    @Test
    @DbFit(when = { "wiki/customer/testcase.customer.when.wiki" }, then = { "wiki/customer/testcase.customer.updateBalanceByList.then.wiki" })
    public void testUpdateBalanceByList() {
        List<Customer> list = new ArrayList<Customer>();
        list.add(new Customer(101, new BigDecimal(1200)));
        customerMapper.updateBalanceByList(list);
    }
}
