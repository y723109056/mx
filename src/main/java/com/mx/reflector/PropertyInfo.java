package com.mx.reflector;

import com.mx.util.StringUtil;
import com.mx.util.TypeUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PropertyInfo {
	private Method getMethod;
	private Method setMethod;
	private Field field;
	private String name;
	private PropertyType type;
	
	public Class<?> getType()
	{
		if(getMethod!=null)return getMethod.getReturnType();
		else if(setMethod!=null)return setMethod.getParameterTypes()[0];
		else return null;
	}
	
	public String getName()
	{
		if(StringUtil.isNullOrEmpty(name))
		{
			if(getMethod!=null)name = getMethod.getName().substring(3);
			else if(setMethod!=null)name = setMethod.getName().substring(3);
		}
		return name.substring(0,1).toLowerCase()+name.substring(1);
	}
	
	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
	
	public Method getGetMethod() {
		return getMethod;
	}

	public void setGetMethod(Method getMethod) {
		this.getMethod = getMethod;
	}

	public Method getSetMethod() {
		return setMethod;
	}

	public void setSetMethod(Method setMethod) {
		this.setMethod = setMethod;
	}
	
	public PropertyType getPropertyType()
	{
		return this.type;
	}
	
	public void setPropertyType(PropertyType type)
	{
		this.type = type;
	}

	public PropertyInfo()
	{
		this.name = "";
		this.type = PropertyType.Value;
	}
	
	public PropertyInfo(Method get,Method set)
	{
		this.getMethod=get;
		this.setMethod=set;
		this.name = "";
		this.type = PropertyType.Value;
	}
	
	public Object getValue(Object obj){
		Object value = null;
		if(this.getMethod!=null){
			try{
				value = this.getMethod.invoke(obj,new Object[0]);
			}catch(Exception e){
				throw new RuntimeException(StringUtil.format("动态获取属性值出错 函数:{0} 对像 {1} ",this.getMethod,obj.getClass().getName()));
			}
		}
		return value;
	}
	
	public void setValue(Object obj,Object value)
	{
		if(this.setMethod!=null){
			Class<?> clazz = this.setMethod.getParameterTypes()[0];
			try{
				if(clazz.isInstance(value)){
					this.setMethod.invoke(obj,new Object[]{value});
				}else{
					this.setMethod.invoke(obj,new Object[]{TypeUtil.changeType(value, clazz)});
				}
			}catch(Exception e){
				throw new RuntimeException(StringUtil.format("动态对像属性赋值出错 函数:{0} 对像 {1} 值 {2} 值类型 {3}",this.setMethod,obj.getClass().getName(),value,value.getClass().getName()));
			}
		}
	}
}
