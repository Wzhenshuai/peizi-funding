package com.icaopan.log;

import java.lang.annotation.Annotation;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icaopan.log.annocation.BusinessLogAnnocation;
import com.icaopan.log.annocation.MoneyAdjustLogAnnocation;
import com.icaopan.log.annocation.TaskLogAnnocation;
import com.icaopan.log.annocation.TradeLogAnnocation;
@Service("serviceLogInterepter")
public class ServiceLogInterepter implements MethodInterceptor {
 
	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		Object obj = null;
		Logger logger=null;
		String tag=null;
		try {
			if (mi.getMethod().isAnnotationPresent(TradeLogAnnocation.class)) {
				Annotation annotation = mi.getMethod().getAnnotation(TradeLogAnnocation.class);
				tag = ((TradeLogAnnocation) annotation).tag();
				logger=LogUtil.getTradeLogger();
			}else if(mi.getMethod().isAnnotationPresent(MoneyAdjustLogAnnocation.class)){
				Annotation annotation = mi.getMethod().getAnnotation(MoneyAdjustLogAnnocation.class);
				tag = ((MoneyAdjustLogAnnocation) annotation).tag();
				logger=LogUtil.getMoneyAdjustLogger();
			}else if(mi.getMethod().isAnnotationPresent(TaskLogAnnocation.class)){
				Annotation annotation = mi.getMethod().getAnnotation(TaskLogAnnocation.class);
				tag = ((TaskLogAnnocation) annotation).tag();
				logger=LogUtil.getTaskLogger();
			}else if(mi.getMethod().isAnnotationPresent(BusinessLogAnnocation.class)){
				Annotation annotation = mi.getMethod().getAnnotation(BusinessLogAnnocation.class);
				tag = ((BusinessLogAnnocation) annotation).tag();
				logger=LogUtil.getBusinessLogger();
			}
			if(logger!=null){
				try {
					Object[] args=mi.getArguments();
					logger.info("----------------------------------");
					logger.info("操作业务:"+tag);
					logger.info("调用方法:"+mi.getMethod().getName());
					logger.info("传入参数:"+JSONArray.toJSONString(args));
				} catch (Exception e) {
					logger.error("ServiceLogInterepter",e);
				}
			}
			obj = mi.proceed();
			try {
				if(logger!=null){
					logger.info("返回结果:"+JSONObject.toJSON(obj));
				}
			} catch (Exception e) {
				
			}
			
		} catch (Exception e) {
			if(logger!=null){
				logger.error(tag,e);
			}else{
				logger=LogUtil.getLogger(getClass());
				logger.error("ServiceLogInterepter",e);
			}
			throw new RuntimeException(e.getMessage());
		}
		return obj;
	}

}
