package com.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shop.model.HostHolder;
import com.shop.serviceImpl.CartServiceImpl;
import com.shop.util.JSONStringUtil;

@Controller
public class CartController {
	
	@Autowired
	private HostHolder hostHolder;
	
	@Autowired
	private CartServiceImpl cartServiceImpl;
	
	@RequestMapping(value = "/cart_add")
	@ResponseBody
	public String addToCart(@RequestBody JSONObject json) {
		if(hostHolder.getMember() == null) {
			return JSONStringUtil.getJSONString(2, "未登陆");
		}
		
		cartServiceImpl.addCart(hostHolder.getMember().getID(), json.getInteger("goodsId"), json.getInteger("num"));
		
		return JSONStringUtil.getJSONString(0, "添加到购物车成功！！");
	}
	
	@RequestMapping(value = "/cart_see")
	public String seeToCart(Model model) {
		
		if(hostHolder.getMember() == null) {
			return "redirect:/login";
		}

		return "/cart_see";
	}
	
	@RequestMapping(value = "/cartDel")
	public String cartDelGoods(@RequestParam("id") int goodsId) {
		
		if(hostHolder.getMember() == null) {
			return "redirect:/login";
		}
		
		cartServiceImpl.delGoods(hostHolder.getMember().getID(), goodsId);
		
		return "redirect:cart_see";
	}
	
	@RequestMapping(value = "/cartLess")
	public String cartLess(@RequestParam("id") int goodsId, @RequestParam("num") int num) {
		
		if(hostHolder.getMember() == null) {
			return "redirect:/login";
		}
		
		cartServiceImpl.setNum(num, hostHolder.getMember().getID(), goodsId);
		
		return "redirect:cart_see";
	}
	
	@RequestMapping(value = "/cart_clear")
	@ResponseBody
	public String clearCart() {
		if(cartServiceImpl.clearCart(hostHolder.getMember().getID()) == 0) {
			return JSONStringUtil.getJSONString(1, "清空购物车失败");
		} else {
			return JSONStringUtil.getJSONString(0, "清空购物车成功");
		}
	}
}
