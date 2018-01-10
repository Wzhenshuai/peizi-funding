package com.icaopan.customer.dao;

import com.icaopan.customer.bean.CustomerBillFeeResult;
import com.icaopan.customer.bean.CustomerBillParams;
import com.icaopan.customer.model.CustomerBill;
import com.icaopan.util.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName CustomerBillMapper
 * @Description (客户账单)
 * @Date 2016年11月29日 下午3:54:20
 */
@Repository
public interface CustomerBillMapper {
    /**
     * @param record
     * @return
     * @Description (生成客户账单)
     */
    boolean insertBill(CustomerBill record);

    /**
     * @param page
     * @return
     * @Description (查询客户账单分页)
     */
    List<CustomerBill> selectCustomerBillByPage(@Param("page") Page page, @Param("params") CustomerBillParams customerBillParams);

    //根据成交 生成资金方流水
    void generateCustomerBill();

    List<CustomerBillFeeResult> selectCustomerCollection(@Param("beforeMonth") String beforeMonth, @Param("customerIdList") List<Integer> customerIdList);

    List<CustomerBillFeeResult> selectCustomerCollectionChannel(@Param("beforeMonth") String beforeMonth, @Param("customerIdList") List<Integer> customerIdList);

    /**
     * @return
     * @Description (生成客户账单)
     */
    boolean insertList(@Param("customerBillList") List<CustomerBill> list);


    public BigDecimal countMonthSumFee(@Param("month") String month, @Param("customerId") Integer customerId);

    public BigDecimal countMonthTradeFee(@Param("month") String month, @Param("customerId") Integer customerId);
}