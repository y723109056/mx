package com.mx.sql.mapping;

import java.util.HashSet;
import java.util.Set;
/**
 * 配置上下文
 */
public class SqlContext {
	/**
	 * 资源路径
	 */
	private Set<String> resources;
	private Set<ISqlMethod> methods;
	private Set<ISqlClauseAssert> asserts;
	
	/** 得到资源列表 */
	public Set<String> getResources()
	{
		return this.resources;
	}
	
	/** 设置资源列表 */
	public void setResources(Set<String> resources)
	{
		this.resources = resources;
	}
	
	/**
	 * 得到自定义函数集合
	 * @return
	 */
	public Set<ISqlMethod> getMethods()
	{
		return this.methods;
	}
	
	/**
	 * 设置自定义函数集合
	 * @param methods
	 */
	public void setMethods(Set<ISqlMethod> methods)
	{
		this.methods = methods;
	}
	
	/**
	 * 得到断言集合
	 * @return
	 */
	public Set<ISqlClauseAssert> getAsserts()
	{
		return this.asserts;
	}
	
	public void setAsserts(Set<ISqlClauseAssert> asserts)
	{
		this.asserts = asserts;
	}
	
	public SqlContext()
	{
		this.resources = new HashSet<String>();
		this.methods = new HashSet<ISqlMethod>();
		this.asserts = new HashSet<ISqlClauseAssert>();
	}
}
