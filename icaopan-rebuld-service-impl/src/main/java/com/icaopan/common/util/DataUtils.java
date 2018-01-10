package com.icaopan.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class DataUtils {

	public static boolean validePrice(String price){
		Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]*)?$");  
        Matcher match=pattern.matcher(price);  
        return match.matches();  
	}
	
	public static boolean valideQuantity(String quantity){
		boolean b= StringUtils.isNumeric(quantity);
		return b;
	}
	
	public static List<Integer> transeStringToListByDotSpliter(String str,String spliter){
		List<Integer> list=new ArrayList<Integer>();
		if(StringUtils.isNotEmpty(str)){
			String[] strs=str.split(spliter);
			for (String  s : strs) {
				if(StringUtils.isEmpty(s)){
					continue;
				}
				list.add(Integer.valueOf(s));
			}
		}
		return list;
	}
	
	public static List<String> transeStringToStringListByDotSpliter(String str,String spliter){
		List<String> list=new ArrayList<String>();
		if(StringUtils.isNotEmpty(str)){
			String[] strs=str.split(spliter);
			for (String  s : strs) {
				list.add(s);
			}
		}
		return list;
	}
}
