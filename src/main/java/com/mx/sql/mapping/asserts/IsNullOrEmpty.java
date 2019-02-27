package com.mx.sql.mapping.asserts;


import com.mx.sql.dialect.IDialect;
import com.mx.sql.mapping.ISqlClauseAssert;

public class IsNullOrEmpty implements ISqlClauseAssert {

	@Override
	public String getName() {
		return "isNullOrEmpty";
	}

	@Override
	public boolean invoke(IDialect dialect, Object... args) {
		if(args.length==0)return true;
		Object arg = args[0];
		if(arg==null || "".equals(arg)){
			return true;
		}
		return false;
	}

}
