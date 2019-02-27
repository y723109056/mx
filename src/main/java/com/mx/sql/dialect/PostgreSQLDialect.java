package com.mx.sql.dialect;


import com.mx.sql.builder.SqlString;
import com.mx.sql.command.ValueType;
import com.mx.util.StringUtil;

/**
 * PostgreSql数据库方言
 */
public class PostgreSQLDialect extends AbstractDialect {
	public PostgreSQLDialect()
	{
    	super();
	}

	/** 开闭符,结尾 */
    @Override
	public  char getCloseQuote()
	{
		return '"';
	}

    /** 开闭符，开头 */
    @Override
	public char getOpenQuote()
	{
		return '"';
	}
    
    /**
     * 是否需要开闭符
     */
    @Override
	public boolean hasQuote()
	{
		return true;
	}
    
	@Override
	public boolean supportsSequences()
    {
        return true;
    }

    /** 是否支持分页Limit */
    @Override
	public boolean supportsLimit()
	{
		return true;
	}

    /** 是否支持分页Limit和Offset */
    @Override
	public boolean supportsLimitOffset()
	{
		return true;
	}
    
    /** 对查询前几条数据 */
    public String getTopString(String querySqlString,int limit){
    	return querySqlString + " limit "+limit+" ";
    }
    
    /** 对查询前几条数据 */
    public SqlString getTopString(SqlString querySqlString,int limit){
    	return querySqlString.contact(" limit "+limit+" ");
    }

    /** 对Sql进行分页包装 */
    @Override
    public  String getLimitString(String querySqlString, int offset, int limit)
    {
        String sizeSql = "";
        if (offset > 0)
        {
            sizeSql = StringUtil.format("{0} limit {1} offset {2}", querySqlString, limit - offset, offset);
        }
        else
        {
            sizeSql = StringUtil.format("{0} limit {1}",querySqlString, limit);
        }
        return sizeSql;
    }
    
    /** 对Sql进行分页包装 */
    @Override
    public  SqlString getLimitString(SqlString querySqlString, int offset, int limit)
    {
        if (offset > 0)
        {
            return querySqlString.contact(new SqlString(StringUtil.format(" limit {0} offset {1}",limit - offset, offset)));
        }
        else
        {
        	return querySqlString.contact(new SqlString(StringUtil.format(" limit {0}",limit)));
        }
    }
    
    /** 当前日期 */
    public String getCurrentDate(){
    	return "";
    }
    
    /** 当前日期时间 */
    public String getCurrentDateTime(){
    	return "";
    }
    
    /** 当前时间 */
    public String getCurrentTime(){
    	return "";
    }

	@Override
	public IMetaSql getMetaSql() {
		if(this.meta==null)this.meta = new PgsqlMeta();
		return this.meta;
	}

	@Override
	public String getDbType() {
		return "PostgreSql";
	}

	class PgsqlMeta implements IMetaSql
	{
		@Override
		public String[] getCreateSequenceSql(String sequenceName, Long value) {
			return new String[]{StringUtil.format("CREATE SEQUENCE \"{0}\" START {1}",sequenceName,value)};
		}
		@Override
		public String getSequencesSql() {
			return "SELECT sequence_name as SNAME,currval(Cast('\"'||sequence_name||'\"' as text)) as SVALUE FROM information_schema.sequences";
		}
		@Override
		public String getSequenceSql(String sequenceName) {
			return StringUtil.format("SELECT sequence_name as SNAME,currval(Cast('\"'||sequence_name||'\"' as text)) as SVALUE FROM information_schema.sequences where sequence_name='{0}'",sequenceName);
		}
		@Override
		public String getSequenceNextSql(String sequenceName) {
			return StringUtil.format("SELECT nextval('\"{0}\"')",sequenceName);
		}
		@Override
		public String getSequenceNextSql(String sequenceName,int num) {
			return StringUtil.format("SELECT nextval('\"{0}\"')",sequenceName);
		}
		@Override
		public String getDropSequenceSql(String sequenceName) {
			return StringUtil.format("DROP SEQUENCE \"{0}\"",sequenceName);
		}
		@Override
		public String getTablesSql() {
			return null;
		}
		@Override
		public String getTablesSql(String tableName) {
			return null;
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
			return null;
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
		public String getRenameTableColumnSql(String tableName, String oldColumnName, String newColumnName, String fieldType) {
			return null;
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
			return null;
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
			return null;
		}
		
		/**
		 * 删除数据库字段
		 * @return
		 */
		@Override
		public String getDeteleTableColumnSql(String tableName,String columnName){
			return null;
		}
		@Override
		public ValueType getType(int fieldType){
			return null;
		}
	}
}
