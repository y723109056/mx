package com.mx.sql.command;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;


/**
 * 数据库操作命令参数
 */
public class SqlParameter implements IDbParameter {
	/** 参数值 */
	private Object value;
	/** 参数名 */
	private String parameterName;
	/** 参数类型 */
	private ParameterType paramType;
	/** 参数序号 */
	private int index;
	/** 参数值类型 */
	private ValueType valueType;
	
	protected SqlParameter() {
	}

	/** 得到参数值类型 */
	@Override
	public ValueType getValueType() {
		return valueType;
	}

	/** 设置参数值类型 */
	public void setSqlType(ValueType valueType) {
		this.valueType = valueType;
	}

	/** 得到参数序号 */
	@Override
	public int getIndex() {
		return index;
	}

	/** 设置参数序号 */
	@Override
	public void setIndex(int index) {
		this.index = index;
	}
	
	public SqlParameter(int parameterIndex,Object value)
	{
		this.parameterName="";
		this.value=value;
		this.index=parameterIndex;
		this.paramType=ParameterType.In;
		this.valueType = this.parserValueType(value);
	}
	
	public SqlParameter(String parameterName,Object value){
		this.parameterName=parameterName;
		this.value=value;
		this.index=-1;
		this.paramType=ParameterType.In;
		this.valueType = this.parserValueType(value);
	}
	
	public SqlParameter(int parameterIndex,Object value,Class<?> valueType){
		this.parameterName="";
		this.value=value;
		this.index=parameterIndex;
		this.paramType=ParameterType.In;
		this.valueType=this.parserClassType(valueType);
	}
	
	public ValueType parserValueType(Object value){
		if(value == null)return ValueType.Null;
		else if(value instanceof String)return ValueType.Varchar;
		else if(value instanceof Character)return ValueType.Char;
		else if(value instanceof Short)return ValueType.SmallInt;
		else if(value instanceof Integer)return ValueType.Integer;
		else if(value instanceof Long)return ValueType.Bigint;
		else if(value instanceof Float)return ValueType.Float;
		else if(value instanceof Double)return ValueType.Double;
		else if(value instanceof BigDecimal)return ValueType.Decimal;
		else if(value instanceof Timestamp)return ValueType.TimeStamp;
		else if(value instanceof Time)return ValueType.Time;
		else if(value instanceof Date)return ValueType.Date;
		else if(value instanceof InputStream)return ValueType.Binary;
		else if(value instanceof Blob)return ValueType.Blob;
		else if(value instanceof Clob)return ValueType.Clob;
		else if(value instanceof Boolean)return ValueType.Boolean;
		else return null;
	}
	
	public ValueType parserClassType(Class<?> clazz){
		if(clazz == null)return ValueType.Null;
		else if(clazz.isAssignableFrom(String.class))return ValueType.Varchar;
		else if(clazz.isAssignableFrom(Character.class))return ValueType.Char;
		else if(clazz.isAssignableFrom(Short.class))return ValueType.SmallInt;
		else if(clazz.isAssignableFrom(Integer.class))return ValueType.Integer;
		else if(clazz.isAssignableFrom(Long.class))return ValueType.Bigint;
		else if(clazz.isAssignableFrom(Float.class))return ValueType.Float;
		else if(clazz.isAssignableFrom(Double.class))return ValueType.Double;
		else if(clazz.isAssignableFrom(BigDecimal.class))return ValueType.Decimal;
		else if(clazz.isAssignableFrom(Timestamp.class))return ValueType.TimeStamp;
		else if(clazz.isAssignableFrom(Time.class))return ValueType.Time;
		else if(clazz.isAssignableFrom(Date.class))return ValueType.Date;
		else if(clazz.isAssignableFrom(Blob.class))return ValueType.Blob;
		else if(clazz.isAssignableFrom(Clob.class))return ValueType.Clob;
		else if(clazz.isAssignableFrom(Boolean.class))return ValueType.Boolean;
		else return null;
	}
	
	/** 得到传值方式 */
	@Override
	public ParameterType getParamType() {
		return paramType;
	}

	/** 设置传值方式 */
	@Override
	public void setParamType(ParameterType paramType) {
		this.paramType = paramType;
	}

	/** 得到参数名称 */
	@Override
	public String getParameterName() {
		// TODO Auto-generated method stub
		return this.parameterName;
	}

	/** 得到参数值 */
	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return this.value;
	}

	/** 设置参数名称 */
	@Override
	public void setParameterName(String name) {
		// TODO Auto-generated method stub
		this.parameterName=name;
	}

	/** 设置参数值 */
	@Override
	public void setValue(Object value) {
		// TODO Auto-generated method stub
		this.value=value;
	}

}
