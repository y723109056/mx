package com.mx.sql.dialect;

import com.mx.sql.command.ValueType;
import com.mx.sql.builder.SqlString;
import com.mx.sql.builder.SqlStringBuilder;
import com.mx.util.StringUtil;

public class DB2Dialect extends AbstractDialect {
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
    	return querySqlString + " fetch first "+limit+" rows only ";
    }
    
    /** 对查询前几条数据 */
    public SqlString getTopString(SqlString querySqlString,int limit){
    	return querySqlString.contact(" fetch first "+limit+" rows only ");
    }
	
	/** 对Sql进行分页包装 */
	@Override
	public String getLimitString(String querySqlString, int offset, int limit)
	{
		StringBuilder sb = new StringBuilder();
		
		String orderBy = this.getLastOrderByString(querySqlString.toString());
		orderBy = this.removeTableAlias(orderBy);
		if(offset > 0){
			sb.append(StringUtil.format("select * from ( select * from ( select row_number() over({0}) as rownum,row_.* from ( ", orderBy));
		}else{
			sb.append(StringUtil.format("select * from ( select row_number() over({0}) as rownum,row_.* from ( ",orderBy));
		}
		//sb.append(orderBy.equals("")?querySqlString:querySqlString.replaceAll("order\\ {1,}by.*|Order\\ {1,}By.*|ORDER\\ {1,}BY.*",""));
		//sb.append(StringUtil.format(") row_ {0}",orderBy));
		sb.append(querySqlString);
		sb.append(") row_ ");
		if (offset > 0){
			sb.append(StringUtil.format(") where rownum <= {0}  ) where rownum > {1}", limit, offset));
		}
		else{
			sb.append(StringUtil.format(") where rownum <= {0}", limit));
		}
		
		return sb.toString();
	}

	
	/** 对Sql进行分页包装 */
	@Override
	public SqlString getLimitString(SqlString querySqlString, int offset, int limit)
	{
		SqlStringBuilder sb = new SqlStringBuilder(SQLBUILDERSIZE);
		
		String orderBy = this.getLastOrderByString(querySqlString.toString());
		orderBy = this.removeTableAlias(orderBy);
		if(offset > 0){
			sb.append(StringUtil.format("select * from ( select * from ( select row_number() over({0}) as rownum,row_.* from ( ",orderBy));
		}else{
			sb.append(StringUtil.format("select * from ( select row_number() over({0}) as rownum,row_.* from ( ",orderBy));
		}
		//sb.append(orderBy.equals("")?querySqlString:querySqlString.replaceAll("order\\ {1,}by.*|Order\\ {1,}By.*|ORDER\\ {1,}BY.*",""));
		//sb.append(StringUtil.format(") row_ {0}",orderBy));
		sb.append(querySqlString);
		sb.append(") row_ ");
		if (offset > 0){
			sb.append(StringUtil.format(") where rownum <= {0}  ) where rownum > {1}", limit, offset));
		}
		else{
			sb.append(StringUtil.format(") where rownum <= {0}", limit));
		}
		
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
	
	/**
	 * 移除分页排序表的别名
	 * @param orderby
	 * @return
	 */
	protected String removeTableAlias(String orderby){
		java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\w{1,}\\.\\w{1,}");
		java.util.regex.Matcher m = p.matcher(orderby);
		
		String result = orderby;
		String group = "";
		while(m.find()){
			group = m.group();
			int n = group.indexOf(".");
			if(n>-1)result=result.replace(group,group.substring(n+1));
		}
		return result;
	}
	
    /** 当前日期 */
    public String getCurrentDate(){
    	return "current date";
    }
    
    /** 当前日期时间 */
    public String getCurrentDateTime(){
    	return "current timestamp";
    }
    
    /** 当前时间 */
    public String getCurrentTime(){
    	return "current time";
    }

	@Override
	public IMetaSql getMetaSql() {
		if(this.meta==null)this.meta = new DB2Meta();
		return this.meta;
	}

	@Override
	public String getDbType() {
		return "DB2";
	}

	class DB2Meta implements IMetaSql
	{
		@Override
		public String[] getCreateSequenceSql(String sequenceName, Long value) {
			return new String[]{StringUtil.format("CREATE SEQUENCE {0} START WITH {1}",sequenceName,value)};
		}
		@Override
		public String getSequencesSql() {
			return "SELECT SEQNAME AS SNAME,MINVALUE AS SVALUE FROM SYSCAT.SEQUENCES";
		}
		@Override
		public String getSequenceSql(String sequenceName) {
			return StringUtil.format("SELECT SEQNAME AS SNAME,MINVALUE AS SVALUE FROM SYSCAT.SEQUENCES WHERE SEQNAME='{0}'",sequenceName);
		}
		@Override
		public String getSequenceNextSql(String sequenceName) {
			return StringUtil.format("SELECT NEXTVAL FOR {0} FROM (VALUES 0)",sequenceName);
		}
		@Override
		public String getSequenceNextSql(String sequenceName,int num) {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT NEXTVAL FOR "+sequenceName);
			sb.append(" FROM (VALUES 0");
			for(int i=0;i<num-1;i++)sb.append(",0");
			sb.append(")");
			return sb.toString();
		}
		@Override
		public String getDropSequenceSql(String sequenceName) {
			return StringUtil.format("DROP SEQUENCE {0}", sequenceName);
		}
		@Override
		public String getTablesSql() {
			return "select name as TABLE_NAME,remarks AS TABLE_REMARK from sysibm.systables where type='T' order by name";
		}
		
		@Override
		public String getTablesSql(String tableName) {
			return "select name as TABLE_NAME,remarks AS TABLE_REMARK from sysibm.systables where type='T' and name='"+tableName+"' order by name";
		}
		
		@Override
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
		public String getFieldsSql(String tableName){
			return "select TABNAME AS TABLE_NAME,COLNAME AS FIELD_NAME,TYPENAME AS FIELD_TYPE,LENGTH AS FIELD_LENGTH,SCALE AS FIELD_SCALE,0 AS FIELD_PRECISION,NULLS AS NULLABLE,CASE WHEN KEYSEQ=1 THEN 'Y' ELSE 'N' END AS IS_PK,REMARKS AS FIELD_REMARK from syscat.columns where tabname='"+tableName+"' ORDER BY COLNO";
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
					return "ALTER TABLE "+tableName+" ADD  "+columnName+" VARCHAR("+length+")";
				case Integer:
					return "ALTER TABLE "+tableName+" ADD "+columnName+" INTEGER";
				case Date:
				case TimeStamp:
					return "ALTER TABLE "+tableName+" ADD "+columnName+" TimeStamp";
				case Numeric:
				case Decimal:
					return "ALTER TABLE "+tableName+" ADD "+columnName+" Decimal("+length+","+scale+")";
				case Double:
					return "ALTER TABLE "+tableName+" ADD "+columnName+" Double";
				default:
					throw new RuntimeException("生成添加数据库表字段SQL出错,没有实现该类型:"+type);
			}
		}
		
		/**
		 * 修改例注释
		 * @param tableName
		 * @param columnName
		 * @param comments
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
			return "ALTER TABLE "+tableName+" DROP COLUMN "+columnName +";"+"CALL SYSPROC.ADMIN_CMD('REORG TABLE "+tableName+"')";
		}
		@Override
		public ValueType getType(int fieldType){
			return null;
		}
	}
}
