package com.mx.sql.mapping;

/**
 * SqlMap 断言的基类
 */
public abstract class AbstractClauseAssert implements ISqlClauseAssert {
	private String name;
	
	/**
	 * 得到断言的名称
	 */
	@Override
	public String getName()
	{
		return this.name;
	}
	
	public AbstractClauseAssert(String name)
	{
		this.name = name;
	}
}
