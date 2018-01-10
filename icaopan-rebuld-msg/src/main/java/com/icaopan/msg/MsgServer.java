package com.icaopan.msg;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.util.NetUtils;

import com.icaopan.msg.channel.mandao.MsgSenderThread;
import com.icaopan.msg.channel.mandao.util.NetUtil;

public class MsgServer {

	private static String[] managerPhones=new String[]{"15726690996","17710315002"};
	
	private static Map<String,Integer> todaySendTimes=new HashMap<String,Integer>();//今天的发送次数
	
	public static void addOnSendTimes(){
		synchronized (todaySendTimes) {
			String todayDate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			Integer counts=todaySendTimes.get(todayDate);
			if(counts!=null){
				counts=counts+1;
			}else {
				counts = 1;
			}
			todaySendTimes.put(todayDate, counts);
		}
	}
	
	public static void sendMsg(String phoneNo,String content){
		MsgServer.sendMsg(phoneNo, content);
	}
	
	/**
	 * 向管理员发送短信
	 * 每天最多发送三次
	 * @param content
	 */
	public static void sendMarketDataMsgToManager(String content){
		try {
			content+="-来自"+NetUtil.getLocalIp()+"的报警";
			String todayDate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			Integer counts=todaySendTimes.get(todayDate);
			if(counts==null){
				counts=0;
			}
			if(counts.intValue()<=3){
				for (String phoneNo : managerPhones) {
					MsgSenderThread thread=new MsgSenderThread(phoneNo, content);
					thread.start();
				}
			}
		} catch (Exception e) {
			
		}
	}
	
	public static void main(String[] args) {
		sendMarketDataMsgToManager("测试");
	}
}
