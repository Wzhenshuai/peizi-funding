package com.icaopan.customer.bean;

/**
 * @author Wangzs
 * @version 1.0.0
 * @ClassName CustomerParams
 * @Description (查询客户信息参数)
 * @Date 2016年12月1日 下午5:07:59
 */
public class CustomerParams {

    private String name;

    private String status;

    private Integer id;

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


}
