package com.mx.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mx.reflector.PropertyInfo;
import org.apache.commons.lang.StringUtils;



/**
 * @author 小米线儿
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class EntityUtil {

	/**
	 * 把对象属性转入MAP
	 * 
	 * @param map
	 * @param entity
	 * @return
	 */
	public static boolean propertiesToMap(Map<String, Object> map, Object entity) {
		boolean hasVal = false;
		if (entity != null) {
			PropertyInfo[] pis = ReflectorUtil.getProperties(entity.getClass());
			for (PropertyInfo pi : pis) {
				if (pi.getField() != null) {
					Object val = ReflectorUtil.getPropertyValue(entity,
							pi.getGetMethod());
					if (val != null && !"".equals(val)) {
						hasVal = true;
						map.put(pi.getName(), val);
					}
				}
			}
		}
		return hasVal;
	}

	/**
	 * 获取对象的所有字段 ,隔开
	 * 
	 * @param entity
	 * @return
	 */
	public static String getEntityFields(Class<?> c) {
		List<String> list = new ArrayList<String>();
		if (c != null) {
			PropertyInfo[] pis = ReflectorUtil.getProperties(c);
			for (PropertyInfo pi : pis) {
				if (pi.getField() != null) {
					String fieldName = pi.getField().getName();
					list.add(fieldName);
				}
			}
		}
		return StringUtils.join(list, ",");
	}
	
	/**
	 * 将源对象与目标对象相同的属性值进行复制
	 * @param source
	 * @param target
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws java.lang.reflect.InvocationTargetException
	 */
	public static void copyproperties(Object source, Object target)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		Class<? extends Object> sourceClass = source.getClass();// 得到源对象的Class
		Class<? extends Object> targetClass = target.getClass();// 得到目标对象的Class

		Field[] sourceFields = sourceClass.getDeclaredFields();// 得到源Class对象的所有属性
		Field[] targetFields = targetClass.getDeclaredFields();// 得到目标Class对象的所有属性

		for (Field sourceField : sourceFields) {
			String name = sourceField.getName();// 属性名
			Class<?> type = sourceField.getType();// 属性类型

			String methodName = name.substring(0, 1).toUpperCase()
					+ name.substring(1);

			Method getMethod = sourceClass.getMethod("get" + methodName);// 得到属性对应get方法

			Object value = getMethod.invoke(source);// 执行源对象的get方法得到属性值

			for (Field targetField : targetFields) {
				String targetName = targetField.getName();// 目标对象的属性名

				if (targetName.equals(name)) {
					Method setMethod = targetClass.getMethod(
							"set" + methodName, type);// 属性对应的set方法

					setMethod.invoke(target, value);// 执行目标对象的set方法
				}
			}
		}
	}

}
