package com.mx.sql.command;

public enum ValueType {
	Bit(-7),
	TinyInt(-6),
	SmallInt(5),
	Integer(4),
	Bigint(-5),
	Float(6),
	Real(7),
	Double(8),
	Numeric(2),
	Decimal(3),
	Char(1),
	Varchar(12),
	LongVarchar(-1),
	Date(91),
	Time(92),
	TimeStamp(93),
	Binary(-2),
	Varbinary(-3),
	LongVarbinary(-4),
	Null(0),
	Distinct(2001),
	Struct(2002),
	Array(2003),
	Blob(2004),
	Clob(2005),
	Rer(2006),
	DataLink(70),
	Boolean(16);
	
	public int value;
    
	ValueType(int value) {
        this.value = value;
	}
	
	public static ValueType valueOfType(int type){
		for(ValueType valueType : ValueType.values()){
			if(valueType.value==type)
				return valueType;
		}
		return null;
	}
}
