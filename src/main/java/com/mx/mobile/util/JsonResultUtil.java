package com.mx.mobile.util;

import com.mx.mobile.response.CodeEnum;
import net.sf.json.JSONObject;

public class JsonResultUtil {

    public static void setSuccess(JSONObject resultJson){
        resultJson.put("code", CodeEnum.CODE_100.getCode());
        resultJson.put("msg", CodeEnum.CODE_100.getMsg());
    }
}
