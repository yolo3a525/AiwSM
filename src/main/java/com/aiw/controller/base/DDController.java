package com.aiw.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aiw.entity.BaseJsonBean;
import com.aiw.entity.DD;
import com.aiw.entity.Page;
import com.aiw.mapper.DDMapper;
import com.aiw.util.DDData;

@Controller
@Scope("prototype")
@RequestMapping(value="/dd")
public class DDController extends BaseController<DDMapper, DD>{
	
	@Autowired
	DDData dddata;

	
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute DD t){
    	return  list_p(page, t);
    }
	
	/**
	 * 刷新数据字典
	 */
	private void refresh() {
		dddata.init();
	}
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public DD save(@ModelAttribute DD t){
		DD d = save_p(t);
		refresh();
		return  d;
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(DD t) {
		bj = update_p(t);
		refresh();
		return bj;
	}
    
    @RequestMapping(value = "/delete/{code}")
	@ResponseBody
    public Integer delete(@PathVariable("code") String code){
    	int i = mapper.delete(code);
		refresh();
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
	
	@RequestMapping(value = "/badd/{groupCode}")
	public ModelAndView badd(@PathVariable("groupCode") String groupCode) {
		ModelAndView modelAndView = new ModelAndView(); 
		DD dd = new DD();
		dd.setGroupCode(groupCode);
		setJspItemPath(modelAndView);
		modelAndView.addObject("edit",dd);
		return modelAndView; 
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

	@Override
	public Integer delete(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelAndView badd() {
		// TODO Auto-generated method stub
		return null;
	}
}
	
