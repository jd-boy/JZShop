
package com.shop.interceptor;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shop.dao.LoginTicketDAO;
import com.shop.dao.ManagerDAO;
import com.shop.dao.MemberDAO;
import com.shop.model.HostHolder;
import com.shop.model.LoginTicket;
import com.shop.model.Manager;
import com.shop.model.Member;
import com.shop.serviceImpl.ManagerServiceImpl;

@Component
public class PassportInterceptor implements HandlerInterceptor {
	
	@Autowired
	private HostHolder hostHolder;

	@Autowired
	private LoginTicketDAO loginTicketDAO;
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private ManagerServiceImpl managerServiceImpl;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String ticket = null;
		if(request.getCookies() != null) {
			for(Cookie cookie : request.getCookies()) {
				if(cookie.getName().equals("ticket")) {
					ticket = cookie.getValue();
					break;
				}
			}
			if(ticket != null) {
				LoginTicket loginTicket = loginTicketDAO.getLoginTicketByTicket(ticket);
				if(loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() == 1) {
					return true;
				}
				
				if(loginTicket.getType() == 0) {
					Member member = memberDAO.getMemberByID(loginTicket.getUserid());
					hostHolder.setMember(member);
				} else {
					Manager manager = managerServiceImpl.getManagerById(loginTicket.getUserid());
					hostHolder.setManager(manager);
				}
				
			}
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if(modelAndView != null && hostHolder.getMember() != null) {
			modelAndView.addObject("member", hostHolder.getMember());
			request.setAttribute("username", hostHolder.getMember().getUserName());
			request.setAttribute("usertype", hostHolder.getMember().getType());
		}
		
		if(modelAndView != null && hostHolder.getManager() != null) {
			modelAndView.addObject("manager", hostHolder.getManager());
			request.setAttribute("mangername", hostHolder.getManager().getManager());
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
