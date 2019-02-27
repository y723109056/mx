package com.mx.util;

public class ClientRefererUtil {
	
	public static String getHostUrl(String url){
		if(url!=null && url.length()>10){
			int n = url.indexOf("://");
			if(n>-1){
				int m = url.indexOf("/", n+3);
				if(m>-1){
					return url.substring(0, m);
				}
			}
		}
		return url;
	}
	
	
}
