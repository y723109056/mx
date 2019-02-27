package com.mx.sql.command;

import com.mx.core.IDisposable;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据库连接接口
 */
public interface IDbConnection extends IDisposable {
	/**
	 * 关闭数据库连接
	 */
	void close();
	
	/**
	 * 判断数据库连接是否关闭
	 * @return
	 */
	boolean isClose();
	
	/**
	 * 打开数据库连接
	 */
	void open();

	/**
	 * 提交事务
	 */
	void commit() throws SQLException;

	/**
	 * 回滚事务
	 */
	void rollback();

	/**
	 * 设置是否开启事务
	 * @param transaction
	 */
	void setTransaction(boolean transaction);

	/**
	 * 设置是否开启事务
	 * @return
	 */
	boolean getTransaction();
	
	/**
	 * 得到一个jdbc连接对像
	 * @return
	 */
	Connection getConnection();
	
	/**
	 * 创建一个数据操作命令接口
	 * @return
	 */
	IDbCommand createCommand();
}
