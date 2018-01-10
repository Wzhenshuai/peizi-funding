package com.icaopan.user.bean;

/**
 * Created by RoyLeong @royleo.xyz on 2016/11/18.
 */
public class CashFunds {

    private Integer userId;

    private double warnLineVar;

    private double openLineVar;

    private double ratioFeeVar;

    private double minCostVar;

    private double amountVar;

    private double availableVar;

    private double cashAmountVar;

    private double financeAmountVar;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public double getWarnLineVar() {
        return warnLineVar;
    }

    public void setWarnLineVar(double warnLineVar) {
        this.warnLineVar = warnLineVar;
    }

    public double getOpenLineVar() {
        return openLineVar;
    }

    public void setOpenLineVar(double openLineVar) {
        this.openLineVar = openLineVar;
    }

    public double getRatioFeeVar() {
        return ratioFeeVar;
    }

    public void setRatioFeeVar(double ratioFeeVar) {
        this.ratioFeeVar = ratioFeeVar;
    }

    public double getMinCostVar() {
        return minCostVar;
    }

    public void setMinCostVar(double minCostVar) {
        this.minCostVar = minCostVar;
    }

    public double getAmountVar() {
        return amountVar;
    }

    public void setAmountVar(double amountVar) {
        this.amountVar = amountVar;
    }

    public double getAvailableVar() {
        return availableVar;
    }

    public void setAvailableVar(double availableVar) {
        this.availableVar = availableVar;
    }

    public double getCashAmountVar() {
        return cashAmountVar;
    }

    public void setCashAmountVar(double cashAmountVar) {
        this.cashAmountVar = cashAmountVar;
    }

    public double getFinanceAmountVar() {
        return financeAmountVar;
    }

    public void setFinanceAmountVar(double financeAmountVar) {
        this.financeAmountVar = financeAmountVar;
    }
}
