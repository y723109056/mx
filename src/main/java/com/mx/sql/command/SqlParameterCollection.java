package com.mx.sql.command;

import com.mx.sql.builder.Parameter;
import com.mx.sql.util.SqlType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * 数据库操作命令参数集合类
 */
public class SqlParameterCollection implements IDbParameterCollection {
	/** 参数集合 */
	private Map<String,IDbParameter> params;
	
	public SqlParameterCollection()
	{
		this.params=new HashMap<String,IDbParameter>();
	}
	
	public SqlParameterCollection(Object[] params)
	{
		this();
		if(params!=null)
		{
			this.fromArray(params);
		}
	}
	
	/** 添加参数集合 */
	public void fromArray(Object[] params){
		for(int i=0;i<params.length;i++){
			if(params[i] instanceof Parameter){
				Parameter param = (Parameter)params[i];
				Object val = SqlType.toSqlValue(param.getValue());
				this.add(i+1,val,param.getType());
			}else{
				Object val = SqlType.toSqlValue(params[i]);
				this.add(i+1, val);
			}
		}
	}

	/** 添加参数集合 */
	public IDbParameter add(IDbParameter param) {
		return this.params.put(param.getParameterName().equals("")?String.valueOf(param.getIndex()):param.getParameterName(),param);
	}
	
	/**
	 * 添加参数集合
	 */
	@Override
	public IDbParameter add(int parameterIndex, int sqlType) {
		IDbParameter param=new SqlParameter(parameterIndex,sqlType);
		this.add(param);
		return param;
	}
	
	public IDbParameter add(int parameterIndex,Object value,Class<?> type){
		IDbParameter param=new SqlParameter(parameterIndex,value,type);
		this.add(param);
		return param;
	} 
	
	/**
	 * 添加参数集合
	 */
	@Override
	public IDbParameter add(int parameterIndex,Object value){
		IDbParameter param=new SqlParameter(parameterIndex,value);
		this.add(param);
		return param;
	}
	
	/**
	 * 添加参数集合
	 */
	@Override
	public IDbParameter add(String parameterName, int sqlType) {
		IDbParameter param=new SqlParameter(parameterName,sqlType);
		this.add(param);
		return param;
	}
	
	/**
	 * 添加参数集合
	 */
	@Override
	public IDbParameter add(String parameterName,Object value){
		IDbParameter param=new SqlParameter(parameterName,value);
		this.add(param);
		return param;
	}

	/** 判断是否包含此参数 */
	@Override
	public boolean contains(String parameterName) {
		return this.params.containsKey(parameterName);
	}

	/** 得到参数对像 */
	@Override
	public IDbParameter get(String parameterName) {
		return this.params.get(parameterName);
	}
	
	/** 得到参数对像 */
	public IDbParameter get(int index)
	{
		return this.params.get(index);
	}
	
	/** 清除所有参数 */
	public void clear()
	{
		this.params.clear();
	}

	/** 设置参数 */
	@Override
	public void set(String parameterName, IDbParameter value) {
		this.params.put(parameterName, value);
	}
	
	/** 得到参数的数量 */
	public int size()
	{
		return this.params.size();
	}

	/** 迭代器 */
	@Override
	public Iterator<IDbParameter> iterator() {
		return this.params.values().iterator();
	}
}
