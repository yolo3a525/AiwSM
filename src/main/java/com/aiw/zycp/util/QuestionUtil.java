package com.aiw.zycp.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aiw.entity.KeyValue;

public class QuestionUtil {
    
    
    public static List<Question> getMBTIQuestions(){
        
        List<Question> list = new  ArrayList<Question>();
        
        InputStreamReader fr = null;
        BufferedReader br = null;
        try {
            fr = new InputStreamReader(new FileInputStream(QuestionUtil.class.getResource("/mbti.txt").getFile()), "UTF-8");
            br = new BufferedReader(fr);
            Question question = null;
            Option option = null;
            String line = null;
            int i = 0;
            
            while((line = br.readLine()) != null){
                line = line.trim();
                if(i % 3 == 0){
                    question = new Question();
                    question.setTitle(line.substring(line.indexOf("."), line.length()));
                    list.add(question);
                }else{
                    option = new Option();
                    option.setTitle(line);
                    option.setValue(line.substring(line.length()-2,line.length()-1));
                    question.getList().add(option);
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return list;
    }
	
	
	public static List<Question> getFPAQuestions(){
		
		List<Question> list = new  ArrayList<Question>();
		
		InputStreamReader fr = null;
        BufferedReader br = null;
        try {
            fr = new InputStreamReader(new FileInputStream(QuestionUtil.class.getResource("/fpa.txt").getFile()), "UTF-8");
			br = new BufferedReader(fr);
			Question question = null;
			Option option = null;
			String line = null;
			int i = 0;
			while((line = br.readLine()) != null){
				line = line.trim();
				if(i % 5 == 0){
					question = new Question();
					question.setTitle(line);
					list.add(question);
				}else{
					option = new Option();
					option.setTitle(line.substring(1, line.length()));
					option.setValue(line.substring(0,1));
					question.getList().add(option);
				}
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public static String getFPADesc(){
		
		StringBuffer sb = new StringBuffer();
		
		InputStreamReader fr = null;
        BufferedReader br = null;
        try {
            fr = new InputStreamReader(new FileInputStream(QuestionUtil.class.getResource("/fpa_rs.txt").getFile()), "UTF-8");
			
			
			br = new BufferedReader(fr);
			String line = null;
			while((line = br.readLine()) != null){
				line = line.trim();
				sb.append(line+"<br>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return sb.toString();
	}
	
	 public  static  Map<String,String>  getDISCRS()  {
	        Map<String,String> map = new HashMap<String, String>();
	        String key = null;
	        InputStreamReader fr = null;
	        BufferedReader br = null;
	        try {
	            fr = new InputStreamReader(new FileInputStream(QuestionUtil.class.getResource("/disc_rs.txt").getFile()), "UTF-8");
	            br = new BufferedReader(fr);
	            String line = null;
	            while((line = br.readLine()) != null){
	                line = line.trim();
	                if(line.contains("Dominance－支配型/控制者") || line.contains("Influence－活泼型/社交者") || line.contains("Steadiness－稳定型/支持者") || line.contains("Compliance－完美型/服从者")){
	                    key = line;
	                    map.put(key, "");
	                }
	                map.put(key, map.get(key)+"<br>"+line);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally{
	            try {
	                fr.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return map;
	    }
	
	public  static  List<List<KeyValue>>  getDISC()  {
		List<List<KeyValue>> list = null;
		InputStreamReader fr = null;
        BufferedReader br = null;
        try {
            fr = new InputStreamReader(new FileInputStream(QuestionUtil.class.getResource("/disc.txt").getFile()), "UTF-8");
            
			br = new BufferedReader(fr);
			String line = null;
			int i = 0;
			list = new ArrayList<List<KeyValue>>();
			List<KeyValue> ti = null;
			KeyValue keyValue = null;
			while((line = br.readLine()) != null){
				line = line.trim();
				if(i % 5 == 0){
					ti = new ArrayList<KeyValue>();
					list.add(ti);
				}else{
					keyValue = new KeyValue();
					keyValue.setKey(line.substring(0, line.length()-1));
					keyValue.setValue(line.substring(line.length()-1, line.length()));
					ti.add(keyValue);
				}
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
