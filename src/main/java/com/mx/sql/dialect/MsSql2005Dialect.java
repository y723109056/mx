package com.mx.sql.dialect;


import com.mx.sql.builder.SqlString;
import com.mx.sql.builder.SqlStringBuilder;
import com.mx.util.StringUtil;

/**
 * Ms SqlServer 2005 数据库方言
 */
public class MsSql2005Dialect extends MsSql2000Dialect {
	
	@Override
	public  boolean supportsLimit()
	{
		return true;
	}

	@Override
	public boolean supportsLimitOffset()
	{
		return true;
	}
	
	/** 对Sql进行分页包装 */
	@Override
	public String getLimitString(String querySqlString, int offset, int limit)
	{
		StringBuilder sb = new StringBuilder();
		
		String orderBy = this.getLastOrderByString(querySqlString);
		if (offset > 0)
		{
			String orderStr = orderBy.equals("")?"order by tempColumn":orderBy;
			sb.append(StringUtil.format("select * from ( select row_number() over({0}) rownum,row__.* from ( ", orderStr));
			sb.append(StringUtil.format("SELECT TOP {0} 0 as tempColumn,row_.* FROM ( ",limit));
		}
		else
		{
			sb.append("select * from ( ");
			sb.append(StringUtil.format("SELECT TOP {0} * FROM ( ",limit));
		}
		sb.append(orderBy.equals("")?querySqlString:querySqlString.replaceAll("order\\ {1,}by.*|Order\\ {1,}By.*|ORDER\\ {1,}BY.*",""));
		sb.append(StringUtil.format(") row_ {0}",orderBy));
		if (offset > 0)
		{
			sb.append(StringUtil.format(" ) row__ ) row__ where rownum > {0}",offset));
		}
		else
		{
			sb.append(" ) row__");
		}
		
		return sb.toString();
	}

	
	/** 对Sql进行分页包装 */
	@Override
	public SqlString getLimitString(SqlString querySqlString, int offset, int limit)
	{
		SqlStringBuilder sb = new SqlStringBuilder(SQLBUILDERSIZE);
		String orderBy = this.getLastOrderByString(querySqlString.toString());
		if (offset > 0)
		{
			String orderStr = orderBy.equals("")?"order by tempColumn":orderBy;
			sb.append(StringUtil.format("select * from ( select row_number() over({0}) rownum,row__.* from ( ",orderStr));
			sb.append(StringUtil.format("SELECT TOP {0} 0 as tempColumn,row_.* FROM ( ",limit));
		}
		else
		{
			sb.append("select * from ( ");
			sb.append(StringUtil.format("SELECT TOP {0} * FROM ( ",limit));
		}
		sb.append(orderBy.equals("")?querySqlString:querySqlString.replaceAll("order\\ {1,}by.*|Order\\ {1,}By.*|ORDER\\ {1,}BY.*",""));
		sb.append(StringUtil.format(") row_ {0}",orderBy));
		if (offset > 0)
		{
			sb.append(StringUtil.format(" ) row__ ) row__ where rownum > {0}",offset));
		}
		else
		{
			sb.append(" ) row__");
		}
		
		return sb.toSqlString();
	}
	
	public MsSql2005Dialect()
	{
		super();
	}
}
