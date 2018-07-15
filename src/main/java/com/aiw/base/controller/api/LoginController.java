package com.aiw.base.controller.api;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aiw.base.entity.Menu;
import com.aiw.base.entity.Page;
import com.aiw.base.entity.Role;
import com.aiw.base.entity.SysResult;
import com.aiw.base.entity.User;
import com.aiw.base.mapper.MenuMapper;
import com.aiw.base.mapper.UserMapper;
import com.aiw.base.util.MD5;

@RestController(value="ApiLoginController")
public class LoginController extends BaseController<UserMapper, User>{
   
	@Autowired  
	private  HttpServletRequest request;  
	
	@Autowired
	MenuMapper menuMapper;
	
	
	
	
    
    
    @RequestMapping(value = "/api/unauthorizedUrl", method = RequestMethod.GET)
    public SysResult unauthorizedUrl(){
    	
		 SysResult SysResult = new SysResult(); 
	     return SysResult;  
    }
	
    /***
     * 注销
     */
    @RequestMapping(value="/api/logout")
	public SysResult logout()throws Exception{
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
        } catch (UnknownAccountException e) {
            e.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        SysResult SysResult = new SysResult(); 
	    return SysResult;  
	}
    
    @RequestMapping(value="/api/tokenexpired")
    public SysResult tokenexpired()throws Exception{
        return SysResult.build(SysResult.NO_LOGIN_ERROR, "token expired");
    }

	
	
	/**请求登录，验证用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/api/login")
	@ResponseBody
	public SysResult loginConfirm(@RequestBody Map<String,String> params)throws Exception{
		
	    String userName = params.get("userName");
	    String password = params.get("password");
	    
        Subject subject = SecurityUtils.getSubject();
        
        User user = new User();
        user.setUsername(userName);
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
        	  }
              request.getSession().setAttribute("menu", all.get(0).getChildren()); 
              request.getSession().setAttribute("user", listUser.get(0));
              
             } catch (UnknownAccountException e) {
                 e.printStackTrace();
             } catch (Exception e) {
                 e.printStackTrace();
             }
             
             Map<String,Object> map = new HashMap<>();
             map.put("info", request.getSession().getAttribute("user"));
             map.put("menu", request.getSession().getAttribute("menu"));
        	return SysResult.oK(map);
        }else {
        	return SysResult.nologin();
        }
	}


	@Override
	public SysResult list(Page page, User t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SysResult save(User t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SysResult update(User t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SysResult delete(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SysResult get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SysResult get() {
		// TODO Auto-generated method stub
		return null;
	}


    @Override
    public SysResult badd() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public SysResult bupdate(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }
    

}
	
