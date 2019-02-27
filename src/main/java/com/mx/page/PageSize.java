package com.mx.page;

public class PageSize implements IPageSize {
	protected int pageNum;
	protected int pageSize;
	protected int offset;
	protected int limit;
	protected int total;
	protected boolean hasCount;
	
	public PageSize(){
		this.pageNum = 1;
		this.pageSize = 25;
		this.total = 0;
		this.hasCount = true;
	}
	
	public PageSize(int pageSize,int pageNum){
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.total = 0;
		this.hasCount = true;
	}
	
	public PageSize(int pageSize,int pageNum,int total)
	{
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.total = total;
		this.hasCount = true;
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getLimit() {
		if(this.total>0){
			if((this.pageSize * this.pageNum) < this.total){
				return this.pageSize * this.pageNum;
			}else{
				return this.total;
			}
		}else{
			return this.pageSize * this.pageNum;
		}
	}
	
	public void setLimit(int limit){
		this.limit = limit;
	}

	public int getOffset(){
		return this.pageSize * (this.pageNum - 1);
	}
	
	public void setOffset(int offset){
		this.offset = offset;
	}

	public boolean getHasCount() {
		return hasCount;
	}

	public void setHasCount(boolean hasCount) {
		this.hasCount = hasCount;
	}
}
