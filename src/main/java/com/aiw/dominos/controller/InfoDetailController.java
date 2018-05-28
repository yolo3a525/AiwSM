package com.aiw.dominos.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aiw.controller.base.BaseController;
import com.aiw.dominos.entity.InfoDetail;
import com.aiw.dominos.mapper.InfoDetailMapper;
import com.aiw.entity.BaseJsonBean;
import com.aiw.entity.Page;


/** 
 * 说明：dominos.infodetail
 * 创建人：aiw
 * 创建时间：2017-10-24
 */

@Controller
@Scope("prototype")
@RequestMapping(value="/dominos/infodetail")
public class InfoDetailController extends BaseController<InfoDetailMapper, InfoDetail>{
   
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute InfoDetail t){
    	return  list_p(page, t);
    }
	
	
	@ResponseBody
    @RequestMapping(value = "/list/json", method = RequestMethod.GET)
    public Page listjson(@ModelAttribute Page page,@ModelAttribute InfoDetail t){
		
		page.setList(mapper.selectPage(page,t));
		
		return page;
    }
	
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public InfoDetail save(@ModelAttribute InfoDetail t){
		return  save_p(t);
    }
	
	public InfoDetail save_p(@ModelAttribute InfoDetail t){
    	//int i = 
    	        mapper.insert(t);
    	return t;
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(InfoDetail t) {
		return update_p(t);
	}
    
	@Override
    @RequestMapping(value = "/delete/{id}")
	@ResponseBody
    public Integer delete(@PathVariable("id") Integer id){
		return delete_p(id);
    }
    
	@Override
    public ModelAndView get(@PathVariable("id") Integer id){
		return get_p(id);
    }
	
    @RequestMapping(value = "/{id}")
    public InfoDetail get3(@PathVariable("id") Integer id){
		Map<String,Object> map = new HashMap<String,Object>();
    	map.put("id", id);
    	List<InfoDetail> list = mapper.select(map);
    	//ModelAndView modelAndView = new ModelAndView(); 
    	if(list != null && list.size() > 0) {
    		return list.get(0);
    	}
	    return null; 
    }
        
	@Override
    @RequestMapping(value = "")
    public ModelAndView get(){
    	return get_p();
    }
    
    
	@Override
	@RequestMapping(value = "/badd")
	public ModelAndView badd() {
		return get_p();
	}

	@Override
	@RequestMapping(value = "/bedit/{id}")
	public ModelAndView bupdate(@PathVariable("id") Integer id) {
		return get_p();
	}
}