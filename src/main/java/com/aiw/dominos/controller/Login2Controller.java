package com.aiw.dominos.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;


/** 
 * 说明：dominos.info
 * 创建人：aiw
 * 创建时间：2017-10-19
 */

@Controller
@Scope("prototype")
@RequestMapping(value="/dominos/login")
public class Login2Controller{
   
	public static String appID = "wx61a9d5e9fd0533bf";
	public static String appsecret=  "d7cc309f741fbf7d93aab7bf26b682b0";
    
	@RequestMapping(value = "")
	@ResponseBody
    public Object login(HttpServletRequest request){
		
		String url2 = "https://api.weixin.qq.com/sns/jscode2session?"
				+ "appid=${appid}&secret=${secret}&js_code="+request.getParameter("code")+"&grant_type=authorization_code";
		//1.拿到code
		HttpGet requestht = new HttpGet(url2.replace("${appid}", appID)
				.replace("${secret}", appsecret));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = null;
        String result = null;
        JSONObject jo = null;
        try {
			response = httpClient.execute(requestht);
			if (response.getStatusLine().getStatusCode() == 200) {
	            result= EntityUtils.toString(response.getEntity(),"utf-8");
	            System.out.println(result);
	            jo = JSONObject.fromObject(result);
	            return jo;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
        return null;
    }
	
	
}