package com.icaopan.msg.channel.mandao;

import org.apache.commons.lang3.StringUtils;

import com.icaopan.msg.channel.mandao.util.MsgUtil;

public class MsgSender {

	
	public static boolean sendMSG(String phoneNum, String content) {
		boolean result = false;
		if (!StringUtils.isEmpty(phoneNum)) {
			try {
				result = MsgUtil.sendMsg(phoneNum, content);
			} catch (Exception e) {
				result = false;
			}
		}
		return result;
	}
}
