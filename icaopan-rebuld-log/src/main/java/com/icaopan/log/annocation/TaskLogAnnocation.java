package com.icaopan.log.annocation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: LoggerBusiness.java 
 * @Package com.common.annotation 
 * @Description: TODO(日志类自定义注解) 
 * @author lifaxin  
 * @date 2015年4月29日 下午2:45:51 
 * @version V1.0   
 */
@Target(ElementType.METHOD)  							//标记 用于方法
@Retention(RetentionPolicy.RUNTIME)   					//编译前保留注释 ，VM舍弃
@Documented						    					//说明该注解将被包含在javadoc中   
public @interface TaskLogAnnocation {
	String tag(); //业务标签
}