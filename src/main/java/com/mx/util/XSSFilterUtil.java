package com.mx.util;

import java.text.SimpleDateFormat;

public class XSSFilterUtil {
	
	public static boolean isJsonString(String value){
		if(value!=null && value.length()>2){
			String str = value.trim();
			if(str.length()>2){
				if(str.charAt(0)=='{' && str.charAt(str.length()-1)=='}')
					return true;
				if(str.charAt(0)=='[' && str.charAt(str.length()-1)==']')
					return true;
			}
		}
		return false;
	}

    public static String cleanXSS(String value) {
//        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//        value = value.replaceAll("%3C", "&lt;").replaceAll("%3E", "&gt;");
////        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
////        value = value.replaceAll("%28", "&#40;").replaceAll("%29", "&#41;");
//        value = value.replaceAll("'", "&#39;");
//
////		if(!isValidDate(value)){
////			value = value.replaceAll("-", "&macr;");
////		}
//        value = value.replaceAll("eval\\((.*)\\)", "");
//        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
//        value = value.replaceAll("script", "");
        return value;
    }

	private static boolean isValidDate(String str) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (Exception e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}
	
}
