package com.mx.sql.mapping;


import com.mx.sql.builder.SqlString;

public class SqlItemSet extends SqlItemContent implements ISqlItemClone<SqlItemSet> {
	
	@Override
	public SqlString toString(SqlItemArgs arg)
	{
		arg.setHidePrepend(true);
		SqlString sql = super.toString(arg);
		return new SqlString(" set ").contact(sql);
	}
	
	@Override
	public SqlItemSet clone(Object args){
		SqlItemSet set = new SqlItemSet();
		set.map=this.map;
		set.parts=this.cloneParts(args);
		return set;
	}
}
