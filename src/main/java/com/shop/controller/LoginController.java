package com.shop.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shop.serviceImpl.GoodsServiceImpl;
import com.shop.serviceImpl.ManagerServiceImpl;
import com.shop.serviceImpl.MemberServiceImpl;
import com.shop.util.JSONStringUtil;

@Controller
public class LoginController<Account> {
	
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private MemberServiceImpl memberServiceImpl;
	
	@Autowired
	private ManagerServiceImpl managerServiceImpl;
	
	@Autowired
	private GoodsServiceImpl goodsServiceImpl;
	
	@RequestMapping("/login")
	public String login() {
		return "/login";
	}
	
	@RequestMapping("/login_check")
	@ResponseBody
	public String login_check(@RequestBody JSONObject jsonParam, HttpServletRequest request, HttpServletResponse response) {
		try {
			
			Map<String, Object> map = memberServiceImpl.login(jsonParam,
					request.getSession().getAttribute("randCheckCode").toString());
			if(map.containsKey("ticket")) {
				Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
				cookie.setPath("/");
				response.addCookie(cookie);
				
				return JSONStringUtil.getJSONString(0, "登录成功");
			} else {
				return JSONStringUtil.getJSONString(1, map);
			}
		} catch(Exception e) {
			logger.error("登录异常" + e.getMessage());
			e.printStackTrace();
			return JSONStringUtil.getJSONString(1, "登录异常");
		}
		
	}
	
	/*@RequestMapping(value="/kk",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String kk(@RequestBody JSONObject jsonParam) {
		System.out.println(jsonParam.get("username"));
		System.out.println(jsonParam.get("password"));
		System.out.println(jsonParam.get("checkCode"));
		//System.out.println(request.getParameter("data"));
		return JSONStringUtil.getJSONString(1, "登录成功");
	}*/
	
	@RequestMapping("/logout")
	public String logout(@CookieValue("ticket") String ticket,
						 @RequestParam("type") int type,
						 Model model) {
		memberServiceImpl.logout(ticket, type);
		
		model.addAttribute("hotGoods", goodsServiceImpl.getHotGoods());
		model.addAttribute("newGoods", goodsServiceImpl.getNewGoods());
		model.addAttribute("discountGoods", goodsServiceImpl.getDiscountGoods());
		return "redirect:index";
	}
	
	@RequestMapping("/manager_login")
	public String ManagerLogin() {
		return "/manager_login";
	}
	
	@RequestMapping("/managerLoginCheck")
	@ResponseBody
	public String ManagerLoginCheck(@RequestBody JSONObject jsonParam,
								HttpServletRequest request,
								HttpServletResponse response) {
		try {
			
			Map<String, Object> map = managerServiceImpl.login(jsonParam,
					request.getSession().getAttribute("randCheckCode").toString());
			if(map.containsKey("ticket")) {
				Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
				cookie.setPath("/");
				response.addCookie(cookie);
				
				return JSONStringUtil.getJSONString(0, "登录成功");
			} else {
				return JSONStringUtil.getJSONString(1, map);
			}
		} catch(Exception e) {
			logger.error("登录异常" + e.getMessage());
			e.printStackTrace();
			return JSONStringUtil.getJSONString(1, "登录异常");
		}
	}
}
