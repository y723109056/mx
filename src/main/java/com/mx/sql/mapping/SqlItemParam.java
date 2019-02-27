package com.mx.sql.mapping;


import com.mx.sql.SqlMapException;
import com.mx.util.StringUtil;

public class SqlItemParam {
	public String text="";
	public String[] parts;
	
	public SqlItemParam(){
		this.parts = new String[0];
	}
	
	public void addPart(String part){
		String[] parts =new String[this.parts.length+1];
		parts[this.parts.length] = part;
		System.arraycopy(this.parts, 0, parts, 0,this.parts.length);
		this.parts = parts;
	}
	
	public void checkParamValid(){
		for(int i=0;i<this.parts.length;i++){
			String param = this.parts[i];
			if(param.charAt(0)=='\'' ^ param.charAt(param.length()-1)=='\'')
				throw new SqlMapException(StringUtil.format("表达式参数不完整 ,缺少单引号{0}", this.text));
			if(param.charAt(0)=='[' ^ param.charAt(param.length()-1)==']')
				throw new SqlMapException(StringUtil.format("表达式参数不完整,缺少中括 号{0}",this.text));
			if(param.charAt(0)=='{' ^ param.charAt(param.length()-1)=='}')
				throw new SqlMapException(StringUtil.format("表达式参数不完整,缺少大括 号{0}",this.text));
			
			if(param.charAt(0)=='\'' || param.charAt(0)=='[' || param.charAt(0)=='{')
			{
				String str = param.substring(1,param.length()-1).trim();
				if(!StringUtil.isNullOrEmpty(str)){
					char c = str.charAt(0);
					if(c=='~' || c=='!' || c=='@' || c=='#' || c=='%' || c=='^' || c=='&' || c=='*' || c=='+' || c=='-' || c=='|' || c=='?')
					{
						throw new SqlMapException(StringUtil.format("参数字符使用不正确 {0}",this.text));
					}
					else if(i<this.parts.length-1 && c=='$'){
						throw new SqlMapException(StringUtil.format("参数字符$只能出现在最后 {0}",this.text));
					}
				}
				else throw new SqlMapException(StringUtil.format("参数名为空 {0}",this.text));
			}
		}
	}
}
