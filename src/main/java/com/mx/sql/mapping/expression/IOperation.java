package com.mx.sql.mapping.expression;

public interface IOperation {
	
	/**
	 * 对两个参数运算操作
	 * @param param1
	 * @param param2
	 * @return
	 */
	Object execute(Object... params);
	
	/**
	 * 获得操作符
	 * @return
	 */
	public String getSign();

	/**
	 * 获得操作符优先级
	 * @return
	 */
	public Integer getPriority();
}
