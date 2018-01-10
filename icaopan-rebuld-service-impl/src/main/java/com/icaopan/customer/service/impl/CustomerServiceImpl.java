package com.icaopan.customer.service.impl;

import com.icaopan.customer.bean.CustomerBalance;
import com.icaopan.customer.bean.CustomerBillFeeResult;
import com.icaopan.customer.bean.CustomerBillParams;
import com.icaopan.customer.bean.CustomerParams;
import com.icaopan.customer.dao.CustomerBillMapper;
import com.icaopan.customer.dao.CustomerMapper;
import com.icaopan.customer.model.Customer;
import com.icaopan.customer.model.CustomerBill;
import com.icaopan.customer.service.ChannelService;
import com.icaopan.customer.service.CustomerService;
import com.icaopan.enums.enumBean.CustomerBillType;
import com.icaopan.log.LogUtil;
import com.icaopan.trade.service.FillService;
import com.icaopan.util.DateUtils;
import com.icaopan.util.page.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 
 * @ClassName CustomerServiceImpl
 * @Description (这里用一句话描述这个类的作用)
 * @author Wangzs
 * @Date 2016年12月7日 上午11:03:47
 * @version 1.0.0
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    private static Logger log = LogUtil.getTaskLogger();
    @Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private CustomerBillMapper customerBillMapper;
	@Autowired
    private ChannelService channelService;
	@Autowired
    private FillService fillService;

    @Override
    public void saveCustomer(Customer record) {
    	if(record.getCreateTime()==null){
    		record.setCreateTime(new Date());
    	}
    	if(record.getModifyTime()==null){
    		record.setModifyTime(new Date());
    	}
    	record.setBalance(BigDecimal.ZERO);
    	customerMapper.insert(record);
    }

    @Override
	public Page getCustomerByPage(Page page, CustomerParams params) {
		page.setAaData(customerMapper.selectCustomerByPage(page, params));
		return page;
	}

    @Override
    public Customer getCustomerById(Integer id) {
        return customerMapper.selectCustomerById(id);
    }

    @Override
    public void updateCustomer(Customer record) {
    	customerMapper.updateCustomer(record);
    }

    @Override
    public boolean updateBalance(Integer customerId, BigDecimal changeDeposit) {
        return customerMapper.updateBalance(customerId,changeDeposit);
    }

    @Override
    public boolean saveCustomerBill(CustomerBill record) {
        return customerBillMapper.insertBill(record);
    }

    @Override
    public Page getCustomerBillByPage(Page page, CustomerBillParams customerBillParams) {
        if (page==null){
            Page page1 = new Page();
            page1.setAaData(customerBillMapper.selectCustomerBillByPage(null, customerBillParams));
            page = page1;
        }else {
            page.setAaData(customerBillMapper.selectCustomerBillByPage(page, customerBillParams));
        }
        return page;
    }

    @Override
    public void updateCustomerBalanceDay() {
        //扣除资金方佣金
        //查询同一资金方的通道 产生的费用和

        List<CustomerBillFeeResult> customerBillList = customerBillMapper.selectCustomerCollection(null,null);
        if (customerBillList != null && customerBillList.size() > 0) {

            List<Customer> customerList = new ArrayList<>();
            for (CustomerBillFeeResult bill : customerBillList){
                Customer customer = new Customer();
                customer.setBalance(bill.getFee());
                customer.setId(bill.getCustomerId());
                customerList.add(customer);
            }
            customerMapper.updateBalanceByList(customerList);

        }

    }



    @Override
    public void updateCustomerBalanceMonthly(){
        String beforeMonth = DateUtils.getBeforeMonth();
        //资金方 抵消走通道模式
        List<Integer> customerIdList = new ArrayList<>();
        //查询所有的资金方
        List<Customer> customerList = customerMapper.selectAllCustomer();
        for (Customer customer : customerList ) {
            if (StringUtils.equals(customer.getCostPattern(),"0")){
                customerIdList.add(customer.getId());
            }
        }


        if (!customerIdList.isEmpty()){
            //走通道模式  把计算上月通道各通道费用
            List<CustomerBillFeeResult> customerBillChannelList = customerBillMapper.selectCustomerCollectionChannel(beforeMonth,customerIdList);
            if(customerBillChannelList != null){
                log.info("资金方月费扣除-抵消通道模式--开始");
                updateCustomerBalance(customerBillChannelList);
                log.info("资金方月费扣除-抵消通道模式--完成");
            }

        }

        List<CustomerBillFeeResult> customerBillList = customerBillMapper.selectCustomerCollection(beforeMonth,customerIdList);
        if (customerBillList != null){
            log.info("资金方月费扣除-抵消非通道模式--开始");
            updateCustomerBalance(customerBillList);
            log.info("资金方月费扣除-抵消非通道模式--完成");

        }
    }

    @Override
    public List<Customer> selectAllCustomer() {
        return customerMapper.selectAllCustomer();
    }

    @Override
    public Page getCustomerBalanceByPage(Page page, CustomerParams params) {
        List<Customer> customerList = customerMapper.selectCustomerByPage(page, params);
        List<CustomerBalance> customerBalanceList = new ArrayList<>();
        for (Customer customer : customerList) {
            CustomerBalance customerBalance = new CustomerBalance();

            customerBalance.setCustomerId(customer.getId());
            customerBalance.setCustomerName(customer.getName());
            customerBalance.setBalance(customer.getBalance());

            String currentMonth = DateUtils.getCurrentMonth();
            String lastMonth = DateUtils.getBeforeMonth();

            BigDecimal lastMonthFee = customerBillMapper.countMonthSumFee(lastMonth,customer.getId());
            BigDecimal lastMonthTradeFee = customerBillMapper.countMonthTradeFee(lastMonth,customer.getId());
            BigDecimal currentMonthTradeFee = customerBillMapper.countMonthTradeFee(currentMonth,customer.getId());
            lastMonthFee = lastMonthFee == null ? new BigDecimal(0.00) : lastMonthFee;
            lastMonthTradeFee = lastMonthTradeFee == null ? new BigDecimal(0.00) : lastMonthTradeFee;
            currentMonthTradeFee = currentMonthTradeFee == null ? new BigDecimal(0.00) : currentMonthTradeFee;
            customerBalance.setLastMonthFee(lastMonthFee.abs());
            customerBalance.setLastMonthTradeFee(lastMonthTradeFee.abs());
            customerBalance.setCurrentMonthTradeFee(currentMonthTradeFee.abs());
            customerBalanceList.add(customerBalance);

        }
        page.setAaData(customerBalanceList);
        return page;
    }

    @Override
    public CustomerBalance getCustomerBalance(Integer customerId) {

        String currentMonth = DateUtils.getCurrentMonth();
        String lastMonth = DateUtils.getBeforeMonth();

        CustomerBalance customerBalance = new CustomerBalance();
        BigDecimal lastMonthFee = customerBillMapper.countMonthSumFee(lastMonth,customerId);
        BigDecimal lastMonthTradeFee = customerBillMapper.countMonthTradeFee(lastMonth,customerId);
        BigDecimal currentMonthTradeFee = customerBillMapper.countMonthTradeFee(currentMonth,customerId);

        lastMonthFee = lastMonthFee == null ? new BigDecimal(0.00) : lastMonthFee;
        lastMonthTradeFee = lastMonthTradeFee == null ? new BigDecimal(0.00) : lastMonthTradeFee;
        currentMonthTradeFee = currentMonthTradeFee == null ? new BigDecimal(0.00) : currentMonthTradeFee;

        customerBalance.setLastMonthFee(lastMonthFee.abs());
        customerBalance.setLastMonthTradeFee(lastMonthTradeFee.abs());
        customerBalance.setCurrentMonthTradeFee(currentMonthTradeFee.abs());
        return customerBalance;
    }

    @Override
    public void saveCustomerBill(CustomerBillType customerBillType, BigDecimal changeDeposit) {

    }

    public void updateCustomerBalance(List<CustomerBillFeeResult> customerBillFeeResultList){
        Date beforeMonthLastDay = DateUtils.getBeforeMonthLastDay();

        //用于插入流水
        List<CustomerBill> customerBills = new ArrayList<>();
        //用于更新资金方余额
        List<Customer> Customers = new ArrayList<>();
        for (CustomerBillFeeResult bill : customerBillFeeResultList) {
            BigDecimal leftFee = bill.getMinCost().add(bill.getFee());
            if (leftFee.doubleValue() > 0) {
                Customer customer = new Customer();
                customer.setBalance(leftFee.negate());
                customer.setId(bill.getCustomerId());
                CustomerBill record = new CustomerBill();
                record.setFee(leftFee.negate());
                record.setOperationUser("sys");
                record.setOperationType("月费扣除");
                record.setCustomerId(bill.getCustomerId());
                record.setOperationTime(beforeMonthLastDay);
                record.setChannelId(bill.getChannelId());
                customerBills.add(record);
                Customers.add(customer);
            }
        }
        if (!customerBills.isEmpty()){
            // 记录
            customerBillMapper.insertList(customerBills);
            //更新资金方的账户余额
            customerMapper.updateBalanceByList(Customers);
        }
    }


}
