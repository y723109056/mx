package com.mx.sql.dialect;


import com.mx.sql.builder.Parameter;
import com.mx.sql.builder.SqlString;
import com.mx.sql.builder.SqlStringBuilder;
import com.mx.sql.command.ValueType;
import com.mx.sql.util.SqlType;
import com.mx.util.DateUtil;
import com.mx.util.StringUtil;

import java.util.Date;

/**
 * Oracle数据库方言
 */
public class OracleDialect extends AbstractDialect {
	private String[] fStartCharsOracle = {"啊", "芭", "擦","搭","蛾","发","噶","哈","击","击","喀","垃","妈","拿","哦","啪",
			 "期","然", "撒","塌","挖","挖","挖","西","压","匝"};
	private String[] fEndCharsOracle = {"澳", "怖", "错","堕","贰","咐","过","祸","啊","骏","阔","络","穆","诺","沤","瀑",
			   "群","弱", "所","唾","啊","啊","误","迅","孕","座"};
	
	public OracleDialect()
	{
		super();
		
		timestampForamt = "yyyy-MM-dd HH24:MI:ss.ff";
		dateTimeFormat = "yyyy-MM-dd HH24:MI:ss";
		this.sqlFunctions.put("SUBSTRING", "SUBSTR");
		this.sqlFunctions.put("COUNT", "COUNT");
		
		this.pyStartChars = fStartCharsOracle;
		this.pyEndChars = fEndCharsOracle;
	}
	
	/** 是否支持分页Limit */
	@Override
	public boolean supportsLimit()
	{
		return true;
	}
	
	/** 是否支持分页Limit和Offset */
	@Override
	public boolean supportsLimitOffset() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean supportsSequences()
    {
        return true;
    }
	
	/** 对查询前几条数据 */
    public String getTopString(String querySqlString,int limit){
    	return this.getLimitString(querySqlString, 0, limit);
    }
    
    /** 对查询前几条数据 */
    public SqlString getTopString(SqlString querySqlString,int limit){
    	return this.getLimitString(querySqlString, 0, limit);
    }
	
	/** 对Sql进行分页包装 */
	@Override
	public String getLimitString(String querySqlString, int offset, int limit)
	{
		StringBuilder sb = new StringBuilder();
		if (offset > 0)
		{
		sb.append("select * from ( select row_.*, rownum rownum_ from ( ");
		}
		else
		{
		sb.append("select * from ( ");
		}
		sb.append(querySqlString);
		if (offset > 0)
		{
		sb.append(StringUtil.format(" ) row_ where rownum <= {0}  ) where rownum_ > {1}", limit, offset));
		}
		else
		{
		sb.append(StringUtil.format(" ) where rownum <= {0}", limit));
		}
		
		return sb.toString();
	}
	
	/** 对Sql进行分页包装 */
	@Override
	public SqlString getLimitString(SqlString querySqlString, int offset, int limit)
	{
		SqlStringBuilder sb = new SqlStringBuilder(SQLBUILDERSIZE);
		if (offset > 0)
		{
			sb.append("select * from ( select row_.*, rownum rownum_ from ( ");
		}
		else
		{
			sb.append("select * from ( ");
		}
		sb.append(querySqlString);
		if (offset > 0)
		{
			sb.append(StringUtil.format(" ) row_ where rownum <= {0}  ) where rownum_ > {1}", limit, offset));
		}
		else
		{
			sb.append(StringUtil.format(" ) where rownum <= {0}", limit));
		}
		
		return sb.toSqlString();
	}
	
	@Override
	public String getParamSign()
	{
		return ":";
	}
	
	/** 日期格式化 */
	@Override
	public Object toDate(Object date){
		if(date instanceof Parameter){
			Parameter p = (Parameter)date;
			String format = DateUtil.hasTime((Date) p.getValue())?dateTimeFormat:dateFormat;
			SqlString part1 = new SqlString("to_date(");
			SqlString part2 = part1.contact(new SqlString(p));
			SqlString part3 = part2.contact(new SqlString(",'"+format+"')"));
			return part3;
		}else if(date instanceof Date){
			Date d = (Date)date;
			String format = DateUtil.hasTime(d)?dateTimeFormat:dateFormat;
			String dateStr = SqlType.toSqlString(d);
			if(dateStr.indexOf(".")>-1){
				return StringUtil.format("to_timestamp('{0}','{1}')",dateStr,timestampForamt);
			}else{
				return StringUtil.format("to_date('{0}','{1}')",dateStr,format);
			}
		}else if(date instanceof String){
			String dateStr = (String)date;
			String format = (dateStr.indexOf(":")>-1)?dateTimeFormat:dateFormat;
			return StringUtil.format("to_date('{0}','{1}')",dateStr,format);
		}
		return null;
	}
	
	/** 日期格式化 */
	public Object toChar(Object date){
		if(date instanceof Parameter){
			Parameter p = (Parameter)date;
			String format = DateUtil.hasTime((Date)p.getValue())?dateTimeFormat:dateFormat;
			SqlString part1 = new SqlString("to_char(");
			SqlString part2 = part1.contact(new SqlString(p));
			SqlString part3 = part2.contact(new SqlString(",'"+format+"')"));
			return part3;
		}else if(date instanceof Date){
			Date d = (Date)date;
			String format = DateUtil.hasTime(d)?dateTimeFormat:dateFormat;
			String dateStr = SqlType.toSqlString(d);
			if(dateStr.indexOf(".")>-1){
				return StringUtil.format("to_char({0},'{1}')",dateStr,timestampForamt);
			}else{
				return StringUtil.format("to_char({0},'{1}')",dateStr,format);
			}
		}else if(date instanceof String){
			String dateStr = (String)date;
			if(dateStr.indexOf(":")>-1){
				return StringUtil.format("to_char({0},'{1}')",dateStr,dateTimeFormat);
			}else{
				return StringUtil.format("to_char({0},'{1}')",dateStr,timestampForamt);
			}
		}
		return null;
	}
	
	/** 日期格式化 */
	public Object toChar(Object date,String format){
		if(date instanceof Parameter){
			Parameter p = (Parameter)date;
			SqlString part1 = new SqlString("to_char(");
			SqlString part2 = part1.contact(new SqlString(p));
			SqlString part3 = part2.contact(new SqlString(",'"+format+"')"));
			return part3;
		}else if(date instanceof Date){
			Date d = (Date)date;
			String dateStr = SqlType.toSqlString(d);
			return StringUtil.format("to_char({0},'{1}')",dateStr,format);
		}else if(date instanceof String){
			String dateStr = (String)date;
			return StringUtil.format("to_char({0},'{1}')",dateStr,format);
		}
		return null;
	}
	
	/** 当前日期 */
    public String getCurrentDate(){
    	return "trunc(sysdate)";
    }

    /** 当前日期时间 */
    public String getCurrentDateTime(){
    	return "sysdate";
    }
    
    /** 当前时间 */
    public String getCurrentTime(){
    	return "";
    }

	@Override
	public IMetaSql getMetaSql() {
		if(this.meta==null)this.meta = new OracleMeta();
		return this.meta;
	}

	@Override
	public String getDbType() {
		return "ORACLE";
	}

	class OracleMeta implements IMetaSql
	{
		@Override
		public String[] getCreateSequenceSql(String sequenceName, Long value) {
			return new String[]{StringUtil.format("CREATE SEQUENCE {0} START WITH {1}",sequenceName,value)};
		}
		@Override
		public String getSequencesSql() {
			return "SELECT SEQUENCE_NAME AS SNAME,MIN_VALUE AS SVALUE FROM USER_SEQUENCES";
		}
		@Override
		public String getSequenceSql(String sequenceName) {
			return StringUtil.format("SELECT SEQUENCE_NAME AS SNAME,MIN_VALUE AS SVALUE FROM USER_SEQUENCES WHERE SEQUENCE_NAME='{0}'",sequenceName);
		}
		@Override
		public String getSequenceNextSql(String sequenceName) {
			return StringUtil.format("SELECT {0}.nextval FROM dual",sequenceName);
		}
		@Override
		public String getSequenceNextSql(String sequenceName,int num) {
			return StringUtil.format("SELECT {0}.nextval FROM dual CONNECT BY LEVEL <= {1}",sequenceName,num);
		}
		@Override
		public String getDropSequenceSql(String sequenceName) {
			return StringUtil.format("DROP SEQUENCE {0}",sequenceName);
		}
		
		@Override
		public String getTablesSql()
		{
			return "SELECT T.TNAME AS TABLE_NAME,C.COMMENTS AS TABLE_REMARK FROM TAB T LEFT JOIN USER_TAB_COMMENTS C ON T.TNAME=C.TABLE_NAME";
		}
		
		@Override
		public String getTablesSql(String tableName)
		{
			return "SELECT T.TNAME AS TABLE_NAME,C.COMMENTS AS TABLE_REMARK FROM TAB T LEFT JOIN USER_TAB_COMMENTS C ON T.TNAME=C.TABLE_NAME AND T.TNAME='"+tableName+"'";
		}
		
		public String getCreateTableSql(String tableName,String pk,String[] exts){
			String pkStr = pk+" INTEGER NOT NULL,";
			String extStr = "";
			if(exts!=null && exts.length>0){
				for(String ext : exts){
					extStr+=ext+",";
				}
			}
			return "CREATE TABLE "+tableName+" ("+pkStr+extStr+"PRIMARY KEY ("+pk+"))";
		}
		
		@Override
		public String getFieldsSql(String tableName)
		{
			String sqlStr = StringUtil.format("SELECT c.TABLE_NAME,c.COLUMN_NAME AS FIELD_NAME,DATA_TYPE AS FIELD_TYPE,DATA_LENGTH AS FIELD_LENGTH,DATA_SCALE AS FIELD_SCALE,DATA_PRECISION AS FIELD_PRECISION,NULLABLE,CASE p.POSITION WHEN 1 THEN 'Y' ELSE 'N' END AS IS_PK,t.COMMENTS AS FIELD_REMARK FROM user_tab_columns c  left join USER_COL_COMMENTS t ON c.COLUMN_NAME=t.COLUMN_NAME AND c.TABLE_NAME=t.TABLE_NAME left join USER_CONS_COLUMNS p ON c.COLUMN_NAME=p.COLUMN_NAME AND c.TABLE_NAME=p.TABLE_NAME AND POSITION=1 WHERE c.table_name= '{0}'", new Object[] {
				tableName
			});
			return sqlStr;
		}
		
		/**
		 * 修改表字段名
		 * @param tableName
		 * @param oldColumnName
		 * @param newColumnName
		 * @param fieldType
		 * @return
		 */
		@Override
		public String getRenameTableColumnSql(String tableName, String oldColumnName, String newColumnName, String fieldType){
			return "ALTER TABLE "+tableName+" RENAME COLUMN "+oldColumnName+" TO "+newColumnName;
		}
		
		/**
		 * 添加数据库字段
		 * @param tableName
		 * @param columnName
		 * @param length
		 * @param type
		 * @return
		 */
		@Override
		public String getAddTableColumnSql(String tableName,String columnName,Integer length,Integer scale,ValueType type){
			switch (type) {
				case Varchar:
					return "ALTER TABLE "+tableName+" ADD ("+columnName+" VARCHAR2("+length+"))";
				case Integer:
					return "ALTER TABLE "+tableName+" ADD ("+columnName+" INTEGER)";
				case Date:
				case TimeStamp:
					return "ALTER TABLE "+tableName+" ADD ("+columnName+" TimeStamp)";
				case Numeric:
				case Decimal:
					return "ALTER TABLE "+tableName+" ADD ("+columnName+" NUMBER("+length+","+scale+"))";
				case Double:
					return "ALTER TABLE "+tableName+" ADD ("+columnName+" NUMBER(15,4))";
				default:
					throw new RuntimeException("生成添加数据库表字段SQL出错,没有实现该类型:"+type);
			}
		}
		
		/**
		 * 修改例注释
		 * @param tableName
		 * @param oldColumnName
		 * @param newColumnName
		 * @return
		 */
		@Override
		public String getCommentTableColumnSql(String tableName,String columnName,String comments){
			return "COMMENT ON column "+tableName+"."+columnName+" IS '"+comments+"'";
		}
		
		/**
		 * 删除数据库字段
		 * @return
		 */
		@Override
		public String getDeteleTableColumnSql(String tableName,String columnName){
			return "ALTER TABLE "+tableName+" DROP COLUMN "+columnName;
		}
		@Override
		public ValueType getType(int fieldType){
			return null;
		}
	}
}
