package com.mx.sql.dialect;


import com.mx.sql.builder.SqlString;
import com.mx.sql.builder.SqlStringBuilder;
import com.mx.util.StringUtil;

/**
 * Ms SqlServer 2000 数据库方言
 */
public class MsSql2000Dialect extends AbstractDialect {
	private static String[] fStartCharsSQL = {"吖", "八", "嚓","咑","妸","发","旮","铪","丌","丌","咔","垃","嘸","拏","噢","妑",
		 "七","呥", "仨","他","屲","屲","屲","夕","丫","帀"};
	private static String[] fEndCharsSQL = {"驁", "簿", "錯","鵽","樲","猤","妎","夻","夻","攈","穒","鱳","椧","桛","漚",	"曝",
		"囕", "鶸","蜶","籜","籜","籜","鶩","鑂","韻","咗"};
	
	public MsSql2000Dialect() 
	{
		super();
		this.sqlFunctions.put("SUBSTRING", "SUBSTRING");
		this.sqlFunctions.put("COUNT", "COUNT");
		
		this.pyStartChars = fStartCharsSQL;
		this.pyEndChars = fEndCharsSQL;
	}
	
	/** 开闭符，开头 */
	@Override
	public  char getCloseQuote()
	{
		return ']';
	}
	
	/** 开闭符，结尾 */
	@Override
	public  char getOpenQuote()
	{
		return '[';
	}
	
    /**
     * 是否需要开闭符
     */
    @Override
	public boolean hasQuote()
	{
		return true;
	}
	
	/** 是否支持分页Limit */
	@Override
	public  boolean supportsLimit()
	{
		return true;
	}
	
	/** 是否支持分页Limit和Offset */
	@Override
	public  boolean supportsLimitOffset()
	{
		return false;
	}
	
	/** 添加开闭符 */
	@Override
	public  String quote(String name)
	{
		return this.getOpenQuote() + name.replace(this.getCloseQuote()+"", StringUtil.getString(this.getCloseQuote(), 2)) + this.getCloseQuote();
	}
	
	/** 解除开闭符 */
	@Override
	public  String unQuote(String quoted)
	{
		if (isQuoted(quoted))
		{
			quoted = quoted.substring(1);
		}
		return quoted.replace(StringUtil.getString(this.getCloseQuote(), 2), this.getCloseQuote()+"");
	}
	
	/** 对查询前几条数据 */
    public String getTopString(String querySqlString,int top){
    	return "SELECT TOP " + top + " * FROM (" + querySqlString + ") row_";
    }
    
    /** 对查询前几条数据 */
    public SqlString getTopString(SqlString querySqlString,int top){
    	return new SqlString("SELECT TOP " + top + " * FROM (").contact(querySqlString).contact(") row_");
    }
	
	/** 对Sql进行分页包装 */
	@Override
	public  String getLimitString(String querySqlString, int offset, int limit)
	{
		if (offset > 0)
		{
			throw new RuntimeException("SQL Server does not support an offset");
		}
	
		StringBuilder sb = new StringBuilder();
		String orderBy = this.getLastOrderByString(querySqlString.toString());
		sb.append("select * from ( ");
		sb.append(StringUtil.format("SELECT TOP {0} * FROM ( ",limit));
		sb.append(orderBy.equals("")?querySqlString:querySqlString.replaceAll("order\\ {1,}by.*|Order\\ {1,}By.*|ORDER\\ {1,}BY.*",""));
		sb.append(StringUtil.format(") row_ {0}",orderBy));
		sb.append(" ) row__");
		return sb.toString();
	}
	
	/** 对Sql进行分页包装 */
	@Override
	public SqlString getLimitString(SqlString querySqlString, int offset, int limit)
	{
		if (offset > 0)
		{
			throw new RuntimeException("SQL Server does not support an offset");
		}
		
		SqlStringBuilder sb = new SqlStringBuilder(SQLBUILDERSIZE);
		String orderBy = this.getLastOrderByString(querySqlString.toString());
		sb.append("select * from ( ");
		sb.append(StringUtil.format("SELECT TOP {0} * FROM ( ",limit));
		sb.append(orderBy.equals("")?querySqlString:querySqlString.replaceAll("order\\ {1,}by.*|Order\\ {1,}By.*|ORDER\\ {1,}BY.*",""));
		sb.append(StringUtil.format(") row_ {0}",orderBy));
		sb.append(" ) row__");
		return sb.toSqlString();
	}
	
	protected String getLastOrderByString(String sqlString)
	{
		java.util.regex.Pattern p = java.util.regex.Pattern.compile("order\\ {1,}by.*|Order\\ {1,}By.*|ORDER\\ {1,}BY.*");
		java.util.regex.Matcher m = p.matcher(sqlString);
		String last = "";
		while(m.find())
		{
			last = m.group();
		}
		return last.trim();
	}
	
	/** 判断是否存在该表 */
	@Override
	public  String getExistTableSql(String tableName)
	{
		return StringUtil.format("SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME='{0}'", tableName);
	}
	

	@Override
	public String getParamSign()
	{
		return super.getParamSign();
	}
	
	/** 当前日期 */
    public String getCurrentDate(){
    	return "";
    }
    
    /** 当前日期时间 */
    public String getCurrentDateTime(){
    	return "getdate()";
    }
    
    /** 当前时间 */
    public String getCurrentTime(){
    	return "";
    }

	@Override
	public IMetaSql getMetaSql() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDbType() {
		return "MsSql";
	}
}
