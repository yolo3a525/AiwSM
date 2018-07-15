package com.aiw.xiaochengxu.util;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpsClient {
	
	
	    private static String charset = "utf-8";  

	      
	    public static void main(String[] args){  
//	    	String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
//	    			+ "wxf36d9dd7b7343243"
//	    			+ "&secret="
//	    			+ "af9d0afdcf25f7e9db4c02188de583e2 ";
//	    	
//	    	System.out.println(url);
//	    	String rs = HttpClientUtil.doGet(url, charset);
//	    	System.out.println(rs);
	        
//	        String APPID = "wx61a9d5e9fd0533bf";//接龙小程序
//            String SECRET ="a5a558b2d4d21aab51ef5aa4f6e12a0e";
            
            //高总的
            String APPID = "wx70091575c17d564e";//接龙小程序
            String SECRET ="786c2928856fe8e576011711c4168600";
            
            //System.out.println(getToken(APPID, SECRET));
	        String token = "10_hwiXnog4q1fLIn3qGGk4dXGJ07MXGVYphTkt9CYFn-M2-iffS4taKlIvzveCWb6xgW8-qNUKPU7Ouk--bcoHiDy3msoLM-K2IeWtBwueNflVPWiiutJa_Fnpljr5semwaKvER70H1xLO8pI2UGMfAGADPV";
	        String ticket = "gQEI8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyLURCNnc3Ul9hNlAxMDAwMDAwNzkAAgREbyNbAwQAAAAA";
	        System.out.println(getQrcode(ticket));
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
	    
	    //10_hwiXnog4q1fLIn3qGGk4dXGJ07MXGVYphTkt9CYFn-M2-iffS4taKlIvzveCWb6xgW8-qNUKPU7Ouk--bcoHiDy3msoLM-K2IeWtBwueNflVPWiiutJa_Fnpljr5semwaKvER70H1xLO8pI2UGMfAGADPV
	    public static String getToken(String appid,String secret) {
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&"
                    + "appid=" + appid
                    + "&secret=" + secret;
                    
            String rs = HttpClientUtil.doGet(url, charset);
            return rs;
            
        }
	    
	    public static String getTicket(String token) {
            String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("action_name", "QR_LIMIT_STR_SCENE");
            Map<String,Object> map2 = new HashMap<String, Object>();
            Map<String,Object> map3 = new HashMap<String, Object>();
            map.put("action_info", map2);
            map2.put("scene", map3);
            map3.put("scene_str", "1");
            
            //{"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "test"}}}
            
            String rs = HttpClientUtil.doPostJson(url, map, "utf-8");
            return rs;
            
        }
	    
	    public static String getQrcode(String ticket) {
            String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + URLEncoder.encode(ticket);
            String rs = HttpClientUtil.doGet(url,"utf-8");
            return rs;
            
        }
	
	    
	    
}
