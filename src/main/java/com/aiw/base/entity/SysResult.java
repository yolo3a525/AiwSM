package com.aiw.base.entity;

import java.io.Serializable;

public class SysResult implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String OK = "OK";
    public static final Integer COMMON_ERROR = 101;
    public static final Integer NO_LOGIN_ERROR = 100;
    public static final Integer SYS_ERROR = 901;
    public static final Integer RONGYUN_ERROR = 701;
    public static final Integer ERROR_CODE_102 = 102;//btc价格变动而导致用户无法下单所报出的错误
    
    /***
     * 100表示没有登录或者token过期
     * 业务返回直接显示错误101-299(此类错误前端直接显示)
     * 数据库错误 701-799 融云错误
     * 数据库错误 801-899
     * 钱包错误     601-699
     * 系统级别错误901-999
     * 
     * 
     */
    
    private Integer code = 0; //表示错误的代号，0表示正确
    
    private String msg = OK ;	 //响应消息
    private Object data;		 //响应中的数据
    
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
	
    public SysResult() {}
    
    public SysResult(String msg) {
	    this.code = COMMON_ERROR;
		this.msg = msg;
	}
    
	public SysResult(Object data) {
		this.data = data;
	}
	
	public SysResult(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public SysResult(Integer code, String msg,Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

	public static SysResult build(Integer code, String msg) {
        return new SysResult(code, msg);
    }
	
	public static SysResult build(Integer code,String msg, Object data) {
	        return new SysResult(code, msg,data);
	}
	
    
    public static SysResult build(String msg) {
        return new SysResult(msg);
    }
    
    
    public static SysResult oK(Object data) {
        return new SysResult(data);
    }
    public static SysResult oK() {
        return SysResult.oK(null);
    }
    
    public static SysResult nologin() {
        return new SysResult(NO_LOGIN_ERROR,"no login");
    }
    
    public static SysResult sysError() {
        return new SysResult(SYS_ERROR,"sys error");
    }
    public static SysResult sysError(String msg) {
        return new SysResult(SYS_ERROR,msg);
    }
    
    
}
