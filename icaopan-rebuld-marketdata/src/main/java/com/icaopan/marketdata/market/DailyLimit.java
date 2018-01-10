package com.icaopan.marketdata.market;

/**
 * Created by ffff on 2016-12-29.
 */
public class DailyLimit {

    private double limitUp;
    private double limitDown;

    DailyLimit(double limitUp, double limitDown) {
        this.limitUp = limitUp;
        this.limitDown = limitDown;
    }

    public double getLimitUp() {
        return limitUp;
    }

    public void setLimitUp(double limitUp) {
        this.limitUp = limitUp;
    }

    public double getLimitDown() {
        return limitDown;
    }

    public void setLimitDown(double limitDown) {
        this.limitDown = limitDown;
    }
}
