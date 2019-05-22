package com.shop.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.shop.dao.LoginTicketDAO;
import com.shop.dao.ManagerDAO;
import com.shop.model.LoginTicket;
import com.shop.model.Manager;
import com.shop.service.ManagerService;
import com.shop.util.JSONStringUtil;
import com.shop.util.MD5Util;

@Service
public class ManagerServiceImpl implements ManagerService {
	
	@Autowired
	private ManagerDAO managerDAO;

	@Autowired
	private LoginTicketDAO loginTicketDAO;
	
	@Override
	public int addManager(Manager manager) {
		
		manager.setSalt(UUID.randomUUID().toString().substring(0, 5));
		manager.setPassword(MD5Util.MD5(manager.getPassword()+manager.getSalt()));
		
		return managerDAO.addManager(manager);
	}

	@Override
	public Manager getManager(String manager) {
		return managerDAO.getManager(manager);
	}
	
	@Override
	public Manager getManagerById(int id) {
		return managerDAO.getManagerById(id);
	}

	@Override
	public Map<String, Object> login(JSONObject jsonParam, String randCheckCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String username = jsonParam.get("username").toString();
		String password = jsonParam.get("password").toString();
		String checkCode = jsonParam.get("checkCode").toString();
		
		if(StringUtils.isBlank(username)) {
			map.put("msg", "用户名不能为空");
			return map;
		}
		
		if(StringUtils.isBlank(password)) {
			map.put("msg", "密码不能为空");
			return map;
		}
		
		Manager manager = getManager(username);
		
		if(manager == null) {
			map.put("msg", "用户不存在");
			return map;
		}
		
		if(manager.getFreeze() == 1) {
			map.put("msg", "账号已被冻结");
			return map;
		}
		
		if(!MD5Util.MD5(password+manager.getSalt()).equals(manager.getPassword())) {
			map.put("msg", "密码错误");
			return map;
		}
		
		if(StringUtils.isBlank(checkCode)) {
			map.put("msg", "验证码不能为空");
			return map;
		}
		
		if(!checkCode.equalsIgnoreCase(randCheckCode)) {
			map.put("msg", "验证码错误");
			return map;
		}
		
		map.put("ticket", addLoginTicket(manager.getId()));
		
		return map;
	}
	
	private String addLoginTicket(int managerId) {
		LoginTicket loginTicket = new LoginTicket();
		
		loginTicket.setUserid(managerId);
		
		Date date = new Date();
		date.setTime(date.getTime() + 1000*3600*24);
		loginTicket.setExpired(date);
		loginTicket.setStatus(0);
		loginTicket.setType(1);
		loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
		loginTicketDAO.addLoginTicker(loginTicket);
		
		return loginTicket.getTicket();
	}

	@Override
	public String setFreezeByUsername(String manager, int freeze) {
		
		if(freeze == 0) {
			
			if(managerDAO.getFreeze(manager) == freeze) {
				return JSONStringUtil.getJSONString(1, "账号未被冻结");
			}
			
			if(managerDAO.setFreezeByUsername(manager, freeze) == 0) {
				return JSONStringUtil.getJSONString(1, "解除管理员账号冻结失败");
			} else {
				return JSONStringUtil.getJSONString(0, "解除管理员账号冻结成功");
			}
			
		} else {
			
			if(managerDAO.getFreeze(manager) == freeze) {
				return JSONStringUtil.getJSONString(1, "账号已被冻结");
			}
			
			if(managerDAO.setFreezeByUsername(manager, freeze) == 0) {
				return JSONStringUtil.getJSONString(1, "冻结管理员账号失败");
			} else {
				return JSONStringUtil.getJSONString(0, "冻结管理员账号成功");
			}
		}
		
		
		
	}
	
	

}
