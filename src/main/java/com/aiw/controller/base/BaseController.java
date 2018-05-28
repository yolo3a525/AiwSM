package com.aiw.controller.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aiw.entity.BaseEntity;
import com.aiw.entity.BaseJsonBean;
import com.aiw.entity.Page;
import com.aiw.entity.User;
import com.aiw.mapper.BaseMapper;


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
	
	protected BaseJsonBean bj = new BaseJsonBean();
			
	/**
	 * 群查
	 */
    public abstract ModelAndView list(@ModelAttribute Page page,@ModelAttribute T t);
    
    
    /***
     * 增之前的请求
     */
    public abstract ModelAndView badd();
    
    
    /***
     * 增
     */
    public abstract T save(@ModelAttribute T t);
    
    
    /***
     * 改之前的请求
     */
    public abstract ModelAndView bupdate(@PathVariable("id") Integer id);

    /**
     * 改
     */
    public abstract BaseJsonBean update(@ModelAttribute T t);
    
    /**
     * 删
     */
    public abstract Integer delete(@PathVariable("id") Integer id);
    

    /**
     * 单查
     */
    public abstract ModelAndView get(@PathVariable("id") Integer id);
        
    /***
     * 新增空白页
     */
    public abstract ModelAndView get();
    
    
    
    //下面可以被继承的方法
    protected String getModule() {
    	
    	String module =this.getClass().getPackage().getName().replaceAll("com.aiw", "")
    								.replaceAll("controller", "")
    								.replaceAll("base", "")
    								.replaceAll("\\.", "");
    	String folder = this.getClass().getSimpleName().toLowerCase().replace("controller", "");
		folder = !module.equals("")?module+"/"+ folder : folder;
    	return folder;
    }
    protected void setJspListPath(ModelAndView m) {
    	m.setViewName("/" + getModule() + "/list");
    }
    protected void setJspItemPath(ModelAndView m) {
    	m.setViewName("/" + getModule() + "/item");
    }
    
    //------------------------------------需要子类调用的通用方法---------开始-----------------
    
    
    public ModelAndView list_p(@ModelAttribute Page page,@ModelAttribute T t){
    	//设置pageSize
    	if(request.getSession().getAttribute("pageSize") != null) {
    		page.setPageSize((Integer)(request.getSession().getAttribute("pageSize")));
    	}
		 list = mapper.selectPage(page,t);
		 ModelAndView modelAndView = new ModelAndView(); 
		 modelAndView.addObject("list", list);//查询结果
		 modelAndView.addObject("query", t);//保留查询条件
		 modelAndView.addObject("module",t.getClass().getSimpleName().toLowerCase());//动态告诉页面什么模块
		 setJspListPath(modelAndView);
	     return modelAndView;  
    }
    public T save_p(@ModelAttribute T t){
    	t.setLastUpdateUser(getUser().getUsername());
    	t.setCreateUser(getUser().getUsername());
    	//int i = 
    	        mapper.insert(t);
    	
    	return t;
    }
	public BaseJsonBean update_p(T t) {
		t.setLastUpdateUser(getUser().getUsername());
		mapper.update(t);
		bj.setData(t);
    	return bj;
	}
    public Integer delete_p(@PathVariable("id") Integer id){
    	int i = mapper.delete(id);
    	return i;
    }
    public ModelAndView get_p(@PathVariable("id") Integer id){
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("id", id);
    	List<T> list = mapper.select(map);
    	ModelAndView modelAndView = new ModelAndView(); 
    	if(list != null && list.size() > 0) {
    		setJspItemPath(modelAndView);
    		modelAndView.addObject("edit", list.get(0));
    	}else {
    		modelAndView.setViewName("/error");
    	}
	    return modelAndView; 
    }
    public ModelAndView get_p(){
    	ModelAndView modelAndView = new ModelAndView(); 
    	setJspItemPath(modelAndView);
	    return modelAndView; 
    }
    
    @RequestMapping(value = "/chart/list")
	public ModelAndView chart() {
		ModelAndView modelAndView = new ModelAndView(); 
		modelAndView.setViewName("/" + getModule() + "/chartlist");
	    return modelAndView; 
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

	public BaseJsonBean getBj() {
		return bj;
	}

	public void setBj(BaseJsonBean bj) {
		this.bj = bj;
	}


}
