package com.mx.sql.mapping.methods;


import com.mx.sql.builder.SqlString;
import com.mx.sql.config.IDbConfig;
import com.mx.sql.dialect.IDialect;
import com.mx.sql.mapping.ISqlMethod;
import com.mx.sql.util.ISqlEngine;
import com.mx.sql.util.SqlEngine;

public class GetSqlValue implements ISqlMethod {

	@Override
	public String getName() {
		return "getSqlValue";
	}

	@Override
	public SqlString invoke(IDbConfig config, IDialect dialect, Object... args) {
		ISqlEngine ish = SqlEngine.instance(config.getName());
		if(args.length>0){
			String sqlId = (String)args[0];
			Object[] sqlArgs = new Object[args.length-1];
			for(int i=1;i<args.length;i++)sqlArgs[i-1]=args[i];
			Object val = ish.queryValue(sqlId, sqlArgs);
			if(val!=null)return new SqlString(val.toString());
		}
		return null;
	}
	

}
