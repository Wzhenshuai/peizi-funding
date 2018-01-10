package com.icaopan.enums.enumBean;

import java.math.BigDecimal;

/**
 * Created by Wangzs on 2017-02-20.
 */
public interface TradeFeeConsts {

    BigDecimal TRADE_COMMISSION_FEE = new BigDecimal("0.0008");  //交易佣金
    BigDecimal TRANSFER_FEE         = new BigDecimal("0.00002");  //过户费
    BigDecimal STAMP_DUTY_FEE       = new BigDecimal("0.001");  //印花税

    BigDecimal MIN_TRADE_COMMISSION_FEE = new BigDecimal(5);  //交易佣金
    BigDecimal MIN_TRANSFER_FEE         = new BigDecimal(1);  //过户费

}
