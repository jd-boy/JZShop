package com.shop.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shop.model.HostHolder;

@Component
public class ModifyMemberInterceptor implements HandlerInterceptor {
	
	@Autowired
	private HostHolder hostHolder;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(hostHolder.getMember() == null) {
			response.sendRedirect("/login");
			return false;
		}
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if(hostHolder.getMember().getEmail() == null) {
			request.setAttribute("email", "");
		} else {
			request.setAttribute("email", hostHolder.getMember().getEmail());
		}
		if(hostHolder.getMember().getTel() == null) {
			request.setAttribute("tel", "");
		} else {
			request.setAttribute("tel", hostHolder.getMember().getTel());
		}
		if(hostHolder.getMember().getPostcode() == null) {
			request.setAttribute("postal", "");
		} else {
			request.setAttribute("postal", hostHolder.getMember().getPostcode());
		}
		if(hostHolder.getMember().getAddress() == null) {
			request.setAttribute("address", "");
		} else {
			request.setAttribute("address", hostHolder.getMember().getAddress());
		}
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		hostHolder.clear();
		
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
}
