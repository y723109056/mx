package com.mx.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

public class SpringContext implements ISpringContext,ApplicationContextAware {
	private static final String SpringContextKey = "SpringContextAware";
	private ApplicationContext context;
	private static ISpringContext instance;
    
	public boolean contains(String name) {
		return context.containsBean(name);
	}

	public Object get(String name) {
		return context.getBean(name);
	}

	public Object get(String name, Object[] args) {
		return context.getBean(name, args);
	}

	public String[] getNames(Class<?> c) {
		return context.getBeanNamesForType(c);
	}

	public Map<String, ?> getMap(Class<?> c){
        return context.getBeansOfType(c);
    }

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;
		instance = (ISpringContext)applicationContext.getBean(SpringContextKey);
	}
	
	public static ISpringContext instance(){
		return instance;
	}
}
