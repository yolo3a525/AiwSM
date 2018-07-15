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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aiw.base.entity.BaseJsonBean;
import com.aiw.base.entity.Page;
import com.aiw.base.entity.Role;
import com.aiw.base.entity.User;
import com.aiw.base.mapper.UserMapper;
import com.aiw.base.util.MD5;

import net.sf.json.JSONArray;

@Controller
@Scope("prototype")
@RequestMapping(value="/user")
public class UserController extends BaseController<UserMapper, User>{
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute User t){
	    
	    
	    
	    ModelAndView modelAndView = list_p(page, t);
	    List<Integer> ids = new ArrayList<>();
	    for (User user : list) {
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
	    
	    
    	return  modelAndView;
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public User save(@ModelAttribute User t){
	    t.setPassword(MD5.md5(t.getPassword()));
		return  save_p(t);
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(User t) {
		
		if(t.getPassword() != null && !t.getPassword().equals("")) {
			String newstr= new String(MD5.md5(t.getPassword()));
			t.setPassword(newstr);
		}
		return update_p(t);
	}
    
	@Override
    @RequestMapping(value = "/delete/{id}")
	@ResponseBody
    public Integer delete(@PathVariable("id") Integer id){
		if(id == 111) {// admin 不能删除
			return 0;
		}
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
	
	@RequestMapping(value = "/roleTree/{id}", method = RequestMethod.GET)
    public ModelAndView qxtree(@PathVariable("id") Integer id){
		 Integer user_id = id;
		 List<Role>  list = mapper.queryRoleByUserId(user_id);
    	 for (Role role : list) {
    		 if(role.getUserId() != null) {
    			 role.setChecked(true);
    		 }
		 }
		 ModelAndView modelAndView = new ModelAndView();
		 modelAndView.addObject("list", JSONArray.fromObject(list).toString());
		 modelAndView.addObject("user_id", user_id);
	     modelAndView.setViewName("/user/role_tree");  
	     return modelAndView;  
    }
	
	
	@RequestMapping(value = "/saveRole", method = RequestMethod.POST)
	@ResponseBody
    public void saveRole(@RequestParam("user_id") Integer user_id,@RequestParam("roles_ids") String roles_ids){
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
	public ModelAndView badd() {
		return get_p();
	}

	@Override
	@RequestMapping(value = "/bedit/{id}")
	public ModelAndView bupdate(@PathVariable("id") Integer id) {
		return get_p(id);
	}
}
	
