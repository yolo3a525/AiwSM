package com.aiw.controller.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aiw.entity.BaseJsonBean;
import com.aiw.entity.Page;
import com.aiw.entity.Privilege;
import com.aiw.entity.Role;
import com.aiw.mapper.RoleMapper;

import net.sf.json.JSONArray;

@Controller
@Scope("prototype")
@RequestMapping(value="/role")
public class RoleController extends BaseController<RoleMapper, Role>{
   
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute Role t){
    	return  list_p(page, t);
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public Role save(@ModelAttribute Role t){
		return  save_p(t);
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(Role t) {
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
	@RequestMapping(value = "/privilegeTree/{id}", method = RequestMethod.GET)
    public ModelAndView privilegeTree(@PathVariable("id") Integer id){
		Integer role_id = id;
		 List<Privilege>  list = mapper.queryPrivilegeByRoleId(role_id);
    	 
    	 List<Privilege> list1 = new ArrayList<>();
    	 for (Privilege privilege : list) {
    		 if(privilege.getRoleId() != null) {
    			 privilege.setChecked(true);
    		 }
    		 if(privilege.getPid() == null || privilege.getPid() == 0) {
    			 list1.add(privilege);
    		 }
		}
    	 
    	 for (Privilege privilege : list) {
    		 if(privilege.getPid() != null && privilege.getPid() != 0) {
	    		for (Privilege privilege1 : list1) {
					
	    			 if(privilege.getPid() == privilege1.getId()) {
	    				 if(privilege1.getSubList() == null) {
	    					 privilege1.setSubList(new ArrayList<Privilege>());
	    				 }
	    				 privilege1.getSubList().add(privilege);
	    			 }
				}
    		 }
			
		}
    	 
    	 
		 ModelAndView modelAndView = new ModelAndView();
		 
		 modelAndView.addObject("list", JSONArray.fromObject(list1).toString().replace("subList", "nodes"));
		 modelAndView.addObject("role_id", role_id);
	     modelAndView.setViewName("/role/privilege_tree");  
	     return modelAndView;  
    }
	
	@Autowired
	MemoryConstrainedCacheManager memoryConstrainedCacheManager;
	
	@RequestMapping(value = "/savePrivilege", method = RequestMethod.POST)
	@ResponseBody
    public void savePrivilege(@RequestParam("role_id") Integer role_id,@RequestParam("privilege_ids") String privilege_ids){
		
		
		try {
			memoryConstrainedCacheManager.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mapper.deleteRolePrivilegeByRoleId(role_id);
		if(privilege_ids == null || privilege_ids.trim().length() < 1) {
			return;
		}
		String[] privilegeIdsArray = privilege_ids.split(",");
		Map<String, Integer> map = null;
		for (String string : privilegeIdsArray) {
			map = new HashMap<String, Integer>();
			map.put("role_id", role_id);
			map.put("privilege_id", java.lang.Integer.parseInt(string));
			mapper.addRolePrivilege(map);
			
		}
    	 
		
    }
	
	@Override
	@RequestMapping(value = "/badd")
	public ModelAndView badd() {
		return get_p();
	}

	@Override
	@RequestMapping(value = "/bedit/{id}")
	public ModelAndView bupdate(@PathVariable("id") Integer id) {
		return get_p(id);
	}
}
	
