package com.shop.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shop.model.HostHolder;
import com.shop.serviceImpl.CartServiceImpl;

@Component
public class CartInterceptor implements HandlerInterceptor {
	
	@Autowired
	private HostHolder hostHolder;
	
	@Autowired
	private CartServiceImpl cartServiceImpl;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(hostHolder.getMember() == null) {
			 response.sendRedirect("/login");
			 return false;
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if(hostHolder.getMember() != null) {
			request.setAttribute("username", hostHolder.getMember().getUserName());
			request.setAttribute("usertype", hostHolder.getMember().getType());
		}
		request.setAttribute("cartList", cartServiceImpl.getCartsByUserId(hostHolder.getMember().getID()));
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		hostHolder.clear();
		
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
