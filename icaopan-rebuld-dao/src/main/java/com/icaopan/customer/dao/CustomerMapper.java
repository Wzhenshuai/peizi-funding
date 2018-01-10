package com.icaopan.customer.dao;

import com.icaopan.customer.bean.CustomerParams;
import com.icaopan.customer.model.Customer;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName CustomerMapper
 * @Description (客户信息管理)
 * @Date 2016年12月1日 下午5:21:04
 */
@Repository
public interface CustomerMapper {
    /**
     * @param record
     * @return
     * @Description (插入客户信息)
     */
    boolean insert(Customer record);

    /**
     * @param page
     * @param params
     * @return
     * @Description (查询客户信息 分页)
     */
    List<Customer> selectCustomerByPage(@Param("page") Page page, @Param("params") CustomerParams params);

    /**
     * @param id
     * @return
     * @Description (查询客户信息根据ID)
     */
    Customer selectCustomerById(Integer id);

    /**
     * @param record
     * @return
     * @Description (更新客户信息)
     */
    boolean updateCustomer(Customer record);

    /**
     * @param customerId
     * @param changeDeposit
     * @return
     * @Description (更新客户信息余额)
     */
    boolean updateBalance(@Param("id") Integer customerId, @Param("changeDeposit") BigDecimal changeDeposit);

    /**
     * @return
     * @Description (根据资金方 通道的流水扣除资金方费用)
     */
    boolean updateBalanceByList(@Param("list") List<Customer> list);

    List<Customer> selectAllCustomer();


}