package com.aiw.${module}.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aiw.controller.base.BaseController;
import com.aiw.${module}.entity.${component};
import com.aiw.${module}.mapper.${component}Mapper;


import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.aiw.entity.BaseJsonBean;
import com.aiw.entity.Page;


/** 
 * 说明：${module}.${componentLower}
 * 创建人：aiw
 * 创建时间：${nowDate?string("yyyy-MM-dd")}
 */

@Controller
@Scope("prototype")
@RequestMapping(value="/${module}/${componentLower}")
public class ${component}Controller extends BaseController<${component}Mapper, ${component}>{
   
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute ${component} t){
    	return  list_p(page, t);
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public ${component} save(@ModelAttribute ${component} t){
		return  save_p(t);
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(${component} t) {
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