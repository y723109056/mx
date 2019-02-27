package com.mx.sql.dialect;

import com.mx.sql.builder.SqlString;

import java.util.Map;

/**
 * 数据库方言接口
 */
public interface IDialect {

	/** 数据库函数 */
	Map<String,String> getSqlFuns();
	
	/** 拼音查询,开始字符 */
    String[] getPYStartChars();

    /** 拼音查询,结束字符 */
    String[] getPYEndChars();
    
    boolean hasQuote();

    /** 开闭符，开头 */
    char getOpenQuote();

    /** 开闭符，结尾 */
    char getCloseQuote();

    /** 是否包含开闭符 */
    boolean isQuoted(String name);

    /** 解除开闭符 */
    String unQuote(String quoted);

    /** 解除开闭符 */
    String[] unQuote(String[] quoted);

    /** 添加开闭符 */
    String quote(String name);

    /** 是否支持Sequence */
    boolean supportsSequences();

    /** 是否支持索引列 */
    boolean supportsIdentityColumns();
    
    /** 是否支持分页Limit */
    boolean supportsLimit();

    /** 是否支持分页Limit和Offset */
    boolean supportsLimitOffset();

    /** 对Sql进行分页包装 */
    String getLimitString(String querySqlString, int offset, int limit);
    
    /** 对Sql进行分页包装 */
    SqlString getLimitString(SqlString querySqlString, int offset, int limit);
    
    /** 对查询前几条数据 */
    String getTopString(String querySqlString, int top);
    
    /** 对查询前几条数据 */
    SqlString getTopString(SqlString querySqlString, int top);

    /** 是否支持子查询 */
    boolean supportsSubSelects();

    int getMaxAliasLength();

    /** 对别名添加开闭符 */
    String quoteForAliasName(String aliasName);

    /** 对列表添加开闭符 */
    String quoteForColumnName(String columnName);

    /** 对表名添加开闭符 */
    String quoteForTableName(String tableName);

    /** 对Sql进行求总数包装 */
    String getCountSqlString(String sqlString);
    
    /** 对Sql进行求总数包装 */
    SqlString getCountSqlString(SqlString sqlString);

    String getParamSign();

    /** 判断名表是否存在 */
    String getExistTableSql(String tableName);

    /** 日期格式化 */
    Object toDate(Object date);
    
    /** 日期格式化 */
    Object toChar(Object date);
    
    /** 日期格式化 */
    Object toChar(Object date, String format);

    /** 当前日期 */
    String getCurrentDate();
    /** 当前日期时间 */
    String getCurrentDateTime();
    /** 当前时间 */
    String getCurrentTime();
    /** 得到翻译者 */
    ITranslator getTranslator();
    
    IMetaSql getMetaSql();

    /**数据库类型*/
    String getDbType();
}
