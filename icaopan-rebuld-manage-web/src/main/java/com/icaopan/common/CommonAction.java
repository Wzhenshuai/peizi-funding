package com.icaopan.common;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.icaopan.log.LogUtil;

public class CommonAction {

	protected Logger logger=LogUtil.getLogger(getClass());
	
	protected String getErrorMsg(Exception e){
		String msg=e.getMessage();
		if(StringUtils.isEmpty(msg)){
			msg="服务器繁忙，请稍后重试";
		}
		return msg;
	}
}
