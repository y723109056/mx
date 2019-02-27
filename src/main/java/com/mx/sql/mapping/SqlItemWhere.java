package com.mx.sql.mapping;


import com.mx.sql.builder.SqlString;

public class SqlItemWhere extends SqlItemContent implements ISqlItemClone<SqlItemWhere> {
	
	@Override
	public SqlString toString(SqlItemArgs arg)
	{
		for(ISqlItemPart part : this.parts){
			if(part instanceof ContentTextWrapper){
				SqlString sql = part.toString(arg);
				if(sql!=null && !sql.isEmpty())break;
			}else if(part instanceof SqlItemClause){
				arg.setHidePrepend(true);
				break;
			}
		}
		SqlString sql = super.toString(arg);
		if(!sql.isEmpty())
		{
			return new SqlString(" where ").contact(sql);
		}
		else return sql;
	}
	
	@Override
	public SqlItemWhere clone(Object args){
		SqlItemWhere where = new SqlItemWhere();
		where.map=this.map;
		where.parts=this.cloneParts(args);
		return where;
	}
}
