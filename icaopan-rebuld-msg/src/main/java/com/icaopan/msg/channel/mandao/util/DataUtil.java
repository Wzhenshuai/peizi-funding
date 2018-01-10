package com.icaopan.msg.channel.mandao.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Joiner;

public class DataUtil {

	// 网上抄来的，将 int 转成字节
	public static byte[] i2b(int i) {
		return new byte[] { (byte) ((i >> 24) & 0xFF),
				(byte) ((i >> 16) & 0xFF), (byte) ((i >> 8) & 0xFF),
				(byte) (i & 0xFF) };
	}

	// 网上抄来的，将字节转成 int。b 长度不得小于 4，且只会取前 4 位。
	public static int b2i(byte[] b) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (b[i] & 0x000000FF) << shift;
		}
		return value;
	}
	
	public static byte[] long2Bytes(long num) {  
	    byte[] byteNum = new byte[8];  
	    for (int ix = 0; ix < 8; ++ix) {  
	        int offset = 64 - (ix + 1) * 8;  
	        byteNum[ix] = (byte) ((num >> offset) & 0xff);  
	    }  
	    return byteNum;  
	}  
	  
	public static long bytes2Long(byte[] byteNum) {  
	    long num = 0;  
	    for (int ix = 0; ix < 8; ++ix) {  
	        num <<= 8;  
	        num |= (byteNum[ix] & 0xff);  
	    }  
	    return num;  
	}  
	
	public static String splitArrayList(List<String> list){
		String str=Joiner.on(",").join(list); 
		return str;
	}
	
	public static List<String> makeArrayList(String str){
		List<String> result = Arrays.asList(StringUtils.split(str,",")); 
		return result;
	}
	
	public static String makeUuid(){
		return UUID.randomUUID().toString();
	}
	
	public static int calcPercent(long length,long finishLength){
		if(length==0){
			return 0;
		}
		double d=(double)finishLength/(double)length;
		d=d*100;
		String data=  new DecimalFormat("#").format(d);
		return Integer.valueOf(data);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		byte[] by=long2Bytes(100000000000000l);
		System.out.println(by.length);
		System.out.println(bytes2Long(by));
		System.out.println("张三你好".getBytes("utf-8").length);
	}
}
