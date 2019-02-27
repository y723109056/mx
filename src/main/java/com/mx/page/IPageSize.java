package com.mx.page;

public interface IPageSize {
	
	int getPageNum();
	
	void setPageNum(int pageNum);
	
	int getPageSize();
	
	void setPageSize(int pageSize);
	
	int getLimit();
	
	void setLimit(int limit);
	
	int getOffset();
	
	void setOffset(int offset);
	
	int getTotal();

	void setTotal(int total);
	
	/**
	 * 是否计算总数
	 * @return
	 */
	boolean getHasCount();
	
	/**
	 * 设置是否要计数总数,默认为true
	 * @param hasCount
	 */
	void setHasCount(boolean hasCount);
}
