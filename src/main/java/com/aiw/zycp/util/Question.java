package com.aiw.zycp.util;

import java.util.ArrayList;
import java.util.List;


public class Question {
	
	private String title;
	private List<Option> list = new ArrayList<Option>();
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Option> getList() {
		return list;
	}
	public void setList(List<Option> list) {
		this.list = list;
	}
	
}
