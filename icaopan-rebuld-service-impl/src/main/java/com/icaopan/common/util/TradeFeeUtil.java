package com.icaopan.common.util;

import java.math.BigDecimal;

import com.alibaba.druid.util.StringUtils;
import com.icaopan.enums.enumBean.TradeFeeConsts;
import com.icaopan.enums.enumBean.TradeSide;
import com.icaopan.trade.model.Placement;
import com.icaopan.trade.service.dto.TradeFeeParam;
import com.icaopan.trade.service.dto.TradeFeeResult;
import com.icaopan.util.BigDecimalUtil;

/**
 * Created by Wangzs on 2017-02-09.
 */
public class TradeFeeUtil {

    public  static BigDecimal getFee(Placement placement){

        BigDecimal comm = getCommission(placement.getAmount(),placement.getRatioFee(),placement.getMinCost());
        BigDecimal stampDuty = getStampDuty(placement.getAmount());
        BigDecimal transferFee = getTransferFee(placement.getAmount());

        if (StringUtils.equals(placement.getSide().getName(), TradeSide.SELL.getName())){
            if (placement.getIsSzTransferFee() || placement.getSecurityCode().startsWith("6")){
                return BigDecimalUtil.add(comm, stampDuty, transferFee);
            }else {
                return BigDecimalUtil.add(comm,stampDuty);
            }
        }else {
            if (placement.getIsSzTransferFee() || placement.getSecurityCode().startsWith("6")){
                return BigDecimalUtil.add(comm,transferFee);
            }else {
                return comm;
            }
        }
    }
    public  static TradeFeeResult getFeeByParam(TradeFeeParam tradeFeeParam){
        TradeFeeResult tradeFeeResult = new TradeFeeResult();
        BigDecimal amount = BigDecimalUtil.multiplyScale4(tradeFeeParam.getPrice(),tradeFeeParam.getQuantity());
        if(amount.compareTo(BigDecimal.ZERO)<1){
            tradeFeeResult.setCommissionFee(BigDecimal.ZERO);
            tradeFeeResult.setStampDutyFee(BigDecimal.ZERO);
            tradeFeeResult.setTransferFee(BigDecimal.ZERO);
            return  tradeFeeResult;
        }
        BigDecimal comm = getCommission(amount,tradeFeeParam.getRatioFee(),tradeFeeParam.getMinCost());
        BigDecimal stampDuty = getStampDuty(amount);
        BigDecimal transferFee = getTransferFee(amount);
        tradeFeeResult.setCommissionFee(comm);
        if (StringUtils.equals(tradeFeeParam.getSide().getName(), TradeSide.SELL.getName())){
            tradeFeeResult.setStampDutyFee(stampDuty);
        }
        if(tradeFeeParam.getIsSzTransferFee() || tradeFeeParam.getSecurityCode().startsWith("6")){
            tradeFeeResult.setTransferFee(transferFee);
        }
        return tradeFeeResult;
    }

    /**
     * 交易佣金
     * @param amount,ratilFee,minCost
     * 成交额，佣金费率，佣金抵消
     */
    public static BigDecimal getCommission(BigDecimal amount,BigDecimal ratioFee,BigDecimal minCost){

        BigDecimal commission = BigDecimalUtil.multiplyScale4(ratioFee, amount);
        if (commission.compareTo(minCost) < 0){
            commission = minCost;
        }
        return commission;
    }
    /**
     * 过户费
     */
    public static BigDecimal getTransferFee(BigDecimal quantity){
        BigDecimal transferFee = TradeFeeConsts.TRANSFER_FEE;
        BigDecimal transfer =  transferFee.multiply(quantity);
//        if (transfer.compareTo(TradeFeeConsts.MIN_TRANSFER_FEE) < 0){
//            transfer = TradeFeeConsts.MIN_TRANSFER_FEE;
//        }
        return transfer;
    }
    /**
     * 印花税
     */
    public static BigDecimal getStampDuty(BigDecimal amount){
        BigDecimal stampDutyFee = TradeFeeConsts.STAMP_DUTY_FEE;
        BigDecimal stampDuty =  stampDutyFee.multiply(amount);
        return stampDuty;
    }
}
