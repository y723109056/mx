package com.mx.reader;

public interface IPropertyReader<T> {
	T getValue(Object entity, String propertyName);

	T getValue(Object entity, int index);
}
