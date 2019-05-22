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
import com.shop.dao.MemberDAO;
import com.shop.model.HostHolder;
import com.shop.model.LoginTicket;
import com.shop.model.Member;
import com.shop.service.MemberService;
import com.shop.util.JSONStringUtil;
import com.shop.util.MD5Util;

@Service
public class MemberServiceImpl implements MemberService {
	
	public static int REMOVE_FREEZE = 0;
	public static int ADD_FREEZE = 1;
	public static int BUYER = 0;
	public static int SELLER = 1;
	
	@Autowired
	private HostHolder hostHolder;
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private LoginTicketDAO loginTicketDAO;
	
	public Member getMemberByID(int id) {
		return memberDAO.getMemberByID(id);
	}
	
	public int getIdByUserName(String userName) {
		return memberDAO.getIdByUserName(userName);
	}

	@Override
	public Member getMemberByUserName(String userName) {
		return memberDAO.getMemberByUserName(userName);
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
		
		Member member = memberDAO.getMemberByUserName(username);
		if(member == null) {
			map.put("msg", "用户不存在");
			return map;
		}
		
		if(member.getFreeze() == 1) {
			map.put("msg", "账号已被冻结");
			return map;
		}
		
		if(!MD5Util.MD5(password+member.getSalt()).equals(member.getPassword())) {
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
		
		map.put("ticket", addLoginTicket(member.getID()));
		
		return map;
	}
	
	@Override
	public void logout(String ticket, int type) {
		loginTicketDAO.updateStatus(ticket, 1, type);
	}
	
	@Override
	public Map<String, Object> register(Member member, String pwd2) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(StringUtils.isBlank(member.getUserName())) {
			map.put("msg", "用户名不能为空");
			return map;
		}
		
		if(StringUtils.isBlank(member.getPassword())) {
			map.put("msg", "密码不能为空");
			return map;
		}
		
		if(StringUtils.isBlank(pwd2)) {
			map.put("msg", "重复密码不能为空");
			return map;
		}
		
		if(!member.getPassword().equals(pwd2)) {
			map.put("msg", "两次密码不一致");
			return map;
		}
		
		if(member.getPassword().length() < 8 || member.getPassword().length() > 16) {
			map.put("msg", "密码长度需大于等于8，小于等于16");
			return map;
		}
		
		Member checkMember = memberDAO.getMemberByUserName(member.getUserName());
		
		if(checkMember != null) {
			map.put("msg", "用户名已经被注册");
			return map;
		}
		
		member.setSalt(UUID.randomUUID().toString().substring(0, 5));
		member.setPassword(MD5Util.MD5(member.getPassword()+member.getSalt()));
		
		memberDAO.addMember(member);
		map.put("ticket", addLoginTicket(getIdByUserName(member.getUserName())));
		
		return map;
	}
	
	private String addLoginTicket(int memberID) {
		LoginTicket loginTicket = new LoginTicket();
		
		loginTicket.setUserid(memberID);
		
		Date date = new Date();
		date.setTime(date.getTime() + 1000*3600*24);
		loginTicket.setExpired(date);
		loginTicket.setStatus(0);
		loginTicket.setType(0);
		loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
		loginTicketDAO.addLoginTicker(loginTicket);
		
		return loginTicket.getTicket();
	}

	@Override
	public int getType(int id) {
		return memberDAO.getType(id);
	}

	@Override
	public int setType(int id, int type) {
		return memberDAO.setType(id, type);
	}

	@Override
	public int setInfo(Member member) {
		if(!StringUtils.isBlank(member.getPassword())) {
			String salt = UUID.randomUUID().toString().substring(0, 5);
			String password = MD5Util.MD5(member.getPassword() + salt);
			setPassword(member.getID(), password, salt);
		}
		return memberDAO.setInfo(member);
	}

	@Override
	public int setPassword(int id, String password, String salt) {
		return memberDAO.setPassword(id, password, salt);
	}

	@Override
	public String setFreeze(String userName, int freeze, int type) {
		
		if(memberDAO.getMemberByUserName(userName) == null) {
			return JSONStringUtil.getJSONString(1, "用户不存在");
		}
		
		if(type == ADD_FREEZE) {
			
			if(memberDAO.getFreeze(userName) == 1) {
				return JSONStringUtil.getJSONString(1, "账号已经被冻结");
			}
			
			if(memberDAO.setFreeze(userName, freeze) == 0) {
				return JSONStringUtil.getJSONString(1, "冻结账号失败");
			} else {
				return JSONStringUtil.getJSONString(0, "冻结账号成功");
			}
		} else {
			
			if(memberDAO.getFreeze(userName) == 0) {
				return JSONStringUtil.getJSONString(1, "账号未被冻结");
			}
			
			if(memberDAO.setFreeze(userName, freeze) == 0) {
				return JSONStringUtil.getJSONString(1, "解除账号冻结失败");
			} else {
				return JSONStringUtil.getJSONString(0, "解除账号冻结成功");
			}
		}
	}

	@Override
	public String alertType(String userName, int type) {
		
		Member member = memberDAO.getMemberByUserName(userName);
		
		if(member == null) {
			return JSONStringUtil.getJSONString(1, "用户不存在");
		}
		
		if(member.getFreeze() == 1) {
			return JSONStringUtil.getJSONString(1, "该账号已被冻结");
		}
		
		if(member.getType() == SELLER) {
			return JSONStringUtil.getJSONString(1, "注册店铺失败\n该账号已是商家！！");
		}
		
		if(memberDAO.alertType(userName, type) == 0) {
			if(type == BUYER) {
				return JSONStringUtil.getJSONString(1, "关闭店铺失败");
			} else {
				return JSONStringUtil.getJSONString(1, "注册店铺失败");
			}
		} else {
			if(type == BUYER) {
				return JSONStringUtil.getJSONString(0, "关闭店铺成功");
			} else {
				return JSONStringUtil.getJSONString(0, "注册店铺成功");
			}
		}
	}

	@Override
	public String getHeadById(int id) {
		return memberDAO.getHeadById(id);
	}

	@Override
	public Member getNEByGoodsId(int goodsId) {
		return memberDAO.getNEByGoodsId(goodsId);
	}
	
}
