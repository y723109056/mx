package com.mx.sql;

public interface ISqlLog {
	void info(String content);
	
	void warn(String content);
	
	void error(String content);
}
