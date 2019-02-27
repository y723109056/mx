package com.mx.mobile.util;

import com.mx.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RequestMapUtil {

	public static String paramsIsNotEmpty(HttpServletRequest request,String params){
		String[] paramNames = params.split("\\,",-1);
		String result = null;
		for(String paramName : paramNames){
			String paramValue = request.getParameter(paramName);
			if(StringUtil.isNullOrEmpty(paramValue)){
				if(result == null)
					result = paramName;
				else
					result += ","+paramName;
			}
		}
		return result;
	}

	/**
	 * 得到提交参数
	 * @param request
	 * @param params
	 * @return
	 */
	public static Map<String,Object> getMapOfRequest(HttpServletRequest request,String params){
		String[] paramNames = params.split("\\,",-1);
		Map<String,Object> map = new HashMap<String,Object>();
		for(String paramName : paramNames){
			String paramValue = request.getParameter(paramName);
			if(StringUtil.isNotEmpty(paramValue)){
				map.put(paramName, paramValue);
			}
		}
		return map;
	}
	
	/**
	 * 打印
	 * @param map
	 * @return
	 */
	public static String getDebugInfoOfMap(Map<String,Object> map){
		StringBuilder sb = new StringBuilder();
		for(String key : map.keySet()){
			sb.append(" "+key+":"+map.get(key));
		}
		return sb.toString();
	}
	
}
