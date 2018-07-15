package com.aiw.base.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aiw.base.entity.Menu;
import com.aiw.base.entity.Page;
import com.aiw.base.entity.SysResult;
import com.aiw.base.mapper.MenuMapper;

@Controller(value="ApiMenuController")
@RequestMapping(value="/api/menu")
public class MenuController extends BaseController<MenuMapper, Menu>{
   
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public SysResult list(@ModelAttribute Page page,@ModelAttribute Menu t){
    	return  list_p(page, t);
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public SysResult save(@RequestBody Menu t){
		return  save_p(t);
    }
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public SysResult update(@RequestBody Menu t) {
		return update_p(t);
	}
    
	@Override
    @RequestMapping(value = "/delete/{id}")
	@ResponseBody
    public SysResult delete(@PathVariable("id") Integer id){
		return delete_p(id);
    }
    
	@Override
    @RequestMapping(value = "/{id}")
    public SysResult get(@PathVariable("id") Integer id){
		return get_p(id);
    }
        
	@Override
    @RequestMapping(value = "")
    public SysResult get(){
    	return get_p();
    }
		
	
	@RequestMapping(value = "/tree")
	@ResponseBody
    public SysResult tree(){
	    
	    
	    
	    
    	
		 List<Menu>  list = mapper.select();
		 
		 Map<Integer,Menu> all = new HashMap<>();
		 Menu root = new Menu();
		 root.setName("根");
		 root.setChildren(new ArrayList<Menu>());
		 root.setDepth(0);
		 root.setId(0);
		 
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
		 //SysResult SysResult = new SysResult();
		 //SysResult.addObject("tree", JSONArray.fromObject(all.get(0)).toString().replaceAll("text", "name"));
	     //SysResult.setViewName("/menu/list");  
	     return SysResult.oK(root);  
    }
	
	@Override
	@RequestMapping(value = "/badd")
	public SysResult badd() {
		return get_p();
	}

	@Override
	@RequestMapping(value = "/bedit/{id}")
	public SysResult bupdate(@PathVariable("id") Integer id) {
		return get_p();
	}
	
}
	
