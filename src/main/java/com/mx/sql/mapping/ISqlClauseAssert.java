package com.mx.sql.mapping;


import com.mx.sql.dialect.IDialect;

/**
 * SqlMap 断言接口
 */
public interface ISqlClauseAssert {
	
	/**
	 * 断言的名称
	 * @return
	 */
	String getName();
	
	/**
	 * 断言函数
	 * @param dialect 数据库方言
	 * @param args 断言函数参数
	 * @return
	 */
	boolean invoke(IDialect dialect, Object... args);
}
