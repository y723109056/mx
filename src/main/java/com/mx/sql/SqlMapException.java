package com.mx.sql;

public class SqlMapException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public SqlMapException(String message)
	{
		super("SQLMAP "+message);
	}
	
	public SqlMapException(String message,Throwable cause)
	{
		super("SQLMAP "+message+" "+cause.getMessage(),cause);
	}
}
