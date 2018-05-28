package com.aiw.shiro;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.aiw.entity.Privilege;
import com.aiw.entity.Role;
import com.aiw.mapper.RoleMapper;
import com.aiw.mapper.UserMapper;


/**
 * @author fh
 *  2015-3-6
 */
public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	protected UserMapper userMapper;
	
	@Autowired
	protected RoleMapper mapper;
	
	/*
	 * 登录信息和用户验证信息验证(non-Javadoc)
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		 String  username = (String)token.getPrincipal();  	//得到id
		 String password = new String((char[])token.getCredentials()); 	//得到密码
		 
	     if(null != username && null != password){
	    	 return new SimpleAuthenticationInfo(username,password, getName());
	     }else{
	    	 return null;
	     }
	}
	
	/*
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法(non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
			Integer id = Integer.parseInt((String)pc.getPrimaryPrincipal());
	        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();  
	        Set<String> set=new HashSet<String>();
	        List<Role>  roleList = userMapper.queryRoleByUserId(id);
	       
	        if(roleList != null && roleList.size() > 0) {
	        	 for (Role role : roleList) {
	        		if(role.getUserId() != null) {
	        			List<Privilege>  list = mapper.queryPrivilegeByRoleId(role.getId());
		 	        	if(list != null) {
		 	        		for (Privilege privilege : list) {
		 	        			if(privilege.getRoleId() != null) {
		 	        				set.add(privilege.getCode());
		 	        			}
		 					}
		 	        	}
	        		}
	 			}
	        }
	        authorizationInfo.setStringPermissions(set);
	        return authorizationInfo;  
	}

}
