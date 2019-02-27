package com.mx.sql.mapping;


import com.mx.reader.Reader;
import com.mx.sql.SqlMapException;
import com.mx.sql.config.IDbConfig;
import com.mx.sql.dialect.IDialect;
import com.mx.util.StringUtil;
import com.mx.util.TypeUtil;

import java.util.Map;

/**
 * 构造Sql语句参数对像
 */
public class SqlItemArgs {
	
	/** SqlMap 外部传入参数 */
	private Object[] datas;
	/** 判断外部参数是对像，还是值,或空  */
	private Boolean isValue;
	/** 局部变量  **/
	private Map<String,Object> variables;
	private boolean hidePrepend;
	private boolean isSelect;			//是查询sql语句
	private IDialect dialect;
	private IDbConfig dbConfig;
	
	protected SqlItemArgs() {
	}

	public Object[] getData()
	{
		return this.datas;
	}
	
	public boolean getHidePrepend()
	{
		return this.hidePrepend;
	}
	
	public IDialect getDialect()
	{
		return this.dialect;
	}
	
	public void setDialect(IDialect dialect)
	{
		this.dialect = dialect;
	}

	public IDbConfig getDbConfig() {
		return dbConfig;
	}

	public void setDbConfig(IDbConfig dbConfig) {
		this.dbConfig = dbConfig;
	}

	public void setHidePrepend(boolean isHide)
	{
		this.hidePrepend = isHide;
	}
	
	public void setIsSelect(boolean isSelect){
		this.isSelect = isSelect;
	}
	
	public boolean getIsSelect(){
		return this.isSelect;
	}
	
	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	/**
	 * 判断外部参数是对像，还是值,或空
	 * @return
	 */
	public Boolean paramIsValues()
	{
		if(isValue==null)
		{
			if(datas.length==1)
			{
				if(TypeUtil.isValue(datas[0]))this.isValue = new Boolean(true);
				else this.isValue = new Boolean(false);
			}
			else if(datas.length>1)
			{
				this.isValue = new Boolean(true);
			}
		}
		return isValue;
	}
	
	public SqlItemArgs(Object[] datas)
	{
		this.datas = datas;
		this.isValue = null;
		this.hidePrepend = false;
		this.isSelect = false;
	}
	
	public Object parserParam(String text){
		return this.parserParam(new String[]{text}, text);
	}
	
	public Object parserParam(String[] params,String text){
		String str = params[0];
		if(str.charAt(0)=='[' && str.charAt(str.length()-1)==']')
		{
			String param = str.substring(1,str.length()-1);
			String paramName = (param.charAt(0)=='$')?param.substring(1):param;
			Object value=null;
			
			if(StringUtil.isNumber(paramName))
			{
				int index = Integer.parseInt(paramName);
				if(index<this.getData().length)
				{
					value = this.getData()[index];
				}
				else throw new SqlMapException(StringUtil.format("参数索引超出界限  {0}",text));
			}
			else
			{
				if(this.getData().length>0 && this.getData()[0]!=null)
				{
					value = Reader.objectReader().getValue(this.getData()[0],paramName);
				}
				else throw new SqlMapException(StringUtil.format("传入参数为空 {0}",text));
			}
			
			int num = 1;
			while(num!=params.length){
				param = params[num].substring(1,params[num].length()-1);
				paramName = (param.charAt(0)=='$')?param.substring(1):param;
				if(value!=null){
					if(!TypeUtil.isValue(value)){
						if(TypeUtil.isCollection(value)){
							if(StringUtil.isNumber(paramName)){
								value = Reader.objectReader().getValue(value,Integer.parseInt(paramName));
								num++;
							}
							else throw new SqlMapException(StringUtil.format("取参数值出错,参数类型不符 {0}",text));
						}
						else{
							value = Reader.objectReader().getValue(value,paramName);
							num++;
						}
					}
					else throw new SqlMapException(StringUtil.format("取参数值出错,参数类型不符 {0}",text));
				}
				else throw new SqlMapException(StringUtil.format("对传入参数取值时,遇到空值 {0}",text));
			}
			
			return value;
		}
		else if(str.charAt(0)=='\'' && str.charAt(str.length()-1)=='\'')
		{
			return str.substring(1,str.length()-1);
		}
		else if(str.charAt(0)=='{' && str.charAt(str.length()-1)=='}'){
			String param = str.substring(1,str.length()-1);
			if(this.variables!=null){
				int n = param.indexOf(".");
				String key = (n==-1)?param:param.substring(0,n).toLowerCase();
				if(this.variables.containsKey(key)){
					Object value = this.variables.get(key);
					if(value!=null){
						return (n==-1)?value:Reader.objectReader().getValue(value,param.substring(n+1));
					}
				}
			}
			return null;
		}
		else
		{
			if(StringUtil.isNumber(str))return Integer.parseInt(str);
			else return str;
		}
	}
	
	/**
	 * 判断Sql语句参数符串
	 * @param param
	 */
	public void checkParam(String param)
	{
		char c = param.charAt(0);
		if(c=='~' || c=='!' || c=='@' || c=='#' || c=='%' || c=='^' || c=='&' || c=='*' || c=='+' || c=='-' || c=='|' || c=='?')
		{
			throw new SqlMapException(StringUtil.format("参数字符不正确请用 [${1}] 不要用 [{0}{1}]",c,param.substring(1,param.length())));
		}
	}
}
