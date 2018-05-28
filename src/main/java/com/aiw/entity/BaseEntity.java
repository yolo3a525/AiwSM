package com.aiw.entity;

public class BaseEntity {
	
	private java.sql.Timestamp lastUpdateTime;
	private java.sql.Timestamp createTime;
	private String createUser;
	private String lastUpdateUser;
	private Integer id;
	
	public java.sql.Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(java.sql.Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public java.sql.Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.sql.Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
