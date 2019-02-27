package com.mx.sql.command;

import com.mx.core.IDisposable;

import java.util.List;

/**
 * 数据库命令组件
 */
public interface IDbCommand extends IDisposable {
	
	/**
	 * 设置Sql
	 * @param sql
	 */
	void setSql(String sql);
	
	/**
	 * 设置批量Sql
	 */
	void setSql(String[] sqls);
	
	/**
	 * 带参数格式的Sql
	 * @param text 有两样格式 格式1: Select * from student where name={0} and sex={1}
	 * 						格式2: Select * from student where name=? and sex=?
	 * @param params
	 */
	void setSql(String sql, Object[] params);
	
	/**
	 * 设置批准带参数格式的Sql
	 * @param text 有两样格式 格式1: Select * from student where name={0} and sex={1}
	 * 						格式2: Select * from student where name=? and sex=?
	 * @param paramsList
	 */
	void setSql(String sql, List<Object[]> paramsList);
	
	/**
	 * 得到类型,Sql或存贮过程
	 * @return
	 */
	CommandType getCommandType();
	
	/**
	 * 执行Sql语句,返回引响行数
	 * @return
	 */
	int[] executeNonQuery();
	
	/**
	 * 执行Sql语句返回第一行第一列
	 * @return
	 */
	Object executeScalar();
	
	/**
	 * 执行Sql语句返回第一列
	 * @return
	 */
	List<Object> executeScalars();
	
	/**
	 * 返回一个数据库数据读取接口
	 * @return
	 */
	IDataReader executeReader();

	/**
	 * 得到执行时间
	 * @return
	 */
	long getExecTime();
	
}
