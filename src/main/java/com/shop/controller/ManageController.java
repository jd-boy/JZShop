package com.shop.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shop.model.HostHolder;
import com.shop.model.Manager;
import com.shop.model.Subtype;
import com.shop.model.Supertype;
import com.shop.serviceImpl.GoodsTypeServiceImpl;
import com.shop.serviceImpl.ManagerServiceImpl;
import com.shop.serviceImpl.MemberServiceImpl;
import com.shop.util.JSONStringUtil;

@Controller
public class ManageController {
	
	@Autowired
	private GoodsTypeServiceImpl goodsTypeServiceImpl;
	
	@Autowired
	private MemberServiceImpl memberServiceImpl;
	
	@Autowired
	private ManagerServiceImpl managerServiceImpl;
	
	@Autowired
	private HostHolder hostHolder;
	
	@RequestMapping(value = "/manage")
	public String manage(Model model) {
		
		if(hostHolder.getManager() == null) {
			return "redirect:/manager_login";
		}
		
		
		Map<Integer, String> smallTypes = new HashMap<Integer, String>();
		Map<Integer, String> bigTypes = new HashMap<Integer, String>();
		
		for(Subtype subtype : goodsTypeServiceImpl.getAllSubtype()) {
			smallTypes.put(subtype.getID(), subtype.getTypeName());
		}
		for(Supertype supertype : goodsTypeServiceImpl.getAllSupertype()) {
			bigTypes.put(supertype.getID(), supertype.getTypeName());
		}
		
		model.addAttribute("smallType", smallTypes);
		model.addAttribute("bigType", bigTypes);
		model.addAttribute("managerType", hostHolder.getManager().getType());
		
		return "/manage";
	}
	
	@RequestMapping(value = "/freezeMember")
	@ResponseBody
	public String freezeMember(@RequestBody JSONObject json) {
		
		return memberServiceImpl.setFreeze(json.getString("username"),1,MemberServiceImpl.ADD_FREEZE);
		
	}
	
	@RequestMapping(value = "/removeMemberFreeze")
	@ResponseBody
	public String removeMemberFreeze(@RequestBody JSONObject json) {
		
		return memberServiceImpl.setFreeze(json.getString("username"),0,MemberServiceImpl.REMOVE_FREEZE);
		
	}
	
	@RequestMapping(value = "/addSupertype")
	@ResponseBody
	public String addSupertype(@RequestBody JSONObject json) {
		
		return goodsTypeServiceImpl.addSupertype(json.getString("supertype"));
		
	}
	
	@RequestMapping(value = "/addSubtype")
	@ResponseBody
	public String addSubtype(@RequestBody JSONObject json) {
		
		return goodsTypeServiceImpl.addSubtype(json.getInteger("supertype"), json.getString("subtype"));
		
	}
	
	@RequestMapping(value = "/alterSubtypeName")
	@ResponseBody
	public String alterSubtypeName(@RequestBody JSONObject json) {
		
		return goodsTypeServiceImpl.setTypeName(json.getInteger("subtype"), json.getString("subtypename"));
		
	}
	
	@RequestMapping(value = "/alterSupertypeName")
	@ResponseBody
	public String alterSupertypeName(@RequestBody JSONObject json) {
		
		return goodsTypeServiceImpl.alertSupertypeName(json.getInteger("supertype"), json.getString("supertypename"));
		
	}
	
	@RequestMapping(value = "/addSeller")
	@ResponseBody
	public String addSeller(@RequestBody JSONObject json) {
		
		return memberServiceImpl.alertType(json.getString("username"), MemberServiceImpl.SELLER);
		
	}
	
	@RequestMapping(value = "/delSupertype")
	@ResponseBody
	public String delSupertype(@RequestBody JSONObject json) {
		
		if(goodsTypeServiceImpl.delSupertype(json.getInteger("supertype")) == 0) {
			return JSONStringUtil.getJSONString(1, "删除类别失败");
		} else {
			return JSONStringUtil.getJSONString(0, "删除类别成功");
		}
	}
	
	@RequestMapping(value = "/delSubtype")
	@ResponseBody
	public String delSubtype(@RequestBody JSONObject json) {
		
		if(goodsTypeServiceImpl.delSubtype(json.getInteger("subtype")) == 0) {
			return JSONStringUtil.getJSONString(1, "删除类别失败");
		} else {
			return JSONStringUtil.getJSONString(0, "删除类别成功");
		}
	}
	
	@RequestMapping(value = "/addManager")
	@ResponseBody
	public String addManager(@RequestBody JSONObject json) {
		
		String pwd = json.getString("pwd").trim();
		String user = json.getString("admin").trim();
		
		if(hostHolder.getManager().getType() == 0) {
			return JSONStringUtil.getJSONString(1, "添加失败！！\n添加管理员账号需要超级管理员权限");
		}
		
		if(managerServiceImpl.getManager(user) != null) {
			return JSONStringUtil.getJSONString(1, "用户名已存在，请重新填写！！");
		}
		
		if(pwd.length() < 8 || pwd.length() > 16) {
			return JSONStringUtil.getJSONString(1, "密码长度需大于等于8，小于等于16");
		}
		
		Manager manager = new Manager();
		manager.setManager(user);
		manager.setPassword(pwd);
		manager.setCreatedate(new Date());
		if(managerServiceImpl.addManager(manager) == 0) {
			return JSONStringUtil.getJSONString(1, "添加管理员失败");
		} else {
			return JSONStringUtil.getJSONString(0, "添加管理员成功");
		}
	}
	
	@RequestMapping(value = "/freezeManager")
	@ResponseBody
	public String freezeManager(@RequestBody JSONObject json) {
		if(hostHolder.getManager().getType() == 0) {
			return JSONStringUtil.getJSONString(1, "冻结失败！！\n冻结管理员账号需要超级管理员权限");
		}
		return managerServiceImpl.setFreezeByUsername(json.getString("admin"),1);
		
	}
	
	@RequestMapping(value = "/removeManagerFreeze")
	@ResponseBody
	public String removeManagerFreeze(@RequestBody JSONObject json) {
		
		if(hostHolder.getManager().getType() == 0) {
			return JSONStringUtil.getJSONString(1, "解除冻结失败！！\n解除管理员账号冻结需要超级管理员权限");
		}
		
		return managerServiceImpl.setFreezeByUsername(json.getString("admin"),0);
		
	}
}
