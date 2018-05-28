package com.aiw.extend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.aiw.bdzb.entity.Tenant;
import com.aiw.bdzb.mapper.TenantMapper;
import com.aiw.entity.BaseJsonBean;

import net.sf.json.JSONObject;

public class AppLoginHandlerIntercepter implements HandlerInterceptor {

	@Autowired
	TenantMapper tenantMapper;
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1, Object arg2) throws Exception {
		HttpSession session = request.getSession();
		String phone = (String) session.getAttribute("phone");
		
		
		
		
		
		
		if (phone != null) {
			// 登陆成功的用户
			return true;
		} else {
			
			/**
			 * 带有手机号码与验证码的用户可以自动登录
			 */
			
			String lphone = request.getParameter("lphone");
			String lvcode = request.getParameter("lvcode");
			
			if(lphone != null && lvcode != null && !lphone.trim().equals("") && !lvcode.trim().equals("")) {
				Tenant tenantDB = tenantMapper.getByPhone(lphone);
				if(tenantDB != null ) {
					if(tenantDB.getAccesskey().equals(lvcode)) {
						request.getSession().setAttribute("phone",lphone);
						request.getSession().setAttribute("id",tenantDB.getId());
						
						if(tenantDB.getVipDeadLine().getTime() >= System.currentTimeMillis() && tenantDB.getId() > 0) {
						    request.getSession().setAttribute("isVip",true);
						}else {
						    request.getSession().setAttribute("isVip",false);
						}
						System.out.println("重新登录成功");
						return true;//说明可以自动登录成功.
					}
				}
			}else {//认为可能是不需要登录的请求.
			    return true;
			}
			
			BaseJsonBean baseJsonBean = new BaseJsonBean();
			baseJsonBean.setMsg("not login");
			returnJson(arg1, JSONObject.fromObject(baseJsonBean).toString());

			return false;
		}
	}

	private void returnJson(HttpServletResponse response, String json) throws Exception {
		PrintWriter writer = null;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(200);
		try {
			writer = response.getWriter();
			writer.print(json);

		} catch (IOException e) {
		} finally {
			if (writer != null)
				writer.close();
		}
	}

}
