package com.mx.sql.dialect;

public interface ITranslator {
	
	/**
	 * 翻译函数
	 * @param source
	 * @return
	 */
	String translateMethod(String method);
	
}
