package com.mx.page;

import java.util.ArrayList;
import java.util.List;

public class PageList<T> extends ArrayList<T> implements IPageList<T> {

	private static final long serialVersionUID = -7671096683461709474L;

	private int total;
	private int pageNum;
	private int pageSize;
	protected int offset;
	protected int limit;
	private boolean hasCount;
	
	public PageList(int total){
		this.total = total;
		this.pageNum = -1;
		this.pageSize = -1;
		this.hasCount = true;
	}
	
	public PageList(List<? extends T> list, int totalCount){
		this(list,totalCount,-1,-1);
	}
	
	public PageList(List<? extends T> list,IPageSize page){
		this(list,page.getTotal(),page.getPageNum(),page.getPageSize());
	}
	
	public PageList(List<? extends T> list, int totalCount,int pageNum,int pageSize){
		this.addAll(list);
		this.total = totalCount;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
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
