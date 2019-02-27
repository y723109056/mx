package com.mx.sql.mapping;


import com.mx.sql.SqlMapException;
import com.mx.sql.builder.SqlString;
import com.mx.util.StringUtil;
import com.mx.util.TypeUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class SqlItemForeach extends SqlItemContent implements ISqlItemClone<SqlItemForeach> {
	private String inParamName;
	private String[] defineVarNames=new String[]{"item","index","size"};
	/** 拼接字符串 */
	private String prepend;
	private String split;
	
	/**
	 * 设置输入参数名
	 * @param paramName
	 */
	public void setInParamName(String paramName){
		this.inParamName = paramName;
	}
	
	public void setVariableNames(String[] variableNames){
		for(int i=0;i<variableNames.length;i++){
			this.defineVarNames[i]=variableNames[i];
		}
	}
	
	/**
	 * 得到拼接字符串
	 * @return
	 */
	public String getPrepend()
	{
		return this.prepend;
	}
	
	/**
	 * 设置接接字符串
	 * @param prepend
	 */
	public void setPrepend(String prepend)
	{
		this.prepend = prepend;
	}
	
	/**
	 * 得到循环分隔符
	 * @return
	 */
	public String getSplit(){
		return this.split;
	}
	
	/**
	 * 设置循环分隔符
	 * @param split
	 */
	public void setSplit(String split){
		this.split = split;
	}
	
	public SqlItemForeach clone(Object args){
		SqlItemForeach foreach = new SqlItemForeach();
		foreach.map=this.map;
		foreach.parts=this.cloneParts(args);
		return foreach;
	}
	
	@Override
	public SqlString toString(SqlItemArgs arg) {
		if(!StringUtil.isNullOrEmpty(inParamName)){
			Object inParam = arg.parserParam(inParamName);
			if(inParam!=null){
				if(TypeUtil.isCollection(inParam)){
					Collection<?> ps = (inParam.getClass().isArray())? Arrays.asList((Object[]) inParam):(Collection<?>)inParam;
					if(ps.size()>0){
						if(arg.getVariables()==null)arg.setVariables(new HashMap<String,Object>());
						String size=this.defineVarNames[2].toLowerCase();
						String index=this.defineVarNames[1].toLowerCase();
						String item=this.defineVarNames[0].toLowerCase();
						arg.getVariables().put(size,ps.size());
						int i=0;
						SqlString sql = (StringUtil.isNullOrEmpty(this.prepend))?new SqlString(""):new SqlString(this.prepend);
						for(Object p : ps){
							arg.getVariables().put(index,i);
							arg.getVariables().put(item,p);
							SqlString sqlPart = super.toString(arg);
							if(!StringUtil.isNullOrEmpty(this.split) && i>0)
								sql=sql.contact(new SqlString(this.split));
							sql=sql.contact(sqlPart);
							i++;
						}
						arg.getVariables().remove(size);
						arg.getVariables().remove(index);
						arg.getVariables().remove(item);
						return sql;
					}
					return null;
				}
				else throw new SqlMapException(StringUtil.format("循环表达式输入参数必须是数组或集合  {0}",inParamName));
			}
			else return null;
		}
		else throw new SqlMapException("循环表达式输入参数名不能为空 ");
	}
}
