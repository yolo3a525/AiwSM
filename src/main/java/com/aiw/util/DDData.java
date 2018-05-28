package com.aiw.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aiw.bdzb.entity.Label;
import com.aiw.bdzb.entity.Sence;
import com.aiw.bdzb.mapper.LabelMapper;
import com.aiw.bdzb.mapper.SenceMapper;
import com.aiw.entity.DD;
import com.aiw.mapper.DDMapper;

public class DDData {
	
	@Autowired
	public  DDMapper  ddMapper;
	
	@Autowired
	public  LabelMapper  labelMapper;
	
	@Autowired
	public  SenceMapper sceneMapper;
	
	public static  Map<String,Map<String,String>> dd = new HashMap<String,Map<String,String>>();
	
	
	public static  Map<String,Map<String,String>> ddLabel = new HashMap<String,Map<String,String>>();
	public static  Map<String,Map<String,String>> ddLabelValueToKey = new HashMap<String,Map<String,String>>();
	
	public void  init() {
		
		List<DD> ddList = ddMapper.select();
		
		for (DD d : ddList) {
			if(dd.get(d.getGroupCode()) == null) {
				dd.put(d.getGroupCode(), new LinkedHashMap<String,String>());
			}
			dd.get(d.getGroupCode()).put(d.getCode(), d.getName());
		}
		
		
		 List<Label> labelList = labelMapper.select();
		 
		 String type = "";

         ddLabel.put("pinlei", new LinkedHashMap<String,String>());
	     ddLabel.put("fengge", new LinkedHashMap<String,String>());
	     ddLabel.put("faxing", new LinkedHashMap<String,String>());
	     ddLabel.put("fuse", new LinkedHashMap<String,String>());
	     ddLabel.put("lianxing", new LinkedHashMap<String,String>());
         ddLabelValueToKey.put("pinlei", new LinkedHashMap<String,String>());
         ddLabelValueToKey.put("fengge", new LinkedHashMap<String,String>());
         ddLabelValueToKey.put("faxing", new LinkedHashMap<String,String>());
         ddLabelValueToKey.put("fuse", new LinkedHashMap<String,String>());
         ddLabelValueToKey.put("lianxing", new LinkedHashMap<String,String>());
   
		 for (Label label : labelList) {
			 if(label.getType() == 1) {//品类
				 type = "pinlei";
			 }else if(label.getType() == 2) {//风格
				 type = "fengge";
			 }else if(label.getType() == 3) {//发型
				 type = "faxing";
			 }else if(label.getType() == 4) {//肤色
				 type = "fuse";
			 }else if(label.getType() == 5) {//脸型
				 type = "lianxing";
			 }else {
				 continue;
			 }
			 ddLabel.get(type).put(label.getId()+"", label.getName());
			 ddLabelValueToKey.get(type).put( label.getName(),label.getId()+"");
		 }
		 
		 ddLabel.put("changjing", new LinkedHashMap<String,String>());
		 ddLabelValueToKey.put("changjing", new LinkedHashMap<String,String>());
		 List<Sence> sceneList =  sceneMapper.select();
		 for (Sence scene : sceneList) {
			 ddLabel.get("changjing").put(scene.getId()+"", scene.getName());
			 ddLabelValueToKey.get("changjing").put(scene.getName(),scene.getId()+"");
		 }
	}
	
	
	
}
