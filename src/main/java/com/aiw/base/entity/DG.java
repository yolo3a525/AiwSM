package com.aiw.base.entity;

import java.util.List;

public class DG extends BaseEntity {
	
	private String dgCode;
	private String dgName;
	private String dgRemark;
	
	public String getDgRemark() {
        return dgRemark;
    }
    public void setDgRemark(String dgRemark) {
        this.dgRemark = dgRemark;
    }
    private List<DD> list;
	
	public String getDgCode() {
		return dgCode;
	}
	public void setDgCode(String code) {
		this.dgCode = code;
	}
	public String getDgName() {
		return dgName;
	}
	public void setDgName(String name) {
		this.dgName = name;
	}
	public List<DD> getList() {
		return list;
	}
	public void setList(List<DD> list) {
		this.list = list;
	}

	
}
