package com.mx.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class ArrayUtil {
	
	/**
	 * 拼接对象List 为字符串
	 * @param list
	 * @param propertyName 对象中的哪个属性
	 * @return
	 */
	public static String toString(List<?> list, String propertyName) {
		List<String> sList = new ArrayList<String>();
		for (Object obj : list) {
			Object value = ReflectorUtil.getPropertyValue(obj, propertyName);
			try{
				String val = value.toString();
				sList.add(val);
			}catch(Exception e){
			}
		}
		return StringUtils.join(sList, ",");
	}

	/**
	 * 拼接字符串
	 * 
	 * @param args
	 * @return
	 */
	public static String toString(Object[] objs) {
		return toString(objs, ",");
	}

	/**
	 * 拼接字符串
	 * 
	 * @param args
	 * @param split
	 * @return
	 */
	public static String toString(Object[] objs, String split) {
		if (objs != null) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < objs.length; i++) {
				sb.append((i == 0) ? objs[i] : split + objs[i]);
			}
			return sb.toString();
		}
		return "";
	}

	/**
	 * 数组转化为
	 * 
	 * @param strs
	 * @return
	 */
	public static Set<String> asSet(String[] strs) {
		Set<String> set = new HashSet<String>();
		for (String str : strs) {
			set.add(str);
		}
		return set;
	}

	/**
	 * 判断数组是否为空
	 * 
	 * @return
	 */
	public static boolean isNullOrEmpty(Object[] args) {
		return args != null && args.length > 0;
	}
	
	/**
	 * 取交集
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List intersect(List<?> ls, List<?> ls2) { 
        List list = new ArrayList(Arrays.asList(new Object[ls.size()])); 
        Collections.copy(list, ls); 
        list.retainAll(ls2); 
        return list; 
    }
    
    /**
     * 取并集
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List union(List ls, List ls2) { 
        List list = new ArrayList(Arrays.asList(new Object[ls.size()])); 
        Collections.copy(list, ls); 
        list.addAll(ls2); 
        return list; 
    }
    
    /**
     * 取差集
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List diff(List ls, List ls2) { 
        List list = new ArrayList(Arrays.asList(new Object[ls.size()])); 
        Collections.copy(list, ls); 
        list.removeAll(ls2); 
        return list; 
    } 
}
