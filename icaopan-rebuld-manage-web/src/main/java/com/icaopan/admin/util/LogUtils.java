package com.icaopan.admin.util;

import com.icaopan.sys.dao.LogMapper;
import com.icaopan.sys.model.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 字典工具类
 * @author wangzs
 * @version 2017-12-21
 */
public class LogUtils {

	private static LogMapper logMapper = SpringContextHolder.getBean(LogMapper.class);

	/**
	 * 保存日志
	 */
	public static void saveLog(HttpServletRequest request, Integer userId, String title){
		//AdminUser user = LoginRealm.getCurrentUser();
		if (userId != null) {
			Log log = new Log();
			log.setUserId(userId);
			log.setTitle(title);
			log.setRemoteAddr(StringUtils.getRemoteAddr(request));
			log.setUserAgent(request.getHeader("user-agent"));
			log.setRequestUri(request.getRequestURI());
			log.setParams(request.getParameterMap());
			log.setMethod(request.getMethod());
			log.setCreateDate(new Date());
			// 异步保存日志
			SaveLogThread saveLogThread = new SaveLogThread(log);
			saveLogThread.start();

		}
	}
	/**
	 * 保存日志线程
	 */
	public static class SaveLogThread extends Thread{
		
		private Log log;

		public SaveLogThread(Log log){
			super(SaveLogThread.class.getSimpleName());
			this.log = log;
		}
		@Override
		public void run() {
			if (StringUtils.isBlank(log.getTitle())){
				return;
			}
			// 保存日志信息
			logMapper.insert(log);
		}
	}

}
