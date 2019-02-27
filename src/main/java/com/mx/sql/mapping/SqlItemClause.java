package com.mx.sql.mapping;


import com.mx.sql.SqlMapException;
import com.mx.sql.builder.NullParameter;
import com.mx.sql.builder.Parameter;
import com.mx.sql.builder.SqlString;
import com.mx.sql.util.SqlType;
import com.mx.util.StringUtil;
import com.mx.util.TypeUtil;

import java.util.Date;

/**
 * Sql子句配置对像
 */
public class SqlItemClause extends SqlItemContent implements ISqlItemClone<SqlItemClause>,ISqlParamMacro {
	/** 标识,继承时会用到 */
	private String id;
	/**条件表达式,成立时拼接子句 */
	private ClauseAssert cAssert;
	/** 拼接字符串 */
	private String prepend;
	/** 空值 */
	private String nvl;
	/** 参数名集合 */
	private String[] paramNames;
	/** 父模版 */
	private SqlItemClause parent;
	
	/**
	 * 得到条件表达式
	 * @return
	 */
	public ClauseAssert getAssert()
	{
		return this.cAssert;
	}
	
	public void setAssert(ClauseAssert cAssert)
	{
		this.cAssert = cAssert;
	}
	
	public void setAssert(String cAssert)
	{
		if(!StringUtil.isNullOrEmpty(cAssert))
		{
			this.cAssert = new ClauseAssert(this,cAssert);
		}
	}
	
	/**
	 * 得到空值
	 * @return
	 */
	public String getNvl() {
		return nvl;
	}
	
	/**
	 * 设置空值
	 * @param nvl
	 */
	public void setNvl(String nvl) {
		this.nvl = nvl;
	}
	
	/**
	 * 得到空参数
	 * @return
	 */
	public Parameter getNvlParam(){
		String str = nvl.trim();
		boolean strFlag = false;
		if(str.charAt(0)=='\'' && str.charAt(str.length()-1)=='\''){
			strFlag = true;
			str=str.substring(1,str.length()-1);
		}
		if(strFlag){
			if("".equals(str)){
				return new Parameter("");
			}else if(StringUtil.isDate(str)){
				Date date = (Date) TypeUtil.changeType(str, Date.class);
				return new Parameter(date);
			}else{
				return new Parameter(str);
			}
		}else{
			if(str.equalsIgnoreCase("null")){
				return new NullParameter();
			}else if(StringUtil.isNumber(str)){
				return new Parameter(Integer.parseInt(str));
			}else{
				Class<?> type = SqlType.parserValueType(str);
				if(type!=null){
					return new Parameter(null,type);
				}else{
					return new NullParameter();
				}
			}
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
	
	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	/**
	 * 得到参数名称
	 * @return
	 */
	public String[] getParams()
	{
		return this.paramNames;
	}
	
	public void setParams(String[] paramNames)
	{
		this.paramNames = paramNames;
	}
	
	public SqlItemClause getParent()
	{
		return this.parent;
	}
	
	public void setParent(SqlItemClause clause)
	{
		this.parent = clause.clone(this);
	}
	
	public SqlItemClause()
	{
		this.id = "";
		this.prepend = "";
		this.paramNames = new String[0];
	}
	
	@Override
	public SqlItemClause clone(Object args){
		SqlItemClause clause = new SqlItemClause();
		clause.map=this.map;
		clause.paramNames=this.paramNames;
		clause.prepend=this.prepend;
		
		if(this.cAssert!=null)clause.cAssert=this.cAssert.clone(args);
		clause.parts=this.cloneParts(args);
		if(this.parent!=null)clause.setParent(this.parent);
		return clause;
	}
	
	/**
	 * 
	 * @param text
	 * @return
	 */
	public String format(String text)
	{
		if(this.paramNames.length>0 && text.indexOf('{')>-1)
		{
			boolean hasParamChar = false;
			String[] names = new String[this.paramNames.length];
			for(int i=0;i<this.paramNames.length;i++)
			{
				String param = this.paramNames[i];
				if(param.length()>0 && param.charAt(0)=='$')
				{
					hasParamChar = true;
					names[i] = param.substring(1);
				}
				else
				{
					names[i] = param;
				}
			}
			if(hasParamChar)text = this.formatParam(text,this.paramNames);
			return StringUtil.format(text,(Object[])names);
		}
		else return text;
	}
	
	private String formatParam(String text,String[] args)
	{		
		String result=text;   
		 
		java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\[\\w*\\{(\\d+)\\}\\w*\\]");
		java.util.regex.Matcher m = p.matcher(text);

		while(m.find())   
		{   
			int index=Integer.parseInt(m.group(1));
			
			if(index<args.length)   
			{
				//替换，以{}数字为下标，在参数数组中取值 
				result=result.replace(m.group(),m.group().replace("{"+index+"}",args[index]));
			}
		}
		return result;
	}
	
	public SqlString toString(SqlItemArgs arg){
		if(this.cAssert!=null){
			if(!this.cAssert.isTrue(arg))return null;
		}
		
		if(StringUtil.isNullOrEmpty(this.prepend)){
			SqlString part = (this.parent!=null)?this.parent.toString(arg):super.toString(arg);
			if(!StringUtil.isNullOrEmpty(this.getNvl()) && part.isEmpty()){
				return part.hasParameter()?new SqlString(this.getNvlParam()):new SqlString(this.getNvl());
			}else return part;
		}else{
			if(arg.getHidePrepend()){
				arg.setHidePrepend(false);
				SqlString part = (this.parent!=null)?this.parent.toString(arg):super.toString(arg);
				if(!StringUtil.isNullOrEmpty(this.getNvl()) && part.isEmpty()){
					return part.hasParameter()?new SqlString(this.getNvlParam()):new SqlString(this.getNvl());
				}else return part;
			}else{
				SqlString part = (this.parent!=null)?this.parent.toString(arg):super.toString(arg);
				if(!StringUtil.isNullOrEmpty(this.getNvl()) && part.isEmpty()){
					return new SqlString(this.prepend+" ").contact(part.hasParameter()?new SqlString(this.getNvlParam()):new SqlString(this.getNvl()));
				}else return new SqlString(this.prepend+" ").contact(part);
			}
		}
	}
	
	/**
	 * 断言处理类
	 */
	class ClauseAssert implements ISqlItemClone<ClauseAssert>
	{
		private SqlItemClause clause;
		private String methodName;
		private String methodParams;
		private String expression;
		private boolean hasOppositeChar;

		public String getMethodName()
		{
			return this.methodName;
		}
		
		public ClauseAssert(SqlItemClause clause,String expression)
		{
			this.clause = clause;
			this.methodParams = "";
			this.expression = expression;
			this.hasOppositeChar = false;
			this.parserMehtod(expression);
		}
		
		/**
		 * 解析断言表达式
		 * @param expression 断言表达式   如  isNull(Name)
		 */
		private void parserMehtod(String expression)
		{
			String str = expression.trim();
			int n = str.indexOf("(");
			int m = str.indexOf(")");
			if(n<0 || m<0)throw new SqlMapException(StringUtil.format("解析断言表达式出错 {0}",expression));
			else
			{
				if(str.charAt(0)=='!')
				{
					this.hasOppositeChar = true;
					this.methodName = str.substring(1, n);
				}
				else
				{
					this.methodName = str.substring(0,n);
				}
				
				String paramStr = str.substring(n+1,m);
				if(!StringUtil.isNullOrEmpty(paramStr))this.methodParams = paramStr;
			}
		}
		
		public ClauseAssert clone(Object args){
			ClauseAssert cAssert = new ClauseAssert(this.clause,this.expression);
			if(args instanceof ISqlParamMacro){
				ISqlParamMacro macro = (ISqlParamMacro)args;
				cAssert.methodParams=macro.format(cAssert.methodParams);
			}
			return cAssert;
		}
		
		public boolean isTrue(SqlItemArgs arg)
		{
			ISqlClauseAssert ca = this.clause.getMap().getAssert(this.methodName);
			if(ca!=null)
			{
				if(!StringUtil.isNullOrEmpty(this.methodParams))
				{
					String[] params = this.methodParams.split("\\,");
					Object[] args = new Object[params.length];
					for(int i=0;i<params.length;i++)
					{
						if(params[i]!=null)
						{
							Object value = arg.parserParam(params[i]);
							args[i] = (value instanceof Parameter)?((Parameter)value).getValue():value;
						}
						else throw new SqlMapException(StringUtil.format("断言表达式参数 为空 {0}",this.methodParams));
					}
					if(this.hasOppositeChar)return !ca.invoke(arg.getDialect(),args);
					else return ca.invoke(arg.getDialect(),args);
				}
				else
				{
					if(this.hasOppositeChar)return !ca.invoke(arg.getDialect(),new Object[0]);
					else return ca.invoke(arg.getDialect(),new Object[0]);
				}
			}
			else throw new SqlMapException(StringUtil.format("找不到断言函数 {0}",this.methodName));
		}
	}
}
