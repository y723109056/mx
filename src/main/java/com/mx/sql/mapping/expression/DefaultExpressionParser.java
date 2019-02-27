package com.mx.sql.mapping.expression;

import java.util.Map;

public class DefaultExpressionParser implements IExpressionParser {
	private Map<String,IOperation> operations;
	
	public Map<String, IOperation> getOperations() {
		return operations;
	}

	public void setOperations(Map<String, IOperation> operations) {
		this.operations = operations;
	}

	public OperationItem parser(String expression) {
		char c;
		String text = expression.trim();
		for(int i=0;i<text.length();i++)
        {
			c = text.charAt(i);
			switch(c){
				
			}
        }
		return null;
	}
}
