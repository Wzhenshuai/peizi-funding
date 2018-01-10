package com.icaopan.web.common;

public class ResponseResult {

    // 定义rescode的错误Code
    public static final String SUCCESS = "success";
    public static final String FAIL    = "fail";
    public static final String LOGIN   = "login";
    // 定义接口的状态	
    public static final String NORMAL  = "normal";
    public static final String ERROR   = "error";
    // 接口调用状态	
    private String status;
    // 接口返回的代码
    private String rescode;
    // 接口返回的信息
    private String message;
    // 接口返回的数据
    private Object data;


    public ResponseResult() {

    }

    public ResponseResult(String status, String rescode, String message, Object data) {
        super();
        this.status = status;
        this.rescode = rescode;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRescode() {
        return rescode;
    }

    public void setRescode(String rescode) {
        this.rescode = rescode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseBody [status=" + status + ", rescode=" + rescode + ", message=" + message + ", data=" + data
               + "]";
    }
}
