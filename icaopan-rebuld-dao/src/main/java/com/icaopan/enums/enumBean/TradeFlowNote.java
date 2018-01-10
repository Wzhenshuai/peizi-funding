package com.icaopan.enums.enumBean;

import org.apache.commons.lang.StringUtils;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName TradeFlowNote
 * @Description (交易流水 备注 补充说明用)
 * @Date 2016年12月1日 下午3:15:31
 */
public enum TradeFlowNote {

    CASH_REDUCEBYHAND(0, "0", "CASH_REDUCEBYHAND", "减少(手工)"),
    CASH_ADDCREATEHAND(1, "1", "CASH_ADDCREATEHAND", "增加(手工)"),
    DECR(3, "3", "DECR", "减少"),
    ADD(4, "4", "ADD", "增加"),
    TRADE_TRANSACTION(5, "5", "TRADE_TRANSACTION", "交易成交"),
    HAND_ADJUST(6, "6", "HAND_ADJUST", "手工操作"),
    CASH_REDUCEBYHAND_MV(7, "7", "CASH_REDUCEBYHAND_MV", "减少(转移)"),
    CASH_ADDCREATEHAND_MV(8, "8", "CASH_ADDCREATEHAND_MV", "增加(转移)");

    private int num;

    private String code;

    private String name;

    private String disploy;

    private TradeFlowNote(int num, String code, String name, String disploy) {

        this.num = num;
        this.code = code;
        this.name = name;
        this.disploy = disploy;
    }

    public static TradeFlowNote getByCode(String code) {
        for (TradeFlowNote tradeFlowNote : TradeFlowNote.values()) {
            if (StringUtils.equals(tradeFlowNote.getCode(), code)) {
                return tradeFlowNote;
            }
        }
        return null;
    }

    public static TradeFlowNote getByName(String name) {
        for (TradeFlowNote tradeFlowNote : TradeFlowNote.values()) {
            if (StringUtils.equals(tradeFlowNote.getName(), name)) {
                return tradeFlowNote;
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

    public String getDisploy() {
        return disploy;
    }

    public void setDisploy(String disploy) {
        this.disploy = disploy;
    }
}
