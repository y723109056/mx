package com.mx.mobile.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;

public class ErrorUtil {

	/**
	 * 统一输出日志
	 * @param log
	 * @param message
	 * @param e
	 */
	public static void logError(String message,Log log,Exception e,HttpServletRequest request){
		Enumeration<String> paramEnumeration =request.getParameterNames();
		StringBuilder paramInfo = new StringBuilder();
		while(paramEnumeration.hasMoreElements()){
			String paramName = paramEnumeration.nextElement();
			String paramValue = request.getParameter(paramName);
			paramInfo.append(" "+paramName+":"+paramValue);
		}
		log.error(message+paramInfo+" error:"+e.getMessage(), e);
	}
	
}
