package com.aiw.extend;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aiw.entity.RequestRecord;
import com.aiw.entity.User;
import com.aiw.mapper.RequestRecordMapper;

import net.sf.json.JSONObject;

public class RequestRecordAOPInterceptor {

	@Autowired 
	private RequestRecordMapper requestRecordMapper;
	
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable{
		ServletRequestAttributes servletContainer = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = servletContainer.getRequest();
	    
		RequestRecord rr = new RequestRecord();
		rr.setRequestURI(request.getRequestURI());
		
		rr.setRequestParameters(request.getRemoteAddr() + JSONObject.fromObject(request.getParameterMap()).toString());
		String userName = null;
		if(request.getSession().getAttribute("user") != null){
			userName = ((User)request.getSession().getAttribute("user")).getUsername();
		}else{
			userName =  "";
		}
		rr.setCreateUser(userName);
		rr.setLastUpdateUser(userName);
		//if(rr.getRequestURI().contains("add")||rr.getRequestURI().contains("update")||rr.getRequestURI().contains("dbback") || rr.getRequestURI().contains("save")) {
			requestRecordMapper.insert(rr);
		//}
		return pjp.proceed();
		
	}
	
}
