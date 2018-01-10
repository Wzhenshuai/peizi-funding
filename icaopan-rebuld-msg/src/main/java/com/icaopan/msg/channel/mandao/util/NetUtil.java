package com.icaopan.msg.channel.mandao.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class NetUtil {
	

	/**
	 * 获取本机IP地址
	 * 
	 * @return
	 */
	public static String getLocalIp() {
		List<String> ipList=new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface
					.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
						.nextElement();
				if (netInterface.isLoopback() || netInterface.isVirtual()
						|| !netInterface.isUp()) {
					continue;
				} else {
					Enumeration<InetAddress> addresses = netInterface
							.getInetAddresses();
					while (addresses.hasMoreElements()) {
						ip = addresses.nextElement();
						if (ip != null && ip instanceof Inet4Address) {
							ipList.add(ip.getHostAddress()) ;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DataUtil.splitArrayList(ipList);
	}

	/**
	 * 获取本机mac地址
	 * 
	 * @return
	 */
	public static String getLocalMac() {
		try {
			Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface
					.getNetworkInterfaces();
			byte[] mac = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
						.nextElement();
				if (netInterface.isLoopback() || netInterface.isVirtual()
						|| !netInterface.isUp()) {
					continue;
				} else {
					mac = netInterface.getHardwareAddress();
					if (mac != null) {
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < mac.length; i++) {
							sb.append(String.format("%02X%s", mac[i],
									(i < mac.length - 1) ? "-" : ""));
						}
						if (sb.length() > 0) {
							return sb.toString();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getComputerName() {
		String computerName = System.getenv().get("COMPUTERNAME");
		return computerName;
	}

	public static String getOpType() {
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
			return "window";
		}
		return "linux";
	}

	public static InputStream getInputStream(String sInputString) {
		if (sInputString != null && !sInputString.trim().equals("")) {
			try {
				ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(
						sInputString.getBytes("gbk"));
				return tInputStringStream;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	public static String readMessageFromInputStream(InputStream input) throws Exception{
		
		BufferedReader sin = new BufferedReader(new InputStreamReader(input,"gbk")); 
		String msg=null;
		
		while((msg=sin.readLine())!=null){
			return msg;
		}
		return msg;
	}

	public static void main(String[] args) {
		System.out.println("Ip:" + getLocalIp());
//		System.out.println("Mac:" + getLocalMac());
//		System.out.println("op:" + getOpType());
//		System.out.println("cp name:" + getComputerName());
		
	}
}
