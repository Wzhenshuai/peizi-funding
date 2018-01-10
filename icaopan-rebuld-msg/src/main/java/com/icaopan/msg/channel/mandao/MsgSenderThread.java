package com.icaopan.msg.channel.mandao;

import com.icaopan.msg.MsgServer;

public class MsgSenderThread extends Thread {

	private String phoneNo;
	private String content;
	public MsgSenderThread(String phoneNo,String content){
		this.phoneNo=phoneNo;
		this.content=content;
	}
	@Override
	public void run() {
		MsgSender.sendMSG(phoneNo, content);
		MsgServer.addOnSendTimes();
	}
}
