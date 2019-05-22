package com.shop.service;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.shop.model.Member;

public interface MemberService {
	
	public int getIdByUserName(String userName);//根据账号名查询账号id
	
	public Member getMemberByID(int id);//根据id查询用户信息
	
	public Member getMemberByUserName(String userName);//根据用户名查询用户信息
	
	public Map<String, Object> login(JSONObject jsonParam, String randCheckCode);//登录校验
	
	public void logout(String ticket, int type);//退出登录
	
	public Map<String, Object> register(Member member, String pwd2);//注册校验
	
	public int getType(int id);//根据用户ID获取账号类型
	
	public int setType(int id, int type);//根据用户ID修改账号类型
	
	public int setInfo(Member member);//修改账号基本信息
	
	public int setPassword(int id, String password, String salt);//修改账号密码
	
	public String setFreeze(String userName, int freeze, int type);//修改账号是否冻结

	public String alertType(String userName, int type);//根据账号名修改账号账号类别
	
	public String getHeadById(int id);//根据用户id获取头像
	
	//根据商品id获取用户名，邮箱
	public Member getNEByGoodsId(int goodsId);
}
