package com.aiw.controller.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aiw.entity.BaseJsonBean;
import com.aiw.entity.Menu;
import com.aiw.entity.Page;
import com.aiw.mapper.MenuMapper;

import net.sf.json.JSONArray;

@Controller
@Scope("prototype")
@RequestMapping(value="/menu")
public class MenuController extends BaseController<MenuMapper, Menu>{
   
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute Menu t){
    	return  list_p(page, t);
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public Menu save(@ModelAttribute Menu t){
		return  save_p(t);
    }
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(Menu t) {
		return update_p(t);
	}
    
	@CrossOrigin(origins="*")
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
    	
		 List<Menu>  list = mapper.select();
		 
		 Map<Integer,Menu> all = new HashMap<>();
		 Menu root = new Menu();
		 root.setName("根");
		 root.setChildren(new ArrayList<Menu>());
		 root.setDepth(0);
		 
		 all.put(0, root);
		 
		 int dept = 1;
		 for(int i = 0;i < dept;i++) {
			 for (Menu menu : list) {
				 //按照深度遍历
				 if(i == 0 && menu.getDepth() > dept) {
					 dept = menu.getDepth();
				 }
				 if((menu.getDepth() - 1) == i) {
					 all.put(menu.getId(), menu);
					 
					 if(menu.getPid()== null) {
						 menu.setPid(0);
					 }
					 if(all.get(menu.getPid()).getChildren() == null) {
						 all.get(menu.getPid()).setChildren(new ArrayList<Menu>());
					 }
					 all.get(menu.getPid()).getChildren().add(menu);
				 }
			}
		 }
		 ModelAndView modelAndView = new ModelAndView();
		 modelAndView.addObject("tree", JSONArray.fromObject(all.get(0)).toString().replaceAll("text", "name"));
	     modelAndView.setViewName("/menu/list");  
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
	
