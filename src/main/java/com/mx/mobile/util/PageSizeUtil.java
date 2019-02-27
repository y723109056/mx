package com.mx.mobile.util;

import com.mx.page.IPageSize;
import com.mx.page.PageLimit;
import com.mx.page.PageSize;
import net.sf.json.JSONObject;

public class PageSizeUtil {

	public static IPageSize getPageSize(JSONObject json,int pageSize) {
		pageSize = json.containsKey("pageSize") ? Integer.parseInt(json.getString("pageSize")) : pageSize;
		int pageNum = json.containsKey("pageNum") ? Integer.parseInt(json.getString("pageNum")) : 1;
		return new PageSize(pageSize, pageNum);
	}
	
	public static IPageSize getPageLimit(JSONObject json,int pageSize){
		pageSize = json.containsKey("pageSize") ? Integer.parseInt(json.getString("pageSize")) : pageSize;
		int offset = json.containsKey("offset") ? Integer.parseInt(json.getString("offset")) : 0;
		int limit = offset+pageSize;
		return new PageLimit(offset, limit,false);
	}

	public static IPageSize getPageLimit(JSONObject json,int pageSize,boolean hasCount){
		pageSize = json.containsKey("pageSize") ? Integer.parseInt(json.getString("pageSize")) : pageSize;
		int offset = json.containsKey("offset") ? Integer.parseInt(json.getString("offset")) : 0;
		if(offset>0){
			hasCount = false;
		}
		int limit = offset+pageSize;
		return new PageLimit(offset, limit,hasCount);
	}
	
	public static IPageSize getPageLimit(int offset,int pageSize){
		int limit = offset+pageSize;
		return new PageLimit(offset, limit,false);
	}
}
