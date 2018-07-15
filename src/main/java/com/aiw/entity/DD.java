package com.aiw.entity;

public class DD extends BaseEntity {
	
	private String ddValue;
	private String ddItem;
	private String dgCode;
	private String dgName;
	private String ddRemark;
	
	
	public String getDdRemark() {
        return ddRemark;
    }
    public void setDdRemark(String ddRemark) {
        this.ddRemark = ddRemark;
    }
    public String getDdValue() {
		return ddValue;
	}
	public void setDdValue(String code) {
		this.ddValue = code;
	}
	public String getDdItem() {
		return ddItem;
	}
	public void setDdItem(String name) {
		this.ddItem = name;
	}
	public String getDgCode() {
		return dgCode;
	}
	public void setDgCode(String groupCode) {
		this.dgCode = groupCode;
	}
	public String getDgName() {
		return dgName;
	}
	public void setDgName(String groupName) {
		this.dgName = groupName;
	}
	
	
}
