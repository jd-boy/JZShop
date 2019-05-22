package com.shop.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shop.aspect.LogAspect;
import com.shop.model.Member;
import com.shop.serviceImpl.MemberServiceImpl;
import com.shop.util.JSONStringUtil;

@Controller
public class RegisterController {
	
	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	@Autowired
	private MemberServiceImpl memberServiceImpl;
	
	@RequestMapping("/register")
	public String register() {
		return "register";
	}
	
	@RequestMapping(value = "/register_deal")
	@ResponseBody
	public String register_deal(@RequestBody JSONObject json, HttpServletResponse response) {
		
		Member member = new Member();
		member.setUserName(json.getString("username"));
		member.setPassword(json.getString("pwd").trim());
		member.setTrueName(json.getString("truename"));
		member.setTel(json.getString("tel"));
		member.setEmail(json.getString("email"));
		try {
			Map<String, Object> map = memberServiceImpl.register(member, json.getString("pwd2").trim());
			if(map.containsKey("ticket")) {
				Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
				cookie.setPath("/");
				response.addCookie(cookie);
				return JSONStringUtil.getJSONString(0, "注册成功");
			} else {
				return JSONStringUtil.getJSONString(1, map);
			}
		} catch(Exception e) {
			logger.error("注册异常" + e.getMessage());
			e.printStackTrace();
			return JSONStringUtil.getJSONString(1, "注册异常");
		}
	}
	
}
