package com.mx.sql.mapping.expression.operation;


import com.mx.sql.mapping.expression.IOperation;

public abstract class AbstractOperation implements IOperation {
	private String sign;				//
	private Integer priority;
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}
