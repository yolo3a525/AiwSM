package com.aiw.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class AllcheckFilter extends AuthorizationFilter{

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object arg2) throws Exception {
	
		
		String path = getPathWithinApplication(request);
		System.out.println(path);
		
		Subject s = getSubject(request, response);
		Object o = s.getPrincipal();
		
		
		return true;
	}

}
