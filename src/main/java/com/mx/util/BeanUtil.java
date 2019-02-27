package com.mx.util;

import com.mx.spring.SpringContext;

import java.util.Map;


public class BeanUtil {
	
	/**
	 * 找到一个接口的实现类，找不到或找到多个时抛异常
	 * @param type
	 * @return
	 */
	public static Object getJustOneBeanByClass(Class<?> type){
		Map<String,?> map = SpringContext.instance().getMap(type);
		if(map.size()==1){
			return map.values().toArray()[0];
		}else if(map.size()>1){
			StringBuilder sb = new StringBuilder();
			for(Object obj : map.values()){
				sb.append(obj+"|");
			}
			throw new RuntimeException(type+"接口只能有一个实现类，但找到多个:"+sb.toString());
		}throw new RuntimeException("找不到"+type+"接口的实现类");
	}

    public static Object getFirstOneBeanByClass(Class<?> type){
        Map<String,?> map = SpringContext.instance().getMap(type);
        if(map.size()>0){
            return map.values().toArray()[0];
        }
        return null;
    }

	public static Object getBean(String name){
		return SpringContext.instance().get(name);
	}
	
}
