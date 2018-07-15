package com.aiw.controller.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aiw.entity.BaseJsonBean;
import com.aiw.entity.Menu;
import com.aiw.entity.Page;
import com.aiw.entity.Role;
import com.aiw.entity.User;
import com.aiw.mapper.MenuMapper;
import com.aiw.mapper.UserMapper;
import com.aiw.util.MD5;

@Controller
@Scope("prototype")
public class LoginController extends BaseController<UserMapper, User>{
   
	@Autowired  
	private  HttpServletRequest request;  
	
	@Autowired
	MenuMapper menuMapper;
	
     public static boolean isAjaxRequest(HttpServletRequest request) {
         String requestedWith = request.getHeader("x-requested-with");
         if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
             return true;
         } else {
             return false;
         }
     }
	
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(){
        
       
    	
		 ModelAndView modelAndView = new ModelAndView(); 
	     modelAndView.setViewName("/login");  
	     return modelAndView;  
    }
    
    
    @RequestMapping(value = "/unauthorizedUrl", method = RequestMethod.GET)
    public ModelAndView unauthorizedUrl(){
    	
		 ModelAndView modelAndView = new ModelAndView(); 
	     modelAndView.setViewName("/unauthorized");  
	     return modelAndView;  
    }
	
    /***
     * 注销
     */
    @RequestMapping(value="/logout")
	public ModelAndView logout()throws Exception{
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
        } catch (UnknownAccountException e) {
            e.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView(); 
	    modelAndView.setViewName("/login");  
	    return modelAndView;  
	}
	
	
	/**请求登录，验证用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/loginConfirm")
	@ResponseBody
	public BaseJsonBean loginConfirm(@RequestParam String username,@RequestParam String password)throws Exception{
		

        Subject subject = SecurityUtils.getSubject();
        
        User user = new User();
        user.setUsername(username);
        List<User>  listUser =  mapper.select(user);
        if(listUser != null && listUser.size() == 1 && listUser.get(0).getPassword().equals(MD5.md5(password))) {
        	UsernamePasswordToken  token = new UsernamePasswordToken(listUser.get(0).getId()+"", password);
             //调用安全管理器
             try {
                 subject.login(token);
                 //从subject中获取保存在安全管理员中的user对象
                 //User user = (User) subject.getPrincipal();
                 //将用户保存在session中,因为自定义拦截器的原因(如果没有自定义拦截器, 将用户存放在session域中的这个步骤可以省略)
                 //request.getSession().setAttribute("loginUser", user);
                 
              //读入菜单
              List<Menu>  list = menuMapper.selectByUserId(listUser.get(0).getId());
        		 
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
        		 
        	  List<Role>  roleList = mapper.queryCheckedRoleByUserId(listUser.get(0).getId());
        	  if(roleList != null && roleList.size() == 1 && roleList.get(0).getName().contains("供应商")){
        	      request.getSession().setAttribute("gys", true); 
        	      bj.setMsg("gys");
        	  }
              request.getSession().setAttribute("menu", all.get(0).getChildren()); 
              request.getSession().setAttribute("user", listUser.get(0));
              
             } catch (UnknownAccountException e) {
                 e.printStackTrace();
             } catch (Exception e) {
                 e.printStackTrace();
             }
             
        	return bj;
        }else {
        	return null;
        }
	}


	@Override
	public ModelAndView list(Page page, User t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public User save(User t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public BaseJsonBean update(User t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer delete(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ModelAndView get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ModelAndView get() {
		// TODO Auto-generated method stub
		return null;
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
	
