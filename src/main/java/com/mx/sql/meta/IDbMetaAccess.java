package com.mx.sql.meta;

import java.util.List;

public interface IDbMetaAccess {
	
	/**
	 * 得到数据库所有表
	 * @return
	 */
	List<Table> getTables();
	
	/**
	 * 通过表名得到数据库表,不存在返回空
	 * @return
	 */
	Table getTable(String tableName);
	
	/**
	 * 创建一个表
	 * @param tableName
	 */
	void createTable(Table tableName);
	
	/**
	 * 摧毁一个数据库表
	 * @param tableName
	 */
	void dropTable(String tableName);
	
	/**
	 * 得到数据库所有Sequene
	 * @return
	 */
	List<Sequence> getSequences();
	
	/**
	 * 通过名称得到数据库Sequene
	 * @return
	 */
	Sequence getSequence(String sequenceName);
	
	/**
	 * 得到下一个Sequene的值
	 * @param sequeneName
	 * @return
	 */
	Long getSequenceNextValue(String sequenceName);
	
	/**
	 * 得到一批Sequene的值
	 * @param sequenceName
	 * @param num
	 * @return
	 */
	Long[] getSequenceNextValue(String sequenceName, int num);
	
	/**
	 * 创建一个Sequene
	 * @param sequeneName
	 */
	void createSequence(Sequence sequence);
	
	/**
	 * 创建一个Sequene
	 * @param sequeneName
	 */
	void createSequence(String sequenceName, Long sequenceValue);
	
	/**
	 * 摧毁一个Sequene
	 * @param sequeneName
	 */
	void dropSequence(String sequeneName);
}
