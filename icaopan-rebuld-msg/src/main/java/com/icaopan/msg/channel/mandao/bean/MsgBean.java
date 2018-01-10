package com.icaopan.msg.channel.mandao.bean;

import java.io.Serializable;

public class MsgBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String phone;
	private String content;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "MsgBean [phone=" + phone + ", content=" + content + "]";
	}

}
