package com.mx.sql.mapping.expression;

public class OperationItem {
	private String text;
	private Object[] params;
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Object[] getParams() {
		return params;
	}
	
	public void setParams(Object[] params) {
		this.params = params;
	}
}
