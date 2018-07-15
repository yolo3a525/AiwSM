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

import com.aiw.base.controller.BaseController;
import com.aiw.base.entity.BaseJsonBean;
import com.aiw.base.entity.Page;
import com.aiw.dominos.entity.Info;
import com.aiw.dominos.mapper.InfoMapper;


/** 
 * 说明：dominos.info
 * 创建人：aiw
 * 创建时间：2017-10-19
 */

@Controller
@Scope("prototype")
@RequestMapping(value="/dominos/info")
public class InfoController extends BaseController<InfoMapper, Info>{
	
	@ResponseBody
    @RequestMapping(value = "/list2", method = RequestMethod.GET)
    public Page list2(@ModelAttribute Page page,@ModelAttribute Info t){
		page.setList(mapper.selectPage(page,t));
		
		return page;
    }
	
	//我参与的接龙
	@ResponseBody
    @RequestMapping(value = "/list/join", method = RequestMethod.GET)
    public Page listjoin(@ModelAttribute Page page,@ModelAttribute Info t){
		page.setList(mapper.selectJoinPage(page,t));
		return page;
    }
	
	
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public Info save(@ModelAttribute Info t){
		int i = mapper.insert(t);
    	
    	return t;
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(Info t) {
		return update_p(t);
	}
    
	@Override
    @RequestMapping(value = "/delete/{id}")
	@ResponseBody
    public Integer delete(@PathVariable("id") Integer id){
		return delete_p(id);
    }
    
	@Override
    @RequestMapping(value = "/{id}")
    public ModelAndView get(@PathVariable("id") Integer id){
		return get_p(id);
    }
	
	@ResponseBody
    @RequestMapping(value = "/json/{id}")
    public Info getJson(@PathVariable("id") Integer id){
		Map<String,Object> map = new HashMap<String,Object>();
    	map.put("id", id);
    	List<Info> list = mapper.select(map);
    	ModelAndView modelAndView = new ModelAndView(); 
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

	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute Info t){
		ModelAndView m = list_p(page, t);
    	return  m;
    }
}