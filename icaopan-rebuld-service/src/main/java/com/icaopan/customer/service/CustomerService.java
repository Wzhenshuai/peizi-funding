package com.icaopan.customer.service;

import com.icaopan.customer.bean.CustomerBalance;
import com.icaopan.customer.bean.CustomerBillParams;
import com.icaopan.customer.bean.CustomerParams;
import com.icaopan.customer.model.Customer;
import com.icaopan.customer.model.CustomerBill;
import com.icaopan.enums.enumBean.CustomerBillType;
import com.icaopan.util.page.Page;

import java.math.BigDecimal;
import java.util.List;


public interface CustomerService {


    public void saveCustomer(Customer record);

    public Page getCustomerByPage(Page page, CustomerParams params);

    public Customer getCustomerById(Integer id);

    public void updateCustomer(Customer record);

    public boolean updateBalance(Integer customerId, BigDecimal changeDeposit);

    public boolean saveCustomerBill(CustomerBill record);

    public Page getCustomerBillByPage(Page page, CustomerBillParams customerBillParams);

    //扣减资金方当日费用，生成记录
    public void updateCustomerBalanceDay();

    //扣减月费，生成记录
    public void updateCustomerBalanceMonthly();

    public List<Customer> selectAllCustomer();

    public Page getCustomerBalanceByPage(Page page, CustomerParams params);

    public CustomerBalance getCustomerBalance(Integer customerId);

    public void saveCustomerBill(CustomerBillType customerBillType, BigDecimal changeDeposit);

}
