package com.icaopan.common.util;

import com.icaopan.marketdata.constant.ExchangeConsts;
import org.apache.commons.lang.StringUtils;

/**
 * Created by RoyLeong @royleo.xyz on 2017/4/6.
 */
public class SecurityUtil {

    public static String getSecurityCodeById(String internalSecurityId) {
        if(StringUtils.isEmpty(internalSecurityId)){
            return "";
        }
        if(internalSecurityId.length()<6){
            return internalSecurityId;
        }
        return internalSecurityId.substring(0,6);
    }

    public static String getInternalSecurityIdBySecurityCode(String securityCode) {
        String internalSecurityId ="";
        if (securityCode==null||securityCode.length()==0){
            return internalSecurityId;
        }
        if (securityCode.startsWith("6")){
            internalSecurityId=securityCode+"_XSHG";
        }else if (securityCode.startsWith("0")||securityCode.startsWith("3")){
            internalSecurityId=securityCode+"_XSHE";
        }
        return internalSecurityId;
    }

    /**
     * 转换自选股的代码->标准股票代码
     * @param selfStockCode
     * @return
     */
    public static String traseStockCodeBySelfStockCode(String selfStockCode){
    	if(StringUtils.isEmpty(selfStockCode)){
    		return "";
    	}
    	return selfStockCode.substring(0,selfStockCode.indexOf("."));
    }

    public static String traseToSelfStockCode(String selfStockCode){
    	String code=traseStockCodeBySelfStockCode(selfStockCode);
    	String firstLetter=code.substring(0,1);
    	if(firstLetter.equals("6")){
    		code=code+".XSHG";
    	}else{
    		code=code+".XSHE";
    	}
    	return code;
    }

    /**
     * 股票代码转换为标准代码
     * 比如：600022 --> 600022.XSHE
     * */
    public static String codeToSelfStockCode(String code){
        if(code.startsWith("6")){
            code=code + "." + ExchangeConsts.SHANG_JIAO_SUO;
        }else{
            code=code + "." + ExchangeConsts.SHEN_JIAO_SUO;
        }
        return code;
    }
    
}
