package com.icaopan.enums.enumBean;

import org.apache.commons.lang.StringUtils;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName CustomerBillType
 * @Description (客户账单 类型)
 * @Date 2016年12月1日 下午3:15:03
 */
public enum CustomerBillType {

    DEDUCTIONBYMONTH(0, "0", "DEDUCTIONBYMONTH", "月费扣除"),
    DECR(1, "1", "DECR", "减少"),
    ADD(2, "2", "ADD", "增加"),
    TRADE_TRANSACTION(3, "3", "TRADE_TRANSACTION", "交易成交");

    private int num;

    private String code;

    private String name;

    private String display;

    private CustomerBillType(int num, String code, String name, String display) {

        this.num = num;
        this.code = code;
        this.name = name;
        this.display = display;
    }

    public static CustomerBillType getByCode(String code) {
        for (CustomerBillType customerBillType : CustomerBillType.values()) {
            if (StringUtils.equals(customerBillType.getCode(), code)) {
                return customerBillType;
            }
        }
        return null;
    }

    public static CustomerBillType getByName(String name) {
        for (CustomerBillType customerBillType : CustomerBillType.values()) {
            if (StringUtils.equals(customerBillType.getName(), name)) {
                return customerBillType;
            }
        }
        return null;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
