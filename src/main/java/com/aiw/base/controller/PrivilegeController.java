package com.aiw.base.controller;

import java.util.ArrayList;
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

import com.aiw.base.entity.BaseJsonBean;
import com.aiw.base.entity.Page;
import com.aiw.base.entity.Privilege;
import com.aiw.base.mapper.PrivilegeMapper;

import net.sf.json.JSONArray;

@Controller
@Scope("prototype")
@RequestMapping(value="/privilege")
public class PrivilegeController extends BaseController<PrivilegeMapper, Privilege>{
   
	
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute Privilege t){
    	return  list_p(page, t);
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public Privilege save(@ModelAttribute Privilege t){
		return  save_p(t);
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(Privilege t) {
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
	
	
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
    public ModelAndView tree(){
    	
		 List<Privilege>  list = mapper.select();
		 
		 Map<Integer,Privilege> all = new HashMap<>();
		 Privilege root = new Privilege();
		 root.setName("根");
		 root.setChildren(new ArrayList<Privilege>());
		 root.setDepth(0);
		 
		 all.put(0, root);
		 
		 int dept = 1;
		 for(int i = 0;i < dept;i++) {
			 for (Privilege Privilege : list) {
				 //按照深度遍历
				 if(i == 0 && Privilege.getDepth() > dept) {
					 dept = Privilege.getDepth();
				 }
				 if((Privilege.getDepth() - 1) == i) {
					 all.put(Privilege.getId(), Privilege);
					 
					 if(Privilege.getPid()== null) {
						 Privilege.setPid(0);
					 }
					 if(all.get(Privilege.getPid()).getChildren() == null) {
						 all.get(Privilege.getPid()).setChildren(new ArrayList<Privilege>());
					 }
					 all.get(Privilege.getPid()).getChildren().add(Privilege);
				 }
			}
		 }
		 ModelAndView modelAndView = new ModelAndView();
		 modelAndView.addObject("tree", JSONArray.fromObject(all.get(0)).toString());
	     modelAndView.setViewName("/privilege/list");  
	     return modelAndView;  
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
	
