package com.icaopan.trade.service.dto;

import java.math.BigDecimal;

/**
 * Created by Wangzs on 2017-03-08.
 */
public class TradeFeeResult {

    private BigDecimal commissionFee;

    private BigDecimal stampDutyFee;

    private BigDecimal transferFee;

    public BigDecimal getCommissionFee() {
        return commissionFee;
    }

    public void setCommissionFee(BigDecimal commissionFee) {
        this.commissionFee = commissionFee;
    }

    public BigDecimal getStampDutyFee() {
        return stampDutyFee;
    }

    public void setStampDutyFee(BigDecimal stampDutyFee) {
        this.stampDutyFee = stampDutyFee;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    public BigDecimal getTotalFee() {
        return this.commissionFee.add(this.stampDutyFee).add(this.transferFee);
    }

    public TradeFeeResult(){
        this.commissionFee = BigDecimal.ZERO;
        this.stampDutyFee = BigDecimal.ZERO;
        this.transferFee = BigDecimal.ZERO;
    }
}
