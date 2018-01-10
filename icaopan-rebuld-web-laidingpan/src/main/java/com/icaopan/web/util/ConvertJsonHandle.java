package com.icaopan.web.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icaopan.web.common.Constants;
import com.icaopan.web.util.security.Des3;

import java.util.List;
import java.util.Map;

/**
 * @author lifaxin
 * @version V1.0
 * @Title: ConvertJsonHandle.java
 * @Package com.toft.gqd.openapi_v1.handle
 * @date 2015年5月15日 上午9:54:52
 */
public class ConvertJsonHandle {

    // 判断接口是否需要加密
    private static boolean isOpenDes = true;

    /**
     * 解析Json串
     *
     * @param json
     * @return
     * @throws Exception
     */
    public static JSONObject fromJson(String json) throws Exception {
        if (isOpenDes) {
            json = Des3.decode(json);
        }
        return JSONObject.parseObject(json);
    }

    /**
     * 生成Json串
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static String toJson(Object value) throws Exception {
        String resultJson = "";

        if (value instanceof List) {
            resultJson = JSONArray.toJSONString(value).toString();
        } else if (value instanceof String) {
            resultJson = value.toString();
        } else {
            resultJson = JSONObject.toJSONString(value).toString();
        }

        if (isOpenDes) {
            resultJson = Des3.encode(resultJson);
        }

        return resultJson;
    }

    /**
     * 处理加密串 （所有返回值， 都进这个方法，为了后来全加密 ，做准备）
     *
     * @param result
     * @return
     * @throws Exception
     */
    public static Map<String, Object> toDesJson(Map<String, Object> result) throws Exception {
        Object resultValue = result.get(Constants.RESULT);
        if (isOpenDes) {
            result.put(Constants.RESULT, toJson(resultValue));
        }
        return result;
    }

}
