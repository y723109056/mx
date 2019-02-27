package com.mx.sql.dialect;


import com.mx.sql.builder.SqlString;

/**
 * SqlLite数据库方言
 */
public class SqlLiteDialect extends AbstractDialect {
	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDbType() {
		return "SqlLite";
	}

	@Override
	public String getTopString(String querySqlString, int top) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SqlString getTopString(SqlString querySqlString, int top) {
		// TODO Auto-generated method stub
		return null;
	}

}