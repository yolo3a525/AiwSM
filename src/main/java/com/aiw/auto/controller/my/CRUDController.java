package com.aiw.auto.controller.my;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aiw.auto.entity.CRUD;
import com.aiw.auto.mapper.CRUDMapper;
import com.aiw.controller.base.BaseController;
import com.aiw.entity.BaseJsonBean;
import com.aiw.entity.Page;

/** 
 * 说明：my.crud
 * 创建人：aiw
 * 创建时间：2017-05-07
 */

@Controller
@RequestMapping(value="/crud")
public class CRUDController extends BaseController<CRUDMapper, CRUD>{
   
	
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
	
	@RequiresPermissions("demo.list")
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute CRUD t){
    	return  list_p(page, t);
    }
    
	@RequiresPermissions("demo.add")
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public CRUD save(@ModelAttribute CRUD t){
		return  save_p(t);
    }
	
	
	@RequiresPermissions("demo.update")
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(CRUD t) {
		return update_p(t);
	}
    
	@RequiresPermissions("demo.delete")
	@Override
    @RequestMapping(value = "/delete/{id}")
	@ResponseBody
    public Integer delete(@PathVariable("id") Integer id){
		return delete_p(id);
    }
    
	
	@RequiresPermissions("demo.update")
	@Override
    @RequestMapping(value = "/{id}")
    public ModelAndView get(@PathVariable("id") Integer id){
		return get_p(id);
    }
        
	@RequiresPermissions("demo.add")
	@Override
    @RequestMapping(value = "")
    public ModelAndView get(){
    	return get_p();
    }
	
}