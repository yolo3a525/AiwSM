package com.aiw.controller.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aiw.entity.DD;
import com.aiw.entity.Page;
import com.aiw.entity.SysResult;
import com.aiw.mapper.DDMapper;
import com.aiw.util.CreateCode;
import com.aiw.util.PathUtil;

@Controller(value="ApiCodeController")
@RequestMapping(value="/api/code")
public class CodeController extends BaseController<DDMapper, DD>{
	
	@Autowired  
	protected  DataSource dataSource; 

	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public SysResult list(@ModelAttribute Page page,@ModelAttribute DD t){
    	return  list_p(page, t);
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public SysResult save(@ModelAttribute DD t){
		return  save_p(t);
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public SysResult update(DD t) {
		return update_p(t);
	}
    
	@Override
    @RequestMapping(value = "/delete/{id}")
	@ResponseBody
    public SysResult delete(@PathVariable("id") Integer id){
		return delete_p(id);
    }
    
	@Override
    @RequestMapping(value = "/{id}")
    public SysResult get(@PathVariable("id") Integer id){
		return get_p(id);
    }
        
	@Override
    @RequestMapping(value = "")
    public SysResult get(){
    	return get_p();
    }
	
	
	
	/***
	 * 生成
	 * @param map
	 * @param keys
	 * @param types
	 */
	@RequestMapping(value = "/create")
	@ResponseBody
    public void save(@RequestParam Map<String,Object> map,@RequestParam("key") String[] keys ,@RequestParam("type") String[] types){
		
		Map<Object, Object> entity = new HashMap<>();
		if(keys != null) {
			for(int i = 0 ; i < keys.length; i++) {
				entity.put(keys[i], types[i]);
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

	
	@Override
	@RequestMapping(value = "/badd")
	public SysResult badd() {
		return get_p();
	}

	@Override
	@RequestMapping(value = "/bedit/{id}")
	public SysResult bupdate(@PathVariable("id") Integer id) {
		return get_p();
	}
}
	
