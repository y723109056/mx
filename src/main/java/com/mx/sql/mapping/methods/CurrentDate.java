package com.mx.sql.mapping.methods;


import com.mx.sql.builder.SqlString;
import com.mx.sql.config.IDbConfig;
import com.mx.sql.dialect.IDialect;
import com.mx.sql.mapping.ISqlMethod;

public class CurrentDate implements ISqlMethod {

	public String getName() {
		return "currentDate";
	}

	public SqlString invoke(IDbConfig config,IDialect dialect, Object... args) {
		return new SqlString(dialect.getCurrentDate());
	}

}
