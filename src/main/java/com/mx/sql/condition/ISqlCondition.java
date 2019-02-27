package com.mx.sql.condition;

public interface ISqlCondition {
	
	String getCondition(Object data, Args args);
	
	class Args {
		private String property;				//属性名
		private String column;					//列名
		private String tableAlias;				//表别名
		private String valueType;				//值类型
		
		public String getProperty() {
			return property;
		}
		
		public void setProperty(String property) {
			this.property = property;
		}
		
		public String getColumn() {
			return column;
		}
		
		public void setColumn(String column) {
			this.column = column;
		}
		
		public String getTableAlias() {
			return tableAlias;
		}
		
		public void setTableAlias(String tableAlias) {
			this.tableAlias = tableAlias;
		}
		
		public String getValueType() {
			return valueType;
		}
		
		public void setValueType(String valueType) {
			this.valueType = valueType;
		}
		
		public Args(){
			this.column = "";
			this.property = "";
			this.tableAlias = "";
			this.valueType = "";
		}
		
		public Args(String property,String column,String tableAlias,String valueType){
			this.property = property;
			this.column = column;
			this.tableAlias = tableAlias;
			this.valueType = valueType;
		} 
	}
	
}
