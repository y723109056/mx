package com.mx.sql.mapping;


import com.mx.sql.builder.ISqlBuilder;
import com.mx.sql.builder.SqlString;
import com.mx.sql.config.IDbConfig;
import com.mx.sql.dialect.IDialect;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Sql语句配置
 */
public class SqlItem implements ISqlBuilder,ISqlItemCount {
	/** 标识 */
	private String id;
	/** 缓存 */
	private String cacheId;
	/** 文本和子节点组成的内容对像 */
	private SqlItemContent content;
	/** 结果实体应射 */
	private SqlResultMap result;
	/** 结果数量类型 */
	private String resultClass;
	/** 外联Sql分组 */
	private Map<String,SqlLinkGroup> linkGroupMap;
	/** 是否动态加入 */
	private boolean dynamic = false;
	/** 动态Xml */
	private String dynamicXml;

	/** 执行次数 */
	private long execCount;
	/** 最大执行时间  */
	private long maxTime;
	/** 最小执行时间 */
	private long minTime;
	/** 总共执行时间 */
	private long totalTime;
	/** 最后执行时间 */
	private long lastTime;
	
	/** 得到标识 */
	public String getId()
	{
		return this.id;
	}
	
	/** 设置标识 */
	public void setId(String id)
	{
		this.id = id;
	}
	
	/** 得到缓存标识 */
	public String getCacheId() {
		return cacheId;
	}

	/** 设置缓存标识 */
	public void setCacheId(String cacheId) {
		this.cacheId = cacheId;
	}

	/** 得到结果类型 */
	public String getResultClass() {
		return resultClass;
	}

	/** 设置结果类型 */
	public void setResultClass(String resultClass) {
		this.resultClass = resultClass;
	}

	/** 得到结果类型信息 */
	public SqlResultMap getResult()
	{
		return this.result;
	}
	
	/** 设置结果类型信息 */
	public void setResult(SqlResultMap result)
	{
		this.result = result;
	}
	
	/** 得到内容 */
	public SqlItemContent getContent()
	{
		return this.content;
	}
	
	/** 设置内容 */
	public void setContent(SqlItemContent content)
	{
		this.content = content;
	}
	
	public boolean isDynamic() {
		return dynamic;
	}

	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}

	public String getDynamicXml() {
		return dynamicXml;
	}

	public void setDynamicXml(String dynamicXml) {
		this.dynamicXml = dynamicXml;
	}

	public Collection<SqlLinkGroup> getLinkGroups() {
		return linkGroupMap.values();
	}

	public SqlItem()
	{
		this("");
	}
	
	public SqlItem(String id)
	{
		this.id = id;
		this.content = new SqlItemContent();
		this.result = null;
		this.dynamicXml = "";
		this.linkGroupMap = new HashMap<String, SqlLinkGroup>();
	}

	/**
	 * 添加SQL外联
	 * @param sqlLink
	 */
	public void addSqlLink(SqlLink sqlLink){
		if(sqlLink!=null) {
			String key = sqlLink.getSqlId() + "_" + sqlLink.getFkey();
			if (!linkGroupMap.containsKey(key))
				linkGroupMap.put(key,new SqlLinkGroup(sqlLink.getSqlId(), sqlLink.getFkey()));
			linkGroupMap.get(key).addSqlLink(sqlLink);
		}
	}
	
	/**
	 * 生成Sql语句
	 */
	@Override
	public SqlString toSqlString(Object ... params) {
		return this.toSqlString(null,params);
	}

	/**
	 * 生成Sql语句
	 */
	@Override
	public SqlString toSqlString(IDialect dialect,Object ... params) {
		SqlItemArgs  args = new SqlItemArgs(params);
		args.setDialect(dialect);
		return this.content.toString(args);
	}
	
	/**
	 * 生成Sql语句
	 */
	@Override
	public SqlString toSqlString(IDbConfig config,IDialect dialect,Object ... params) {
		SqlItemArgs args = new SqlItemArgs(params);
		args.setDialect(dialect);
		args.setDbConfig(config);
		return this.content.toString(args);
	}

	/**
	 * 执行Sql执行时间
	 * @param sqlString
	 */
	public void updateExecSqlTime(SqlString sqlString){
		long time = sqlString.getExceTime();
		if(this.maxTime<time)this.maxTime = time;
		if(this.minTime>time)this.minTime = time;
		this.totalTime+=time;
		this.execCount++;
		this.lastTime=time;
	}

	/**
	 * 计算Sql执行时间
	 * @param sqlStrings
	 */
	public void updateExecSqlTime(SqlString[] sqlStrings){
		if(sqlStrings.length>0){
			long time = sqlStrings[0].getExceTime()/sqlStrings.length;
			if(this.maxTime<time)this.maxTime = time;
			if(this.minTime>time)this.minTime = time;
			this.totalTime+=sqlStrings[0].getExceTime();
			this.execCount+=sqlStrings.length;
			this.lastTime=time;
		}
	}

	/**
	 * 平均执行时间
	 * @return
	 */
	public long getAverageTime(){
		if(this.execCount>0){
			return this.totalTime / this.execCount;
		}
		return 0;
	}

	/**
	 * 最执执行时间
	 * @return
	 */
	public long getMaxTime(){
		return this.maxTime;
	}

	/**
	 *
	 * @return
	 */
	public long getMinTime(){
		return this.minTime;
	}

	/**
	 * 执行次数
	 * @return
	 */
	public long getExceCount(){
		return this.execCount;
	}

	/**
	 * 总行执行时间
	 * @return
	 */
	public long getTotalTime(){
		return this.totalTime;
	}

	public long getLastTime(){
		return this.lastTime;
	}



	/**
	 * 执行SqlId
	 * @return
	 */
	public String getSqlId(){
		return this.id;
	}

}
