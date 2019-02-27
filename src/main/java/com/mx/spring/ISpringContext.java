package com.mx.spring;

import java.util.Map;

public interface ISpringContext {
	Object get(String name);

    Object get(String name, Object[] args);

    boolean contains(String name);

    String[] getNames(Class<?> c);

    Map<String, ?> getMap(Class<?> c);
}
