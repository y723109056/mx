package com.mx.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


public class DojoUtil {
	
	/**
	 * 
	 * @param entities
	 * @param mapping
	 * @return
	 */
	public static JSONArray toJson(List<?> entities,Map<String,String> mapping){
		JSONArray ja = new JSONArray();
		for(Object entity : entities){
			JSONObject jo = new JSONObject();
			for(String key : mapping.keySet()){
				Object val = ReflectorUtil.getPropertyValue(entity,mapping.get(key));
				jo.put(key, val);
			}
			ja.add(jo);
		}
		return ja;
	}
	
	/**
	 * 转换字段为json数据
	 * @param entities
	 * @param colNames
	 * @return
	 */
	public static JSONArray toJson(List<?> entities, String colNames){
		Map<String,String> colMap = new HashMap<String, String>();
		if(!StringUtil.isNullOrEmpty(colNames) && colNames.contains(",")){
			for(String col : colNames.split(",")){
				colMap.put(col, col);
			}
		} else {
			colMap.put(colNames, colNames);
		}
		return toJson(entities, colMap);
	}
	
	/**
	 * List 所有对象字段转成json array
	 * @param entities
	 * @return
	 */
	public static JSONArray toJson(List<?> entities){
		JSONArray ja = new JSONArray();
		for(Object entity : entities){
			ja.add(bean2JsonObj(entity));
		}
		return ja;
	}
	
	/**
	 * bean 转Json 字符串
	 * @param obj
	 * @return
	 */
	public static String bean2JsonWithDateFormate(Object obj, String dataFormate){
		JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(dataFormate));
        return JSONObject.fromObject(obj, jsonConfig).toString();
	}
	
	/**
	 * bean 转Json 字符串
	 * @param obj
	 * @return
	 */
	public static String bean2Json(Object obj){
		JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        jsonConfig.registerJsonValueProcessor(BigDecimal.class, new DefaultValueProcessor());
        return JSONObject.fromObject(obj, jsonConfig).toString();
	}
	/**
	 * bean 转JsonObject
	 * @param obj
	 * @return
	 */
	public static JSONObject bean2JsonObj(Object obj){
		JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        return JSONObject.fromObject(obj, jsonConfig);
	}
	/**
	 * 根据指定字段转json
	 * @param obj
	 * @param mapping
	 * @return
	 */
	public static String bean2Json(Object obj, Map<String,String> mapping){
		JSONObject jo = new JSONObject();
		for(String key : mapping.keySet()){
			Object val = ReflectorUtil.getPropertyValue(obj,key);
			jo.put(key, val);
		}
		return jo.toString();
	}
	
	/**
	 * 根据指定字段转json
	 * @param obj
	 * @param mapping
	 * @return
	 */
	public static String bean2Json(Object obj, String colNames){
		Map<String,String> colMap = new HashMap<String, String>();
		if(!StringUtil.isNullOrEmpty(colNames) && colNames.contains(",")){
			for(String col : colNames.split(",")){
				colMap.put(col, null);
			}
		} else {
			colMap.put(colNames, null);
		}
		return bean2Json(obj, colMap);
	}
}
