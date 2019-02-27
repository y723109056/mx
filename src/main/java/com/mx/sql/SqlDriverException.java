package com.mx.sql;

public class SqlDriverException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public SqlDriverException(String message)
	{
		super("DRIVER "+message);
	}
	
	public SqlDriverException(String message,Throwable cause)
	{
		super("DRIVER "+message+" "+cause.getMessage(),cause);
	}
}
