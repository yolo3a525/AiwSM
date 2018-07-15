package com.aiw.api.base.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aiw.entity.DD;
import com.aiw.entity.Page;
import com.aiw.entity.SysResult;
import com.aiw.mapper.DDMapper;
import com.aiw.util.CreateCode;
import com.aiw.util.PathUtil;

@Controller(value="ApiCodeController")
@RequestMapping(value="/api/code")
public class CodeController{
	
	@Autowired  
	protected  DataSource dataSource; 

	@Autowired  
    protected  HttpServletResponse response;  
	
	
	/***
	 * 生成
	 * @param map
	 * @param keys
	 * @param types
	 */
	@RequestMapping(value = "/create")
    public void save(@RequestBody Map<String,Object> map){
		
	    ArrayList keys = (ArrayList)map.get("key");
	    ArrayList types = (ArrayList)map.get("type");
	    
		Map<Object, Object> entity = new HashMap<>();
		if(keys != null) {
			for(int i = 0 ; i < keys.size(); i++) {
				entity.put(keys.get(i), types.get(i));
			}
		}
		map.put("entity", entity);
		try {
			CreateCode.create(map,response,dataSource.getConnection());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }


	
	@RequestMapping(value = "/dbback")
	@ResponseBody
    public String dbback(){
		
		File file = new File(PathUtil.getClassResources() + "aiw.sql");
	    ScriptRunner runner;
		try {
			runner = new ScriptRunner(dataSource.getConnection());
			 runner.setErrorLogWriter(null);  
			    runner.setLogWriter(null);  
			    runner.runScript(new FileReader(file));  
		} catch (SQLException | FileNotFoundException e) {
			e.printStackTrace();
		}  
	   
		return "";
    }

	
}
	
