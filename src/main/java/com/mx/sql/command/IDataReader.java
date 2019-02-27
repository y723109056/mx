package com.mx.sql.command;

import com.mx.sql.meta.Column;

import java.util.List;


/**
 * 数据读取接口
 */
public interface IDataReader {
	/**
	 * 关闭读取,并关闭连接
	 */
	void close();
	
	/**
	 * 读取下一行
	 * @return
	 */
	boolean read();
	
	/**
	 * 得到列的值
	 * @param columnName
	 * @return
	 */
	Object get(String columnName);
	
	/**
	 * 得到列的值
	 * @param columnIndex
	 * @return
	 */
	Object get(int columnIndex);
	
	/**
	 * 读取返回数据集的列名
	 * @return
	 */
	String[] getColumnNames();
	
	/**
	 * 读取返回数据集的列信息
	 * @return
	 */
	List<Column> getColumns();
	
}
