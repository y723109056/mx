package com.mx.util;

import java.math.BigDecimal;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DefaultValueProcessor implements JsonValueProcessor {

	@Override
	public Object processArrayValue(Object value, JsonConfig conf) {
		return process(value);
	}

	@Override
	public Object processObjectValue(String key, Object value, JsonConfig conf) {
		return process(value);
	}

	private Object process(Object value){
		if(value!=null){
			BigDecimal bd = (BigDecimal) value;
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			return bd.toString();
		} else {
			return "";
		}
		
	}
	
}
