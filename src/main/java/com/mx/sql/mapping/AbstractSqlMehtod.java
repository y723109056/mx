package com.mx.sql.mapping;

public abstract class AbstractSqlMehtod implements ISqlMethod {
	private String name;
	
	/**
	 * 得到函数的名称
	 */
	@Override
	public String getName()
	{
		return this.name;
	}
	
	public AbstractSqlMehtod(String name)
	{
		this.name = name;
	}
}
