package com.mx.sql.command;

/**
 * 数据库操作命令参数集合接口
 */
public interface IDbParameterCollection extends Iterable<IDbParameter> {
	/**
	 * 添加一个参数
	 * @param parameterName 参数名称
	 * @param sqlType 参数类型
	 * @return
	 */
	IDbParameter add(String parameterName, int sqlType);
	/**
	 * 添加一个参数
	 * @param parameterName 参数名称
	 * @param value 参数值
	 * @return
	 */
	IDbParameter add(String parameterName, Object value);
	/**
	 * 添加一个参数
	 * @param parameterIndex 参数序号
	 * @param sqlType 参数类型
	 * @return
	 */
	IDbParameter add(int parameterIndex, int sqlType);
	/**
	 * 添加一个参数
	 * @param parameterIndex 参数序号
	 * @param value 参数值
	 * @return
	 */
	IDbParameter add(int parameterIndex, Object value);
	/**
	 * 得到一个参数对像
	 * @param parameterName 参数名称
	 * @return
	 */
	IDbParameter get(String parameterName);
	/**
	 * 设置一个参数对像
	 * @param parameterName 参数名称
	 * @param value 参数值
	 */
	void set(String parameterName, IDbParameter value);
	/**
	 * 是否包含该参数
	 * @param parameterName
	 * @return
	 */
	boolean contains(String parameterName);
	/**
	 * 参数个数
	 * @return
	 */
	int size();
	
	/**
	 * 清除所有参数
	 */
	void clear();
	
	/**
	 * 得到参数对像
	 * @param index
	 * @return
	 */
	IDbParameter get(int index);
	
	/**
	 *  添加参数集合
	 * @param params
	 */
	void fromArray(Object[] params);
}
