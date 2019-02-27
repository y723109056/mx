package com.mx.writer;

import com.mx.spring.SpringContext;

/**
 * Created by zhusw on 2017/9/26.
 */
public class Writer {

    private static final String ObjectWriterKey = "ObjectWriter";
    private static IPropertyWriter<String> stringWriter;
    private static IPropertyWriter<Integer> intWriter;
    private static IPropertyWriter<Boolean> boolWriter;
    private static IPropertyWriter<Object> objectWriter;

    public static IPropertyWriter<String> stringWriter()
    {
        if(stringWriter==null)
        {
            stringWriter = new StringWriter(objectWriter());
        }
        return stringWriter;
    }

    public static IPropertyWriter<Integer> intWriter()
    {
        if(intWriter==null)
        {
            intWriter = new IntegerWriter(objectWriter());
        }
        return intWriter;
    }

    public static IPropertyWriter<Boolean> boolWriter()
    {
        if(boolWriter==null)
        {
            boolWriter = new BooleanWriter(objectWriter());
        }
        return boolWriter;
    }

    public static IPropertyWriter<Object> objectWriter()
    {
        if(objectWriter==null)
        {
            objectWriter = (IPropertyWriter<Object>) SpringContext.instance().get(ObjectWriterKey);
        }
        return objectWriter;
    }

    static class StringWriter implements IPropertyWriter<String>
    {
        private IPropertyWriter<Object> writer;

        public StringWriter(IPropertyWriter<Object> writer)
        {
            this.writer = writer;
        }

        @Override
        public void setValue(Object entity, String propertyName, String value) {
            writer.setValue(entity, propertyName, value);
        }

        @Override
        public void setValue(Object entity, int index, String value) {
            writer.setValue(entity, index, value);
        }
    }

    static class IntegerWriter implements IPropertyWriter<Integer>
    {
        private IPropertyWriter<Object> writer;

        public IntegerWriter(IPropertyWriter<Object> writer)
        {
            this.writer = writer;
        }

        @Override
        public void setValue(Object entity, String propertyName, Integer value) {
            writer.setValue(entity, propertyName, value);
        }

        @Override
        public void setValue(Object entity, int index, Integer value) {
            writer.setValue(entity, index, value);
        }
    }

    static class BooleanWriter implements IPropertyWriter<Boolean>
    {
        private IPropertyWriter<Object> writer;

        public BooleanWriter(IPropertyWriter<Object> writer)
        {
            this.writer = writer;
        }

        @Override
        public void setValue(Object entity, String propertyName, Boolean value) {
            writer.setValue(entity, propertyName, value);
        }

        @Override
        public void setValue(Object entity, int index, Boolean value) {
            writer.setValue(entity, index, value);
        }
    }

}
