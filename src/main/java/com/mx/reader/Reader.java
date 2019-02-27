package com.mx.reader;


import com.mx.spring.SpringContext;
import com.mx.util.TypeUtil;

public class Reader {
	private static final String ObjectReaderKey = "ObjectReader";
	private static final String CollectionReaderKey = "CollectionReader";
	private static IPropertyReader<String> stringReader;
	private static IPropertyReader<Integer> intReader;
	private static IPropertyReader<Boolean> boolReader;
	private static IPropertyReader<?> objectReader;
	private static ICollectionReader collectionReader;
	
	public static IPropertyReader<String> stringReader()
	{
		if(stringReader==null)
		{
			stringReader = new StringReader(objectReader());
		}
		return stringReader;
	}
	
	public static IPropertyReader<Integer> intReader()
	{
		if(intReader==null)
		{
			intReader = new IntegerReader(objectReader());
		}
		return intReader;
	}
	
	public static IPropertyReader<Boolean> boolReader()
	{
		if(boolReader==null)
		{
			boolReader = new BooleanReader(objectReader());
		}
		return boolReader;
	}
	
	public static IPropertyReader<?> objectReader()
	{
		if(objectReader==null)
		{
			objectReader = (IPropertyReader<?>) SpringContext.instance().get(ObjectReaderKey);
		}
		return objectReader;
	}
	
	public static ICollectionReader collectionReader()
	{
		if(collectionReader==null)
		{
			collectionReader = (ICollectionReader)SpringContext.instance().get(CollectionReaderKey);
		}
		return collectionReader;
	}
	
	static class StringReader implements IPropertyReader<String>
	{
		private IPropertyReader<?> reader;
		
		public StringReader(IPropertyReader<?> reader)
		{
			this.reader = reader;
		}

		@Override
		public String getValue(Object entity, String propertyName) {
			Object obj = reader.getValue(entity, propertyName);
			return (String)TypeUtil.changeType(obj,String.class);
		}

		@Override
		public String getValue(Object entity, int index) {
			Object obj = reader.getValue(entity, index);
			return (String) TypeUtil.changeType(obj, String.class);
		}
	}
	
	static class IntegerReader implements IPropertyReader<Integer>
	{
		private IPropertyReader<?> reader;
		
		public IntegerReader(IPropertyReader<?> reader)
		{
			this.reader = reader;
		}

		@Override
		public Integer getValue(Object entity, String propertyName) {
			Object obj = reader.getValue(entity, propertyName);
			return (Integer)TypeUtil.changeType(obj,Integer.class);
		}

		@Override
		public Integer getValue(Object entity, int index) {
			Object obj = reader.getValue(entity, index);
			return (Integer)TypeUtil.changeType(obj,Integer.class);
		}
	}
	
	static class BooleanReader implements IPropertyReader<Boolean>
	{
		private IPropertyReader<?> reader;
		
		public BooleanReader(IPropertyReader<?> reader)
		{
			this.reader = reader;
		}

		@Override
		public Boolean getValue(Object entity, String propertyName) {
			Object obj = reader.getValue(entity, propertyName);
			return (Boolean)TypeUtil.changeType(obj,Boolean.class);
		}

		@Override
		public Boolean getValue(Object entity, int index) {
			Object obj = reader.getValue(entity, index);
			return (Boolean)TypeUtil.changeType(obj,Boolean.class);
		}
	}
}
