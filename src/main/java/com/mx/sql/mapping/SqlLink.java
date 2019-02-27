package com.mx.sql.mapping;


import com.mx.sql.SqlMapException;
import com.mx.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 主从表连接
 */
public class SqlLink {
	private String sqlId; // 连接的SqlId
	private String pkey; // 主键
	private String split;	//主键分隔符,主键为字符集合
	private String fkey; // 外键
	private List<LinkColumn> columns;

	public String getSqlId() {
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}
	
	public List<LinkColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<LinkColumn> columns) {
		this.columns = columns;
	}

	public String getPkey() {
		return pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}

	public String getFkey() {
		return fkey;
	}

	public void setFkey(String fkey) {
		this.fkey = fkey;
	}

	public String getSplit() {
		return split;
	}

	public void setSplit(String split) {
		this.split = split;
	}

	public SqlLink() {
		this.sqlId = "";
		this.pkey = "";
		this.fkey = "";
		this.split = "";
		this.columns = new ArrayList<LinkColumn>();
	}
	
	public LinkColumn addColumn(String name,String op){
		return this.addColumn(name, op,"",null);
	}
	
	public LinkColumn addColumn(String name,String op,String split,String alias){
		if(!StringUtil.isNullOrEmpty(op) && OP.get(op)==null)throw new SqlMapException(StringUtil.format("link.Column节点 op 不存在类型  {0}",op));
		LinkColumn column = new LinkColumn(name,OP.get(op));
		column.setSplit(split);
		column.setAlias(alias);
		this.columns.add(column);
		return column;
	}

	public class LinkColumn {
		private OP op;
		private String split;
		private String name;
		private String alias;

		public OP getOp() {
			return op;
		}

		public void setOp(OP op) {
			this.op = op;
		}

		public String getSplit() {
			return split;
		}

		public void setSplit(String split) {
			this.split = split;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public LinkColumn() {
			this("",null);
		}
		
		public String getAlias() {
			return alias;
		}

		public void setAlias(String alias) {
			this.alias = alias;
		}
		
		public String getColumnName(){
			if(!StringUtil.isNullOrEmpty(this.alias)){
				return this.alias;
			}else{
				return this.name;
			}
		}

		public LinkColumn(String name, OP op) {
			this.name = name;
			this.op = op;
			this.split = "";
			this.alias = null;
		}
	}

	public enum OP {
		JOIN, SUM, AVERAGE, MAX, MIN, FIRST, LAST;

		public static OP get(String name) {
			if ("join".equalsIgnoreCase(name))
				return JOIN;
			else if ("sum".equalsIgnoreCase(name))
				return SUM;
			else if ("average".equalsIgnoreCase(name))
				return AVERAGE;
			else if ("max".equalsIgnoreCase(name))
				return MAX;
			else if ("min".equalsIgnoreCase(name))
				return MIN;
			else if ("first".equalsIgnoreCase(name))
				return FIRST;
			else if ("last".equalsIgnoreCase(name))
				return LAST;
			else
				return null;
		}
	}
}
