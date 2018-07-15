package com.aiw.zycp.entity;

import com.aiw.base.entity.BaseEntity;

public class Answer extends BaseEntity {
	private String name;
	
    private String answer;
	private String email;
	private String type;//测试类型
	
	public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
	
	
	
}
