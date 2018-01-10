/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.icaopan.sys.util;

import com.google.common.collect.Lists;
import com.icaopan.admin.util.SpringContextHolder;
import com.icaopan.sys.dao.DictMapper;
import com.icaopan.sys.model.Dict;

import java.util.List;

/**
 * 字典工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class DictUtils {
	
	private static DictMapper dictDao = SpringContextHolder.getBean(DictMapper.class);

	public static String getDicOptions(String type){
		StringBuffer sb = new StringBuffer();
		List<Dict> getDictList=getDictList(type);
		for (Dict dict : getDictList) {
			sb.append(String.format("<option value='%s'>%s</option>", dict.getValue(),dict.getLabel()));
		}
		return sb.toString();
	}
	public static List<Dict> getDictList(String type){
		Dict dict=new Dict();
		dict.setType(type);
		List<Dict> dictList = dictDao.findList(dict);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
}
