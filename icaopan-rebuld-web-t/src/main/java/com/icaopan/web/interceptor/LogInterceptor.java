/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.icaopan.web.interceptor;


import com.icaopan.log.LogUtil;
import com.icaopan.user.model.User;
import com.icaopan.web.user.realm.LoginRealm;

import net.sf.json.JSONArray;

import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 日志拦截器
 *
 * @author ThinkGem
 * @version 2014-8-19
 */
public class LogInterceptor implements HandlerInterceptor {

    protected org.slf4j.Logger logger = LogUtil.getVisitLogger();

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String userName = "";
        User user = null;
        try {
            user = LoginRealm.getCurrentUser();
            if (user != null) {
                userName = user.getUserName();
            }
        } catch (Exception e) {
        }
        String logTemplate = "[操作日志]用户：%s，IP:%s，URL:%s，参数：%s";
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String params = JSONArray.fromObject(request.getParameterMap()).toString();
        String logInfo = String.format(logTemplate, userName, ip, url, params);
        logger.info(logInfo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        if (ex != null) {
        	logger.error("异常", ex);
        }
    }

}
