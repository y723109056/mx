package com.mx.sql.mapping;


import com.mx.sql.builder.SqlString;
import com.mx.sql.config.IDbConfig;
import com.mx.sql.dialect.IDialect;

/**
 * SqlMap 自定义函数接口
 */
public interface ISqlMethod {
	
	/**
	 * 得到自定义函数的名称
	 * @return
	 */
	String getName();
	
	/**
	 * 生成Sql语句对像
	 * @param dialect 数据库方言
	 * @param args 自定义函数参数
	 * @return Sql语句对像
	 */
	SqlString invoke(IDbConfig config, IDialect dialect, Object... args);
}
