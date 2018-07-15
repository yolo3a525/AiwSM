package com.aiw.base.entity;

import java.util.List;


public class Page {
	
	private int pageSize = 20;
	private int totalSize;
	private int currentPage;
	private int totalPage;
	
	private List<?> list;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
		if(totalSize % pageSize == 0) {
			this.totalPage = totalSize / pageSize;
		}else {
			this.totalPage = totalSize / pageSize + 1;
		}
		
		if(totalPage < 1) totalPage = 1;
		if(currentPage > totalPage)  currentPage = totalPage;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}


	public int getCurrentPage() {
		if(currentPage <= 1) {
			currentPage  =1;
		}
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	} 
	
	
}
