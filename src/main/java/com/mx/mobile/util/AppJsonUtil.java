package com.mx.mobile.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mx.mobile.constant.DateFormatConst;
import com.mx.reflector.PropertyInfo;
import com.mx.reflector.PropertyType;
import com.mx.util.ReflectorUtil;
import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AppJsonUtil {
	
	public static String containsKeys(JSONObject data,String params){
		if(StringUtils.isNotBlank(params) && params.contains(",")){
			for(String param : params.split(",")){
				if(!data.containsKey(param)||StringUtils.isBlank(data.getString(param)))
					return param;
			}
		}else{
			if(!data.containsKey(params)||StringUtils.isBlank(data.getString(params)))
				return params;
		}
		return null;
	}
	
	public static JSONObject toJson(Object data, String params){
		Map<String,String> paramsMap = getParamsMap(params);
		SimpleDateFormat df = new SimpleDateFormat(DateFormatConst.DEFAULT_DATE_FORMAT);
		return toJson(data, paramsMap,df);
	}
	
	public static JSONObject toJson(Object data, String params,String dateFormat){
		Map<String,String> paramsMap = getParamsMap(params);
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		return toJson(data, paramsMap,df);
	}
	
	public static JSONArray toJsonArray(List<?> dataList, String params){
	    return listToArray(dataList, params, DateFormatConst.DEFAULT_DATE_FORMAT);
	}
	
	public static JSONArray listToArray(List<?> objList,String params, String dateFormat){
		JSONArray array=new JSONArray();
		Map<String,String> paramsMap = getParamsMap(params);
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		for (Object entity : objList) {
			JSONObject ja = toJson(entity,paramsMap,df);
			array.add(ja);
		}
		return array;
	}
	
	public static Map<String,String> getParamsMap(String params){
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
	
	public static JSONObject toJson(Object entity,Map<String,String> paramsMap,SimpleDateFormat df){
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
						json.put(pi.getName(), "");
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
	
}
