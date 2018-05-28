package com.aiw.entity;

public class BaseJsonBean {
	private Integer code= 0;
	private String msg = "OK";
	private Object data;
	
	public final static Integer E1001 = 1001;//需要付超出费用
	public final static Integer E1002 = 1002;//逾期要付费
	
	
	public final static Integer E1003 = 1003;//个人资料不全
	
	public final static Integer E1004 = 1004;//非vip用户或vip但是已经过期用户
	
	
	public final static Integer E901 = 901;//没有登录
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
