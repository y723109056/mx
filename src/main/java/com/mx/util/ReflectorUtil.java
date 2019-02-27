package com.mx.util;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.mx.reflector.ClassInfo;
import com.mx.reflector.PropertyInfo;
import com.mx.reflector.PropertyType;
import net.sf.cglib.beans.BeanGenerator;

public class ReflectorUtil {
	private static Map<Class<?>,ClassInfo> classCache;
	private static Set<String> filterPropertyMethods;
	
	static{
		classCache = new ConcurrentHashMap<Class<?>,ClassInfo>();
		filterPropertyMethods = new HashSet<String>();
		filterPropertyMethods.add("getClass");
	}
	
	public static PropertyInfo getProperty(Class<?> c,String propertyName){
		checkClassCache(c);
		return classCache.get(c).getProperty(propertyName);
	}
	
	public static boolean containProperty(Class<?> c,String propertyName){
		checkClassCache(c);
		return classCache.get(c).contains(propertyName);
	}
	
	private static void checkClassCache(Class<?> c){
		if(!classCache.containsKey(c)){
			Method[] methods=c.getMethods();
			Map<String,PropertyInfo> properies=new HashMap<String,PropertyInfo>();
			for(Method method : methods){
				String name = method.getName();
				if(!filterPropertyMethods.contains(name) && name.length()>3){
					String pre = name.substring(0,3);
					String property = name.substring(3,4).toLowerCase()+name.substring(4);
					if(pre.equals("get") && method.getParameterTypes().length==0){
						if(method.getReturnType()!=null){
							if(!properies.containsKey(property)){
								PropertyInfo pi = new PropertyInfo();
								pi.setGetMethod(method);
								pi.setPropertyType(getPropertyType(method.getReturnType()));
								properies.put(property, pi);
							}else{
								PropertyInfo pi = properies.get(property);
								pi.setGetMethod(method);
							}
						}
					}else if(pre.equals("set")  && method.getParameterTypes().length == 1){
						if(!properies.containsKey(property)){
							PropertyInfo pi = new PropertyInfo();
							pi.setSetMethod(method);
							pi.setPropertyType(getPropertyType(method.getParameterTypes()[0]));
							properies.put(property, pi);
						}else{
							PropertyInfo pi = properies.get(property);
							pi.setSetMethod(method);
						}
					}
				}
			}
			
			Field[] fs = getAllFields(c);
			for(Field f : fs){
				String property = StringUtil.toLower(f.getName(), 0, 1);
				if(properies.containsKey(property)){
					f.setAccessible(true);
					properies.get(property).setField(f);
				}
			}
			
			ClassInfo classInfo = new ClassInfo();
			classInfo.setPropertyMap(properies);
			classInfo.setProperties(properies.values().toArray(new PropertyInfo[0]));
			
			classCache.put(c,classInfo);
		}
	}
	
	public static PropertyInfo[] getProperties(Class<?> c){
		checkClassCache(c);
		return classCache.get(c).getProperties();
	}
	
	private static PropertyType getPropertyType(Class<?> clazz){
		if(TypeUtil.isValueType(clazz))return PropertyType.Value;
		else if(TypeUtil.isCollectionType(clazz))return PropertyType.Collection;
		else return PropertyType.Object;
	}
	
	public static Class<?> getClassForName(String className){
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(StringUtil.format("找不到该类 {0}", className),e);
		}
	}
	
	public static Object newInstance(Class<?> c){
		if(c!=null)
		{
			try {
				if(c.getName().indexOf("$")>-1)c=c.getSuperclass();
				return c.newInstance();
			} catch (InstantiationException e) {
				 throw new RuntimeException("创建的实列出错 Error:"+e.toString());
			} catch (IllegalAccessException e) {
				 throw new RuntimeException("创建的实列出错 Error:"+e.toString());
			}
		}
		else throw new RuntimeException("创建的实列类型为能为空");
	}
	
	/**
	 * 生成代理对像
	 * @param c
	 * @param properties
	 * @return
	 */
	public static Object newProxy(Class<?> c,Set<String> properties){
		if(c!=null)
		{
			try{
				BeanGenerator generator = new BeanGenerator();
				generator.setSuperclass(c);
				for (Iterator<String> i = properties.iterator(); i.hasNext();) {  
					String property = i.next();
					generator.addProperty(property,Object.class);
				}
				return generator.create();
			}catch(Exception e){
				throw new RuntimeException("创建的代理实列出错 Error:"+e.toString());
        	}
		}
		else throw new RuntimeException("创建的实列类型为能为空");
	}
	
	/**
	 * 生成代理对像
	 * @param c
	 * @param propertyMap
	 * @return
	 */
	public static Object newProxy(Class<?> c,Map<String,Class<?>> propertyMap){
		if(c!=null)
		{
			try{
				BeanGenerator generator = new BeanGenerator();
				generator.setSuperclass(c);
				Set<String> keySet = propertyMap.keySet();
				for (Iterator<String> i = keySet.iterator(); i.hasNext();) {  
					String key = i.next();
					generator.addProperty(key,propertyMap.get(key));
				}
				return generator.create();
			}catch(Exception e){
				throw new RuntimeException("创建的代理实列出错 Error:"+e.toString());
        	}
		}
		else throw new RuntimeException("创建的实列类型为能为空");
	}
	
	public static Method getSetMethod(Class<?> c,String fieldName){   
    	Method setMethod=null;
    	List<Field> list = new ArrayList<Field>();
    	getAllFields(c,list);
        for(int i=0;i<list.size();i++){  
            if(list.get(i).getName().equals(fieldName)){
                String firstLetter=fieldName.substring(0,1).toUpperCase(); 
                String setMethodName="set"+firstLetter+fieldName.substring(1);
                try{
                	setMethod=c.getMethod(setMethodName,new Class[]{list.get(i).getType()});
                }catch(Exception e){
            		throw new RuntimeException(StringUtil.format("动态获取对像赋值函数出错 对像 {0} 字段 {1}",c.getName(),fieldName));
            	}
            }
        }   
        return setMethod;   
    }
	
	public static Method getGetMethod(Class<?> c,String fieldName){
    	Method getMethod = null;
    	String firstLetter = fieldName.substring(0, 1).toUpperCase();
    	String getMethodName = "get" + firstLetter + fieldName.substring(1);
    	try{
    		getMethod = c.getMethod(getMethodName, new Class[]{});
    	}catch(Exception e){
    		throw new RuntimeException(StringUtil.format("动态获取对像访问函数出错 对像 {0} 字段 {1}",c.getName(),fieldName));
    	}
    	return getMethod;
    }
	
	public static Method getMethod(Class<?> clazz,String methodName,Class<?>[] parameterTypes){
		try{
			return clazz.getDeclaredMethod(methodName, parameterTypes);
		}catch (SecurityException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(StringUtil.format("函数不存在 类{0} 函数{1}",clazz.getName(),methodName));
		}catch (NoSuchMethodException e){
			if(clazz.getSuperclass()!=null){
				return getMethod(clazz.getSuperclass(),methodName,parameterTypes);
			}
			throw new RuntimeException(StringUtil.format("函数不存在 类{0} 函数{1}",clazz.getName(),methodName));
		}
	}
	
	public static Class<?> getFieldType(Class<?> c,String fieldName){
		List<Field> list = new ArrayList<Field>();
		getAllFields(c,list);
    	for(int i=0;i<list.size();i++){
    		Field field=list.get(i);
            if(field.getName().equals(fieldName)){
            	return field.getType();
            }
        }
    	return null;
	}
	
	public static void setPropertyValue(Object obj,String propertyName,Object propertyValue){
		if(obj!=null){
			PropertyInfo pi = getProperty(obj.getClass(),propertyName);
			if(pi!=null && pi.getSetMethod()!=null){
				try{
					Class<?> clazz = pi.getSetMethod().getParameterTypes()[0];
					if(clazz.isInstance(propertyValue)){
						pi.getSetMethod().invoke(obj,new Object[]{propertyValue});
					}else{
						pi.getSetMethod().invoke(obj,new Object[]{TypeUtil.changeType(propertyValue, clazz)});
					}
				}catch(Exception e){
					throw new RuntimeException(StringUtil.format("动态对像属性赋值出错 函数:{0} 对像 {1} 属性 {2} 值 {3} 值类型{4}",pi.getSetMethod(),obj.getClass().getName(),propertyName,propertyValue,propertyValue.getClass().getName()));
				}
			}
		}
	}
	
	public static void setPropertyValue(Object obj,Method method,Object propertyValue){
		if(obj!=null){
			if(method!=null){
				try{
					Class<?> clazz = method.getParameterTypes()[0];
					if(clazz.isInstance(propertyValue)){
						method.invoke(obj,new Object[]{propertyValue});
					}else{
						method.invoke(obj,new Object[]{TypeUtil.changeType(propertyValue, clazz)});
					}
				}catch(Exception e){
					throw new RuntimeException(StringUtil.format("对像属性赋值出错 函数:{0} 对像 {1} 值 {2} 值类型 {3}",method,obj.getClass().getName(),propertyValue,propertyValue.getClass().getName()));
				}
			}
		}
	}
	
	public static Object getPropertyValue(Object obj,String propertyName){
		if(obj!=null){
			PropertyInfo pi = getProperty(obj.getClass(),propertyName);
			if(pi!=null && pi.getGetMethod()!=null){
				try{
					return pi.getGetMethod().invoke(obj,new Object[]{});
				}catch(Exception e){
					throw new RuntimeException(StringUtil.format("获取对像属性出错 函数:{0} 对像 {1} 属性 {2}",pi.getGetMethod(),obj.getClass().getName(),propertyName));
				}
			}
		}
		return null;
	}
	
	public static Object getPropertyValue(Object obj,Method method){
		if(obj!=null){
			if(method!=null){
				try{
					return method.invoke(obj,new Object[]{});
				}catch(Exception e){
					throw new RuntimeException(StringUtil.format("获取对像属性出错 函数:{0} 对像 {1}",method,obj.getClass().getName()));
				}
			}
		}
		return null;
	}
	
	public static Map<String,Object> getPropertyMap(Object obj){
		Map<String,Object> map = new HashMap<String,Object>();
		if(obj!=null){
			PropertyInfo[] pis = getProperties(obj.getClass());
			for(int i=0;i<pis.length;i++){
				PropertyInfo pi = pis[i];
				Object val = pi.getValue(obj);
				map.put(pi.getName(),val);
			}
		}
		return map;
	}
	
	public static Field[] getAllFields(Class<?> c){
		List<Field> list=new ArrayList<Field>();
		getAllFields(c,list);
		return list.toArray(new Field[0]);
	}
	
	public static void getAllFields(Class<?> c, List<Field> list) {
    	list.addAll(Arrays.asList(c.getDeclaredFields()));
    	if(c.getSuperclass() != null)
    		getAllFields(c.getSuperclass(), list);		
    }
	
}
