package com.mx.sql.command;

import com.mx.sql.SqlDriverException;
import com.mx.sql.config.IDbConfig;

import java.sql.Connection;
import java.sql.SQLException;




/**
 * 数据库连接
 */
public class SqlConnection implements IDbConnection {

	/** jdbc连接对像 */
	protected Connection conn;
	/** 数据库配置接口 */
	protected IDbConfig dbConfig;

	/* 执行完Sql是否关闭连接 */
	protected boolean transaction;
	
	protected SqlConnection() {

	}

	public SqlConnection(IDbConfig dbConfig)
	{
		this.dbConfig=dbConfig;
		this.transaction = false;
	}
	
	/**
	 * 关闭数据库连接
	 */
	@Override
	public void close() {
		if(this.conn!=null)
		{
			try {
				this.conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 打开数据库连接
	 */
	@Override
	public void open()
	{
		if(this.conn==null)
		{
			try {
				this.conn=this.dbConfig.getConnection();
			}
			catch(SQLException e)
			{
				throw new SqlDriverException("得到数据库连接出错",e);
			}
			catch(Exception e)
			{
				throw new SqlDriverException("连接数据库出错",e);
			}
		}
		else
		{
			try {
				boolean isClose=this.conn.isClosed();
				if(isClose)
				{
					this.conn=this.dbConfig.getConnection();
				}
			} catch (SQLException e) {
				throw new SqlDriverException("得到数据库连接出错",e);
			}
			catch(Exception e)
			{
				throw new SqlDriverException("连接数据库出错",e);
			}
		}
	}

	/**
	 * 判断数据库连接是否关闭
	 */
	@Override
	public boolean isClose() {
		if(this.conn==null)return true;
		else
		{
			try {
				if(this.conn.isClosed())return true;
				
			} catch (SQLException e) {
				throw new SqlDriverException("查看数据库连接是否关闭出错",e);
			}
		}
		return false;
	}

	/**
	 * 提交事务
	 */
	public void commit() throws SQLException {
		if(!this.isClose()){
			this.conn.commit();
		}
	}

	/**
	 * 回滚事务
	 */
	public void rollback(){
		if(!this.isClose()){
			try {
				this.conn.rollback();
			} catch (SQLException e) {
				throw new SqlDriverException("数据库连接回滚事务出错",e);
			}
		}
	}

	/**
	 * 设置是否开启事务
	 * @param transaction
	 */
	public void setTransaction(boolean transaction){
		this.transaction = transaction;
	}

	/**
	 * 设置是否开启事务
	 * @return
	 */
	public boolean getTransaction(){
		return this.transaction;
	}

	/**
	 * 创建一个数据库操作类
	 */
	@Override
	public IDbCommand createCommand()
	{
		return new SqlCommand(this);
	}

	/**
	 * 释放数据库连接
	 */
	@Override
	public void dispose() {
		if(this.conn!=null)
		{
			try {
				this.conn.close();
				this.conn=null;
			} catch (SQLException e) {
				throw new SqlDriverException("半闭数据库连接出错",e);
			}
		}
	}
	/**
	 * 得到jdbc连接对像
	 */
	@Override
	public Connection getConnection() {
		return this.conn;
	}
	
}
