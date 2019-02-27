package com.mx.sql.mapping;


import com.mx.sql.SqlMapException;
import com.mx.sql.builder.NullParameter;
import com.mx.sql.builder.Parameter;
import com.mx.sql.builder.SqlString;
import com.mx.sql.util.SqlType;
import com.mx.util.StringUtil;
import com.mx.util.TypeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本和节点混合对像,输出定位
 */
public class SqlItemContent implements ISqlItemPart {
	/** 内容块，文本和节点 */
	protected List<ISqlItemPart> parts;

	protected SqlMap map;
	
	public SqlMap getMap()
	{
		return this.map;
	}
	
	public void setMap(SqlMap map)
	{
		this.map = map;
	}
	
	/** 得到内容块集合 */
	public List<ISqlItemPart> getParts()
	{
		return this.parts;
	}
	
	public int size()
	{
		return this.parts.size();
	}
	
	/** 添加内容块 */
	public void addPart(ISqlItemPart part)
	{
		this.parts.add(part);
	}
	
	/** 添加内容块 */
	public void addPart(String text)
	{
		this.parts.add(new ContentTextWrapper(text));
	}
	
	public SqlItemContent()
	{
		this.parts = new ArrayList<ISqlItemPart>();
	}
	
	protected List<ISqlItemPart> cloneParts(Object args){
		List<ISqlItemPart> ps = new ArrayList<ISqlItemPart>();
		for(ISqlItemPart part : this.parts){
			if(part instanceof ISqlItemClone<?>){
				ps.add((ISqlItemPart)((ISqlItemClone<?>)part).clone(args));
			}
			else
			{
				ps.add(part);
			}
		}
		return ps;
	}
	
	@Override
	public SqlString toString(SqlItemArgs arg){
		SqlString sql = new SqlString("");
		for(ISqlItemPart part : this.parts){
			sql = sql.contact(part.toString(arg));
		}
		return sql;
	}
	
	class ContentTextWrapper implements ISqlItemPart,ISqlItemClone<ContentTextWrapper>
	{
		private String content;
		private List<ContentTextPart> parts;
		//private Object lockObj = new Object();
		
		public ContentTextWrapper(String content)
		{
			this.content = this.replaceBlank(content);
		}
		
		@Override
		public ContentTextWrapper clone(Object args){
			if(args instanceof ISqlParamMacro){
				ISqlParamMacro macro = (ISqlParamMacro)args;
				String text = macro.format(this.content);
				return new ContentTextWrapper(text);
			}else{
				return new ContentTextWrapper(this.content);
			}
		}

		@Override
		public SqlString toString(SqlItemArgs arg) {
			//synchronized(this.lockObj){
				if(this.parts==null && !StringUtil.isNullOrEmpty(this.content)){
					this.parts = this.parserTextParts(this.content);
				}
			//}
			if(this.parts!=null){
				SqlString sql = new SqlString("");
				for(ContentTextPart part : this.parts){
					sql=sql.contact(this.parserParam(part,arg));
				}
				return sql;
			}
			return null;
		}
		
		private SqlString parserParam(ContentTextPart part,SqlItemArgs arg){
			switch(part.type){
				case 0:{
					if(part.text.toLowerCase().indexOf("select")>-1){
						arg.setIsSelect(true);
					}
					return new SqlString(part.text);
				}
				case 1:{
					String[] params = part.param.parts;
					Object value = arg.parserParam(params,part.text);
					String lastParam = params[params.length-1];
					if(value!=null)
					{
						if(TypeUtil.isValue(value) || TypeUtil.isCollection(value))
						{
							if(lastParam.charAt(1)=='$')return new SqlString(new Parameter(value));
							else
							{
								String paramValue = SqlType.toSqlString(value);
								if(arg.getIsSelect() && paramValue.indexOf("'")>-1){
									paramValue = paramValue.replaceAll("'", "''");
								}
								return new SqlString(paramValue);
							}
						}
						else throw new SqlMapException(StringUtil.format("不是值类型参数 {0} {1}",value.getClass().toString(),part.text));
					}else{
						return (lastParam.charAt(1)=='$')?new SqlString(new NullParameter()):null;
					}
				}
				case 2:{
					Object value = arg.parserParam(part.text);
					String paramValue = SqlType.toSqlString(value);
					return new SqlString(paramValue);
				}
				default:
					return null;
			}
		}
		
		private List<ContentTextPart> parserTextParts(String text){
			char c;
			int start=0;
			ContentTextPart curr=null;
			List<ContentTextPart> parts=new ArrayList<ContentTextPart>();
			for(int i=0;i<text.length();i++)
            {
				c = text.charAt(i);
				switch(c){
					case '[':
					case '{':
						if(curr==null){
							if(start<i){
								ContentTextPart textPart = new ContentTextPart(0);
								textPart.text=text.substring(start,i);
								parts.add(textPart);
							}
							curr=new ContentTextPart((c=='[')?1:2);
							start=i;
						}break;
					case ']':
						if(curr!=null && curr.closeQuote==']'){
							curr.param.addPart(text.substring(start,i+1));
							curr.param.text += text.substring(start,i+1);
							start=i+1;
							if(!(i+1<text.length() && text.charAt(i+1)=='[')){
								curr.param.checkParamValid();
								parts.add(curr);
								curr=null;
							}
						}break;
					case '}':
						if(curr!=null && curr.closeQuote=='}'){
							curr.text=text.substring(start,i+1);
							parts.add(curr);
							curr=null;
							start=i+1;
						}break;
				}
            }
			if(curr==null){
				if(start<text.length()){
					ContentTextPart textPart = new ContentTextPart(0);
					textPart.text=text.substring(start);
					parts.add(textPart);
				}
				return parts;
			}
			else throw new SqlMapException(StringUtil.format("参数不完整,缺少{0} {1}",curr.closeQuote,text));
		}
		
		/**
		 * 移除空白
		 * @param text
		 * @return
		 */
		private String replaceBlank(String text)
		{
			//Pattern p = Pattern.compile("\t|\r|\n");
			Pattern p = Pattern.compile("\n");
			Matcher m = p.matcher(text);
			return m.replaceAll(" ");
		}
		
		class ContentTextPart{
			private int type;
			private char closeQuote;
			private String text="";
			private SqlItemParam param;
			
			ContentTextPart(int type){
				this.type=type;
				if(type==1){
					this.closeQuote=']';
					this.param=new SqlItemParam();
				}else if(type==2){
					this.closeQuote='}';
				}
			}
		}
	}
}
