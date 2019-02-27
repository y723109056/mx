package com.mx.page;

public class PageLimit implements IPageSize  {
	protected int offset;
	protected int limit;
	protected int pageNum;
	protected int pageSize;
	protected int total;
	protected boolean hasCount;
	
	public PageLimit(int offset,int limit){
		this.offset = offset;
		this.limit = limit;
		this.hasCount = true;
	}
	
	public PageLimit(int offset,int limit,boolean hasCount){
		this.offset = offset;
		this.limit = limit;
		this.hasCount = hasCount;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public boolean getHasCount() {
		return hasCount;
	}

	public void setHasCount(boolean hasCount) {
		this.hasCount = hasCount;
	}
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


}
