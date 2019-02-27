package com.mx.sql.mapping;

public interface ISqlItemCount {
	
	/**
	 * 平均执行时间
	 * @return
	 */
	long getAverageTime();
	
	/**
	 * 最执执行时间
	 * @return
	 */
	long getMaxTime();
	
	/**SqlItemCount.java
	 * 
	 * @return
	 */
	long getMinTime();
	
	/**
	 * 执行次数
	 * @return
	 */
	long getExceCount();
	
	/**
	 * 总行执行时间
	 * @return
	 */
	long getTotalTime();
	
	/**
	 * 最后执行时间
	 * @return
	 */
	long getLastTime();
	
	/**
	 * 执行SqlId
	 * @return
	 */
	String getSqlId();
	
}
