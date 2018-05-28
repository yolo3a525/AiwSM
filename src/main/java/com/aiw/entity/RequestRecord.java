package com.aiw.entity;

public class RequestRecord extends BaseEntity {
	private String requestURI;
	private String requestParameters;
	
	public String getRequestURI() {
		return requestURI;
	}
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	
	public String getRequestParameters() {
		return requestParameters;
	}
	public void setRequestParameters(String requestParameters) {
		this.requestParameters = requestParameters;
	}
	
}
