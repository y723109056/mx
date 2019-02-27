package com.mx.sql.mapping;


import com.mx.sql.builder.SqlString;

/**
 * SqlMap 元素对像接口
 */
public interface ISqlItemPart {
	/**
	 * 构造Sql语句
	 * @param arg 构造Sql语句参数对像
	 * @return Sql语句对像
	 */
	SqlString toString(SqlItemArgs arg);
}
