package com.mx.sql.util;

import com.mx.collection.DataTable;

import java.util.List;
import java.util.Map;

public interface ISqlEngine extends ISqlHelper {
	
	DataTable getDataTable(String sqlId, Object... conditions);
	
	DataTable getDataTable(String sqlId, int top, Object... conditions);
	
	DataTable getDataTable(String sqlId, int offset, int limit, Object... conditions);
	
	Map<String,Object> getMap(String sqlId, Object... conditions);
	
	List<Map<String,Object>> getMapList(String sqlId, Object... conditions);
	
	Object queryValue(String sqlId, Object... conditions);
	
	Object queryFirstValue(String sqlId, Object... conditions);
	
	Integer queryInt(String sqlId, Object... conditions);
	
	List<?> queryValueList(String sqlId, Object... conditions);
	
	int queryCount(String sqlId, Object... conditions);
	
	int executeNoneQuery(String sqlId, Object... conditions);
	
	int[] executeNoneQuerys(String sqlId, List<?> conditions);

}
