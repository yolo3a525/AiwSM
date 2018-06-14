package com.aiw.zycp.util;

public class HttpsClient {
	
	
	    private static String charset = "utf-8";  

	      
	    public static void main(String[] args){  
	    	String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
	    			+ "wxf36d9dd7b7343243"
	    			+ "&secret="
	    			+ "af9d0afdcf25f7e9db4c02188de583e2 ";
	    	
	    	System.out.println(url);
	    	String rs = HttpClientUtil.doGet(url, charset);
	    	System.out.println(rs);
	    }  
	
	    
	    public static String getSessionKey(String code) {
	        String APPID = "wx61a9d5e9fd0533bf";//接龙小程序
	        String SECRET ="a5a558b2d4d21aab51ef5aa4f6e12a0e";
	        String url = "https://api.weixin.qq.com/sns/jscode2session?"
	                + "appid=" + APPID
	                + "&secret=" + SECRET
	                + "&js_code=" + code
	                + "&grant_type=authorization_code";
	        String rs = HttpClientUtil.doGet(url, charset);
	        return rs;
	        
	    }
	
}
