package com.mx.sql;

public class SqlExecuteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public SqlExecuteException(String message)
	{
		super("SQL "+message);
	}
	
	public SqlExecuteException(String message,Throwable cause)
	{
		super("SQL "+message+" "+cause.getMessage(),cause);
	}
}
