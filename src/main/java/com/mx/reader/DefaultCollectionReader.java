package com.mx.reader;

import java.util.Arrays;

public class DefaultCollectionReader implements ICollectionReader {

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Object> getCollection(Object data) {
		
		if(data.getClass().isArray()){   
			return Arrays.asList((Object[])data);
		}
		else if(data instanceof Iterable){
			return (Iterable<Object>)data;
		}
		return null;
	}
}
