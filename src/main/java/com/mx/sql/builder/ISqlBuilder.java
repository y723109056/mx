package com.mx.sql.builder;

import com.mx.sql.config.IDbConfig;
import com.mx.sql.dialect.IDialect;

/**
 * Sql构造接口
 */
public interface ISqlBuilder {
	/**
	 * 传入参数构造一条Sql语句
	 * @param params 条件数据对像
	 * @return
	 */
	SqlString toSqlString(Object... params);
	
	/**
	 * 传入参数构造一条Sql语句
	 * @param params 条件数据对像
	 * @return
	 */
	SqlString toSqlString(IDialect dialect, Object... params);
	
	/**
	 * 传入参数构造一条Sql语句
	 * @param dialect
	 * @param params
	 * @return
	 */
	SqlString toSqlString(IDbConfig config, IDialect dialect, Object... params);
}
