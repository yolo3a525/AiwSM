package com.aiw.base.extend;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShiroCrossFilter implements Filter{

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain arg2)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse; 
        
        
        //String origin = (String) servletRequest.getRemoteHost()+":"+servletRequest.getRemotePort(); 
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8081"); 
        //response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE"); 
        //response.setHeader("Access-Control-Max-Age", "3600"); 
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept"); 
        response.setHeader("Access-Control-Allow-Credentials","true"); 
       
        
        if(servletRequest instanceof HttpServletRequest) {
            if(((HttpServletRequest)servletRequest).getMethod().equals("OPTIONS")){
                return;
            }
        }
        arg2.doFilter(servletRequest, servletResponse);
        
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
        
    }

}
