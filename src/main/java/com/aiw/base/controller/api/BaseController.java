package com.aiw.base.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.aiw.base.entity.BaseEntity;
import com.aiw.base.entity.Page;
import com.aiw.base.entity.SysResult;
import com.aiw.base.entity.User;
import com.aiw.base.mapper.BaseMapper;


public abstract class BaseController<M extends BaseMapper<T>,T extends BaseEntity> {
	
	protected  final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired  
	protected  HttpServletRequest request; 
	@Autowired  
	protected  HttpServletResponse response;  
	
	@Autowired
	protected M mapper;
	protected List<T> list;
	protected T t;
	
			
	/**
	 * 群查
	 */
    public abstract SysResult list(@ModelAttribute Page page,@ModelAttribute T t);
    
    
    /***
     * 增之前的请求
     */
    public abstract SysResult badd();
    
    
    /***
     * 增
     */
    public abstract SysResult save(@ModelAttribute T t);
    
    
    /***
     * 改之前的请求
     */
    public abstract SysResult bupdate(@PathVariable("id") Integer id);

    /**
     * 改
     */
    public abstract SysResult update(@ModelAttribute T t);
    
    /**
     * 删
     */
    public abstract SysResult delete(@PathVariable("id") Integer id);
    

    /**
     * 单查
     */
    public abstract SysResult get(@PathVariable("id") Integer id);
        
    /***
     * 新增空白页
     */
    public abstract SysResult get();
    
    
    
    
    //------------------------------------需要子类调用的通用方法---------开始-----------------
    
    
    public SysResult list_p(@ModelAttribute Page page,@ModelAttribute T t){
    	//设置pageSize
    	if(request.getSession().getAttribute("pageSize") != null) {
    		page.setPageSize((Integer)(request.getSession().getAttribute("pageSize")));
    	}
		 list = mapper.selectPage(page,t);
		 //ModelAndView modelAndView = new ModelAndView(); 
		 //modelAndView.addObject("list", list);//查询结果
		 //modelAndView.addObject("query", t);//保留查询条件
		 //modelAndView.addObject("module",t.getClass().getSimpleName().toLowerCase());//动态告诉页面什么模块
		 page.setList(list);
	     return SysResult.oK(page);  
    }
    public SysResult save_p(@ModelAttribute T t){
    	t.setLastUpdateUser(getUser().getUsername());
    	t.setCreateUser(getUser().getUsername());
    	int i = mapper.insert(t);
    	logger.debug("update " + i);
    	return SysResult.oK(t);
    }
	public SysResult update_p(T t) {
		t.setLastUpdateUser(getUser().getUsername());
		mapper.update(t);
    	return SysResult.oK(t);
	}
    public SysResult delete_p(@PathVariable("id") Integer id){
    	Integer i = mapper.delete(id);
    	return SysResult.oK(i);
    }
    public SysResult get_p(@PathVariable("id") Integer id){
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("id", id);
    	List<T> list = mapper.select(map);
    	
    	
    	return SysResult.oK(list.get(0));
    }
    public SysResult get_p(){
	    return SysResult.oK(); 
    }
    
   
   
    //------------------------------------需要子类调用的通用方法---------结束-----------------

	
    public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
	public Integer getId() {
		if(getUser() != null) {
			return getUser().getId();
		}
		return null;
	}
	
	public boolean isGys() {
	    return request.getSession().getAttribute("gys") != null;
	}
	
	public User getUser() {
		User user = (User) request.getSession().getAttribute("user");
		if(user != null) {
			return user;
		}
		return null;
	}

	


}
