package com.mx.reader;

import com.mx.collection.DataRow;
import com.mx.util.ReflectorUtil;
import com.mx.util.StringUtil;
import com.mx.util.TypeUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DefaultObjectReader implements IPropertyReader<Object> {

	public Object getValue(Object obj, String propertyName) {
		if(obj instanceof Map){
			Map<?,?> map = (Map<?,?>)obj;
			if(map.containsKey(propertyName))
				return map.get(propertyName);
			else return null;
		}else if(obj instanceof DataRow){
			DataRow row = (DataRow)obj;
			return row.get(propertyName);
		}else if(TypeUtil.isValue(obj)){
			return obj;
		}else{
			return ReflectorUtil.getPropertyValue(obj, propertyName);
		}
	}

	public Object getValue(Object obj, int propertyIndex) {
		if(obj instanceof List){
			List<?> list = (List<?>)obj;
			if(propertyIndex<list.size())
				return list.get(propertyIndex);
			else throw new RuntimeException(StringUtil.format("索引超出集合长度范围 {0} {1}", Arrays.toString(list.toArray()), propertyIndex));
		}else if(TypeUtil.isArray(obj)){
			Object[] objs = ((Object[])obj);
			if(propertyIndex<objs.length){
				return objs[propertyIndex];
			}
			else throw new RuntimeException(StringUtil.format("索引超出数组长度范围 {0} {1}",Arrays.toString(objs),propertyIndex));
		}else if(obj instanceof DataRow){
			DataRow row = (DataRow)obj;
			row.get(propertyIndex);
		}else if(TypeUtil.isValue(obj)){
			return obj;
		}
		if(obj!=null)throw new RuntimeException("对像属性不支持,索引读取");
		return null;
	}

}
