package com.mx.sql.command;

/**
 * 数据库操作命令参数接口
 */
public interface IDbParameter {
	/**
	 * 参数类型
	 * @return
	 */
	ValueType getValueType();
	/**
	 * 参数名称
	 * @return
	 */
	String getParameterName();
	/**
	 * 设置参数名称
	 * @param name
	 */
	void setParameterName(String name);
	/**
	 * 序号
	 * @return
	 */
	int getIndex();
	/**
	 * 设置序号
	 * @param index
	 */
	void setIndex(int index);
	/**
	 * 得到值
	 * @return
	 */
	Object getValue();
	/**
	 * 设置值
	 * @param value
	 */
	void setValue(Object value);
	/**
	 * 参数类型，输入或输出
	 * @return
	 */
	ParameterType getParamType();
	/**
	 * 设置参数类型
	 * @param type
	 */
	void setParamType(ParameterType type);
}
