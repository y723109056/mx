package com.mx.util;


import com.mx.listener.SpringContextUtil;

import java.util.Map;

public class RegisterInterfaceImpl {
	@SuppressWarnings("rawtypes")
	public static Map getInterfaceImpl(Class cls){
		return SpringContextUtil.getApplicationContext().getBeansOfType(cls);
	}
}
