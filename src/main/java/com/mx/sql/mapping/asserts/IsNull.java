package com.mx.sql.mapping.asserts;


import com.mx.sql.dialect.IDialect;
import com.mx.sql.mapping.ISqlClauseAssert;

public class IsNull implements ISqlClauseAssert {

	@Override
	public String getName() {
		return "isNull";
	}

	@Override
	public boolean invoke(IDialect dialect, Object... args) {
		if(args.length==0)return true;
		return args[0]==null;
	}

}
