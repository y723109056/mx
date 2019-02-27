package com.mx.sql.util;

import com.mx.collection.DataRow;
import com.mx.collection.DataTable;
import com.mx.spring.SpringContext;
import com.mx.sql.SqlExecuteException;
import com.mx.sql.builder.SqlString;
import com.mx.sql.cache.CacheKey;
import com.mx.sql.cache.CacheModel;
import com.mx.sql.command.IDataReader;
import com.mx.sql.driver.IDataProvider;
import com.mx.sql.event.SqlExecuteEvent;
import com.mx.sql.mapping.ISqlMap;
import com.mx.sql.mapping.SqlItem;
import com.mx.sql.mapping.SqlLink;
import com.mx.sql.mapping.SqlLinkGroup;
import com.mx.util.MathUtil;
import com.mx.util.ReflectorUtil;
import com.mx.util.StringUtil;
import com.mx.util.TypeUtil;

import java.util.*;



public class SqlEngine extends SqlHelper implements ISqlEngine {
	protected static final String SqlMapKey = "SqlMap";

	protected ISqlMap getSqlMap(){
		return (ISqlMap) SpringContext.instance().get(SqlMapKey);
	}

	public SqlEngine(IDataProvider provider){
		super(provider);
	}

	public static ISqlEngine instance(){
		IDataProvider provider = getBuilder().build();
		return new SqlEngine(provider);
	}

	public static ISqlEngine instance(String configName){
		IDataProvider provider = getBuilder().build(configName);
		return new SqlEngine(provider);
	}

	@Override
	public DataTable getDataTable(String sqlId,Object... conditions) {
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlString sqlString = sqlItem.toSqlString(this.getConfig(),this.getDialect(),conditions);
		if(!StringUtil.isNullOrEmpty(sqlItem.getCacheId())){
			CacheModel cache = this.getSqlMap().getCache(sqlItem.getCacheId());
			CacheKey key = this.getCacheKey(sqlString.toString(), sqlString.getParamValues());
			Object obj = cache.getObject(key);
			if(obj!=null && obj instanceof DataTable){
				return (DataTable)obj;
			}else{
				try{
					IDataReader reader = this.getDataReader(sqlString);
					sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
					DataTable table = this.getDataTable("",reader);
					if(table.rowSize()>0){
						this.linkTable(sqlItem,table,sqlId);
					}
					this.provider.getConnection().close();
					cache.putObject(key, table);
					//SqlExecuteEvent.raise(sqlId);
					return table;
				}catch(SqlExecuteException e){
					throw e;
				}finally{
					this.provider.getConnection().close();
				}
			}
		}else{
			try{
				IDataReader reader = this.getDataReader(sqlString);
				sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
				DataTable table = this.getDataTable("",reader);
				if(table.rowSize()>0){
					this.linkTable(sqlItem,table,sqlId);
				}
				//SqlExecuteEvent.raise(sqlId);
				return table;
			}catch(SqlExecuteException e){
				throw e;
			}finally{
				this.provider.getConnection().close();
			}
		}
	}

	public DataTable getDataTable(String sqlId,int top,Object ... conditions){
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlString sqlString = sqlItem.toSqlString(this.getConfig(),this.getDialect(),conditions);
		if(!StringUtil.isNullOrEmpty(sqlItem.getCacheId())){
			CacheModel cache = this.getSqlMap().getCache(sqlItem.getCacheId());
			CacheKey key = this.getCacheKey(sqlString.toString(), sqlString.getParamValues());
			Object obj = cache.getObject(key);
			if(obj!=null && obj instanceof DataTable){
				return (DataTable)obj;
			}else{
				try{
					IDataReader reader = this.getDataReader(sqlString,top);
					sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
					DataTable table = this.getDataTable("",reader);
					if(table.rowSize()>0){
						this.linkTable(sqlItem,table,sqlId);
					}
					cache.putObject(key, table);
					//SqlExecuteEvent.raise(sqlId);
					return table;
				}catch(SqlExecuteException e){
					throw e;
				}finally{
					this.provider.getConnection().close();
				}
			}
		}else{
			try{
				IDataReader reader = this.getDataReader(sqlString,top);
				sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
				DataTable table = this.getDataTable("",reader);
				if(table.rowSize()>0){
					this.linkTable(sqlItem,table,sqlId);
				}
				//SqlExecuteEvent.raise(sqlId);
				return table;
			}catch(SqlExecuteException e){
				throw e;
			}finally{
				this.provider.getConnection().close();
			}
		}
	}

	@Override
	public DataTable getDataTable(String sqlId, int offset, int limit,
								  Object... conditions) {
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlString sqlString = sqlItem.toSqlString(this.getConfig(),this.getDialect(),conditions);
//        System.out.println(sqlString.toString());
		if(!StringUtil.isNullOrEmpty(sqlItem.getCacheId())){
			CacheModel cache = this.getSqlMap().getCache(sqlItem.getCacheId());
			CacheKey key = this.getCacheKey(sqlString.toString(), sqlString.getParamValues());
			Object obj = cache.getObject(key);
			if(obj!=null && obj instanceof DataTable){
				return (DataTable)obj;
			}else{
				try{
					IDataReader reader = this.getDataReader(sqlString,offset,limit);
					sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
					DataTable table = this.getDataTable("",reader);
					if(table.rowSize()>0){
						this.linkTable(sqlItem,table,sqlId);
					}
					cache.putObject(key, table);
					//SqlExecuteEvent.raise(sqlId);
					return table;
				}catch(SqlExecuteException e){
					throw e;
				}finally{
					this.provider.getConnection().close();
				}
			}
		}else{
			try{
				IDataReader reader = this.getDataReader(sqlString,offset,limit);
				sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
				DataTable table = this.getDataTable("",reader);
				if(table.rowSize()>0){
					this.linkTable(sqlItem,table,sqlId);
				}
				//SqlExecuteEvent.raise(sqlId);
				return table;
			}catch(SqlExecuteException e){
				throw e;
			}finally{
				this.provider.getConnection().close();
			}
		}
	}

	/**
	 * 查询返回第一行数据
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public Map<String,Object> getMap(String sqlId,Object ... conditions){
		DataTable table = this.getDataTable(sqlId,conditions);
		if(table.rowSize()>0){
			return table.getRow(0).toMap();
		}
		return null;
	}

	/**
	 * 查询返回MapList数据
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public List<Map<String,Object>> getMapList(String sqlId,Object ... conditions){
		DataTable table = this.getDataTable(sqlId,conditions);
		return table.toMapList();
	}

	/**
	 * 查询值
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public Object queryValue(String sqlId,Object ... conditions){
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlString sqlString = sqlItem.toSqlString(this.getConfig(),this.getDialect(),conditions);
		if(!StringUtil.isNullOrEmpty(sqlItem.getCacheId())) {
			CacheModel cache = this.getSqlMap().getCache(sqlItem.getCacheId());
			CacheKey key = this.getCacheKey(sqlString.toString(), sqlString.getParamValues());
			Object obj = cache.getObject(key);
			if (obj != null) {
				return obj;
			} else {
				Object result = this.executeScalar(sqlString);
				sqlItem.updateExecSqlTime(sqlString);        //统计执行时间
				//SqlExecuteEvent.raise(sqlId);
				if (!StringUtil.isNullOrEmpty(sqlItem.getResultClass())) {
					Class<?> clazz = ReflectorUtil.getClassForName(sqlItem.getResultClass());
					result = TypeUtil.changeType(result, clazz);
				}
				cache.putObject(key, result);
				return result;
			}
		}else {
			Object result = this.executeScalar(sqlString);
			sqlItem.updateExecSqlTime(sqlString);        //统计执行时间
			//SqlExecuteEvent.raise(sqlId);
			if (!StringUtil.isNullOrEmpty(sqlItem.getResultClass())) {
				Class<?> clazz = ReflectorUtil.getClassForName(sqlItem.getResultClass());
				return TypeUtil.changeType(result, clazz);
			} else {
				return result;
			}
		}
	}

	/**
	 * 第一行，第一列
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public Object queryFirstValue(String sqlId, Object... conditions) {
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlString sqlString = sqlItem.toSqlString(this.getConfig(),this.getDialect(),conditions);
		if(!StringUtil.isNullOrEmpty(sqlItem.getCacheId())) {
			CacheModel cache = this.getSqlMap().getCache(sqlItem.getCacheId());
			CacheKey key = this.getCacheKey(sqlString.toString(), sqlString.getParamValues());
			Object obj = cache.getObject(key);
			if (obj != null) {
				return obj;
			} else {
				SqlString firstSqlString = this.getDialect().getTopString(sqlString, 1);
				Object result = this.executeScalar(firstSqlString);
				sqlItem.updateExecSqlTime(sqlString);        //统计执行时间
				SqlExecuteEvent.raise(sqlId);
				if (!StringUtil.isNullOrEmpty(sqlItem.getResultClass())) {
					Class<?> clazz = ReflectorUtil.getClassForName(sqlItem.getResultClass());
					result = TypeUtil.changeType(result, clazz);
				}
				cache.putObject(key, result);
				return result;
			}
		}else {
			SqlString firstSqlString = this.getDialect().getTopString(sqlString, 1);
			Object result = this.executeScalar(firstSqlString);
			sqlItem.updateExecSqlTime(sqlString);        //统计执行时间
			SqlExecuteEvent.raise(sqlId);
			if (!StringUtil.isNullOrEmpty(sqlItem.getResultClass())) {
				Class<?> clazz = ReflectorUtil.getClassForName(sqlItem.getResultClass());
				return TypeUtil.changeType(result, clazz);
			} else {
				return result;
			}
		}
	}

	public Integer queryInt(String sqlId,Object ... conditions){
		Object result = this.queryValue(sqlId, conditions);
		if(result!=null){
			return (Integer)TypeUtil.changeType(result,Integer.class);
		}
		return null;
	}

	/**
	 * 查询值列表
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public List<?> queryValueList(String sqlId,Object ... conditions){
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlString sqlString = sqlItem.toSqlString(this.getConfig(),this.getDialect(),conditions);
		List<Object> result = this.executeScalars(sqlString);
		sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
		SqlExecuteEvent.raise(sqlId);
		return result;
	}

	/**
	 * 查询总数
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public int queryCount(String sqlId,Object ... conditions){
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlString sqlString = sqlItem.toSqlString(this.getConfig(),this.getDialect(),conditions);
		SqlString countSqlString = this.getDialect().getCountSqlString(sqlString);
		Integer count = (Integer)TypeUtil.changeType(this.executeScalar(countSqlString),Integer.class);
		SqlExecuteEvent.raise(sqlId);
		return count.intValue();
	}

	/**
	 * 执行Sql语句,返回引响行数
	 */
	public int executeNoneQuery(String sqlId,Object ... conditions){
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlString sqlString = sqlItem.toSqlString(this.getConfig(),this.getDialect(),conditions);
		int obj = this.executeNoneQuery(sqlString);
		sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
		SqlExecuteEvent.raise(sqlId);
		return obj;
	}

	public int[] executeNoneQuerys(String sqlId,List<?> conditions){
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		if(conditions!=null && conditions.size()>0){
			SqlString[] sqlStrings = new SqlString[conditions.size()];
			for(int i=0;i<conditions.size();i++){
				sqlStrings[i] = sqlItem.toSqlString(this.getConfig(),this.getDialect(),conditions.get(i));
			}
			int[] obj = this.executeNoneQuery(sqlStrings);
			sqlItem.updateExecSqlTime(sqlStrings);		//统计执行时间
			SqlExecuteEvent.raise(sqlId);
			return obj;
		}
		return new int[0];
	}

	private CacheKey getCacheKey(String sqlString,Object[] params){
		CacheKey cacheKey = new CacheKey();
		cacheKey.update(sqlString);
		if(params!=null){
			for(Object param : params){
				cacheKey.update(param);
			}
		}
		return cacheKey;
	}

	/**
	 * 连接外部表
	 * @param sqlItem
	 * @param table
	 * @param sqlId
	 */
	private void linkTable(SqlItem sqlItem,DataTable table,String sqlId){
		if(sqlItem.getLinkGroups()!=null && sqlItem.getLinkGroups().size()>0){
			Map<String,DataTable> linkTableMap = new HashMap<String,DataTable>();
			Map<String,Set<Object>> pksMap = new HashMap<String,Set<Object>>();
			for(SqlLinkGroup linkGroup : sqlItem.getLinkGroups()){
				String groupKey = linkGroup.getKey();
				for(SqlLink link : linkGroup.getLinks()) {
					if (!table.contains(link.getPkey()))
						throw new RuntimeException(StringUtil.format("在 common {0} 的返回结果中,找不到 link列{1}", sqlId, link.getPkey()));
				}
				if(!pksMap.containsKey(groupKey))pksMap.put(groupKey,new HashSet<Object>());
				for (DataRow row : table) {
					for(SqlLink link : linkGroup.getLinks()) {
						String pkey = link.getPkey();
						if (!StringUtil.isNullOrEmpty(link.getSplit())) {
							Object pkVals = row.get(pkey);
							if (pkVals != null && !"".equals(pkVals)) {
								String[] vs = pkVals.toString().split(link.getSplit(), -1);
								for (String v : vs) {
									if (!"".equals(v) && !pksMap.get(groupKey).contains(v)) pksMap.get(groupKey).add(v);
								}
							}
						} else {
							Object pkVal = row.get(pkey);
							if (pkVal != null && !"".equals(pkVal) && !pksMap.get(groupKey).contains(pkVal))
								pksMap.get(groupKey).add(pkVal);
						}
					}
				}

				if(!linkTableMap.containsKey(groupKey)){
					if(pksMap.get(groupKey).size()>0){
						DataTable linkTable = this.getDataTable(linkGroup.getSqlId(),new Object[]{pksMap.get(groupKey)});
						linkTableMap.put(groupKey, linkTable);
					}
				}

				for(SqlLink link : linkGroup.getLinks()) {
					if (linkTableMap.containsKey(groupKey)) this.linkTable(table, link, linkTableMap.get(groupKey), sqlId);
				}
			}

		}
	}

	/**
	 * 连接外部表
	 * @param table
	 * @param link
	 * @param linkTable
	 * @param sqlId
	 */
	private void linkTable(DataTable table,SqlLink link,DataTable linkTable,String sqlId){
		String pkey = link.getPkey();
		String fkey = link.getFkey();
		if(!linkTable.contains(fkey))throw new RuntimeException(StringUtil.format("在 common {0} 的返回结果中,找不到 列{1}",link.getSqlId(),fkey));
		Set<String> addCols = new HashSet<String>();
		for(SqlLink.LinkColumn col : link.getColumns()){
			if(!table.contains(col.getColumnName()))addCols.add(col.getColumnName());			//非重名列
		}
		Map<Object,Object[]> map = new HashMap<Object,Object[]>();
		for(DataRow row : linkTable){
			Object fkVal = row.get(fkey);
			if(fkVal!=null){
				if(!map.containsKey(fkVal))map.put(fkVal,new Object[link.getColumns().size()]);
				for(int i=0;i<link.getColumns().size();i++){
					String colName = link.getColumns().get(i).getName();
					if(linkTable.contains(colName)){
						Object val = this.getLinkColumnValue(row.get(colName),map.get(fkVal)[i],link.getColumns().get(i));
						map.get(fkVal)[i]=val;
					}
				}
			}
		}

		for (DataRow row : table) {
			if(!StringUtil.isNullOrEmpty(link.getSplit())){
				Object pkVals = row.get(pkey);
				if(pkVals!=null && !"".equals(pkVals)){
					String[] vs = pkVals.toString().split(link.getSplit(),-1);
					for(int i=0;i<link.getColumns().size();i++){
						String colName = link.getColumns().get(i).getColumnName();
						if(addCols.contains(colName)){
							String text = "";
							for(String v : vs){
								if(map.containsKey(v)){
									Object val = map.get(v)[i];
									text+=(text.length()>0)?link.getSplit()+val.toString():val.toString();
								}
							}
							row.set(colName,text);
						}
					}
				}
			}else{
				Object pkVal = row.get(pkey);
				if(map.containsKey(pkVal)){
					for(int i=0;i<link.getColumns().size();i++){
						String colName = link.getColumns().get(i).getColumnName();
						if(addCols.contains(colName)){
							if(link.getColumns().get(i).getOp()==SqlLink.OP.AVERAGE){
								Object[] vals = (Object[])map.get(pkVal)[i];
								Object val = MathUtil.average(vals[0], (Integer) vals[1]);
								row.set(colName,val);
							}else{
								Object val = map.get(pkVal)[i];
								row.set(colName,val);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 计算连接外部表列的值
	 * @param val
	 * @param oldVal
	 * @param column
	 * @return
	 */
	private Object getLinkColumnValue(Object val,Object oldVal,SqlLink.LinkColumn column){
		if(column.getOp()!=null){
			switch(column.getOp()){
				case JOIN:{
					String ov = (oldVal!=null)?(String)TypeUtil.changeType(oldVal, String.class):"";
					if(val!=null){
						String v = (String)TypeUtil.changeType(val, String.class);
						ov+=(!"".equals(ov))?column.getSplit()+v:v;
					}
					return ov;
				}
				case SUM:
					return MathUtil.add(val, oldVal);
				case AVERAGE:
					Object[] av = new Object[2];
					if(oldVal!=null){
						av[0] = MathUtil.add(val,((Object[])oldVal)[0]);
						av[1] = ((Integer)((Object[])oldVal)[0])+1;
					}else{
						av[0] = val;
						av[1] = 1;
					}
					return av;
				case MAX:
					if(TypeUtil.compareTo(val,oldVal)>0)return val;
				case MIN:
					if(TypeUtil.compareTo(val,oldVal)<0)return val;
				case FIRST:
					if(oldVal!=null)return oldVal;
				case LAST:
					if(val!=null)return val;
			}
		}
		return val;
	}

}
