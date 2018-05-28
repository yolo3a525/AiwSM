package com.aiw.controller.base;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aiw.entity.BaseJsonBean;
import com.aiw.entity.DG;
import com.aiw.entity.Page;
import com.aiw.mapper.DGMapper;

@Controller
@Scope("prototype")
@RequestMapping(value="/dg")
public class DGController extends BaseController<DGMapper, DG>{
	
	
	
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute DG t){
    	return  list_p(page, t);
    }
	
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public DG save(@ModelAttribute DG t){
		DG d = save_p(t);
		return  d;
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(DG t) {
		bj = update_p(t);
		return bj;
	}
    
	@Override
	@ResponseBody
    public Integer delete(@PathVariable("id") Integer id){
		int t = delete_p(id);
		return t;
    }
    
	
	@RequestMapping(value = "/delete/{code}")
	@ResponseBody
    public Integer delete(@PathVariable("code") String code){
    	int i = mapper.delete(code);
		return i;
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

	@RequestMapping(value = "/bedit/{code}")
	public ModelAndView bupdate(@PathVariable("code") String code) {
    	ModelAndView modelAndView = new ModelAndView(); 
    		setJspItemPath(modelAndView);
    		modelAndView.addObject("edit", mapper.get(code));
	    return modelAndView; 
	}


	@Override
	public ModelAndView bupdate(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
}
	
