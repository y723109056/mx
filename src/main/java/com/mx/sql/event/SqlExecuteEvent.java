package com.mx.sql.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SqlExecuteEvent {
	private static Map<String,List<SqlExecuteListener>> listeners;
	
	static{
		listeners = new ConcurrentHashMap<String,List<SqlExecuteListener>>();
	}
	
	public static void addListener(String group,SqlExecuteListener listener){
		if(!listeners.containsKey(group))listeners.put(group,new ArrayList<SqlExecuteListener>());
		listeners.get(group).add(listener);
	}
	
	public static void raise(String group){
		if(listeners.containsKey(group)){
			List<SqlExecuteListener> list = listeners.get(group);
			if(list!=null){
				for(SqlExecuteListener listener : list){
					listener.onSqlExecute();
				}
			}
		}
	}
}
