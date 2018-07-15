package com.aiw.api.base.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aiw.entity.Page;
import com.aiw.entity.Role;
import com.aiw.entity.SysResult;
import com.aiw.entity.User;
import com.aiw.mapper.UserMapper;
import com.aiw.util.MD5;

@RestController(value="ApiUserController")
@RequestMapping(value="/api/user")
public class UserController extends BaseController<UserMapper, User>{
    
    
    
    
	@SuppressWarnings("unchecked")
    @Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public SysResult list(@ModelAttribute Page page,@ModelAttribute User t){
	    
	    
	    
	    SysResult sysResult = list_p(page, t);
	    List<Integer> ids = new ArrayList<>();
	    for (User user : (List<User>)((Page)sysResult.getData()).getList()) {
	        ids.add(user.getId());
        }
	    
	    List<Role> roleList = mapper.queryRoleByUserIds(ids);
	    
	    for (User user : list) {
	        user.setRoleList(new ArrayList<Role>());
	        for (Role role : roleList) {
	            if(user.getId().equals(role.getUserId())) {
	                user.getRoleList().add(role);
	            }
	        }
        }
	    
    	return  sysResult;
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public SysResult save(@RequestBody User t){
	    t.setPassword(MD5.md5(t.getPassword()));
		return  save_p(t);
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public SysResult update(@RequestBody User t) {
		
		if(t.getPassword() != null && !t.getPassword().equals("")) {
			String newstr= new String(MD5.md5(t.getPassword()));
			t.setPassword(newstr);
		}
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
	
	@RequestMapping(value = "/roleTree/{id}", method = RequestMethod.GET)
    public SysResult qxtree(@PathVariable("id") Integer id){
		 Integer user_id = id;
		 List<Role>  list = mapper.queryRoleByUserId(user_id);
    	 for (Role role : list) {
    		 if(role.getUserId() != null) {
    			 role.setChecked(true);
    		 }
		 }
		 SysResult SysResult = new SysResult();
		 SysResult.setData(list);
		 //SysResult.addObject("list", JSONArray.fromObject(list).toString());
		 //SysResult.addObject("user_id", user_id);
	     //SysResult.setViewName("/user/role_tree");  
	     return SysResult;  
    }
	
	
	@RequestMapping(value = "/saveRole", method = RequestMethod.POST)
	@ResponseBody
    public void saveRole(@RequestBody Map<String,String> params){
	    
	        
	    Integer user_id = Integer.parseInt(params.get("userId"));
	    String roles_ids = (String)params.get("roleIds");
	    
		mapper.deleteUserPrivilegeByUserId(user_id);
		if(roles_ids != null && roles_ids.trim().length() > 0) {
			String[] roleIdsArray = roles_ids.split(",");
			if(roleIdsArray != null && roleIdsArray.length > 0) {
				Map<String, Integer> map = null;
				for (String string : roleIdsArray) {
					map = new HashMap<String, Integer>();
					map.put("user_id", user_id);
					map.put("role_id", java.lang.Integer.parseInt(string));
					mapper.addUserRole(map);
					
				}
			}
		}
    }
	
	public static void main(String[] args) {
		    //加密后的字符串
			String newstr= new String(MD5.md5("3333"));
			System.out.println(newstr);
		
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
	
