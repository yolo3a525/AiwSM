package com.aiw.entity;

import java.util.List;

public class DG extends BaseEntity {
	
	private String code;
	private String name;
	
	private List<DD> list;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<DD> getList() {
		return list;
	}
	public void setList(List<DD> list) {
		this.list = list;
	}

	
}
