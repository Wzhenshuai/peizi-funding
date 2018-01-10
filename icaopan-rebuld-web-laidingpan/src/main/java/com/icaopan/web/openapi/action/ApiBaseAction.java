package com.icaopan.web.openapi.action;

import com.alibaba.fastjson.JSONObject;
import com.icaopan.log.LogUtil;
import com.icaopan.web.common.Constants;
import com.icaopan.web.util.ConvertJsonHandle;

import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ApiBaseAction {
    protected Logger logger = LogUtil.getLogger(getClass());

    /**
     * 解密从移动端传递过来的参数
     *
     * @param paraJson
     * @return
     */
    protected JSONObject parseEncryptedParam(String paraJson) {
        JSONObject obj = null;
        try {
            obj = ConvertJsonHandle.fromJson(paraJson);
        } catch (Exception e) {
            logger.error("convert error", e);
        }
        return obj;
    }

    /**
     * 组装返回给移动端的结果集
     *
     * @param result
     * @return
     */
    protected Map<String, Object> makeApiResult(Map<String, Object> result) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put(Constants.INFO, ConvertJsonHandle.toDesJson(result));
        } catch (Exception e) {
            logger.error("convert to des json error", e);
        }
        return resultMap;
    }
}
