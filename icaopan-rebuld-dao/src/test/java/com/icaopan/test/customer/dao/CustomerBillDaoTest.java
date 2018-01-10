package com.icaopan.test.customer.dao;

import com.icaopan.customer.bean.CustomerBillParams;
import com.icaopan.customer.dao.CustomerBillMapper;
import com.icaopan.customer.model.CustomerBill;
import com.icaopan.test.common.dao.BaseTestDao;
import com.icaopan.util.page.Page;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringBeanByType;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author Wangzs
 * @ClassName: CustomerBillDaoTest
 * @Description: (客户账单)
 * @date 2016年11月28日 下午6:01:22
 */
public class CustomerBillDaoTest extends BaseTestDao {

    @SpringBeanByType
    private CustomerBillMapper customerBillMapper;

    @Test
    @DbFit(when = { "wiki/customer/testcase.clean_customer_bill.when.wiki" }, then = { "wiki/customer/testcase.customer_bill.then.wiki" })
    public void testCustomerInsert() {
        CustomerBill customerBill = new CustomerBill();
        //customerBill.setId(101);
        customerBill.setChannelId(20001);
        customerBill.setBalance(new BigDecimal(299800.00));
        customerBill.setFillAmount(new BigDecimal(3000));
        customerBill.setFee(new BigDecimal(-200));
        customerBill.setCustomerId(101);
        customerBill.setOperationUser("张三");
        customerBillMapper.insertBill(customerBill);
    }

    @Test
    @DbFit(when = { "wiki/customer/testcase.customer_bill.when.wiki" })
    public void testCustomerSelectByPage() {
        Page page = new Page();

        CustomerBillParams customerBillParams = new CustomerBillParams();
        customerBillParams.setChannelId(20001);
        customerBillParams.setCustomerId(101);
        List<CustomerBill> list = customerBillMapper.selectCustomerBillByPage(page, customerBillParams);
        org.testng.Assert.assertTrue(list.size() > 0);
    }
}
