package com.mx.reflector;

import java.util.Map;

public class ClassInfo {
	
	private PropertyInfo[] properties;
	private Map<String,PropertyInfo> propertyMap;

	public PropertyInfo[] getProperties() {
		return properties;
	}

	public void setProperties(PropertyInfo[] properties) {
		this.properties = properties;
	}

	public Map<String, PropertyInfo> getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(Map<String, PropertyInfo> propertyMap) {
		this.propertyMap = propertyMap;
	}
	
	/**
	 * 是否包属性
	 * @param propertyName
	 * @return
	 */
	public boolean contains(String propertyName){
		if(this.propertyMap!=null){
			return this.propertyMap.containsKey(propertyName);
		}
		return false;
	}
	
	/**
	 * 得到属性信息
	 * @param propertyName
	 * @return
	 */
	public PropertyInfo getProperty(String propertyName){
		if(this.propertyMap!=null && this.propertyMap.containsKey(propertyName)){
			return this.propertyMap.get(propertyName);
		}
		return null;
	}
}
