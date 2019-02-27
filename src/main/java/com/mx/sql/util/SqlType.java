package com.mx.sql.util;

import com.mx.util.DateUtil;
import com.mx.util.TypeUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;



/**
 * Sql类型转化,把值类型转化为Sql值类型
 */
public class SqlType {
	
	/**
	 * 指值类型转化为Sql字符串
	 * @param value
	 * @return
	 */
	public static String toSqlString(Object value)
	{
		if(value!=null)
		{
			if(value instanceof String)
			{
				return (String)value;
			}
			else if(value instanceof Date)
			{
				Date d = (Date)value;
				if(DateUtil.hasTime(d))
				{
					return new Timestamp(d.getTime()).toString(); 
				}
				else
				{
					return new java.sql.Date(d.getTime()).toString();
				}
			}
			else if(value instanceof java.sql.Date)
			{
				return value.toString();
			}
			else if(value instanceof Timestamp)
			{
				return value.toString();
			}
			else if(value instanceof Collection)
			{
				String str="";
				for(Object val : (Collection<?>)value)
				{
					str+=(str.equals(""))?SqlType.toSqlString(val):","+SqlType.toSqlString(val);
				}
				return str;
			}
			else if(value.getClass().isArray())
			{
				String str="";
				Object[] vals = (Object[])value;
				for(Object val : vals)
				{
					str+=(str.equals(""))?SqlType.toSqlString(val):","+SqlType.toSqlString(val);
				}
				return str;
			}
			else return (String) TypeUtil.changeType(value, String.class);
		}
		else return "";
	}
	
	/**
	 * 把值类型转化为SqlType类型
	 * @param value
	 * @return
	 */
	public static Object toSqlValue(Object value)
	{
		if(value!=null)
		{
			if(value instanceof Date)
			{
				Date d = (Date)value;
				if(DateUtil.hasTime(d))
				{
					return new Timestamp(d.getTime());
				}
				else
				{
					return new java.sql.Date(d.getTime());
				}
			}
			else return value;
		}
		else return null;
	}
	
	public static Class<?> parserValueType(String type){
		if("String".equalsIgnoreCase(type)){
			return String.class;
		}else if("Date".equalsIgnoreCase(type)){
			return Date.class;
		}else if("Integer".equalsIgnoreCase(type)){
			return Integer.class;
		}else if("Long".equalsIgnoreCase(type)){
			return Long.class;
		}else if("BigDecimal".equalsIgnoreCase(type)){
			return BigDecimal.class;
		}else if("Double".equalsIgnoreCase(type)){
			return Double.class;
		}else if("Float".equalsIgnoreCase(type)){
			return Float.class;
		}else if("Char".equalsIgnoreCase(type)){
			return Character.class;
		}else{
			return null;
		}
	}
}
