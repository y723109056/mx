package com.mx.writer;

/**
 * Created by zhusw on 2017/9/26.
 */
public interface IPropertyWriter<T> {

    void setValue(Object entity, String propertyName, T value);

    void setValue(Object entity, int index, T value);

}
