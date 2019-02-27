package com.mx.sql.mapping.expression;

public interface IExpressionParser {
	
	/**
	 * 
	 * @param text
	 * @return
	 */
	public OperationItem parser(String expression);
}
