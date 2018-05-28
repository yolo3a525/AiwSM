package com.aiw.util.json;

import java.text.SimpleDateFormat;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class ElFunctions{

    public static String toJsonString(Object obj){
    	
    	JsonConfig jc = new JsonConfig();
    	jc.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessor() {
			
    		
			@Override
			public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
				if(arg1 != null)
					return new SimpleDateFormat("yyyy-MM-dd").format(arg1);
				else
					return null;
			}
			
			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		} );
    	
    	
    	jc.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonValueProcessor() {
			
    		
			@Override
			public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
				if(arg1 != null)
					return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(arg1);
				else
					return null;
			}
			
			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		} );
    	
    	
        // 将java对象转换为json对象
        JSONObject json = JSONObject.fromObject(obj, jc);
        String str = json.toString();
        return str;
    }
}
