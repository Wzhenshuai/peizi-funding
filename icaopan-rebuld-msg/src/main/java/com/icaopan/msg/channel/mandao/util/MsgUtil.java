package com.icaopan.msg.channel.mandao.util;

import java.net.URLEncoder;

public class MsgUtil {

	public static boolean sendMsg(String phoneNum, String msg) {
		boolean callBack = sendMsgByManDao(phoneNum, msg);
		return callBack;
	}

	/**
	 * Description: 漫道发送短信的接口走的webservice
	 * 
	 * @author 李学朝
	 * @date 2015年8月18日 下午12:37:27
	 */
	public static boolean sendMsgByManDao(String mobiles, String cont) {
		String content = null;
		String sn = "SDK-BBX-010-23585";
		String pwd = "DbaD9-6D";
		try {
			//			cont = cont + "【易财富365】";
			if(cont.contains("【ycf365.com】。")){
				cont = cont.replace("【ycf365.com】。", "【ycf365.com】");
			}
			content = URLEncoder.encode(cont, "utf8");
			Client client = new Client(sn, pwd);
			String result_mt = client.mdSmsSend_u(mobiles, content, "", "", "");
			// 发送短信，如果是以负号开头就是发送失败。
			if (result_mt.startsWith("-") || result_mt.equals("")) {
				return false;
			} else {
				// 输出返回标识，为小于19位的正数，String类型的。记录您发送的批次。
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Description: 测试短信的发送
	 * 
	 * @author 李学朝
	 * @date 2015年8月18日 下午12:39:10
	 */
	public static void main(String[] args) {
		boolean b = sendMsgByManDao("13366995646",
				"您的用户名为liu****ua现金账户，成功收款54.38元。");
		System.out.println(b);
	}
}
