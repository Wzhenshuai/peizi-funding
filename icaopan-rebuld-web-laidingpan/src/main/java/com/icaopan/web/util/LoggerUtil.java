package com.icaopan.web.util;

import com.alibaba.fastjson.JSONObject;
import com.icaopan.user.model.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class LoggerUtil {

    // 定义日志的logger
    private static Logger errorLogger = LoggerFactory.getLogger("error");
    private static Logger authLogger  = LoggerFactory.getLogger("auth");

    public static Logger getLogger(Class clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * @param e: 异常 errorMsg ： 错误信息
     * @return 返回值
     * @throws 异常描述
     * @Description 输出错误日志
     */
    public static void errorLog(Exception e, String errorMsg) {

        // 错误日志的Map
        Map<String, Object> errorMap = new HashMap<String, Object>();

        // 自定义的错误日志的描述
        errorMsg = e.getMessage().trim().equals("") ? (StringUtils.isBlank(errorMsg) ? "发生系统错误" : errorMsg) : e.getMessage();
        errorMap.put("errorMsg", errorMsg);

        // 错误的栈信息
        errorMap.put("errorStackTraceElements", e.getStackTrace());

        // 转化成Json 输出到日志中
        errorLogger.info(JSONObject.toJSONString(errorMap));
    }

    /**
     * @param adminUser : 后台用户  操作日志 ： operatLog
     * @Description 用户认证日志
     */
    public static void authLog(User user, String operatLog) {
        String logTemplate = "[用户认证] 用户（ID：%s）：%s，执行了操作：%s";
        String logInfo = String.format(logTemplate, user.getId(), user.getRealName(), operatLog);
        authLogger.info(logInfo);
    }

    /**
     * @param request ： 用户的请求
     * @return IP 地址
     * @Description 获取当前请求的IP地址
     */
    public final static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

}
