package com.icaopan.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

	/**
	 * 获取任务日志
	 * @return
	 */
	public static Logger getTaskLogger(){
		return LoggerFactory.getLogger("task");
	}
	
	/**
	 * 一般业务日志
	 * @return
	 */
	public static Logger getBusinessLogger(){
		return LoggerFactory.getLogger("business");
	}
	
	/**
	 * 访问日志
	 * @return
	 */
	public static Logger getVisitLogger(){
		return LoggerFactory.getLogger("visit");
	}
	
	/**
	 * 清算日志
	 * @return
	 */
	public static Logger getClearLogger(){
		return LoggerFactory.getLogger("Clearing");
	}
	
	/**
	 * 股票交易日志
	 * @return
	 */
	public static Logger getTradeLogger(){
		return LoggerFactory.getLogger("trade");
	}
	
	/**
	 * 资金调整日志
	 * @return
	 */
	public static Logger getMoneyAdjustLogger(){
		return LoggerFactory.getLogger("money_adjust");
	}
	
	public static Logger getLogger(Class clazz){
		return LoggerFactory.getLogger(clazz);
	}
}
