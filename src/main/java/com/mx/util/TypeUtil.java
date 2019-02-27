package com.mx.util;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 值类型转化类
 */
public class TypeUtil {
	
	/**
	 * 把值类型转化为指定的类型
	 * @param obj 值
	 * @param clazz 目标类型
	 * @return 转化类型后的值
	 */
	@SuppressWarnings("rawtypes")
	public static Object changeType(Object obj,Class clazz)
	{
		if(obj!=null)
		{
			if(clazz.isInstance(obj))return obj;
			else
			{
				String s=obj.toString();
				if (clazz.equals(Integer.class)) {
					if(obj instanceof Number)return ((Number)obj).intValue();
					else return (!s.equals(""))?Integer.parseInt(s):new Integer(0);
		        }else if(clazz.equals(Long.class)){
					if(obj instanceof Number)return ((Number)obj).longValue();
					else return (!s.equals(""))?Long.parseLong(s):new Long(0);
				}else if (clazz.equals(Float.class)){
		        	if(obj instanceof Number)return ((Number)obj).floatValue();
		        	else return (!s.equals(""))?Float.parseFloat(s):new Float(0);
		        }else if (clazz.equals(Double.class)){
		        	if(obj instanceof Number)return ((Number)obj).doubleValue();
		        	else return (!s.equals(""))?Double.parseDouble(s):new Double(0);
		        }else if (clazz.equals(BigDecimal.class)){
		        	return BigDecimalConvert.convert(obj);
		        }else if (clazz.equals(String.class)){
		        	return s;
		        }else if(clazz.equals(Character.class)){
		        	return s.charAt(0);
		        }else if (Date.class.isAssignableFrom(clazz)){
		        	if(!"".equals(obj)){
		        		Date d = DateConvert.convert(obj);
		        		if(clazz.equals(Timestamp.class))return new Timestamp(d.getTime());
		        		else if(clazz.equals(java.sql.Date.class))return new java.sql.Date(d.getTime());
		        		else if(clazz.equals(Time.class))return new Time(d.getTime());
		        		else return d;
		        	}
		        	return (!s.equals(""))?DateConvert.convert(obj):null;
		        }else if (clazz.equals(Boolean.class)){
		            if("True".equalsIgnoreCase(s))return true;
		            else if("False".equalsIgnoreCase(s))return false;
		            else{
		            	if(obj instanceof Number){
		            		return ((Number)obj).doubleValue()>0;
		            	}else{
		            		return Boolean.parseBoolean(s);
		            	}
		            }
		        }
			}
		}
		return obj;
	}
	
	public static int compareTo(Object val1,Object val2){
		if(val1!=null && val2!=null){
			if(val1 instanceof Number && val2 instanceof Number){
				Number n1 = (Number)val1;
				Number n2 = (Number)val2;
				if(n1.longValue()>n2.longValue())return 1;
				else if(n1.longValue()==n2.longValue()){
					if(n1.doubleValue()>n2.doubleValue()){
						return 1;
					}else if(n1.doubleValue()==n2.doubleValue()){
						return 0;
					}
					else return -1;
				}else{
					return -1;
				}
			}
			else if(val1 instanceof Date && val2 instanceof Date){
				if(((Date)val1).getTime()>((Date)val2).getTime()){
					return 1;
				}
				else if(((Date)val1).getTime()==((Date)val2).getTime()){
					return 0;
				}
				else return -1;
			}
			else{
				return val1.toString().compareTo(val2.toString());
			}
		}else if(val1!=null){
			return 1;
		}else if(val2!=null){
			return -1;
		}
		return 0;
	}
	
	/**
	 * 判断对像是否为值类型
	 * @param obj
	 */
	public static boolean isValue(Object obj)
	{
		if (obj instanceof String || obj instanceof Date || obj instanceof Boolean || obj instanceof Character)
		{
			return true;
		}
		else if (obj instanceof Number)
        {
        	return true;
        }
		return obj==null;
	}
	
	/**
	 * 判断对像是否为值类型
	 * @param obj
	 */
	public static boolean isCollection(Object obj)
	{
		if(obj.getClass().isArray())return true;
		else if(obj instanceof Collection)return true;
		else return false;
	}
	
	/**
	 * 判断对像是否为数组
	 * @param obj
	 * @return
	 */
	public static boolean isArray(Object obj){
		if(obj!=null){
			if(obj.getClass().isArray())return true;
		}
		return false;
	}
	
	/**
	 * 判断类型是否为值类型
	 * @param clazz
	 * @return
	 */
	public static boolean isValueType(Class<?> clazz)
	{
		if (String.class.equals(clazz) || Character.class.equals(clazz))return true;
		else if(Number.class.isAssignableFrom(clazz))return true;
		else if(Date.class.isAssignableFrom(clazz))return true;
		else if(Boolean.class.isAssignableFrom(clazz))return true;
		else if(clazz.getName().equals("int") || clazz.getName().equals("long") || clazz.getName().equals("boolean") || clazz.getName().equals("double") || clazz.getName().equals("float"))return true;
		return false;
	}
	
	/**
	 * 判断对像是否为集合类型
	 * @param clazz 集合类型
	 * @return
	 */
	public static boolean isCollectionType(Class<?> clazz)
	{
		if (List.class.isAssignableFrom(clazz) || Set.class.isAssignableFrom(clazz))return true;
		else if(Collection.class.isAssignableFrom(clazz) || Array.class.isAssignableFrom(clazz))return true;
		else return false;
	}
	
	public static Object cloneValue(Object value)
	{
		if(value instanceof Long)return new Long((Long)value);
		else if(value instanceof Integer)return new Integer((Integer)value);
		else if(value instanceof Double)return new Double((Double)value);
		else if(value instanceof Float)return new Float((Float)value);
		else if(value instanceof BigDecimal)return BigDecimal.valueOf(((BigDecimal)value).doubleValue());
		else if(value instanceof String)return new String((String)value);
		else if(value instanceof Boolean)return new Boolean((Boolean)value);
		else if(value instanceof Date)return new Date(((Date)value).getTime());
		else return null;
	}
	
	public static Class<?> getValueType(Object value)
	{
		if(value instanceof Long)return Long.class;
		else if(value instanceof Integer)return Integer.class;
		else if(value instanceof Double)return Double.class;
		else if(value instanceof Float)return Float.class;
		else if(value instanceof BigDecimal)return BigDecimal.class;
		else if(value instanceof String)return String.class;
		else if(value instanceof Boolean)return Boolean.class;
		else if(value instanceof Date)return Date.class;
		else return null;
	}

	public static boolean isNullOrEmpty(Object val){
		if(val == null){
			return true;
		}else{
			if(val instanceof String && "".equals(((String)val).trim())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 日期类型转化
	 */
	static class DateConvert
	{
		public static Date convert(Object obj)
		{
			if(obj instanceof Date)return (Date)obj;
			else if(obj instanceof String){
				String dateString = (String)obj;
				if(!StringUtil.isNullOrEmpty(dateString)){
					String ds=dateString.trim();
					String format = "";
					try {
						switch(ds.length()){
							case 8:
								if(dateString.indexOf("/")>-1)format = "yy/MM/dd";
								else if(dateString.indexOf("-")>-1)format = "yy-MM-dd";
								else format = "yyyyMMdd";
								break;
							case 10:
								if(dateString.indexOf("-")>-1)format = "yyyy-MM-dd";
								else if(dateString.indexOf("/")>-1)format = "yyyy/MM/dd";
								break;
							case 9:
								if(dateString.indexOf("-")>-1)format = "yyyy-M-dd";
								else if(dateString.indexOf("/")>-1)format = "yyyy/M/dd";
								break;
							case 16:
								if(dateString.indexOf("-")>-1)format = "yyyy-MM-dd HH:mm";
								else if(dateString.indexOf("/")>-1)format = "yyyy/MM/dd HH:mm";
								break;
							case 19:
								if(dateString.indexOf("-")>-1)format = "yyyy-MM-dd HH:mm:ss";
								else if(dateString.indexOf("/")>-1)format = "yyyy/MM/dd HH:mm:ss";
								break;
							case 23:
								if(dateString.indexOf(":")>-1)format = "yyyy-MM-dd HH:mm:ss.SSS";
								else format = "yyyy-MM-dd-HH.mm.ss.SSS";
								break;
							case 26:
								if(dateString.indexOf(":")>-1)format = "yyyy-MM-dd HH:mm:ss.SSSSSS";
								else format = "yyyy-MM-dd-HH.mm.ss.SSSSSS";
								break;
						}
						return new SimpleDateFormat(format).parse(dateString);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		}
	}
	
	static class BigDecimalConvert
	{
		public static BigDecimal convert(Object obj)
		{
			if(obj instanceof Double)return BigDecimal.valueOf((Double)obj);
			else if(obj instanceof Float)return BigDecimal.valueOf(((Float)obj).doubleValue());
			if(obj instanceof Long || obj instanceof Integer)return BigDecimal.valueOf(((Number)obj).longValue());
			else if(obj instanceof String){
				return new BigDecimal((String)obj);
			}else {
				try
				{
					return BigDecimal.valueOf(Double.parseDouble(obj.toString()));
				}
				catch(Exception e)
				{
					throw new RuntimeException(StringUtil.format("转化类型出错 原类型 {0} 目标类型{1} 值{2}",obj.getClass().getName(),BigDecimal.class.getName(),obj),e);
				}
			}
		}
	}

	public static void main(String[] args) {

		System.out.println(changeType("300000000000000.00123456", BigDecimal.class));
	}
}
