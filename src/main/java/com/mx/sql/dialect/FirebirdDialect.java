package com.mx.sql.dialect;


import com.mx.sql.builder.SqlString;
import com.mx.sql.builder.SqlStringBuilder;
import com.mx.sql.command.ValueType;
import com.mx.util.StringUtil;

/**
 * Firebird数据库方言
 */
public class FirebirdDialect extends AbstractDialect {
	public FirebirdDialect()
	{
		super();
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
	
	/** 对Sql语句进去分页包装 */
	@Override
	public String getLimitString(String querySqlString, int offset, int limit)
	{
		StringBuilder sb = new StringBuilder();

        if (offset > 0)
        {
            sb.append(StringUtil.format("select first {0} skip {1} * from (}", limit - offset, offset));
        }
        else
        {
        	sb.append(StringUtil.format("select first {0} * from (", limit));
        }
        
        sb.append(querySqlString);
        sb.append(")");
        
        return sb.toString();
	}
	
	/** 对Sql语句进去分页包装 */
	@Override
	public SqlString getLimitString(SqlString querySqlString, int offset, int limit)
	{
		SqlStringBuilder sb = new SqlStringBuilder(SQLBUILDERSIZE);

        if (offset > 0)
        {
            sb.append(StringUtil.format("select first {0} skip {1} * from (",limit - offset,offset));
        }
        else
        {
        	sb.append(StringUtil.format("select first {0} * from (", limit));
        }
        
        sb.append(querySqlString);
        sb.append(")");
        
        return sb.toSqlString();
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
		if(this.meta==null)this.meta = new FirebirdMeta();
		return this.meta;
	}

	@Override
	public String getDbType() {
		return "Firebird";
	}

	class FirebirdMeta implements IMetaSql
	{
		@Override
		public String[] getCreateSequenceSql(String sequenceName, Long value) {
			String[] strs = new String[2];
			strs[0] = StringUtil.format("CREATE SEQUENCE {0}",sequenceName);
			strs[1] = StringUtil.format("ALTER SEQUENCE {0} RESTART WITH {1}",sequenceName,value);
			return strs;
		}
		@Override
		public String getSequencesSql() {
			return "SELECT RDB$GENERATOR_NAME AS SNAME,RDB$GENERATOR_ID AS SVALUE FROM RDB$GENERATORS WHERE RDB$SYSTEM_FLAG=0";
		}
		@Override
		public String getSequenceSql(String sequenceName) {
			return StringUtil.format("SELECT RDB$GENERATOR_NAME AS SNAME,RDB$GENERATOR_ID AS SVALUE FROM RDB$GENERATORS WHERE RDB$SYSTEM_FLAG=0 AND RDB$GENERATOR_NAME='{0}'",sequenceName);
		}
		@Override
		public String getSequenceNextSql(String sequenceName) {
			return StringUtil.format("SELECT NEXT VALUE FOR {0} FROM RDB$DATABASE",sequenceName);
		}
		@Override
		public String getSequenceNextSql(String sequenceName,int num) {
			return StringUtil.format("SELECT NEXT VALUE FOR {0} FROM RDB$DATABASE",sequenceName);
		}
		@Override
		public String getDropSequenceSql(String sequenceName) {
			return StringUtil.format("DROP SEQUENCE \"{0}\"",sequenceName);
		}
		@Override
		public String getTablesSql(){
			return "SELECT RDB$RELATION_NAME AS TABLE_NAME FROM RDB$RELATIONS WHERE RDB$SYSTEM_FLAG = 0 AND RDB$VIEW_BLR IS NULL ORDER BY RDB$RELATION_NAME";
		}
		@Override
		public String getTablesSql(String tableName){
			return "SELECT RDB$RELATION_NAME AS TABLE_NAME FROM RDB$RELATIONS WHERE RDB$SYSTEM_FLAG = 0 AND RDB$VIEW_BLR IS NULL AND RDB$RELATION_NAME='"+tableName+"' ORDER BY RDB$RELATION_NAME";
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
			return StringUtil.format("SELECT A.RDB$FIELD_NAME AS FIELD_NAME,B.RDB$FIELD_TYPE AS FIELD_TYPE,B.RDB$FIELD_LENGTH AS FIELD_LENGTH,B.RDB$FIELD_PRECISION AS FIELD_PRECISION,B.RDB$FIELD_SCALE AS FIELD_SCALE FROM RDB$RELATION_FIELDS A,RDB$FIELDS B WHERE A.RDB$RELATION_NAME = '{0}' AND A.RDB$FIELD_SOURCE = B.RDB$FIELD_NAME",tableName);
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
		 * @param columnName
		 * @param comments
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
			switch(fieldType){
				case 7:
					return ValueType.SmallInt;
				case 8:
					return ValueType.Integer;
				case 12:
					return ValueType.Date;
				case 16:
					return ValueType.Decimal;
				case 37:
					return ValueType.Varchar;
				case 35:
					return ValueType.TimeStamp;
			}
			return null;
		}
	}
}
