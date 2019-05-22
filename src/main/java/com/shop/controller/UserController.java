package com.shop.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shop.async.EventModel;
import com.shop.async.EventType;
import com.shop.model.EntityType;
import com.shop.model.HostHolder;
import com.shop.model.Member;
import com.shop.serviceImpl.MailServiceImpl;
import com.shop.serviceImpl.MemberServiceImpl;
import com.shop.util.JSONStringUtil;
import com.shop.util.JedisAdapter;

@Controller
public class UserController {
	
	@Autowired
	private HostHolder hostHolder;
	
	@Autowired
	private MemberServiceImpl memberServiceImpl;
	
	@Autowired
	private MailServiceImpl mailServiceImpl;
	
	@Autowired
	private JedisAdapter jedisAdapter;
	
	@RequestMapping(path = {"/modifyMember"})
	public String modifyMember(HttpServletRequest request) {
		
		return "/modifyMember";
	}
	
	@RequestMapping(path = {"/saveMember"})
	@ResponseBody
	public String saveMemberInfo(@RequestBody JSONObject json) {
		Member member = new Member();
		member.setID(hostHolder.getMember().getID());
		member.setPassword(json.getString("pwd"));
		member.setTel(json.getString("tel"));
		member.setEmail(json.getString("email"));
		member.setPostcode(json.getString("email"));
		member.setAddress(json.getString("address"));
		
		if(memberServiceImpl.setInfo(member) == 0) {
			return JSONStringUtil.getJSONString(1, "修改个人信息失败");
		} else {
			return JSONStringUtil.getJSONString(0, "修改个人信息成功");
		}
	}
	
	@RequestMapping(path = {"/sendCheckCode"})
	@ResponseBody
	public String sendCheckCode() {
		
		mailServiceImpl.alterInfo();
		
		return jedisAdapter.get("checkcode"+hostHolder.getMember().getID());
	}
	
}
