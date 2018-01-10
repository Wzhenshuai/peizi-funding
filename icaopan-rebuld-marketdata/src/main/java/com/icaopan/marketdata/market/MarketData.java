package com.icaopan.marketdata.market;

import java.io.Serializable;

public class MarketData implements Serializable {

    private static final long serialVersionUID = 1L;
    // private static final String EXCHANGE_CODE = "XSHG";

    /* 行情日期 */
    private String eventDate;
    /* 行情时间 */
    private String eventTime;
    /* 证券代码 */
    private String s1;
    /* 证券名称 */
    private String s2;
    /* 前收盘价格 */
    private Double s3;
    /* 金开盘价格 */
    private Double s4;
    /* 今成交价 */
    private Double s5;
    /* 最高价 */
    private Double s6;
    /* 最低价 */
    private Double s7;
    /* 最新价格 */
    private Double s8;
    /* 当前买入价格 */
    private Double s9;
    /* 当前卖出价格 */
    private Double s10;
    /* 成交数量 */
    private Double s11;
    /* 市盈率 */
    private Double s13;
    /* 申买量一 */
    private Double s15;
    /* 申买价二 */
    private Double s16;
    /* 申买量二 */
    private Double s17;
    /* 申买价三 */
    private Double s18;
    /* 申买量三 */
    private Double s19;
    /* 申卖量一 */
    private Double s21;
    /* 申卖价二 */
    private Double s22;
    /* 申卖量二 */
    private Double s23;
    /* 申卖价三 */
    private Double s24;
    /* 申卖量三 */
    private Double s25;
    /* 申买价四 */
    private Double s26;
    /* 申买量四 */
    private Double s27;
    /* 申买价五 */
    private Double s28;
    /* 申买量五 */
    private Double s29;
    /* 申卖价四 */
    private Double s30;
    /* 申卖量四 */
    private Double s31;
    /* 申卖价五 */
    private Double s32;
    /* 申卖量五 */
    private Double s33;

    /**
     * /** 1 S1 证券代码C6 2 S2 证券名称C8 3 S3 前收盘价格N8(3) 4 S4 今开盘价格N8(3) 5 S5 今成交金额N12
     * 6 S6 最高价格N8(3) 7 S7 最低价格N8(3) 8 S8 最新价格N8(3) 9 S9 当前买入价格N8(3) 10 S10
     * 当前卖出价格N8(3) 11 S11 成交数量N10 12 S13 市盈率N8(3) 13 S15 申买量一N10 14 S16
     * 申买价二N8(3) 15 S17 申买量二N10 16 S18 申买价三N8(3) 17 S19 申买量三N10 18 S21 申卖量一N10
     * 19 S22 申卖价二N8(3) 20 S23 申卖量二N10 21 S24 申卖价三N8(3) 22 S25 申卖量三N10 23 S26
     * 申买价四N8(3) 24 S27 申买量四N10 25 S28 申买价五N8(3) 26 S29 申买量五N10 27 S30 申卖价四N8(3)
     * 28 S31 申卖量四N10 29 S32 申卖价五N8(3) 30 S33 申卖量五N10
     *
     * @param record
     * @return
     */
    public static MarketData getInstance(Object[] record) {
        MarketData marketData = new MarketData();
        marketData.s1 = record[1].toString();
        marketData.s2 = record[2].toString();
        marketData.s3 = (Double) record[3];
        marketData.s4 = (Double) record[4];
        marketData.s5 = (Double) record[5];
        marketData.s6 = (Double) record[6];
        marketData.s7 = (Double) record[7];
        marketData.s8 = (Double) record[8];
        marketData.s9 = (Double) record[9];
        marketData.s10 = (Double) record[10];
        marketData.s11 = (Double) record[11];
        marketData.s13 = (Double) record[12];
        marketData.s15 = (Double) record[13];
        marketData.s16 = (Double) record[14];
        marketData.s17 = (Double) record[15];
        marketData.s18 = (Double) record[16];
        marketData.s19 = (Double) record[17];
        marketData.s21 = (Double) record[18];
        marketData.s22 = (Double) record[19];
        marketData.s23 = (Double) record[20];
        marketData.s24 = (Double) record[21];
        marketData.s25 = (Double) record[22];
        marketData.s26 = (Double) record[23];
        marketData.s27 = (Double) record[24];
        marketData.s28 = (Double) record[25];
        marketData.s29 = (Double) record[26];
        marketData.s30 = (Double) record[27];
        marketData.s31 = (Double) record[28];
        marketData.s32 = (Double) record[29];
        marketData.s33 = (Double) record[30];
        marketData.eventDate = (String) record[31];
        marketData.eventTime = (String) record[32];
        return marketData;
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public Double getS3() {
        return s3;
    }

    public Double getS4() {
        return s4;
    }

    public Double getS5() {
        return s5;
    }

    public Double getS6() {
        return s6;
    }

    public Double getS7() {
        return s7;
    }

    public Double getS8() {
        return s8;
    }

    public Double getS9() {
        return s9;
    }

    public Double getS10() {
        return s10;
    }

    public Double getS11() {
        return s11;
    }

    public Double getS13() {
        return s13;
    }

    public Double getS15() {
        return s15;
    }

    public Double getS16() {
        return s16;
    }

    public Double getS17() {
        return s17;
    }

    public Double getS18() {
        return s18;
    }

    public Double getS19() {
        return s19;
    }

    public Double getS21() {
        return s21;
    }

    public Double getS22() {
        return s22;
    }

    public Double getS23() {
        return s23;
    }

    public Double getS24() {
        return s24;
    }

    public Double getS25() {
        return s25;
    }

    public Double getS26() {
        return s26;
    }

    public Double getS27() {
        return s27;
    }

    public Double getS28() {
        return s28;
    }

    public Double getS29() {
        return s29;
    }

    public Double getS30() {
        return s30;
    }

    public Double getS31() {
        return s31;
    }

    public Double getS32() {
        return s32;
    }

    public Double getS33() {
        return s33;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String evnetDate) {
        this.eventDate = evnetDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String evnetTime) {
        this.eventTime = evnetTime;
    }

}
