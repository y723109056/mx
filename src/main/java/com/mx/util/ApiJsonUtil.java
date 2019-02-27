package com.mx.util;

import com.mx.reflector.PropertyInfo;
import com.mx.reflector.PropertyType;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 对外接口转JSON
 * Created by zhusw on 2017/8/24.
 */
public class ApiJsonUtil {
    public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static JSONObject toJson(Object data, String params){
        Map<String,String> paramsMap = getParamsMap(params);
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return toJson(data, paramsMap,df);
    }

    public static JSONObject toJson(Object data, String params,String dateForamt){
        Map<String,String> paramsMap = getParamsMap(params);
        SimpleDateFormat df = new SimpleDateFormat(dateForamt);
        return toJson(data, paramsMap,df);
    }

    private static Map<String,String> getParamsMap(String params){
        Map<String,String> paramsMap = new HashMap<String, String>();
        if(StringUtils.isNotBlank(params) && params.contains(",")){
            for(String col : params.split(",")){
                paramsMap.put(col.toLowerCase(), null);
            }
        }else{
            paramsMap.put(params.toLowerCase(), null);
        }
        return paramsMap;
    }

    private static JSONObject toJson(Object entity,Map<String,String> paramsMap,SimpleDateFormat df){
        PropertyInfo[] propertyInfos = ReflectorUtil.getProperties(entity.getClass());
        JSONObject json = new JSONObject();
        for(PropertyInfo pi : propertyInfos){
            if(!pi.getPropertyType().equals(PropertyType.Value)){
                continue;
            }
            if(paramsMap.containsKey(pi.getName().toLowerCase())){
                Object val = ReflectorUtil.getPropertyValue(entity, pi.getGetMethod());
                if(pi.getType().isAssignableFrom(String.class)){
                    if(val!=null){
                        json.put(pi.getName(), val.toString());
                    }else{
                        json.put(pi.getName(), "");
                    }
                }else if(pi.getType().isAssignableFrom(Date.class)){
                    if(val != null){
                        json.put(pi.getName(), df.format(val));
                    }else{
                        json.put(pi.getName(), null);
                    }
                }else{
                    if(val!=null){
                        json.put(pi.getName(),val);
                    }else{
                        json.put(pi.getName(),null);
                    }
                }
            }
        }
        return json;
    }

    public static boolean isJson(String content) {

        try{
            JSONObject.fromObject(content);
            return true;
        }catch (Exception e){

            return false;
        }

    }

}
