package com.aiw.base.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aiw.base.entity.Page;
import com.aiw.base.entity.Privilege;
import com.aiw.base.entity.Role;
import com.aiw.base.entity.SysResult;
import com.aiw.base.mapper.RoleMapper;

@RestController(value="ApiRoleController")
@RequestMapping(value="/api/role")
public class RoleController extends BaseController<RoleMapper, Role>{
   
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public SysResult list(@ModelAttribute Page page,@ModelAttribute Role t){
    	return  list_p(page, t);
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public SysResult save(@RequestBody Role t){
		return  save_p(t);
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public SysResult update(@RequestBody Role t) {
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
	@RequestMapping(value = "/privilegeTree/{id}", method = RequestMethod.GET)
    public SysResult privilegeTree(@PathVariable("id") Integer id){
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
    	 
    	 
		 SysResult SysResult = new SysResult();
		 
		 //SysResult.addObject("list", JSONArray.fromObject(list1).toString().replace("subList", "nodes"));
		 //SysResult.addObject("role_id", role_id);
	     //SysResult.setViewName("/role/privilege_tree");  
		 SysResult.setData(list);
	     return SysResult;  
    }
	
	@Autowired
	MemoryConstrainedCacheManager memoryConstrainedCacheManager;
	
	@RequestMapping(value = "/savePrivilege", method = RequestMethod.POST)
	@ResponseBody
    public void savePrivilege(@RequestBody Map<String,String> params ){
		
	    Integer role_id = Integer.parseInt(params.get("roleId"));
        String privilege_ids = (String)params.get("privilegeIds");
        
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
	public SysResult badd() {
		return get_p();
	}

	@Override
	@RequestMapping(value = "/bedit/{id}")
	public SysResult bupdate(@PathVariable("id") Integer id) {
		return get_p(id);
	}
}
	
