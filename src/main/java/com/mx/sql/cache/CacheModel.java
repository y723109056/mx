package com.mx.sql.cache;

import com.mx.sql.SqlMapException;
import com.mx.sql.event.SqlExecuteEvent;
import com.mx.sql.event.SqlExecuteListener;
import com.mx.util.StringUtil;

import java.util.HashSet;
import java.util.Set;


public class CacheModel implements SqlExecuteListener {
	private long lastFlush;
	private long flushInterval;
	private int hits = 0;
	private int requests = 0;
	private String id;
	private ICache cache;
	public static final Object NULL_OBJECT = new Object();
	private Set<String> flushStatements;
	
	public CacheModel(){
		requests = 0;
        hits = 0;
        flushInterval = -99999L;
        lastFlush = System.currentTimeMillis();
        flushStatements = new HashSet<String>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getHitRatio(){
        return (double)hits / (double)requests;
    }
	
	public long getFlushIntervalSeconds(){
        return flushInterval / 1000L;
    }
	
	public long getFlushInterval(){
		return this.flushInterval;
	}
	
	public void setFlushInterval(Long interval){
		this.flushInterval = interval;
	}
	
	public void addFlushStatement(String statementName){
        flushStatements.add(statementName);
        SqlExecuteEvent.addListener(statementName, this);
    }
	
	/**
	 * 
	 */
	public void flush(){
        //synchronized(this){
            this.cache.flush(this);
            lastFlush = System.currentTimeMillis();
        //}
    }
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Object getObject(CacheKey key){
		Object value = null;
        //synchronized(this){
            if(flushInterval != -99999L && System.currentTimeMillis() - lastFlush > flushInterval)this.flush();
            value = this.cache.getObject(this, key);
            requests++;
            if(value != null)
                hits++;
        //}
        return value;
	}
	
	public void setCacheType(String cacheType){
		String className=null;
		if("OSCACHE".equalsIgnoreCase(cacheType)){
			className = "com.mx.common.cache.oscache.OSCache";
		}
		if(className!=null){
			try {
				Class<?> clazz = Class.forName(className);
				cache = (ICache)clazz.newInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new SqlMapException(StringUtil.format("实列化缓存类 {0} 出错", className));
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new SqlMapException(StringUtil.format("实列化缓存类 {0} 出错",className));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new SqlMapException(StringUtil.format("实列化缓存类 {0} 出错",className));
			}
		}else throw new SqlMapException(StringUtil.format("未知缓存类型 {0}",cacheType));
    }
	
	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void putObject(CacheKey key, Object value){
		if(value == null)value = NULL_OBJECT;
        synchronized(this){
            cache.putObject(this, key, value);
        }
	}

	public void onSqlExecute() {
		this.flush();
	}
}
