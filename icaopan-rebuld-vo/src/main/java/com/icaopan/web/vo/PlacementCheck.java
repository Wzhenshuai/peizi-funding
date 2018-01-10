package com.icaopan.web.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlacementCheck {

	private static Map<String,List<TdxFillItemVO>> checkPlacementMap=new HashMap<String,List<TdxFillItemVO>>();
	
	public static void clearMap(){
		checkPlacementMap.clear();
	}
	
	public synchronized static void put(String account,List<TdxFillItemVO> list){
		checkPlacementMap.put(account, list);
	}
	
	public static List<TdxFillItemVO> getList(){
		List<TdxFillItemVO> list=new ArrayList<TdxFillItemVO>();
    	if(!checkPlacementMap.isEmpty()){
    		for(String account:checkPlacementMap.keySet()){
    			List<TdxFillItemVO> childList=checkPlacementMap.get(account);
    			list.addAll(childList);
    		}
    	}
    	return list;
	}
	
}
