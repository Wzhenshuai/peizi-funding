package com.icaopan.customer.bean;

public class CustomerBillParams {

    /**
     * @Description:
     * @author:Wangzs
     * @time:2016年11月30日 下午5:57:19
     */
    private Integer channelId;

    private String operationType;

    private Integer customerId;

    private String startDate;

    private String endDate;

    public Integer getChannelId() {
        return channelId;
    }


    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }


    public String getOperationType() {
        return operationType;
    }


    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }


    public Integer getCustomerId() {
        return customerId;
    }


    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }


    public String getStartDate() {
    	if("undefined".equals(startDate)){
    		startDate=null;
    	}
        return startDate;
    }


    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public String getEndDate() {
    	if("undefined".equals(endDate)){
    		endDate=null;
    	}
        return endDate;
    }


    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


}
