package com.mx.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataSet {
	private String name;
	private Map<String,DataTable> tables;
	
	protected DataSet() {
	}

	public DataSet(String name)
	{
		this.name=name;
		this.tables = new HashMap<String,DataTable>();
	}
	
	/**
	 * 得到数据集的名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置数据集的名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 向数据集添加一个表对像
	 * @param table
	 */
	public void addTable(DataTable table) {
		tables.put(table.getName(),table);
	}

	/**
	 * 通过索引得到一个表对像
	 * @param index
	 * @return
	 */
	public DataTable getTable(int index) {
		return tables.get(index);
	}

	/**
	 * 通过表对像名称得到一个表对像
	 * @param tableName
	 * @return
	 */
	public DataTable getTable(String tableName) {
		return tables.get(tableName);
	}

	public Iterator<DataTable> iterator() {
		return this.tables.values().iterator();
	}
	
	/**
	 * 是否包含该名称的表对像
	 * @param tableName
	 * @return
	 */
	public boolean contains(String tableName)
	{
		return this.tables.containsKey(tableName);
	}

	/**
	 * 得到表对像数组
	 * @return
	 */
	public DataTable[] getTables() {
		return this.tables.values().toArray(new DataTable[0]);
	}
}
