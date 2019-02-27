package com.mx.sql.condition;


import com.mx.util.StringUtil;

/**
 * 左模糊查询
 */
public class FuzzyLeftCondition implements ISqlCondition  {

	public String getCondition(Object data, Args args) {
		String property = args.getProperty();
		String column = args.getColumn();
		String alias = (!StringUtil.isNullOrEmpty(args.getTableAlias()))?args.getTableAlias()+".":"";
		return "<clause prepend=\"and\" assert=\"!isNullOrEmpty(["+property+"])\" >"+alias+"\""+column+"\" like '%["+property+"]%' </clause>";
	}
	
}
