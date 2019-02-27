package com.mx.mobile.util;

import com.mx.collection.DataSet;
import com.mx.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonToTableUtil {

	public DataSet getDataSetByJson(JSONObject root){
		JSONArray dja = (JSONArray)root.get("data");
		if(dja!=null){
			for(int i=0;i<dja.size();i++){
				JSONObject djo = (JSONObject)dja.get(i);
				for(Object key : djo.keySet()){
					String propertName = (String)key;
					String columnName = propertyToColumn(propertName);
					
				}
			}
		}
		return null;
	}
	
	private String propertyToColumn(String propertyName){
		StringBuffer colBf = new StringBuffer();
		if(StringUtil.isNotEmpty(propertyName)){
			for (int i = 0; i < propertyName.length(); i++) {
				if(Character.isUpperCase(propertyName.charAt(i))){
					colBf.append("_");
				}
				colBf.append(propertyName.charAt(i));
			}
		}
		return colBf.toString().toUpperCase();
	}
	
}
