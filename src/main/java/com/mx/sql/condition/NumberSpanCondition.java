package com.mx.sql.condition;


import com.mx.util.StringUtil;

public class NumberSpanCondition implements ISqlCondition {

	@Override
	public String getCondition(Object data, Args args) {
		String property = args.getProperty();
		String propertyEnd = property+"End";
		String column = args.getColumn();
		String alias = (!StringUtil.isNullOrEmpty(args.getTableAlias()))?args.getTableAlias()+".":"";
		return "<clause prepend=\"and\" assert=\"!isNull(["+property+"])\" > <![CDATA["+alias+"\""+column+"\" >= ]]>["+property+"] </clause>"
				+"<clause prepend=\"and\" assert=\"!isNull(["+propertyEnd+"])\" > <![CDATA["+alias+"\""+column+"\" <= ]]>["+propertyEnd+"] </clause>";
	}
	
}
