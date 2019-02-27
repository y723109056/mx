package com.mx.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数据类型转换
 */
public class ConvertTypeUtil {
	
	/**
     * 
     * @param map
     * @param params
     */
    public static void convertIntToMap(Map<String,Object> map,String params){
    	convertIntToMap(map,params.split("\\,"));
    }
    
	/**
     * 
     * @param map
     * @param params
     */
    public static void convertIntArrayToMap(Map<String,Object> map,String params){
    	convertIntArrayToMap(map,params.split("\\,"));
    }
    
	/**
     * 
     * @param map
     * @param params
     */
    public static void convertArrayToMap(Map<String,Object> map,String params){
    	convertArrayToMap(map,params.split("\\,"));
    }
    
    /**
     * 格式化Map对像里的整形
     * @param map
     * @param params
     */
    public static void convertIntToMap(Map<String,Object> map,String[] params){
    	convertTypeToMap(map,params,Integer.class);
    }
    
    public static void convertIntArrayToMap(Map<String,Object> map,String[] params){
    	convertTypeArrayToMap(map,params,Integer.class);
    }

	/**
     * 
     * @param map
     * @param params
     */
    public static void convertDateToMap(Map<String,Object> map,String params){
    	convertDateToMap(map,params.split("\\,"));
    }
    
    /**
     * 格式化Map对像里的日期
     * @param map
     * @param params
     */
    public static void convertDateToMap(Map<String,Object> map,String[] params){
    	convertTypeToMap(map,params,Date.class);
    }
    
    /**
     * 
     * @param map
     * @param params
     */
    public static void convertDecimalToMap(Map<String,Object> map,String params){
    	convertDecimalToMap(map,params.split("\\,"));
    }
    
    /**
     * 格式化Map对像里的日期
     * @param map
     * @param params
     */
    public static void convertDecimalToMap(Map<String,Object> map,String[] params){
    	convertTypeToMap(map,params,BigDecimal.class);
    }
    
    public static void convertTypeToMap(Map<String,Object> map,String params,Class<?> clazz){
    	convertTypeToMap(map,params.split("\\,"),BigDecimal.class);
    }
    	
    public static void convertTypeToMap(Map<String,Object> map,String[] params,Class<?> clazz){
    	for(String param : params){
    		Object value = map.get(param);
    		if(value instanceof String){
    			try {
    				Object changeValue = TypeUtil.changeType(value, clazz);
    				if(changeValue!=null)
    					map.put(param, changeValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
    	}
    }
    
    public static void convertTypeArrayToMap(Map<String,Object> map,String[] params,Class<?> clazz){
    	for(String param : params){
    		Object value = map.get(param);
    		if(value instanceof String){
    			try {
    				String[] valStrs = value.toString().split("\\,");
    				List<Object> valList = new ArrayList<Object>();
    				for(int i=0;i<valStrs.length;i++){
	    				Object changeValue = TypeUtil.changeType(valStrs[i], clazz);
	    				if(changeValue!=null)
	    					valList.add(changeValue);
    				}
    				if(valList.size()>0)
    					map.put(param, valList.toArray());
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
    	}
    }
    
    public static void convertArrayToMap(Map<String,Object> map,String[] params){
    	for(String param : params){
    		Object value = map.get(param);
    		if(value instanceof String){
    			try {
    				String[] valStrs = value.toString().split("\\,");
    				if(valStrs.length>0)
    					map.put(param, valStrs);
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
    	}
    }
	
}
