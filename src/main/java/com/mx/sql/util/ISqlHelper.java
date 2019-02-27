package com.mx.sql.util;

import com.mx.collection.DataTable;
import com.mx.core.IDisposable;
import com.mx.sql.builder.SqlString;
import com.mx.sql.command.IDataReader;
import com.mx.sql.dialect.IDialect;
import com.mx.sql.driver.IDataProvider;
import com.mx.sql.meta.Column;
import com.mx.sql.meta.IDbMetaAccess;

import java.util.List;
import java.util.Map;


/**
 * Sql封装类
 */
public interface ISqlHelper extends IDisposable {
	
	/**
	 * 返回一个数据库数据读取接口
	 * @param sqlString
	 * @return
	 */
	IDataReader getDataReader(String sqlString);
	
	/**
	 * 返回一个数据库数据读取接口
	 * @param sqlString
	 * @param top
	 * @return
	 */
	IDataReader getDataReader(String sqlString, int top);
	
	/**
	 * 返回一个数据库数据读取接口
	 * @param sqlString
	 * @param offset
	 * @param limit
	 * @return
	 */
	IDataReader getDataReader(String sqlString, int offset, int limit);
	
	/**
	 * 返回一个数据库数据读取接口
	 * @param sqlString
	 * @return
	 */
	IDataReader getDataReader(SqlString sqlString);
	
	/**
	 * 返回一个数据库数据读取接口
	 * @param sqlString
	 * @param offset
	 * @param limit
	 * @return
	 */
	IDataReader getDataReader(SqlString sqlString, int offset, int limit);

	/**
	 * 执行Sql语句返回第一行第一个对像
	 * @param sqlString
	 * @return
	 */
    Object executeScalar(String sqlString);
    
	/**
	 * 执行Sql语句返回第一行第一个对像
	 * @param sqlString
	 * @return
	 */
    Object executeScalar(SqlString sqlString);
    
    /**
     * 执行Sql语句返回第一个列
     */
	public List<Object> executeScalars(String sqlString);

    /**
     * 执行Sql语句返回第一列
     */
	public List<Object> executeScalars(SqlString sqlString);
    
    /**
     * 执行Sql语句,返回引响行数
     * @param sqlString
     * @return
     */
    int executeNoneQuery(String sqlString);
    
    /**
     * 批量执行Sql语句,返回引响行数
     * @param sqlString
     * @return
     */
    int[] executeNoneQuery(String[] sqlStrings);
    
    /**
     * 执行Sql语句,返回引响行数
     * @param sqlString
     * @return
     */
    int executeNoneQuery(SqlString sqlString);
    
    /**
     * 批量执行Sql语句,返回引响行数
     * @param sqlString
     * @return
     */
    int[] executeNoneQuery(SqlString[] sqlStrings);
    
    /**
     * 执行Sql语句,返回一个数据结果集
     * @param sqlString
     * @return
     */
    DataTable getDataTable(String sqlString);
    
    /**
     * 执行Sql语句,返回一个数据结果集
     * @param sqlString
     * @return
     */
    DataTable getDataTable(String sqlString, int offset, int limit);
    
    /**
     * 执行Sql语句,返回一个数据结果集
     * @param sqlString
     * @return
     */
    DataTable getDataTable(SqlString sqlString);
    
    /**
     * 执行Sql语句,返回一个数据结果集
     * @param sqlString
     * @param pageSize
     * @param pageNum
     * @return
     */
    DataTable getDataTable(SqlString sqlString, int offset, int limit);

    /**
     * 执行Sql语句,返回一个数据结果集
     * @param sqlString
     * @param tableName
     * @return
     */
    DataTable getDataTable(String sqlString, String tableName);
    
    /**
     * 执行Sql语句,返回一个数据结果集
     * @param sqlString
     * @param pageSize
     * @param pageNum
     * @param tableName
     * @return
     */
    DataTable getDataTable(String sqlString, int offset, int limit, String tableName);
    
    /**
     * 执行Sql语句,返回一个数据结果集
     * @param sqlString
     * @param tableName
     * @return
     */
    DataTable getDataTable(SqlString sqlString, String tableName);
    
    /**
     * 执行Sql语句,返回一个数据结果集
     * @param sqlString
     * @param pageSize
     * @param pageNum
     * @param tableName
     * @return
     */
    DataTable getDataTable(SqlString sqlString, int offset, int limit, String tableName);
    
    /**
	 * 执行Sql语句,返回第一行的Map数据对像
	 * @param sqlString
	 * @return
	 */
    Map<String,Object> getMap(String sqlString);
    
    /**
	 * 执行Sql语句,返回第一行的Map数据对像
	 * @param sqlString
	 * @return
	 */
    Map<String,Object> getMap(SqlString sqlString);
	
    /**
     * 执行Sql语句,返回MapList数据对像
     * @param sqlString
     * @return
     */
	List<Map<String,Object>> getMapList(String sqlString);
	
	/**
	 * 执行Sql语句,返回MapList数据对像
	 * @param sqlString
	 * @return
	 */
	List<Map<String,Object>> getMapList(SqlString sqlString);
	
	/**
	 * 通过Sql返回结果集列信息
	 * @return
	 */
	List<Column> getResultColumns(String sqlString);
	
	/**
	 * 通过表名获取列信息
	 * @param tableName
	 * @return
	 */
	List<Column> getTableColumns(String tableName);

    /**
     * 得到当前访问的数据库方言接口
     * @return
     */
    IDialect getDialect();
    
    /**
     * 得到当前访问的数据库接口
     */
    IDataProvider getDriver();
    
    /**
     * 得到当前访问的数据库元数据接口
     * @return
     */
    IDbMetaAccess getDbMeta();

	/**
	 * 开始事务处理
	 */
	void beginTransaction();

	/**
	 * 结束事务处理
	 */
    void endTransaction(boolean errorFlag);

}
