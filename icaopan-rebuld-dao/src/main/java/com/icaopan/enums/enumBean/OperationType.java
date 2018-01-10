package com.icaopan.enums.enumBean;

/**
 * Created by Administrator on 2017/7/17 0017.
 */
public enum OperationType {


    AUTO_CHECK(0, "0", "AUTO_SETTLEMENT", "自动检查");

    private int num;
    private String code;
    private String name;
    private String display;

    private OperationType(int num, String code, String name, String display) {
        this.num = num;
        this.code = code;
        this.name = name;
        this.display = display;
    }


}
