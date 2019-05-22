package com.shop.service;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.shop.model.Manager;

public interface ManagerService {
	
	public int addManager(Manager manager);//添加一个管理员账号
	
	public Manager getManager(String manager);//根据账号名查询某管理员账号的信息
	
	public Map<String, Object> login(JSONObject jsonParam, String randCheckCode);//登录校验
	
	public Manager getManagerById(int id);//根据账号id查询某管理员账号的信息
	
	public String setFreezeByUsername(String manager, int freeze);//根据账号名修改管理员账号是否冻结
	
}
