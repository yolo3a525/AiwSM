package com.aiw.shiro;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class AllcheckFilter extends AuthorizationFilter{

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object arg2) throws Exception {
	
		
		String path = getPathWithinApplication(request);
		System.out.println(path);
		
		Subject s = getSubject(request, response);
		Object o = s.getPrincipal();
		if(o == null) {
		    return false;
		}
		
		return true;
	}

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        if (this.isLoginRequest(request, response)) {
//            if (this.isLoginSubmission(request, response)) {
//                return this.executeLogin(request, response);
//            } else {
//                return true;
//            }
            return true;
        } else {
            try {
                request.getRequestDispatcher("/api/tokenexpired").forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            }
//            if(isAjaxRequest((HttpServletRequest)request)){
//                try {
//                    request.getRequestDispatcher("/api/tokenexpired").forward(request,response);
//                } catch (ServletException e) {
//                    e.printStackTrace();
//                }
//            }else{
//                this.saveRequestAndRedirectToLogin(request, response);
//            }
            return false;
        }
    }


    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }
	

}
