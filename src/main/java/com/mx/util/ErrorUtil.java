package com.mx.util;

import org.apache.commons.logging.Log;

public class ErrorUtil {
	
	public static void logError(Exception e,Log log){
//		e.printStackTrace();
		log.error("error:"+e.getMessage(),e);
	}
	
}
